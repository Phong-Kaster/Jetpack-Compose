---
paths:
  - "**/data/**/*.kt"
  - "**/domain/repository/**/*.kt"
  - "**/domain/model/**/*.kt"
  - "**/injection/RepositoryModule.kt"
  - "**/injection/DatabaseModule.kt"
  - "**/injection/DatastoreModule.kt"
  - "**/injection/NetworkModule.kt"
---

# Repository Layer
> Loaded when working on data/, domain/repository/, domain/model/ or data DI modules. Portable across projects. Wins for repository-layer details — if it conflicts with a higher-priority architecture rule, follow that rule and mention the conflict in your summary.

---

## When these rules apply

Any time you write or edit:

- Anything under `domain/repository/`, `data/repository/`, `data/mapper/`, `data/datastore/`, `data/database/`, `data/remote/`.
- Business-logic / use cases under `domain/usecase/`.
- ViewModel ↔ repository wiring (constructor injection, Flow collection, suspend calls).
- DI modules where repositories are wired (`RepositoryModule.kt`, `ViewModelModule.kt`, etc.).

---

## The two-folder contract

| Folder | Holds | Allowed dependencies |
|---|---|---|
| `domain/repository/` | `FooRepository` interfaces | `kotlinx.coroutines.*`, `domain.model.*`, `domain.enums.*`, plain Kotlin / Java |
| `data/repository/impl/` | `FooRepositoryImpl` classes | Everything in `data/` (datastores, DAOs, APIs, mappers), Android `Context`, dispatchers |

Domain interfaces stay **Android-free**. If you reach for `android.*` in `domain/`, stop — the type belongs in `data/` and must be mapped before crossing the boundary.

DI wiring lives in a single repository module. One entry per repository, named arguments at the call site, inject by interface from ViewModels.

---

## Interface anatomy

```kotlin
interface FooRepository {

    /** Hot in-memory state: single source of truth, observed by ViewModels. */
    val state: StateFlow<Foo>

    /** Persisted / cold reads: name them `xxxFlow`, no suspend. */
    val itemsFlow: Flow<List<Item>>

    /** One-shot work that may suspend (network, disk, compute). */
    suspend fun loadFoo(id: String, refresh: Boolean = false): Foo?

    /** Fire-and-forget controls (e.g. player commands). Plain `fun`, no return. */
    fun stopPlayback()

    /** Writes are suspend by default. */
    suspend fun setFoo(value: Foo)
}
```

Conventions:

- One interface per file, named `FooRepository`. Match the model name where possible.
- `suspend fun` for one-shot work; plain `fun` for fire-and-forget controls. Never make a Flow-returning method `suspend`.
- Hot in-memory state → `val state: StateFlow<X>`. Persisted streams → `val xxxFlow: Flow<X>`.
- Default parameter values are encouraged for paging / refresh flags.
- KDoc brief, plain-English. Write as if a junior dev is reading.
- Section dividers `// ---------- Section name ----------` once the interface grows past one screen.
- No companion objects, no default method bodies, no `private` members on the interface.
- Do not expose `MutableStateFlow` / `MutableSharedFlow` on the interface. Public type is always the read-only counterpart.

---

## Implementation anatomy

```kotlin
class FooRepositoryImpl(
    private val fooApi: FooApi,
    private val fooDao: FooDao,
    private val fooDatastore: FooDatastore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FooRepository {

    private val _state = MutableStateFlow(Foo.empty())
    override val state: StateFlow<Foo> = _state.asStateFlow()

    @Volatile
    private var cachedFoo: Foo? = null

    private val fetchMutex = Mutex()

    override suspend fun loadFoo(id: String, refresh: Boolean): Foo? = fetchMutex.withLock {
        if (!refresh) cachedFoo?.let { return@withLock it }
        try {
            val dto = withContext(ioDispatcher) { fooApi.getFoo(id) }
            val foo = dto.toDomain()
            cachedFoo = foo
            _state.value = foo
            foo
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "loadFoo($id) failed", e)
            null
        }
    }

    companion object {
        private const val TAG = "FooRepositoryImpl"
    }
}
```

Conventions:

- All dependencies are `private val` constructor params. Named arguments at every Koin/Hilt site.
- Backing state pattern: `private val _x = MutableStateFlow(...)` + `override val x: StateFlow<...> = _x.asStateFlow()`.
- `@Volatile private var xxxCache: T? = null` for simple nullable in-memory caches.
- One `Mutex` per logical concern. Name it for what it guards (`fetchMutex`, `seedMutex`).
- Class-level KDoc: one or two lines on what the class does and which sources it touches.
- `companion object` at the bottom for `TAG`, asset filenames, default constants, formatters.

---

## Data-source anatomy

### DataStore class

Create one `FooDatastore` per concern in `data/datastore/`. It owns the `Preferences` key names and exposes typed Flows and suspend writers:

```kotlin
class FooDatastore(private val context: Context) {

    private val dataStore = context.dataStore  // delegate created via Context.createDataStore or PreferenceDataStoreFactory

    val itemFlow: Flow<String> = dataStore.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { prefs -> prefs[KEY_ITEM] ?: "" }

    suspend fun setItem(value: String) {
        dataStore.edit { prefs -> prefs[KEY_ITEM] = value }
    }

    companion object {
        private val KEY_ITEM = stringPreferencesKey("key_item")
    }
}
```

- One DataStore instance per file (one file = one DataStore delegate in `common/` or `injection/`).
- Key names are `private` constants in the companion — never exposed outside the class.
- `.catch { IOException → emptyPreferences() }` on every Flow so cold-start read errors don't crash collectors.
- Writers are `suspend` — call from `viewModelScope.launch(Dispatchers.IO)` in the ViewModel.

---

### Room DAO + Entity

One `@Dao` interface and one `@Entity` data class per table, both in `data/database/`:

```kotlin
// Entity
@Entity(tableName = "foo")
data class FooEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val createdAt: Long,
)

// DAO
@Dao
interface FooDao {

    @Query("SELECT * FROM foo WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): FooEntity?

    @Query("SELECT * FROM foo ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<FooEntity>>

    @Query("SELECT COUNT(*) FROM foo")
    suspend fun rowCount(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: FooEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<FooEntity>)

    @Delete
    suspend fun delete(entity: FooEntity)
}
```

Conventions:
- `observeAll()` returns `Flow<List<FooEntity>>` (no `suspend`) — Room manages the emission.
- `rowCount()` is needed for seed-guard checks (Recipe E).
- Mapper extension functions (`FooEntity.toDomain()`, `Foo.toEntity()`) live in `data/mapper/FooMapper.kt` — the repository never maps inline.
- Register every `@Entity` in the `@Database` class in `data/database/AppDatabase.kt`.

---

## Data-source recipes

Pick the recipe that matches your data source. Mix multiple sources in one repository when the domain calls for it.

### A. DataStore-only (pass-through)

```kotlin
class FooRepositoryImpl(
    private val fooDatastore: FooDatastore,
) : FooRepository {

    override val itemFlow: Flow<Foo> = fooDatastore.itemFlow

    override suspend fun setItem(value: Foo) = fooDatastore.setItem(value)
}
```

Pass-through for any field that doesn't need transformation. Combine multiple datastore flows with `combine(...) { ... }` when the public flow is a derived state.

### B. Room-only

```kotlin
class FooRepositoryImpl(
    private val fooDao: FooDao,
) : FooRepository {

    override suspend fun get(id: Long): Foo? = fooDao.getById(id)?.toDomain()

    override fun observeAll(): Flow<List<Foo>> =
        fooDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun upsert(value: Foo) = fooDao.upsert(value.toEntity())
}
```

`toDomain()` / `toEntity()` extension functions live in `data/mapper/`. The repo never imports `androidx.room.*` directly — only the DAO and entity types.

### C. API-backed with safeApiCallFlow

`safeApiCallFlow` is a skeleton utility in `data/remote/util/safeApiCallFlow.kt`. Its full implementation:

```kotlin
fun <T> safeApiCallFlow(apiCall: suspend () -> T): Flow<Outcome<T>> = flow {
    emit(Outcome.Loading)
    try {
        emit(Outcome.Success(apiCall()))
    } catch (e: Exception) {
        emit(Outcome.Error(e.message ?: "Unknown error", e))
    }
}.flowOn(Dispatchers.IO)
```

