---
paths:
  - "**/ui/**/*.kt"
---

# Jetpack Compose Ui
> Loaded when working in any file under `ui/`. Use together with `android-skeleton-project.md` (highest priority) and `figma-design-system.md`.

---

## Always add @Preview for layout composables

When creating or modifying a fragment that uses Jetpack Compose, always add a `@Preview` composable for the layout composable so it can be previewed in Android Studio.

```kotlin
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun XxxLayoutPreview() {
    XxxLayout(
        uiState = XxxUiState(),
    )
}
```

- Preview the layout composable (e.g. `XxxLayout`), not the fragment itself.
- Use sample / default UI state with realistic data.
- Place the preview at the end of the file, after the layout composable.
- Wrap with `MaterialTheme { ... }` only if your composable uses Material primitives that require a theme (e.g. `HorizontalDivider`). Do not add it by default.

### Multiple named previews for meaningful states

When a composable has 2 or more visually distinct states, add a separate `@Preview(name = "...")` per state. Do not collapse them into one.

```kotlin
@Preview(name = "Expanded")
@Composable
private fun XxxLayoutExpandedPreview() {
    XxxLayout(isExpanded = true, uiState = XxxUiState(...))
}

@Preview(name = "Collapsed")
@Composable
private fun XxxLayoutCollapsedPreview() {
    XxxLayout(isExpanded = false, uiState = XxxUiState(...))
}
```

Common state pairs: expanded/collapsed, loading/loaded, empty/filled, enabled/disabled.

---

## val/var placement in composables

Do not declare `val`/`var` in the middle of composable content. Declare all of them at the top.

**Do not:**
```kotlin
HorizontalDivider(...)

val label = stringResource(R.string.foo)
val enabled = item != null
Row(...)
```

**Do this:**
```kotlin
val label = stringResource(R.string.foo)
val enabled = item != null

HorizontalDivider(...)
Row(...)
```

---

## When to extract a val vs. write inline

Only extract a `val`/`var` when computation or if-else is needed to decide the value. Otherwise write the value straight at its position. Applies to all composable attributes: `stringResource`, `Color`, `RoundedCornerShape`, `TextStyle`, `Boolean`, and similar.

**Do not:**
```kotlin
val label = stringResource(R.string.empty)
val color = if (isEmpty) Color.Red else Color.Blue

Text(text = label, color = color, ...)
```

**Do this:**
```kotlin
val color = if (isEmpty) Color.Red else Color.Blue  // needs if-else, so val is justified

Text(text = stringResource(R.string.empty), color = color, ...)
```

---

## Do not pre-declare colors at the top of a composable

If a color value does not need computation, write it inline at its position.

**Do not:**
```kotlin
val cardShape = RoundedCornerShape(24.dp)
val primaryBlue = Color(0xFF2F70BC)
val borderGray = Color(0xFFE6E7E9)

Card(shape = cardShape, border = BorderStroke(1.dp, borderGray))
```

**Do this:**
```kotlin
Card(shape = RoundedCornerShape(24.dp), border = BorderStroke(1.dp, Color(0xFFE6E7E9)))
```

Only keep a `val` for a color when the same hex is genuinely used in 3+ places inside the same composable.

---

## File-level private constants: numbers only

Use `private val` at file level exclusively for **numeric constants** that are reused across composables in the same file — animation durations, offsets, scale factors. Never for `Color`, `RoundedCornerShape`, or `TextStyle`.

```kotlin
// ✅ correct — reused animation timing
private val DURATION_MILLIS = 220

// ❌ wrong — pre-declaring colors/shapes at file level
private val cardShape = RoundedCornerShape(24.dp)
private val primaryBlue = Color(0xFF2F70BC)
```

---

## `by` delegation: when to use it vs. `val`

### Use `by` to auto-unwrap `State<T>`

Use `by` whenever you want the value itself, not the wrapper:

