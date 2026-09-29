# Android Compose Skeleton — Project Rules
> Always-on. Highest priority. If another rule conflicts with this one, follow this file and mention the conflict in your summary.

---

## Purpose

This is a **reusable skeleton app**. Code and structure should stay easy to copy into new projects. Prefer clear layers and config over one-off hacks.

## Spirit of the project

- Always write code that can be easier expandable, respect Android official document recommendation.
- Always write code that can be easier recycled from a project to other projects.
- Prefer write code that is easier to understand for human than write code that has high performance.

---

## Documentation

- Always add document and write example for class/object/interface
- Add documents for complex functions. Opposite, simple function does not need to add document
- Always add `@author Phong-Kaster` at the end of the KDoc:
  ```kotlin
  /**
   * The purpose of this class is ....
   * @param A do something
   * @param B do something
   * @author Phong-Kaster
   */
  ```
- Write document or inline comment like you are explaining to a kid: "I am just a kid & I need to understand your code."
- Always add `README.md` to explain overall the purpose of the app & what features the app does.
- Always add a project package tree in `README.md` (see **README & package tree** section below).

---

## Naming & Style

- Repository interfaces: `XxxRepository`. Implementations: `XxxRepositoryImpl` in `data/repository/impl/`.
  - Example: interface `SettingRepository` in `domain/repository/` → impl `SettingRepositoryImpl` in `data/repository/impl/`.

- API services: `XxxApi` in `data/remote/api/`. DTOs: `XxxDto` in `data/remote/dto/`.
  - Example: `PostApi` in `data/remote/api/PostApi`, DTO classes in `data/remote/dto/post/…`.

- Shared utilities/extensions → `common/` (e.g. `common.extensions`). UI-only helpers → `ui/util`.
  - Example: date/time extensions → `DateAndTimeExtension` in `common/extensions/`.
  - If complicated: `common/extension/date_and_time/` with `BasicDateAndTimeExtension` and `AdvancedDateAndTimeExtension`.

- Do not hide lambda functions. Always write the full lambda:
  ```kotlin
  val prevActive = itemStates.getOrElse(index = index - 1, defaultValue = { XxxItemState.Inactive }) == XxxItemState.Active
  val thisActive = itemStates.getOrElse(index = index, defaultValue = { XxxItemState.Inactive }) == XxxItemState.Active
  ```

- Do not use `MutableStateFlow.update { … }` or `_uiState.update { it.copy(…) }` for screen UI state. Always use:
  ```kotlin
  _uiState.value = _uiState.value.copy(...)
  ```

- If the codebase does not use Activity or Fragment, skip any rules related to Activity or Fragment.

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material3
- **DI:** Koin or Hilt
- **Navigation:** Navigation Component (Fragment-based, Compose inside fragments)
- **Network:** Ktor client or Retrofit
- **Local:** Room, DataStore
- **Config:** `core.config.AppConfig` (API base URL, timeouts); `common.Constant` (app URLs, datastore name)
- Leverage use-cases when an operation spans two or more repositories, or when the same logic is reused across multiple ViewModels. See `usecase-layer.md` for anatomy, naming, patterns, and DI wiring.

---

## Clean Architecture — Package Layout

