---
paths:
  - "**/*ViewModel.kt"
  - "**/*UiState.kt"
  - "**/injection/ViewModelModule.kt"
---

# ViewModel + UiState Layer
> Loaded when working on *ViewModel.kt, *UiState.kt or ViewModelModule.kt. Portable across projects. If it conflicts with `android-skeleton-project.md` (highest priority), follow that file and mention the conflict.

---

## When these rules apply

Any time you write or edit:

- `XxxViewModel.kt` files anywhere under `ui/`.
- `XxxUiState.kt` files anywhere under `ui/`.
- `viewModelModule` in `injection/`.

---

## ViewModel anatomy

```kotlin
class XxxViewModel(
    private val xxxRepository: XxxRepository,
    private val yyyRepository: YyyRepository,
) : ViewModel() {

    private val TAG = "XxxViewModel"

    private val _uiState = MutableStateFlow(XxxUiState())
    val uiState: StateFlow<XxxUiState> = _uiState.asStateFlow()

    init {
        collectXxx()
        loadYyy()
        observeZzz()
    }

    private fun collectXxx() {
        viewModelScope.launch {
            xxxRepository.xxxFlow.collectLatest { value ->
                _uiState.value = _uiState.value.copy(xxx = value)
            }
        }
    }

    private fun loadYyy() {
        viewModelScope.launch {
            val result = yyyRepository.getYyy()
            _uiState.value = _uiState.value.copy(yyy = result)
        }
    }

    fun setXxx(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            xxxRepository.setXxx(value)
        }
    }
}
```

Conventions:

- `TAG` is `private val TAG = "XxxViewModel"` — instance property, **not** a companion object. This is different from the repository convention.
- `_uiState` and `uiState` are declared immediately after `TAG`, before everything else.
- `init { }` follows the state declarations. It calls only named private functions — never inline `viewModelScope.launch` directly inside `init`.
- All functions called from `init` are `private`. Public functions are only for the Fragment to call.

---

## Function naming conventions

| Pattern | When to use |
|---|---|
| `private fun collectXxx()` | Ongoing Flow collection via `.collectLatest` or `.collect` |
| `private fun loadXxx()` / `private fun fetchXxx()` | One-shot suspend reads from repository |
| `private fun observeXxx()` | Same as `collectXxx` — prefer `collectXxx` for new code |
| public `fun doSomething()` | User-triggered actions forwarded from the Fragment (no prefix) |

---

## Coroutine patterns

### A. Ongoing Flow collector (most common)

```kotlin
private fun collectXxx() {
    viewModelScope.launch {
        xxxRepository.selectedItemFlow.collectLatest { item ->
            _uiState.value = _uiState.value.copy(selectedItem = item)
        }
    }
}
```

Use `.collectLatest` when only the latest value matters (UI state). Use `.collect` when every emission must be processed (e.g. history lists).

### B. Combining multiple Flows into one state update

```kotlin
private fun collectXxxAndYyy() {
    viewModelScope.launch {
        combine(
            xxxRepository.xxxFlow,
            yyyRepository.yyyFlow,
        ) { xxx, yyy ->
            xxx to yyy
        }.collectLatest { (xxx, yyy) ->
            _uiState.value = _uiState.value.copy(
                xxx = xxx,
                yyy = yyy,
            )
        }
    }
}
```

### C. One-shot read (no loading state)

```kotlin
private fun loadCurrentAddress() {
    viewModelScope.launch {
        val address = locationRepository.getCurrentAddress()
        _uiState.value = _uiState.value.copy(currentAddress = address)
    }
}
```

### D. Write operation — always explicit IO dispatcher

```kotlin
fun increaseLocationPermissionDenial() {
    viewModelScope.launch(Dispatchers.IO) {
        settingRepository.incrementLocationPermissionDenial()
    }
}
```

Write to DataStore or Room with `Dispatchers.IO`. Reads do not need it — the repository handles dispatcher selection.

### E. Load with `isLoading` — use `try/finally`

`isLoading` is set to `true` before the work starts and **always** cleared in `finally`, even on exception or early return:

```kotlin
private fun loadXxxAndYyy() {
    viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        runCatching {
            val xxxList = xxxRepository.getListOfXxx()
            val yyyList = yyyRepository.getListOfYyy()
            _uiState.value = _uiState.value.copy(
                listOfXxx = xxxList,
                listOfYyy = yyyList,
                isLoading = false,
            )
        }.onFailure {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
```

