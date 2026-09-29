---
paths:
  - "**/ui/**/*.kt"
---

# Figma → Compose
> Loaded when working in any file under `ui/`. Use together with `android-skeleton-project.md` (highest priority) and `jetpack-compose-ui.md`.
>
> This file defines how screens are built in this project. Generated Compose code MUST blend into the existing codebase — same shape, same readability, same trade-offs.
>
> Rule of thumb: if a new file does not look like a sibling of the existing fragments after a glance, it is wrong — fix the shape before adding behavior.
>
> **Skeleton-provided primitives** (do not recreate, just use):
> - `CoreFragment` — base fragment with `ComposeView()` entry point and `safeNavigate(...)`.
> - `CoreLayout` — opinionated `Scaffold` wrapper with edge-to-edge insets. Use instead of raw `Scaffold`.
> - `CoreTopBar` / `CoreBottomBar` — pre-styled top/bottom bars with dynamic status-bar padding.
> - `customizedTextStyle(fontSize, fontWeight, lineHeight, color)` — house text style helper in `ui/theme/Type.kt`. Use instead of `MaterialTheme.typography`.

---

## 1. Screen spine: Fragment is thin, `XxxLayout` is pure UI

Every screen has **exactly two composable layers**:

- `XxxFragment : CoreFragment()` — owns the ViewModel, lifecycle, navigation, permissions.
- `private fun XxxLayout(uiState, lambdas...)` — pure UI. No Koin, no `LocalNavController`, no `safeNavigate`. Just renders state and forwards events.

`ComposeView()` inside the fragment is the wiring spot — it collects state, picks the side-effect popups, and calls the Layout once:

```kotlin
@Composable
override fun ComposeView() {
    super.ComposeView()
    val uiState by viewModel.uiState.collectAsState()

    XxxLayout(
        uiState = uiState,
        onOpenSetting = { safeNavigate(R.id.toSetting) },
        onItemSelected = { item -> viewModel.selectItem(item) },
    )

    // Overlays / bottom sheets / permission requests live HERE, after the Layout call.
    XxxPermissionRequest(...)
    XxxBottomSheet(...)
    XxxLoadingDialog(enabled = ...)
}
```

Do not put `safeNavigate`, `Toast`, or `requireContext()` inside `XxxLayout`. The Layout must be previewable with `XxxLayout(uiState = XxxUiState(...))` — nothing else.

**Lambda defaults are mandatory.** Every callback in `XxxLayout(...)` ends with `= {}` so previews work with zero boilerplate:

```kotlin
@Composable
private fun XxxLayout(
    uiState: XxxUiState,
    onOpenSetting: () -> Unit = {},
    onItemSelected: (XxxModel) -> Unit = {},
    onOpenDetail: () -> Unit = {},
)
```

---

## 2. Scaffolding: `CoreLayout` + bars

Use `CoreLayout` (not raw `Scaffold`) for every screen. Anything that must stay visible while the user scrolls belongs in `topBar` or `bottomBar` — **never** sticky-positioned inside `content`.

```kotlin
CoreLayout(
    modifier = Modifier.background(color = Color.White),
    topBar = { CoreTopBar(title = stringResource(R.string.setting), onClickLeft = onBack, leftIcon = R.drawable.ic_back_2) },
    bottomBar = { CoreBottomBar() },
    content = { /* scrollable area */ },
)
```

Notes:

- `CoreTopBar` already handles `dynamicStatusBarPadding()`. Do not wrap it in another `Spacer` for status bar.
- Top-level screens (main navigation tabs) use `CoreBottomBar()` directly; secondary screens typically omit it.
- Loading state at screen level → `CoreLayout(showLoading = true, ...)`. Custom overlays (e.g. a Lottie loading dialog) live in the fragment, not inside `CoreLayout`.
- For a screen with an image background, wrap `CoreLayout` in a `Box` and paint the background behind it.

---

## 3. Typography: `customizedTextStyle` is the house style

`MaterialTheme.typography` is **not used**. Every `Text` style uses `customizedTextStyle(...)`:

```kotlin
Text(
    text = stringResource(R.string.section_title),
    style = customizedTextStyle(
        fontSize = 16,
        fontWeight = 600,
        lineHeight = 22,
        color = ColorTextSecond,
    ),
)
```

