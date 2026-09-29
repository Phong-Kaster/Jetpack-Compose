---
paths:
  - "**/domain/usecase/**/*.kt"
  - "**/injection/UseCaseModule.kt"
---

# Use Case Layer
> Loaded when working on domain/usecase/. Portable across projects. If it conflicts with `android-skeleton-project.md` (highest priority), follow that file and mention the conflict.
>
> `Outcome<T>` = `common.Outcome<T>` — the project's sealed class (Loading / Success / Error):
>
> ```kotlin
> // common/Outcome.kt
> sealed class Outcome<out T> {
>     data object Loading : Outcome<Nothing>()
>     data class Success<T>(val data: T) : Outcome<T>()
>     data class Error(val message: String, val throwable: Throwable? = null) : Outcome<Nothing>()
> }
> ```
>
> Use cases return `Outcome.Success` or `Outcome.Error` only — never `Outcome.Loading` (Loading is the ViewModel's responsibility via `isLoading` in UiState).

---

## When these rules apply

Any time you write or edit:

- Anything under `domain/usecase/`.
- Business logic that spans more than one repository.
- Logic shared by two or more ViewModels.
- DI modules where use cases are wired (`AppModule`, `UseCaseModule`, etc.).

---

## When to write a use case — and when not to

**Write a use case when:**

- The operation touches **two or more repositories**.
- The **same logic is needed in 2+ ViewModels** — don't duplicate, extract.
- The operation has **non-trivial business rules** that don't belong in a repository (validation, sequencing, transformation).
- The operation is a **multi-step workflow** where each step depends on the previous result.

**Skip a use case when:**

- It would be a pure pass-through to a single repository method — the ViewModel calls the repository directly.
- The logic is UI-only (belongs in ViewModel).
- The logic is data mapping (belongs in `data/mapper/`).
- The class would have one line: `return repository.getX()`.

---

## Use case anatomy

### One-shot use case

```kotlin
/**
 * Retrieves Xxx by combining data from [XxxRepository] and [YyyRepository].
 * Returns [Outcome.Success] with the assembled model, or [Outcome.Error] on error.
 *
 * @author Phong-Kaster
 */
class GetXxxUseCase(
    private val xxxRepository: XxxRepository,
    private val yyyRepository: YyyRepository,
) {

    suspend operator fun invoke(id: String): Outcome<XxxModel> {
        val xxx = xxxRepository.getById(id)
            ?: return Outcome.Error("Xxx $id not found")
        val yyy = yyyRepository.getForXxx(id)
        return Outcome.Success(xxx.copy(yyyData = yyy))
    }
}
```

### Flow-based use case

```kotlin
/**
 * Observes the live Xxx stream filtered by the given [filter].
 * Use when the caller needs ongoing updates, not a one-shot fetch.
 *
 * @author Phong-Kaster
 */
class ObserveXxxUseCase(
    private val xxxRepository: XxxRepository,
) {

    operator fun invoke(filter: XxxFilter): Flow<List<XxxModel>> =
        xxxRepository.xxxFlow
            .map { list -> list.filter { it.matchesFilter(filter) } }
            .distinctUntilChanged()
}
```

Conventions:

- Entry point is always `operator fun invoke(...)` — callers write `useCase(param)`, not `useCase.execute(param)`.
- `suspend operator fun invoke` for one-shot work. Plain `operator fun invoke` returning `Flow` for streams — never make a Flow-returning method `suspend`.
- One use case per file, one public `invoke` function per class.
- Class-level KDoc: one or two lines on what it does and which sources it touches. `@author Phong-Kaster` at the end.
- No companion objects, no static state, no `TAG` — use cases are stateless.
- Do not inject `Context` — use cases live in `domain/` and must be Android-free.

---

## Naming conventions

| Prefix | When to use | Example |
|---|---|---|
| `Get` | One-shot read that assembles or transforms | `GetUserProfileUseCase` |
| `Observe` | Ongoing Flow that emits on change | `ObserveCartItemsUseCase` |
| `Create` | Persists a new entity | `CreateOrderUseCase` |
| `Update` | Modifies an existing entity | `UpdateProfileUseCase` |
| `Delete` / `Remove` | Removes an entity | `DeleteDraftUseCase` |
| `Validate` | Pure business rule check, returns `Boolean` or `Outcome` | `ValidateCheckoutUseCase` |
| `Sync` | Pulls remote data into local store | `SyncCatalogUseCase` |
| `Calculate` | CPU-bound computation with no side effects | `CalculateShippingUseCase` |
| `Send` / `Submit` | Fire-and-confirm network write | `SubmitFeedbackUseCase` |

One verb, one noun, always ends in `UseCase`. Do not abbreviate.

---

## Patterns

### A. Single repository, one-shot read

```kotlin
class GetXxxUseCase(
    private val xxxRepository: XxxRepository,
) {

    suspend operator fun invoke(id: String): XxxModel? =
        xxxRepository.getById(id)
}
```

This is the minimum meaningful use case — only write it if a second caller already exists or the fetch will gain business logic soon.

### B. Multiple repositories combined

```kotlin
class GetXxxWithYyyUseCase(
    private val xxxRepository: XxxRepository,
    private val yyyRepository: YyyRepository,
) {

    suspend operator fun invoke(xxxId: String): Outcome<XxxWithYyy> {
        val xxx = xxxRepository.getById(xxxId)
            ?: return Outcome.Error("Xxx $xxxId not found")
        val yyy = yyyRepository.getForXxx(xxxId)
        return Outcome.Success(XxxWithYyy(xxx = xxx, yyy = yyy))
    }
}
```

Return `Outcome.Success` or `Outcome.Error` directly — never `Outcome.Loading` from a use case.

### C. Flow from multiple repositories (combined stream)

```kotlin
class ObserveXxxSummaryUseCase(
    private val xxxRepository: XxxRepository,
    private val settingRepository: SettingRepository,
) {

    operator fun invoke(): Flow<XxxSummary> =
        combine(
            xxxRepository.xxxFlow,
            settingRepository.preferenceFlow,
        ) { items, prefs ->
            XxxSummary(
                items = items.filter { prefs.showHidden || !it.isHidden },
                count = items.size,
            )
        }
}
```

### D. Validation use case

```kotlin
class ValidateXxxUseCase {

    operator fun invoke(input: XxxInput): ValidationOutcome {
        if (input.name.isBlank()) return ValidationOutcome.Error("Name is required")
        if (input.value < 0) return ValidationOutcome.Error("Value must be positive")
        return ValidationOutcome.Success
    }
}

sealed class ValidationOutcome {
    data object Success : ValidationOutcome()
    data class Error(val message: String) : ValidationOutcome()
}
```

Validation use cases have no repository dependencies and are stateless — they can be instantiated directly in the ViewModel (`private val validateXxx = ValidateXxxUseCase()`) or wired as `factory { ValidateXxxUseCase() }` in the DI module. Either is acceptable; prefer DI wiring when the same validator is used in 2+ ViewModels.

### E. Multi-step operation

```kotlin
class CreateXxxUseCase(
    private val xxxRepository: XxxRepository,
    private val yyyRepository: YyyRepository,
    private val notificationRepository: NotificationRepository,
) {

    suspend operator fun invoke(params: CreateXxxParams): Outcome<XxxModel> {
        // Step 1: validate preconditions
        val yyy = yyyRepository.getById(params.yyyId)
            ?: return Outcome.Error("Yyy ${params.yyyId} not found")

        // Step 2: persist
        val xxx = xxxRepository.create(
            name = params.name,
            yyyId = yyy.id,
        )

        // Step 3: side effects
        notificationRepository.scheduleForXxx(xxx.id)

        return Outcome.Success(xxx)
    }
}
```

Each step is a named comment. Return `Outcome.Error` early on any failed precondition rather than throwing.

### F. Background computation

```kotlin
class CalculateXxxUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(input: XxxInput): XxxCalculationResult =
        withContext(ioDispatcher) {
            // CPU-bound work here
            XxxCalculationResult(value = input.items.sumOf { it.weight * it.quantity })
        }
}
```

Use `Dispatchers.Default` for CPU-bound work, `Dispatchers.IO` for blocking I/O. Inject the dispatcher so tests can swap in `UnconfinedTestDispatcher`.

---

## Return type guide

> `Outcome<T>` = `common.Outcome<T>` (Loading/Success/Error). Use cases return only `Success` or `Error` — never `Loading`.

| Scenario | Return type |
|---|---|
| One-shot fetch that can fail | `Outcome<T>` — return `Outcome.Success(data)` or `Outcome.Error(message)` |
| One-shot fetch, null = not found | `T?` |
| One-shot write with no return value | `Outcome<Unit>` |
| Ongoing stream | `Flow<Outcome<T>>` via `safeApiCallFlow` from the repository |
| Validation | `ValidationOutcome` sealed class (see Pattern D) |
| Pure computation, never fails | `T` (no wrapping) |

Do not return raw exceptions to ViewModels. Use `Outcome.Error(message, throwable)` and let the ViewModel map `is Outcome.Error` to a UI state.

---

## Coroutine conventions

- `suspend operator fun invoke` for one-shot work.
- Plain `operator fun invoke` returning `Flow<T>` for streams — never `suspend` a Flow-returning function.
- Inject `ioDispatcher: CoroutineDispatcher = Dispatchers.IO` when the use case does blocking work.
- Always re-throw `CancellationException` if you write a manual `try/catch`:

```kotlin
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Outcome.Error(e.message ?: "Unknown error", e)
}
```

Prefer early-return style (`return Outcome.Error(...)`) over `try/catch` wrapping when the failure points are explicit precondition checks.

---

## DI wiring

### Koin

```kotlin
val useCaseModule = module {
    factory { GetXxxUseCase(xxxRepository = get()) }
    factory { ObserveXxxSummaryUseCase(xxxRepository = get(), settingRepository = get()) }
    factory { CreateXxxUseCase(xxxRepository = get(), yyyRepository = get(), notificationRepository = get()) }
}
```

- Always `factory { }`, never `single { }` — use cases are stateless and cheap to create per ViewModel.
- Named arguments at every call site.
- Include `useCaseModule` in the top-level `appModule`.

### Hilt

```kotlin
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetXxxUseCase(
        xxxRepository: XxxRepository,
    ): GetXxxUseCase = GetXxxUseCase(xxxRepository)
}
```

Or use constructor injection directly if all dependencies are `@Inject`-able.

---

## Where files live

```
domain/
└── usecase/
    └── <feature>/
        ├── GetXxxUseCase.kt
        ├── ObserveXxxUseCase.kt
        ├── CreateXxxUseCase.kt
        └── ValidateXxxUseCase.kt
```

- One use case per file.
- Group by feature folder (`order/`, `cart/`, `auth/`), not by verb.
- No `impl/` subfolder — use cases have no interface/impl split.
- Domain models used as params live in `domain/model/<feature>/`.

---

## Add a new use case (checklist)

1. Identify the need — does it span 2+ repos, or is it reused across 2+ ViewModels? If no to both, call the repository directly from the ViewModel.
2. Create `XxxUseCase.kt` in `domain/usecase/<feature>/`.
3. Pick the right pattern (A–F above) and return type from the guide.
4. Inject only repository interfaces — never impls, never Android types.
5. Wire `factory { XxxUseCase(repo = get()) }` in the use case DI module.
6. Inject into ViewModel via constructor: `private val xxxUseCase: XxxUseCase`.
7. Call from ViewModel with `xxxUseCase(params)` — the `invoke` operator makes it look like a function call.

---

## DO / DON'T

DO:

- `operator fun invoke(...)` as the single entry point.
- `suspend` for one-shot work; plain `fun` returning `Flow` for streams.
- Return `Outcome.Success(data)` or `Outcome.Error(message, throwable)` for fallible one-shot operations.
- `factory { }` in DI — use cases are stateless.
- One class, one responsibility, one public function.
- Group by feature in `domain/usecase/<feature>/`.
- Inject `ioDispatcher: CoroutineDispatcher = Dispatchers.IO` for blocking/compute work.
- KDoc on every class — one line per param, `@author Phong-Kaster` at the end.

DON'T:

- Write a use case that only calls `return repository.getX()` with no transformation — call the repository from the ViewModel directly.
- Inject `Context`, `Activity`, or any Android framework type — domain layer is Android-free.
- Put UI logic in a use case (string formatting, navigation, permission checks).
- Put data mapping in a use case — mappers belong in `data/mapper/`.
- Use `single { }` in DI — use cases hold no state.
- Return raw exceptions — use `Outcome.Error(message, throwable)` or a `ValidationOutcome` sealed class.
- Return `Outcome.Loading` from a use case — Loading is the ViewModel's job via `isLoading` in UiState.
- Use Kotlin stdlib `kotlin.Result` — always use the project's `common.Outcome<T>` (they are different types with different semantics).
- Name with vague verbs: `HandleXxxUseCase`, `ProcessXxxUseCase`, `ManageXxxUseCase` — be specific.
- Make a Flow-returning function `suspend`.

---

## @author Phong-Kaster