For complex flows with conditional loading, use `try/finally` so the flag always resets:

```kotlin
private fun fetchXxx() {
    viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        try {
            val item = xxxRepository.xxxFlow.firstOrNull { it != null } ?: return@launch
            _uiState.value = _uiState.value.copy(currentXxx = item)
        } finally {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
```

### F. Chained Flow operators

```kotlin
// Only re-runs when selectedId actually changes value
val selectedIdFlow = _uiState.map { it.selectedId }.distinctUntilChanged()

selectedIdFlow
    .flatMapLatest { id -> xxxRepository.getRecordFlow(id = id) }
    .onEach { record -> _uiState.value = _uiState.value.copy(xxxForSelectedId = record) }
    .launchIn(viewModelScope)
```

### G. Pass-through Flow (direct Fragment observation)

Only expose a repository Flow directly when the Fragment (or Activity) needs to observe it independently from `uiState` — for example, a system-level setting that affects the entire host Activity, not just this screen:

```kotlin
// Use pass-through only when the signal belongs at the Activity level,
// not when it is just another field in the screen's UiState.
val systemSettingFlow: Flow<Boolean> = settingRepository.systemSettingFlow
```

For everything else, collect in the ViewModel and reflect in `_uiState`.

### H. Early exit on null

```kotlin
fun setXxxDone(id: String, done: Boolean) {
    viewModelScope.launch {
        val item = _uiState.value.selectedXxx ?: return@launch
        // safe to use item below
    }
}
```

---

## Additional StateFlow (outside UiState)

Add a second `StateFlow` only when the signal is a Fragment-level event that doesn't belong in `XxxUiState` (e.g. a one-time paywall trigger):

```kotlin
private val _pendingForegroundPaywall = MutableStateFlow(false)
val pendingForegroundPaywall: StateFlow<Boolean> = _pendingForegroundPaywall.asStateFlow()

/**
 * Consumes the pending foreground-paywall signal so it does not fire again
 * until the next true app foreground event.
 *
 * @author Phong-Kaster
 */
fun consumePendingForegroundPaywall() {
    _pendingForegroundPaywall.value = false
}
```

Always provide a `consumeXxx()` function that resets the flag to `false`.

---

## Lifecycle observer pattern

Only register a `DefaultLifecycleObserver` when the ViewModel must react to app-level foreground events. Always remove it in `onCleared` to avoid a memory leak:

```kotlin
private val appLifecycleObserver = object : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        viewModelScope.launch {
            // respond to true app foreground (cold start or background resume)
        }
    }
}

private fun observeAppLifecycle() {
    ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
}

override fun onCleared() {
    ProcessLifecycleOwner.get().lifecycle.removeObserver(appLifecycleObserver)
    super.onCleared()
}
```

`onCleared` is **only** needed when you have registered external observers. Do not add it otherwise.

---

## UiState anatomy

```kotlin
/**
 * UI state for the Xxx screen.
 *
 * @param isLoading True while [XxxViewModel.loadXxx] runs on first load.
 * @param xxxData Loaded model; null while loading or when unavailable.
 * @param listOfItems Full item list from repository.
 * @param showSomePopup True when the bottom sheet should be visible.
 * @param somePopupMessage Non-null while a transient message is shown; null clears it.
 * @author Phong-Kaster
 */
data class XxxUiState(
    val isLoading: Boolean = false,

    // --- Domain data ---
    val xxxData: XxxModel? = null,
    val listOfItems: List<XxxModel> = emptyList(),

    // --- UI control ---
    val showSomePopup: Boolean = false,
    val somePopupMessage: String? = null,  // non-null while a transient message is shown; null hides it

    // --- Permission counters (only if screen handles permission flows) ---
    val numberOfLocationDenial: Int = 0,
    val numberOfNotificationDenial: Int = 0,
)
```

### Rules for every UiState