Use it in repository implementations:

```kotlin
class FooRepositoryImpl(
    private val fooApi: FooApi,
) : FooRepository {

    override fun getFooFlow(): Flow<Outcome<List<Foo>>> = safeApiCallFlow {
        fooApi.getAll().map { it.toDomain() }
    }

    companion object { private const val TAG = "FooRepositoryImpl" }
}
```

`safeApiCallFlow` emits `Outcome.Loading` first, then `Outcome.Success` or `Outcome.Error`. The ViewModel collects this flow and maps each state to `UiState`:

```kotlin
// In ViewModel:
private fun collectFoo() {
    viewModelScope.launch {
        fooRepository.getFooFlow().collectLatest { outcome ->
            when (outcome) {
                is Outcome.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                is Outcome.Success -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    items = outcome.data,
                )
                is Outcome.Error -> _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
```

### D. Asset (JSON) backed

```kotlin
// Foo.kt — must be @Serializable
@Serializable
data class Foo(val id: String, val name: String)

class FooRepositoryImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FooRepository {

    @Volatile private var cache: List<Foo>? = null

    override suspend fun getAll(): List<Foo> = withContext(ioDispatcher) {
        cache ?: try {
            val json = context.assets.open(ASSET_NAME).reader(Charsets.UTF_8).use { it.readText() }
            val list = Json.decodeFromString<List<Foo>>(json)
            cache = list
            list
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load $ASSET_NAME", e)
            emptyList()
        }
    }

    companion object {
        private const val TAG = "FooRepositoryImpl"
        private const val ASSET_NAME = "foo.json"
    }
}
```

Asset reads block — always `withContext(ioDispatcher)`. Cache the parsed list in memory. Domain model must be annotated with `@Serializable` (kotlinx.serialization).

### E. Asset → Room seeding (first-run)

```kotlin
private val seedMutex = Mutex()

private suspend fun ensureSeeded() = seedMutex.withLock {
    if (fooDao.rowCount() > 0L) return@withLock
    try {
        val rows = readAssetJson()
        if (rows.isNotEmpty()) fooDao.insertAll(rows.map { it.toEntity() })
    } catch (e: Exception) {
        Log.e(TAG, "Seed failed", e)
    }
}
```

Call `ensureSeeded()` from every read path (`Flow.onStart { ensureSeeded() }`, first line of every `suspend` reader). The mutex prevents two coroutines from both inserting the seed on first launch.

### F. System-API wrapping (Geocoder, Sensors, FusedLocation, etc.)

```kotlin
override suspend fun getAddress(lat: Double, lng: Double): String? = withContext(ioDispatcher) {
    runCatching {
        Geocoder(context, Locale.getDefault())
            .getFromLocation(lat, lng, 1)
            ?.firstOrNull()
            ?.let { listOfNotNull(it.locality, it.adminArea, it.countryName).joinToString(", ") }
    }.getOrNull()
}
```

`runCatching { ... }.getOrNull()` is the preferred shorthand when the only failure outcome is "no value". `withContext(ioDispatcher)` because Android system APIs commonly block.

---

## Concurrency

- One `Mutex` per logical concern. Never share one mutex across unrelated operations.
- `synchronized(lockObj) { ... }` on a `private val lockObj = Any()` for fast non-suspending cache-hit checks where `Mutex.withLock` would force a suspension.
- `@Volatile private var xxxCache: T? = null` for "read often, write seldom" nullable caches.
- Avoid `Channel` and `MutableSharedFlow` unless you genuinely need replay / buffering semantics.

---

## Dispatcher injection

- Constructor-inject `private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO`. This is the default — the seam lets tests swap in `UnconfinedTestDispatcher`.
- Wrap blocking work (asset reads, file I/O, Geocoder, JNI) with `withContext(ioDispatcher) { ... }`.
- For Flow chains, use `.flowOn(ioDispatcher)` near the source.
- Don't hardcode `Dispatchers.IO` deep inside a function when the class already has `ioDispatcher`.

---

## Fail-soft error contract

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

Repositories MUST NOT throw to callers. Return type by scenario:

| Scenario | Return type |
|---|---|
| API-backed stream | `Flow<Outcome<T>>` via `safeApiCallFlow` — emits Loading → Success/Error |
| Single item fetch, null = not found | `T?` |
| Fallible one-shot read (multiple steps) | `Outcome<T>` — Success or Error, no Loading |
| Write / fire-and-forget | `Unit` or `Outcome<Unit>` if caller needs confirmation |
| Room-backed stream | `Flow<List<T>>` — Room never errors; emit empty list as default |

Inside the impl — API stream (most common):

```kotlin
override fun getFooFlow(): Flow<Outcome<List<Foo>>> = safeApiCallFlow {
    fooApi.getAll().map { it.toDomain() }
}
```

Inside the impl — single item one-shot:

```kotlin
override suspend fun getFoo(id: String): Foo? {
    return try {
        fooApi.getFoo(id).toDomain()
    } catch (e: CancellationException) {
        throw e  // always re-throw — swallowing breaks coroutine cancellation
    } catch (e: Exception) {
        Log.e(TAG, "getFoo($id) failed", e)
        null
    }
}
```

- `Log.e(TAG, "what failed", e)` for actual failures.
- `Log.w(TAG, "recovered from X")` for soft recoveries.
- `ex.printStackTrace()` is acceptable in legacy code but prefer `Log.e(TAG, ..., e)` going forward.

---

## Mappers, constants, logging

- Mappers live in `data/mapper/` as extension functions named `toDomain()`, `toEntity()`, `toXxx()`. Group by feature (`FooMapper.kt`).
- Constants go in `companion object` at the bottom of the impl: asset filenames, page sizes, default ids, date formatters.
- `TAG` is `private const val TAG = "FooRepositoryImpl"` inside the companion. Prefer the companion form for new code.

---

## DI wiring

Koin:

```kotlin
val repositoryModule = module {
    single<FooRepository> {
        FooRepositoryImpl(
            fooApi = get(),
            fooDao = get(),
            fooDatastore = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }
}
```

Hilt:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFooRepository(impl: FooRepositoryImpl): FooRepository
}
```

Every repository **must** be wired. Inject by **interface** in ViewModels — never the impl.

---

## The interface-less exception

Acceptable to start with `class FooRepository(...)` (no interface) when the API surface is still being shaped. Leave a comment explaining why and a `// TODO: extract FooRepository interface once the surface stabilises` note. Extract as soon as a second caller appears or the class crosses ~3 public members.

---

## Add a new repository (7-step checklist)

1. Create domain model(s) in `domain/model/<feature>/`.
2. Create `interface FooRepository` in `domain/repository/` — `suspend fun`, Flows, properties only; no Android types.
3. Pick a data-source recipe (A–F above) — or compose multiple.
4. If converting DTOs / Entities: add `FooMapper.kt` extension functions in `data/mapper/`.
5. Create `class FooRepositoryImpl(...) : FooRepository` in `data/repository/impl/` using the matching recipe.
6. Wire `single<FooRepository> { FooRepositoryImpl(...) }` (Koin) or `@Binds` (Hilt) in the repository DI module.
7. Inject `private val fooRepository: FooRepository` into ViewModels via the ViewModel DI module.

---

## DO / DON'T

DO:

- `suspend fun` for one-shots, `Flow` / `StateFlow` for streams.
- Fail-soft: return `null` / `emptyList()` / sentinel — never throw.
- One `Mutex` per concern, named for what it guards.
- `withContext(ioDispatcher)` around blocking work.
- Mapper extension functions in `data/mapper/`.
- Companion-object constants (`TAG`, asset names, formatters, defaults).
- Inject by interface in ViewModels.
- Re-throw `CancellationException` inside catch blocks.

DON'T:

- Throw exceptions across the repository boundary.
- Expose `MutableStateFlow` / `MutableSharedFlow` on the interface.
- Leak `android.*` types into `domain/`.
- Reuse one mutex across unrelated operations.
- Hardcode magic strings — promote to constants.
- Skip DI wiring; never `new` a repository from a ViewModel.
- Build a 30-method "god" repository — split by concern.
- Hardcode `Dispatchers.IO` when the class already has `ioDispatcher`.

---

## @author Phong-Kaster