- `fontSize`, `fontWeight`, `lineHeight` are `Int` (the helper converts to `sp` and `FontWeight(...)`).
- Default `fontWeight`s: `400` (body), `500` (emphasis), `600` (titles), `700` (section heads).
- Pass `lineHeight` explicitly when Figma specifies one. If skipped, the helper auto-computes `fontSize * 1.5`.
- For single-line labels add both `maxLines = 1` and `modifier = Modifier.basicMarquee(Int.MAX_VALUE)`.

Do not introduce `TextStyle(fontFamily = ..., ...)` manually unless you have a very specific reason.

---

## 4. Colors: two systems, used on purpose

The repo intentionally mixes two color sources. Don't try to "clean this up" — both exist for a reason.

**(a) Named semantic tokens** in `ui/theme/Color.kt`:

> **New project:** Replace the hex values below with your brand palette. Keep the semantic names — they are referenced across rule files and composables.

| Token | Meaning | Skeleton default |
|---|---|---|
| `ColorTextPrimary` | Body / primary text on light surfaces | #091222 |
| `ColorTextSecond` | Brand blue used in headers, icons-on-light | #2F70BC |
| `ColorBorderSubtle` | Card borders and dividers | #E6E7E9 |
| `ColorIconMuted` | Muted chevrons / secondary icons | #9CA3AF |

**(b) Inline `Color(0xFF...)`** for one-off literals from a specific Figma node:

```kotlin
.background(color = Color(0xFF091222))
tint = Color(0xFF2F70BC)
```

Rule of thumb: **use a token if the color appears in 3+ places**. Otherwise inline the hex. Pre-declaring every color as a `val` at the top of a composable is explicitly discouraged.

Do not add semantic tokens for one-off brand asset colors (e.g. a golden gradient in a paywall illustration). Keep those inline.

---

## 5. The card / surface recipe

Cards in this app are **not** Material `Card`. They are a `Column` with a deliberate modifier stack:

```kotlin
Column(
    modifier = modifier
        .fillMaxWidth()
        .border(
            width = 1.dp,
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFE6E7E9),
        )
        .dropShadow(
            shape = RoundedCornerShape(24.dp),
            shadow = Shadow(
                radius = 30.dp,
                spread = 0.dp,
                color = Color.Black.copy(alpha = 0.06f),
                offset = DpOffset(x = 0.dp, y = 8.dp),
            ),
        )
        .background(
            color = Color.White,
            shape = RoundedCornerShape(24.dp),
        ),
) {
    // header Row, body, HorizontalDivider, footer Row
}
```

Rules of the shape:

- Order is always `fillMaxWidth → border → dropShadow → background`. Shadow comes before background so it doesn't paint over the surface.
- The same `RoundedCornerShape(24.dp)` is passed to all three — duplicate it inline, don't extract a `val cardShape`.
- Inner content `Column` uses `padding(16.dp)` + `verticalArrangement = Arrangement.spacedBy(12.dp)`.
- Within the body, lists of fields use `Arrangement.spacedBy(8.dp)`.
- A `HorizontalDivider(thickness = 1.dp, color = ColorBorderSubtle)` separates content from the action row.
- Cards never get `elevation` from Material — we draw our own shadow.

---

## 6. Modifier ordering (sticky convention)

1. **Size / fill** — `fillMaxWidth()`, `size(...)`, `aspectRatio(...)`, `weight(...)`
2. **Shape clipping** — `clip(RoundedCornerShape(...))`
3. **Border / shadow** — `border(...)`, `dropShadow(...)`
4. **Background / paint** — `background(...)`, `paint(...)`
5. **Click / focus** — `clickable(...)`
6. **Padding** (inside-the-click) — `padding(...)`
7. **Custom drawing / scroll** — `nestedScroll(...)`, `basicMarquee(...)`

---

## 7. Clickable: ripple + accessibility label

Most clicks go through the full call so screen-reader labels and ripple shape are explicit:

```kotlin
.clickable(
    onClickLabel = stringResource(R.string.action_label),
    interactionSource = remember { MutableInteractionSource() },
    indication = ripple(bounded = true),
    onClick = onOpenDetail,
)
```