1. **Always `data class`** — never `sealed class`, interface, or plain `class`.
2. **Every field has a default value** — `XxxUiState()` must compile with zero arguments.
3. **No `error: String?` field** — errors are caught in ViewModel (`runCatching`, `try/catch`); they do not surface as persistent state.
4. **`isLoading: Boolean = false`** — include when the screen has an initial async load. Omit for screens that are always ready (e.g. a simple DataStore-backed settings screen).
5. **Nullable domain objects**: `val xxxData: XxxModel? = null`.
6. **Collections default to `emptyList()` / `emptySet()`** — never `null` for collections.
7. **KDoc on the class** — one line per param describing what it controls. `@author Phong-Kaster` at the end.
8. **Inline `/** ... */` comments on non-obvious fields** only.

### Derived properties (computed vals inside the data class)

When the Layout needs a filtered or transformed view of raw data, compute it as a derived `val` **inside the data class** — not in the ViewModel, not in the Fragment:

```kotlin
data class XxxUiState(
    val listOfItems: List<XxxModel> = emptyList(),
    val favoriteIds: Set<Int> = emptySet(),
    val searchQuery: String = "",
) {
    /** Items matching [searchQuery] on display name; all items when query is blank. */
    val itemsForDisplay: List<XxxModel> =
        if (searchQuery.isBlank()) listOfItems
        else listOfItems.filter { it.name.contains(searchQuery, ignoreCase = true) }

    /** All items whose ID is in [favoriteIds]. */
    val favoritedItems: List<XxxModel> =
        listOfItems.filter { it.id in favoriteIds }
}
```

### One-shot trigger fields

For scroll-to-position, animation triggers, or any "fire once and forget" event — use an incrementing `Int` instead of `SharedFlow` or `Channel`. This survives recomposition and is simpler:

```kotlin
data class XxxUiState(
    /** Incremented when user taps "scroll to top"; list scrolls to the first item. */
    val scrollToTopTrigger: Int = 0,
)

// In ViewModel:
fun scrollToTop() {
    _uiState.value = _uiState.value.copy(
        scrollToTopTrigger = _uiState.value.scrollToTopTrigger + 1,
    )
}

// In Layout (LaunchedEffect fires every time the Int changes):
LaunchedEffect(uiState.scrollToTopTrigger) {
    listState.animateScrollToItem(0)
}
```

---

## DI wiring (Koin)

```kotlin
val viewModelModule = module {
    viewModel {
        XxxViewModel(
            xxxRepository = get(),
            yyyRepository = get(),
        )
    }
}
```

- Always `viewModel { }`, never `single { }`.
- Named arguments at every call site.
- Inject by repository **interface**, never the impl.

---

## Add a new screen (checklist)

1. Create `XxxUiState` — `data class`, all fields with defaults, KDoc, no error field.
2. Create `XxxViewModel` — `TAG`, `_uiState`/`uiState`, `init` block, private collectors/loaders.
3. Wire `viewModel { XxxViewModel(...) }` in `viewModelModule`.
4. Create `XxxFragment` + `XxxLayout` following `figma-design-system.md`.

---

## DO / DON'T

DO:

- `_uiState.value = _uiState.value.copy(...)` always — no exceptions.
- `private val TAG = "XxxViewModel"` as an instance property (not companion).
- Call only named `private fun` from `init`, never inline `viewModelScope.launch`.
- `viewModelScope.launch(Dispatchers.IO)` for every write operation.
- `return@launch` for early null exit inside a coroutine.
- `try/finally` when `isLoading` must always be reset regardless of outcome.
- `runCatching { }.onFailure { }` for simpler load-and-set operations.
- Derived `val` inside `data class` for filtered/transformed views of raw data.
- `scrollToTopTrigger: Int = 0` + increment for one-shot scroll/animation events.
- `override fun onCleared()` only when lifecycle observers are registered.

DON'T:

- Use `_uiState.update { it.copy(...) }` — always `.value = .value.copy(...)`.
- Add `error: String?` to `XxxUiState` — handle errors inside the ViewModel.
- Inline `viewModelScope.launch` directly inside `init`.
- Expose `MutableStateFlow` publicly.
- Put TAG in a companion object — that is the repository convention, not the ViewModel convention.
- Compute filtered lists in the Layout — put them as derived `val` in UiState.
- Use `SharedFlow` or `Channel` for scroll/animation triggers — use the increment-int pattern.
- Forget `override fun onCleared()` when you register a lifecycle observer.

---

## @author Phong-Kaster