```kotlin
// animateFloatAsState / animateDp / animateColorAsState
val heightDp by transition.animateDp(...) { ... }
val animatedAngle by animateFloatAsState(targetValue = ..., label = "angle")

// collectAsState / rememberUpdatedState
val uiState by viewModel.uiState.collectAsState()
val latestValue by rememberUpdatedState(currentValue)

// mutableStateOf when you only ever READ the value
var expanded by remember { mutableStateOf(false) }
var initialized by remember { mutableStateOf(false) }
```

### Use `val` (no `by`) when you need the `.value` setter

If you need to write to the state object inside a callback or `LaunchedEffect`, keep the wrapper:

```kotlin
// ✅ keep val — we need rotationTarget.floatValue = ... inside LaunchedEffect
val rotationTarget = remember { mutableFloatStateOf(0f) }

LaunchedEffect(targetAngle) {
    rotationTarget.floatValue = currentDisplayed + delta
}
```

---

## `remember { }` — what needs it and what does not

Only use `remember` when the object must survive recomposition or is expensive to allocate.

**Always remember:**
```kotlin
// MutableInteractionSource — always
remember { MutableInteractionSource() }

// mutable state
remember { mutableStateOf(false) }
remember { mutableFloatStateOf(0f) }

// reused heavy objects (Path, Bitmap, etc.)
val drawPath = remember { Path() }
```

**Never `remember`:**
```kotlin
// modifier chains — DO NOT
val mod = remember { Modifier.fillMaxWidth().padding(16.dp) }

// updateTransition result — it already handles stability internally
val transition = updateTransition(targetState = expanded, label = "...")
// NOT: val transition = remember { updateTransition(...) }

// plain lambdas / lambda captures — DO NOT
val onClick = remember { { doSomething() } }
```

---

## Animation patterns

### Value tweens with `updateTransition`

```kotlin
private val DURATION_MILLIS = 220  // file-level constant

val transition = updateTransition(targetState = expanded, label = "XxxTransition")

val heightDp by transition.animateDp(
    transitionSpec = { tween(durationMillis = DURATION_MILLIS, easing = LinearOutSlowInEasing) },
    label = "height",
) { isExpanded -> if (isExpanded) 250.dp else 120.dp }
```

### Continuous float animation with `animateFloatAsState`

```kotlin
val animatedValue by animateFloatAsState(
    targetValue = target.floatValue,
    animationSpec = tween(durationMillis = 120),
    label = "value",
)
```

### Animation spec selection

| Use case | Spec |
|---|---|
| Size / height / corner radius | `tween(N, LinearOutSlowInEasing)` |
| Float / rotation | `tween(120)` |
| Fade in | `tween(300)` |
| Fade out | `tween(200)` |

---

## `rememberUpdatedState` — latest value inside effects

When a `LaunchedEffect` needs to read a frequently-changing value without being retriggered by it, capture it with `rememberUpdatedState`:

```kotlin
// animatedValue changes every frame — we want the newest value inside
// LaunchedEffect(target) without the effect restarting every frame.
val latestValue by rememberUpdatedState(animatedValue)

LaunchedEffect(target) {
    val current = latestValue  // always the newest frame value
    rotationTarget.floatValue = current + delta
}
```

---

## `LaunchedEffect` key discipline

The key(s) must be exactly the value(s) that should trigger re-execution — not `Unit` unless the intent is "run once on first composition":

```kotlin
LaunchedEffect(targetValue) { ... }  // reruns whenever targetValue changes ✅
LaunchedEffect(Unit) { ... }         // runs once on first composition ✅
LaunchedEffect(true) { ... }         // same as Unit — acceptable ✅
```

Do not use `LaunchedEffect(key1, key2)` unless the effect genuinely needs to rerun for BOTH changes.

---

## Block comment for non-obvious state machines

Before any composable that has a non-trivial state machine, coordinate transform, or animation invariant — add a plain block comment (not KDoc `/**`) explaining the WHY in plain words. Write as if explaining to someone new.