When a child inside a clickable container needs its own click, use `indication = null` on the child to avoid two overlapping ripples. The short `Modifier.clickable { onClick() }` is only acceptable for icon-only square tiles where there's no semantic label.

---

## 8. Spacing and padding

- **Screen horizontal padding**: `16.dp` (applied per-item inside `LazyColumn`, not on the column itself).
- **Card inner padding**: `16.dp`.
- **Vertical rhythm inside a card**: `12.dp` between major sections, `8.dp` between tight related lines.
- **Vertical rhythm between list items**: `Arrangement.spacedBy(20.dp)` for home-feed style, `16.dp` for tighter lists.
- **Horizontal item spacing in rows**: `8.dp` for icon+text, `12.dp` for tiles side-by-side, `16.dp` for wider icon rows.
- Prefer `Arrangement.spacedBy(N.dp)` over manual `Spacer(Modifier.height(N.dp))` inside Columns/Rows.

---

## 9. Resources: strings, drawables, icons

- Never hardcode user-visible strings. Add to `app/src/main/res/values/strings.xml` (append to end) and use `stringResource(R.string.x)` inline in `Text(text = ...)`.
- Extract a `val` for a `stringResource` **only** when it's read inside a non-composable lambda (e.g. `shareChooserTitle` inside a share `onClick`).
- Drawable naming: `ic_<name>` for icons, `img_<name>` for raster/illustration assets.
- Lottie raw files live in `res/raw/animation_<feature>_<purpose>.json`.

---

## 10. State & null handling

- Screen state: a single `data class XxxUiState(...)` collected with `viewModel.uiState.collectAsState()`. Pass the whole thing down — don't destructure into N parameters at the screen level.
- Fragment-local UI state (popup flags, permission triggers) uses `mutableStateOf` / `mutableIntStateOf` on the Fragment itself.
- For nullable card data ("of the day" pattern), capture with `rememberUpdatedState`, then branch:

```kotlin
val latestItem by rememberUpdatedState(item)
val shareEnabled = latestItem != null
val shareColor = if (shareEnabled) ColorTextPrimary else ColorTextPrimary.copy(alpha = 0.38f)

if (latestItem == null) {
    Text(text = stringResource(R.string.empty_state_message), ...)
} else {
    val detail = latestItem!!  // safe inside the else after rememberUpdatedState
}
```

`!!` after a null check inside the same composable scope is the project's accepted shorthand. Don't over-engineer with `?.let { }` chains that hurt readability.

---

## 11. Lists

- `LazyColumn` items each get a descriptive `key = "Name"` — use readable names like `"XxxTopBar"`, `"XxxItemCard"`, not `"item1"`. Future debugging relies on these names.
- `verticalArrangement = Arrangement.spacedBy(20.dp)` at the LazyColumn level handles inter-item gaps; each item then applies its own `horizontal = 16.dp` padding.
- `LazyRow` for horizontal icon strips — same `item(key = "...")` pattern.

---

## 12. Side-effects, dialogs, sheets

Overlays are NOT children of `XxxLayout`. They live at the same level in `ComposeView()`:

```kotlin
XxxLayout(uiState = ..., onAction = ...)

XxxPermissionRequest(visible = ..., ...)
XxxLoadingDialog(enabled = ...)
XxxBottomSheet(enabled = ..., onDismiss = ..., onSubmit = ...)
```

This keeps `XxxLayout` previewable with no fake permission state. Use an `ActionPopup` enum inside the Fragment when overlays are mutually exclusive so only one shows at a time.

---

## 13. Navigation (fragment-side only)

Inside the lambdas passed to `XxxLayout`, the Fragment handles navigation:

```kotlin
onOpenSomething = {
    safeNavigate(R.id.toSomething)
},
```

- Always `safeNavigate(...)` from `NavigationUtil` — never raw `findNavController().navigate(...)`.
- For navigation with args, build a `XxxFragmentDirections.toY(...)` and pass it to `safeNavigate`.

`XxxLayout` itself NEVER imports `safeNavigate` or `findNavController`.

---

## 14. Animation — small, intentional, commented

- `updateTransition` + `animateDp` for value tweens.
- `AnimatedContent` for swap-between-two-states (collapsed/expanded card).
- `animateContentSize` on the outer Box when content height changes.
- Animation specs: `tween(220, LinearOutSlowInEasing)` for size, `tween(300)` / `tween(200)` for fade.
- Always add a brief inline comment when the trigger is non-obvious.