- **domain/** — `model/`, `repository/` (interfaces only). No Android or data dependencies.
- **data/** — `remote/api/` (ApiPath, *Api classes), `remote/dto/`, `remote/util/` (e.g. `safeApiCallFlow`), `mapper/`, `repository/impl/`.
- **core/** — `config/` (AppConfig), base classes (`CoreActivity`, `CoreFragment`, `CoreLayout`). These are skeleton-provided — do not recreate them. `CoreFragment` owns the `ComposeView()` entry point; `CoreLayout` wraps `Scaffold` with edge-to-edge insets; `safeNavigate` is a `NavigationUtil` helper on `CoreFragment`.
- **common/** — Shared types and constants: `Outcome<T>` (custom sealed class below), `Constant`, `Language`, extensions (e.g. validation).

```kotlin
// common/Outcome.kt
sealed class Outcome<out T> {
    data object Loading : Outcome<Nothing>()
    data class Success<T>(val data: T) : Outcome<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Outcome<Nothing>()
}
```

- **ui/** — Fragments, ViewModels, components, theme, util (e.g. `UiErrorMapper`). `customizedTextStyle(fontSize, fontWeight, lineHeight, color)` lives in `ui/theme/Type.kt` — use it for every `Text`; never use `MaterialTheme.typography`.
- **injection/** — Koin modules (`appModule` includes database, datastore, repository, viewModel, network, locale).

---

## API & Data Conventions

- Put endpoint paths in `data/remote/api/ApiPath.kt` (use `AppConfig` for base URLs).
- New API: add `*Api` in `data/remote/api/`, DTO in `data/remote/dto/`, mapper in `data/mapper/`, repository interface in `domain/repository/`, impl in `data/repository/impl/`, bind in `NetworkModule` + `RepositoryModule`.
- Wrap API calls in `safeApiCallFlow`; it returns `Flow<Outcome<T>>` where `Outcome` is the project's custom `common.Outcome<T>` sealed class (Loading / Success / Error) — **not** Kotlin stdlib `kotlin.Result` (they are different types with different semantics). Map errors in UI with `Throwable.toUiMessage(context)` (`UiErrorMapper`).

---

## Create new fragment

- Only applies when the project uses Activity and Fragment.
- When asked to create a new fragment, create: `XxxFragment`, `XxxViewModel`, `XxxUiState`.

---

## Add new string content in strings.xml

- Always append new content to the end of `strings.xml`.

---

## README & package tree

- `README.md` must describe app purpose, features, tech stack, and include a **package tree** for `app/src/main/java/com/example/skeleton/` (or the current app package).
- **When to update README** in the same change (do not defer):
  - You add, remove, or rename a top-level or meaningful subpackage under the app source root.
  - You change architecture or layer responsibilities described in README.
  - You edit this rule file in a way that changes what README is supposed to contain — sync README in that same task.
- For each major folder in the tree, add a short note on the purpose of that package, not only file names.

### Tree drawing order

Under any folder, list **subfolders first** (deepest paths expanded), **then** `.kt` files at that folder level.

**Correct:**
```
ui/fragment/xxx/
├── component/
│   ├── XxxCard.kt
│   └── XxxHeader.kt
├── XxxFragment.kt
├── XxxUiState.kt
└── XxxViewModel.kt
```

**Avoid:** listing `XxxFragment.kt` before `component/` when `component/` is a child of `xxx/`.

---

## TLS / Networking

- This app may use `BuildConfig`-based debug vs release base URLs and `network_security_config.xml`. Do not suggest disabling TLS verification globally for production.
- For `CertPathValidatorException`, consider build type, `network_security_config`, and environment (proxy, VPN, corporate inspection) before changing code.

---

## Agent checklist (before finishing a structural task)

- [ ] If packages or layers changed: README package tree and package-purpose notes are updated.
- [ ] If this rule file changed: README still matches new documentation rules.
- [ ] New remote feature: ApiPath, DTO, mapper, repository + NetworkModule / RepositoryModule (and ViewModelModule if needed).
- [ ] ViewModels: screen state uses `_uiState.value = _uiState.value.copy(...)` only.

---

## String content

- Do not set a name for a word because itself has meaning
  Do not <string name="my_creations_download">Download</string> instead of
  do <string name="download">Download</string>
  Do not <string name="my_creations_download_success">Image saved to your gallery</string> instead
  of do <string name="image_saved_to_gallery">Image saved to your gallery</string>

---

## If-else statement

- When only one condition, do not use if-else statement, just use if statement
  Do not
```kotlin
  bottomBar = {
        if (!isError) {
            // do something
        }
    }
```

Instead, do it

```kotlin
  bottomBar = {
    if (isError) return@ResultActionRow
    // do something
}
```


- When user if-else condition, write if statement with straight forward logic, do not use negative condition
  Do not
```kotlin
  if (!isError) {
    // do something B
  } else {
    // do something A
  }
```

Instead, do it
```kotlin
  if (isError) {
    // do something A
  } else {
    // do something B
  }
```

## Kotlin Lambda Argument Naming Rule

When calling functions that accept callback lambda parameters, always use named arguments for lambda parameters.

Do not use Kotlin trailing lambda syntax when the lambda parameter has a meaningful name (for example: `onNextAction`, `onSuccess`, `onError`, `onClick`, `onBack`, `onNavigate`).

Always write the lambda parameter explicitly.

Bad:
```kotlin
AdsUtil.displayInterstitialBeforeGoingBack(activity = requireActivity()) {
    safeNavigateUp()
}
```
Good:
```kotlin
AdsUtil.displayInterstitialBeforeGoingBack(
    activity = requireActivity(),
    onNextAction = {
        safeNavigateUp()
    }
)
```
Apply this rule especially for:

Navigation callbacks
UI event callbacks
Async completion callbacks
Listener callbacks
Any lambda parameter with an onXxx naming convention

The goal is to improve readability by making the callback purpose explicit at the call site.

---

## The 8 Golden Rules of Writing Clean Functions - Clean Code In Kotlin

While writing Kotlin, i need you follow these golden rules but apply them flexible. If functions are too small, write them as possible as simple
1. Single Responsibility Principle (SRP)
2. Avoid Side Effects
3. Do Not Repeat Yourself (DRY)
4. Minimize Parameters (Group into Classes)
5. Fail-Fast Principle
6. Guard Clauses
7. Single Level of Abstraction Principle (SLAP)
8. Explicit Receivers

## @author Phong-Kaster