```kotlin
/*
 * --- Rotation smoothing (simple story) ---
 * The sensor gives us a new target angle every frame. We want smooth motion, but jumping
 * straight to the new value (e.g. 359° → 1°) would cause a full reverse spin.
 *
 * Instead, we remember a "rotation target" number and always advance it by the shortest
 * angular delta from what is currently visible on screen. animateFloatAsState then
 * smoothly tweens toward that target.
 *
 * latestDisplayed is a sticky-note helper: LaunchedEffect reads the newest drawn angle
 * without being re-triggered on every animation frame.
 */
@Composable
fun XxxRotatingComposable(...) { ... }
```

Do not add this comment to simple layout composables that just render state.

---

## Inner local functions inside drawing composables

Acceptable inside private `DrawScope` extension functions or `Canvas` lambdas when transforming coordinate spaces. Keep them one-liners:

```kotlin
Canvas(modifier = Modifier.fillMaxSize()) {
    // Translates from source coordinate space to screen pixels.
    fun sx(vx: Float) = originX + (vx - centerX) * scale
    fun sy(vy: Float) = originY + (vy - centerY) * scale

    path.moveTo(sx(0f), sy(0f))
    path.lineTo(sx(10f), sy(5f))
    ...
}
```

Do not use inner functions in regular layout composables — extract a named private composable instead.

---

## Text

By default, always add `maxLines = 1` and `modifier = Modifier.basicMarquee(Int.MAX_VALUE)` for single-line `Text`:

```kotlin
Text(
    text = ...,
    style = ...,
    maxLines = 1,
    modifier = Modifier.basicMarquee(Int.MAX_VALUE),
)
```

---

## Documentation in composables

Write KDoc short enough that a person can understand in 2 seconds after reading.

```kotlin
// ❌ too long
/**
 * When this is **true** (row 0, 2, 4…), the **left** tile is the narrow "square"...
 * When **false** (row 1, 3, 5…), we **swap**: left becomes the wide rectangle...
 * So the grid zig-zags...
 */
val squareFirst = rowIndex % 2 == 0

// ✅ correct
/** squareFirst answers: is the left position a square shape or a rectangle shape? */
val squareFirst = rowIndex % 2 == 0
```

---

---

## Decomposing complex composables

When a composable grows beyond ~60 lines or has multiple visually distinct sections, split it into smaller composables organised in sub-packages under `component/`.

### Package layout

```
ui/feature/<area>/<screen>/
├── XxxFragment.kt
├── XxxUiState.kt
├── XxxViewModel.kt
├── component/
│   ├── XxxCard.kt
│   ├── <featurename>/
│   │   ├── XxxFeature.kt            ← main composable (entry point)
│   │   ├── sub/                      ← tiny leaf composables (buttons, badges, tags)
│   │   │   ├── XxxButton.kt
│   │   │   └── XxxBadge.kt
│   │   └── subcomponent/             ← medium composables with their own layout logic
│   │       ├── XxxHeader.kt
│   │       └── XxxTimeline.kt
└── model/                            ← screen-local enums and data classes (not domain)
    ├── XxxPopup.kt
    └── XxxItemState.kt
```

### sub/ vs subcomponent/

| Folder | Use for |
|---|---|
| `sub/` | Tiny, single-purpose leaf composables — one button, one badge, one icon+label tile |
| `subcomponent/` | Medium composables that own a layout section with multiple children |

Example — a horizontal icon strip:
- `XxxFeatureRow.kt` — `LazyRow` container, delegates each tile to `XxxFeatureButton`
- `sub/XxxFeatureButton.kt` — one icon+label tile
- `sub/XxxAnimatedBadge.kt` — optional Lottie badge overlaid on a button

Example — a collapsible header with two states:
- `XxxTopBarAdvance.kt` — outer shell, toggles between expanded and collapsed card
- `XxxCardExpanded.kt` / `XxxCardCollapsed.kt` — two state variants of the inner card
- `subcomponent/XxxTimeline.kt` — a row of nodes + connectors
- `subcomponent/XxxTimelineNode.kt` — a single node (Done / Upcoming / Rest)
- `subcomponent/XxxConnector.kt` — the line drawn between two nodes