If a Figma frame doesn't ask for motion, don't add motion. Static is fine.

---

## 15. Where files live

```
ui/fragment/<area>/<screen>/
├── XxxFragment.kt              // thin fragment, owns VM and side effects
├── XxxViewModel.kt
├── XxxUiState.kt
├── component/                  // screen-local composables
│   ├── XxxCard.kt
│   ├── XxxHeader.kt
│   └── sub/                    // tiny leaf composables of a component
│       └── XxxButton.kt
└── model/                      // screen-local helpers (sharing text builders, enums)
```

- Screen-local composables → `component/<feature>/...`. Use `sub/` only when a component has small leaf children that wouldn't make sense outside of it.
- Shared widgets (used by 2+ unrelated screens) → `ui/component/`.
- New theme constants → `ui/theme/Color.kt`. New text helpers → `ui/theme/Type.kt`.

---

## 16. Figma MCP workflow

When receiving an order to build UI/UX from Figma — take a moment, think like a human:

- How many composables should I create to build this UI?
- Which part is the top bar? Which part is the bottom bar? Which part is content?
- What component should stay at the same position while the user scrolls?
  - Example: Submit button → put in `bottomBar` block to avoid scrolling.
  - Example: Top bar with back button and title → put in `topBar` block.

```kotlin
@Composable
private fun XxxLayout() {
    CoreLayout(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            XxxBottomBar(
                action1 = {},
                action2 = {},
                action3 = {},
            )
        },
        topBar = {
            XxxTopBar(
                action1 = {},
                action2 = {},
                action3 = {},
            )
        },
        content = {
            // other composable
        },
    )
}
```

Figma MCP steps:

- **Auth check** — `whoami` confirms the signed-in Figma user.
- **Implement a node** — `get_design_context(fileKey, nodeId)` (replace `-` with `:` in the node id from the URL). Treat the returned React+Tailwind as reference only — re-express in Compose using the patterns above. Drop `position: absolute`, `display: flex`, raw pixel offsets — replace with `Column`/`Row`, `Arrangement.spacedBy`, `Modifier.weight`, intrinsic sizing.
- **Pixel-check** — `get_screenshot` or `get_metadata` when hierarchy or spacing is ambiguous.
- **Component search first** — before writing a new composable, check `ui/component/` and `ui/fragment/<sibling>/component/` for something close. Reuse beats rebuild.

---

## Quick DO / DON'T

DO:

- Split every screen into `XxxFragment` + `private fun XxxLayout(uiState, lambdas...)`.
- Use `customizedTextStyle(fontSize = 14, fontWeight = 400, lineHeight = 20, color = ColorTextPrimary)`.
- Build cards as `Column.fillMaxWidth().border(...).dropShadow(...).background(...)`.
- Inline `Color(0xFF...)`, `RoundedCornerShape(24.dp)`, `16.dp` when they're literals from a node.
- Pass `onClickLabel = stringResource(...)` and explicit `interactionSource` to every meaningful click.
- Add `maxLines = 1` + `basicMarquee(Int.MAX_VALUE)` to single-line labels.
- Give every `LazyColumn`/`LazyRow` item a readable `key = "..."`.
- Place `safeNavigate` in the Fragment lambdas, never inside `XxxLayout`.
- Keep overlays as siblings of the Layout call inside `ComposeView()`.
- Add `@author Phong-Kaster` on KDoc for non-trivial composables.

DON'T:

- Use Material `Card { ... }` — write the manual shell.
- Pre-declare `val cardShape`, `val borderColor`, `val primaryBlue` at the top of every composable.
- Use `MaterialTheme.typography.bodyMedium` — call `customizedTextStyle(...)` instead.
- Put `Toast`, `requireContext()`, or `safeNavigate` inside `XxxLayout`.
- Make `XxxLayout` lambdas required — every callback gets a `= {}` default.
- Sticky-position UI inside scrollable `content` — that's what `topBar` / `bottomBar` are for.
- Wrap every modifier in `remember { ... }` — only `MutableInteractionSource` needs `remember`.
- Delete commented-out blocks during unrelated edits; the engineer keeps them on purpose for context.

---

## @author Phong-Kaster