### model/ — screen-local enums and data classes

Put in `model/` any type that exists only to support the screen's UI logic and has no place in `domain/`:

```kotlin
// Mutual-exclusion enum for overlays on this screen
enum class XxxPopup { Loading, PermissionRequest, RateSheet }

// Display state of a single list tile
enum class XxxItemState { Done, Upcoming, Rest }

// Maps a domain type + a value accessor to one rendered row
data class XxxRowItem(
    val type: XxxType,
    val valueProvider: (XxxData) -> String,
)
```

Never put domain models here — types needed by the repository or use-case layer belong in `domain/model/`.

---

## Mutually exclusive overlay enum

When a screen can show multiple dialogs or bottom sheets (loading, permission, rate…), model them as an enum so only one shows at a time.

```kotlin
// model/XxxPopup.kt
enum class XxxPopup { Loading, PermissionRequest, RateSheet }
```

```kotlin
// In ComposeView():
val currentPopup = when {
    uiState.isLoading        -> XxxPopup.Loading
    shouldShowPermission     -> XxxPopup.PermissionRequest
    uiState.showRateSheet    -> XxxPopup.RateSheet
    else                     -> null
}

XxxLayout(uiState = uiState, ...)

XxxLoadingDialog(enabled = currentPopup == XxxPopup.Loading)
XxxPermissionSheet(visible = currentPopup == XxxPopup.PermissionRequest, ...)
XxxRateSheet(enabled = currentPopup == XxxPopup.RateSheet, ...)
```

- The `when` block lives in `ComposeView()`, never inside `XxxLayout`.
- Each overlay receives a single `Boolean`. It returns early when false.
- Priority order in the `when` matters — higher entries win.
- Add new enum entries before adding new overlays on the screen.

---

## Manual spacing wrapper for animated LazyColumn items

`Arrangement.spacedBy(N.dp)` breaks visual spacing when an item contains an animated composable (Lottie, `animateContentSize`, `AnimatedContent`). The animation clips or overlaps the gap.

Fix: set `verticalArrangement = Arrangement.spacedBy(0.dp)` on the `LazyColumn`, then wrap each animated item in a composable that manually prepends a `Spacer`:

```kotlin
// component/XxxItemSpacer.kt
@Composable
fun XxxItemSpacer(content: @Composable () -> Unit = {}) {
    Column(modifier = Modifier) {
        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
        content()
    }
}

// In the LazyColumn:
item(key = "AnimatedCard") {
    XxxItemSpacer {
        XxxAnimatedCard(modifier = Modifier.fillMaxWidth())
    }
}
```

Use this for any `item` whose content contains Lottie, `animateContentSize`, or `AnimatedContent`. Non-animated items at the top (e.g. `stickyHeader`) do not need it.

---

## Sticky header + NestedScrollConnection for collapsible top bars

```kotlin
var headerExpanded by remember { mutableStateOf(true) }
val listStateRef = rememberUpdatedState(newValue = listState)
val nestedScrollConnection = remember {
    object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (source != NestedScrollSource.UserInput) return Offset.Zero
            val st = listStateRef.value
            if (available.y < 0f) headerExpanded = false
            else if (available.y > 0f && !st.canScrollBackward) headerExpanded = true
            return Offset.Zero  // consume nothing — let the list scroll normally
        }
    }
}

LazyColumn(
    state = listState,
    modifier = Modifier.nestedScroll(connection = nestedScrollConnection),
    verticalArrangement = Arrangement.spacedBy(0.dp),
) {
    stickyHeader(key = "XxxHeader") {
        XxxHeaderAdvance(expanded = headerExpanded, ...)
    }
    item(key = "XxxContent") { ... }
}
```

- Use `stickyHeader` (not `item`) so the bar stays pinned while content scrolls underneath.
- `rememberUpdatedState(listState)` ensures the connection lambda always reads the current `LazyListState` without being recreated.
- The composable inside `stickyHeader` receives `expanded: Boolean` and handles its own collapse animation internally (e.g. `updateTransition` + `animateDp`).

---

## Self-contained bottom sheet composable

A bottom sheet should own its own display-value derivation. The caller (Fragment) passes domain objects; the sheet computes what to show internally. This keeps the Fragment a thin wiring layer and makes the sheet previewable with only domain data.

### Structure

```
fun XxxSheet(
    enable: Boolean = true,           // forwarded to your bottom sheet wrapper
    domainObject: XxxModel? = null,   // domain type, not pre-formatted String
    selectedDate: LocalDate = LocalDate.now(),
    // ... state params (selection, checkboxes, etc.)
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    // 1. Derive display strings at the top
    val context = LocalContext.current
    val title = stringResource(domainObject?.titleRes ?: R.string.fallback)
    val displayTime = domainObject?.let { ... } ?: ""
    val displayDate = selectedDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
    val subtitle = buildString { ... }.takeIf { it.isNotEmpty() }

    // 2. Local functions for enum ↔ index mapping (no if-else in the UI tree)
    fun selectionIndex() = if (selection == XxxOption.A) 0 else 1
    fun indexToSelection(i: Int) = if (i == 0) XxxOption.A else XxxOption.B

    // 3. Single bottom sheet wrapper call — no raw ModalBottomSheet
    XxxBottomSheet(enable = enable, onDismissRequest = onDismiss, ...) {
        Column(...) {
            // pure UI — no val declarations, no if-else logic
            XxxSegmentedControl(
                selectedIndex = selectionIndex(),
                onSelect = { onSelectionChange(indexToSelection(it)) },
                ...
            )
        }
    }
}
```

### Rules

- **Domain in, display out.** Accept domain types (`XxxType`, `LocalDate`, `XxxModel?`) — never accept pre-formatted `String` params for things the sheet can derive itself.
- **All `val` declarations at the top** — none inside the bottom sheet wrapper or `Column` content.
- **All business logic as named local `fun`** — enum↔index mapping, state derivation, condition checks. The UI tree contains zero inline `if-else`.
- **`enable: Boolean`** — always forward to the bottom sheet wrapper. Never guard with `if (enable) return`. The caller decides when to show; the composable decides how.
- **Use your project's bottom sheet wrapper** — avoid raw `ModalBottomSheet` where a wrapper exists. A good wrapper centralises nav-bar insets, `dragHandle = null`, and the hide animation.
- **`@file:OptIn`** at the top of the file when any Material3 experimental API (e.g. `ripple`) is used across multiple private composables — avoids scattering `@OptIn` on every function.

### Caller pattern (Fragment)

The Fragment passes `uiState` fields directly — no computation, no formatting:

```kotlin
// ✅ correct — Fragment is a thin wiring layer
XxxSheet(
    enable = uiState.sheetTarget != null,
    domainObject = uiState.sheetTarget ?: XxxModel.default(),
    selectedDate = uiState.selectedDate,
    selection = uiState.sheetSelection,
    onDismiss = { viewModel.dismissSheet() },
    onConfirm = { viewModel.confirmSheet() },
)

// ❌ wrong — caller computing display strings that belong in the sheet
val title = stringResource(uiState.sheetTarget?.titleRes ?: R.string.fallback)
val timeStr = uiState.sheetTarget?.time?.formatTimeShort(is24Hour(context)) ?: ""
XxxSheet(title = title, timeStr = timeStr, ...)
```

---
### Never hide lambda function when the last argument is a composable function

- For example, we have a composable function
  @Composable
  fun xyzName(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
  ) {}

- When use xyzName composable names, do not write
  xyzName {
  if (condition) {
  // do something A
  } else {
  // do something B
  }
  }

- Instead, you must write
  xyzName(
  context = {
  if (condition) {
  // do something A
  } else {
  // do something B
  }
  )

## @author Phong-Kaster
