<h1 align="center">Jetpack Compose Playground<br/>
    <sub>A skeleton and lesson catalog for Android apps built with Kotlin and Jetpack Compose</sub>
</h1>

<p align="center">
    <img src="./photo/background_cover.jpg" width="1280" alt="Cover" />
</p>

<p align="center">
    <img src="https://img.shields.io/badge/Kotlin-2.4.21-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin" />
    <img src="https://img.shields.io/badge/AGP-9.5.3-3DDC84?logo=android&logoColor=white" alt="AGP" />
    <img src="https://img.shields.io/badge/Gradle-9.9.2-02303A?logo=gradle&logoColor=white" alt="Gradle" />
    <img src="https://img.shields.io/badge/Compose%20BOM-2026.03.01-4285F4?logo=jetpackcompose&logoColor=white" alt="Compose BOM" />
    <img src="https://img.shields.io/badge/minSdk-26-informational" alt="minSdk" />
    <img src="https://img.shields.io/badge/targetSdk-36-informational" alt="targetSdk" />
</p>

---

## Table of Contents

<p align="center">
    <b>English</b> ·
    <a href="README.vi.md">Tiếng Việt</a> ·
    <a href="README.es.md">Español</a> ·
    <a href="README.de.md">Deutsch</a> ·
    <a href="README.fr.md">Français</a> ·
    <a href="README.hi.md">हिन्दी</a> ·
    <a href="README.ja.md">日本語</a> ·
    <a href="README.ko.md">한국어</a>
</p>

1. [Introduction](#1-introduction)
2. [Highlights](#2-highlights)
3. [Getting Started](#3-getting-started)
   - [Requirements](#31-requirements)
   - [Build and run](#32-build-and-run)
   - [Configuration](#33-configuration)
4. [Tech Stack](#4-tech-stack)
5. [Architecture](#5-architecture)
   - [Layers](#51-layers)
   - [Screen anatomy](#52-screen-anatomy)
   - [Navigation](#53-navigation)
   - [Sample catalog](#54-sample-catalog)
6. [App Flow](#6-app-flow)
7. [Lessons](#7-lessons)
   - [UI and layout](#71-ui-and-layout)
   - [Animation and graphics](#72-animation-and-graphics)
   - [Text input](#73-text-input)
   - [Data and networking](#74-data-and-networking)
   - [Permissions and device features](#75-permissions-and-device-features)
   - [Media and background work](#76-media-and-background-work)
   - [Kotlin and coroutines](#77-kotlin-and-coroutines)
8. [Bottom Bar Tabs](#8-bottom-bar-tabs)
9. [Reusable Component Library](#9-reusable-component-library)
   - [Core components](#91-core-components)
   - [Custom views](#92-custom-views)
   - [Charts](#93-charts)
   - [Modifiers](#94-modifiers)
   - [Utilities](#95-utilities)
10. [Notifications and Lock Screen](#10-notifications-and-lock-screen)
11. [Localization and Theming](#11-localization-and-theming)
12. [Performance: Baseline Profile](#12-performance-baseline-profile)
13. [Project Structure](#13-project-structure)
14. [Adding a New Lesson](#14-adding-a-new-lesson)
15. [Work in Progress](#15-work-in-progress)
16. [Reading List](#16-reading-list)
17. [AI-Assisted Development](#17-ai-assisted-development)

---

## 1. Introduction

This repository is a **Jetpack Compose playground** and a **reusable app skeleton** at the same time.

- **As a playground**, it gathers more than 25 runnable lessons: animations, collapsing top bars, permissions, Bluetooth, location, media playback, downloads, Room, DataStore, Retrofit, Kotlin Flow, and more. Each lesson is a screen you can open from the Home grid, read, and copy into your own project.
- **As a skeleton**, it ships the parts most production apps need: splash, language picker, onboarding, a bottom bar with four tabs, settings (dark mode, language, app icon, rating), daily notifications, a lock-screen notification, dependency injection, and a Baseline Profile.

The design is inspired by [Android Architecture Samples](https://github.com/android/architecture-samples). The code is 100% Kotlin. Every screen is a Compose UI hosted in a Fragment.

## 2. Highlights

| Area | What you get |
|---|---|
| **UI** | Jetpack Compose + Material 3, 40+ custom views, 13 Canvas charts, 8 custom modifiers |
| **Architecture** | Single activity, Fragment + Compose screens, MVVM, repository pattern, Hilt DI |
| **Async** | Kotlin Coroutines, `Flow` / `StateFlow`, a `safeApiCall` wrapper that turns a Retrofit `Response` into a `Status` |
| **Data** | Room (quotes), DataStore Preferences (settings, tutorial state), Retrofit + OkHttp (AccuWeather, DummyJSON) |
| **Device** | Runtime permissions, Fused Location, Bluetooth Classic + BLE, camera, MediaStore, WebView |
| **Background** | Foreground media service, WorkManager (`@HiltWorker`), DownloadManager, exact alarms |
| **App shell** | Splash, intro, language picker, bottom bar, settings, dynamic launcher icon, in-app review |
| **Quality** | Baseline Profile + startup benchmark module, `@Preview` for most composables |
| **Localization** | English, German, French, Hindi, Japanese, Korean, Vietnamese |

## 3. Getting Started

### 3.1 Requirements

| Tool | Version |
|---|---|
| Android Studio | A release that supports AGP 9.5 |
| JDK | 17 or newer (the project targets Java 17 bytecode) |
| Gradle | 9.9.2 (downloaded by the wrapper) |
| Android SDK | compileSdk 37, minSdk 26 |

### 3.2 Build and run

```bash
git clone https://github.com/Phong-Kaster/Jetpack-Compose.git
cd Jetpack-Compose

# Install the debug build on a connected device or emulator
./gradlew :app:installDebug

# Other useful tasks
./gradlew :app:assembleRelease        # release APK (R8 enabled)
./gradlew :app:testDebugUnitTest      # unit tests
./gradlew :baselineprofile:connectedCheck  # generate the Baseline Profile / run benchmarks
```

Every APK is renamed by the build to `Bundeswehr_v<versionName>(<versionCode>)_<timestamp>-<variant>.apk`.

### 3.3 Configuration

| Setting | Location | Notes |
|---|---|---|
| AccuWeather API key | `configuration/Constant.kt` → `ACCU_WEATHER_KEY` | Replace with your own key from [AccuWeather Developer](https://developer.accuweather.com/). Prefer `local.properties` + `BuildConfig` for real projects. |
| AccuWeather base URL | `configuration/Constant.kt` → `ACCU_WEATHER_URL` | `https://dataservice.accuweather.com` |
| DummyJSON base URL | `injection/module/NetworkModule.kt` | `https://dummyjson.com/` |
| Library versions | `gradle/libs.versions.toml` | Single version catalog for every dependency and plugin |

## 4. Tech Stack

| Category | Libraries |
|---|---|
| Language and build | Kotlin 2.4.21, AGP 9.5.3, Gradle 9.9.2, KSP 2.3.14, version catalog |
| UI | Compose BOM 2026.08.00, Material 3, Material Icons Extended, ConstraintLayout Compose (MotionLayout), Accompanist Permissions |
| Architecture | AndroidX Lifecycle 2.10, Navigation Component 2.9.7 (Fragment + Safe Args), Hilt 2.60.1 |
| Data | Room 2.8.4, DataStore Preferences 1.2.1, Kotlinx Collections Immutable |
| Network | Retrofit 2.12, OkHttp logging interceptor 5.3, Gson |
| Images and animation | Coil 2 (+ GIF), Glide 4, Lottie 6 |
| Media | `MediaPlayer` + `MediaStyle` notification, Media3 ExoPlayer / UI / Session |
| Background | WorkManager 2.11 + Hilt Work, `AlarmManager`, `DownloadManager` |
| Google Play | Play Services Location, Play In-App Review |
| Performance | Baseline Profile 1.4, Macrobenchmark, ProfileInstaller |

## 5. Architecture

### 5.1 Layers

```
┌──────────────────────────────────────────────────────────┐
│ ui/        Fragments → Compose layouts → ViewModels      │  presentation
├──────────────────────────────────────────────────────────┤
│ domain/    models, enums, managers, sample catalog       │  business types
├──────────────────────────────────────────────────────────┤
│ data/      repositories → Room DAO / DataStore           │  local data
│ network/   Retrofit services, DTOs, interceptors         │  remote data
├──────────────────────────────────────────────────────────┤
│ injection/ Hilt modules wire everything together         │  DI
└──────────────────────────────────────────────────────────┘
```

- **ViewModels** expose screen state as `StateFlow` and never touch Android views.
- **Repositories** are the only door to Room, DataStore and Retrofit. A screen asks a repository, never a DAO or a service.
- **Network calls** go through `safeApiCall` (`network/util/SafeApiFlow.kt`), which wraps a Retrofit `Response` into a `Status` (`Success` / `Failure`); repositories expose it to ViewModels as a `Flow`.
- **Hilt** provides the database, the two Retrofit clients, the repositories and the coroutine dispatchers (`@IoDispatcher`, `@DefaultDispatcher`).

### 5.2 Screen anatomy

Every screen follows the same shape:

```kotlin
@AndroidEntryPoint
class QuoteFragment : CoreFragment() {          // 1. Fragment = navigation entry point
    private val viewModel: QuoteViewModel by viewModels()

    @Composable
    override fun ComposeView() {                 // 2. CoreFragment hosts Compose
        super.ComposeView()
        val quotes by viewModel.quotes.collectAsStateWithLifecycle()
        QuoteLayout(                             // 3. Stateless layout, easy to preview
            quotes = quotes,
            onSave = { viewModel.insertOrUpdate(it) },
        )
    }
}
```

| Base class | Job |
|---|---|
| `core/base/CoreActivity` | Applies the saved language in `attachBaseContext`, toast + connectivity helpers |
| `core/base/CoreFragment` | Creates the `ComposeView`, applies the app theme and dark mode, provides `LocalNavController` and `LocalLocale` |
| `core/base/CoreLayout` | A `Scaffold` wrapper with top bar, bottom bar, FAB and edge-to-edge insets |
| `core/base/CoreBehavior` | Shared contract: `showToast`, `isInternetConnected`, `trackEvent` |

### 5.3 Navigation

- One activity (`MainActivity`) holds a `NavHostFragment` and the bottom bar.
- All destinations live in `res/navigation/navigation_graph.xml`. The start destination is the splash screen; the intro screens are a nested graph.
- Navigate with `safeNavigate(R.id.toXxx)` from `util/NavigationUtil.kt`; it checks the current destination first, so a double tap never crashes.

### 5.4 Sample catalog

`domain/catalog/SampleRegistry.kt` is the index of every lesson. Each `HomeShortcut` has a `SampleMeta` entry with:

- the main source file to read or copy,
- extra related files,
- topics (`SampleTopic`: `ANIMATION`, `PERMISSION`, `MEDIA`, …),
- search aliases.

The Home search bar uses this registry, so typing `retrofit`, `pinch` or `qibla` finds the right lesson.

## 6. App Flow

```
Splash ──(first launch)──► Intro language ──► Intro pages ──► Home
   │                                                         ▲
   └──────────────────(returning user)───────────────────────┘

Home ◄──► Insight ◄──► Article ◄──► Settings      (bottom bar)
  │
  └──► Lesson screens (Home grid, search, sort)
```

| Screen | Package | What it shows |
|---|---|---|
| Splash | `secondary/splash` | Decides between the intro and Home from DataStore |
| Intro language | `secondary/intro` | First-run language picker |
| Intro | `secondary/intro` | Onboarding pager with a custom page indicator |
| Language | `secondary/language` | Change the app language later, from Settings |

## 7. Lessons

Every lesson below opens from the **Home** grid. Paths are relative to `app/src/main/java/com/example/jetpack/ui/fragment/`.

### 7.1 UI and layout

| Lesson | Package | What you learn |
|---|---|---|
| Collapsible Top Bar | `secondary/collapsibletopbar/collapsible` | Collapse a header from scroll position with `derivedStateOf` |
| Collapsible Top Bar 2 | `secondary/collapsibletopbar/collapsible2` | Material 3 `TopAppBarDefaults.enterAlwaysScrollBehavior` + `nestedScroll` |
| Collapsible Top Bar 3 | `secondary/collapsibletopbar/collapsible3` | A hand-made `NestedScrollConnection` with full / minimized headers |
| Collapsible Top Bar 4 | `secondary/collapsibletopbar/collapsible4` | `LargeTopAppBar` switching on `firstVisibleItemIndex` |
| Collapsible Top Bar 5 | `secondary/collapsibletopbar/collapsible5` | Expanded / collapsed bars combined with `enterAlwaysScrollBehavior` |
| Instagram Carousel | `secondary/instagramcarousel` | Pager with a long-press, draggable dot indicator |
| Pinch to Zoom | `secondary/pitchtozoom` | `detectTransformGestures` + `graphicsLayer` for zoom, pan and rotate |
| Tutorial | `secondary/tutorial` | A step-by-step spotlight tutorial built with `onGloballyPositioned` and `boundsInRoot`, state saved in DataStore |
| Login | `secondary/login` | Login form: `FocusRequester`, `KeyboardOptions`, IME actions, `PasswordVisualTransformation` |
| WebView | `secondary/webview` | `WebView` inside `AndroidView`, `WebViewClient` callbacks, back handling with `BackHandler` |

### 7.2 Animation and graphics

| Lesson | Package | What you learn |
|---|---|---|
| Animation | `secondary/animation` | `tween`, `spring`, `keyframes`, `infiniteRepeatable`, `drawBehind`, `drawWithContent`, affine transforms |
| Motion Layout | `secondary/motionlayout` | Compose `MotionLayout` with JSON5 motion scenes (`res/raw/*.json5`) and a custom collapsing toolbar state |
| Mesh Gradient | `secondary/meshgradient` | Animated "liquid" backdrop with `MeshGradientPainter` (Compose 1.12): draw-phase state reads, a seamless looping clock, colour flow, reduced-motion and lifecycle gating, and drawing the mesh small then scaling it up to keep the CPU cost low |

### 7.3 Text input

| Lesson | Package | What you learn |
|---|---|---|
| Basic Text Field (state) | `secondary/textfield` | `BasicTextField` with `TextFieldState`: `InputTransformation`, built-in transformations, `OutputTransformation`, autofill, auto-size, a flexible text field |

The reusable input rules live in `util/inputtransformation/` (`DigitsOnlyTransformation`, `VerificationCodeTransformation`).

### 7.4 Data and networking

| Lesson | Package | What you learn |
|---|---|---|
| Quote | `secondary/quote` | Room CRUD: entity, DAO, type converters (`Date`, `Category`), mapper, `Flow` of rows |
| AccuWeather | `secondary/accuweather` | Retrofit + OkHttp interceptor that adds the API key, city autocomplete search |
| Home (DummyJSON) | `primary/home` | A second Retrofit client (`DummyService`) called from the Home ViewModel |

### 7.5 Permissions and device features

| Lesson | Package | What you learn |
|---|---|---|
| Permissions | `secondary/permission` | Request one and many permissions directly in a Fragment, rationale dialog, open app settings |
| Permissions 2 | `secondary/permission` | The same flows moved into reusable `LifecycleObserver` classes (`lifecycleobserver/`) |
| Last Known Location | `secondary/location` | Fused Location Provider via `MapManager`: last location, live updates, turn on GPS |
| Bluetooth | `secondary/bluetooth` | Bluetooth Classic (paired devices, discovery, connect) and BLE scanning, Lottie scan animation |
| Photo from Device Storage | `secondary/gallery` | Read photos from MediaStore by folder, camera capture, permission bottom sheet |

### 7.6 Media and background work

| Lesson | Package | What you learn |
|---|---|---|
| Music Player | `secondary/mediaplayer` | `MediaPlayer` in a foreground **and** bound service, custom `MediaStyle` notification with `RemoteViews`, songs from `res/raw` and MediaStore |
| Music Player 2 | `secondary/mediaplayer` | A second version of the same player with its own service (`MediaPlayerService2`) and receiver |
| Download with WorkManager | `secondary/downloadwithworker` | `@HiltWorker` that downloads a PDF or video, shows progress in a notification, returns the saved `Uri` |
| Download Manager | `secondary/downloadmanager` | The system `DownloadManager` plus a `BroadcastReceiver` for completion |

### 7.7 Kotlin and coroutines

| Lesson | Package | What you learn |
|---|---|---|
| Kotlin Flow | `secondary/kotlinflow` | Flow builders (`flow`, `flowOf`, `asFlow`), terminal operators (`collect`, `first`, `toList`, `reduce`), `async` and `Job` control |

Related utilities: `util/CoroutineUtil.kt` (five ways to merge async results), `util/FlowUtil.kt` (combine more than five flows).

## 8. Bottom Bar Tabs

| Tab | Fragment | Content |
|---|---|---|
| **Home** | `primary/home/HomeFragment` | Lesson grid with search, sort options, shimmer loading, a floating draggable box, a digital clock and a premium card |
| **Insight** | `primary/chart/InsightFragment` | Chip tabs: Component gallery, Calendar, Line / Ring / Bar / Area charts, Analogue clock, Colour palette, Photo adjust test (contrast, white balance) |
| **Article** | `primary/article/ArticleFragment` | Showcase of graphic views: Atomic loader, DNA helix, analogue clock, sunrise canvas, before / after image swipe |
| **Settings** | `primary/setting/SettingFragment` | Dark mode, language, app icon, rate dialog + Play In-App Review, disclaimer |

Settings also contains `SettingIconFragment`, which switches the launcher icon at runtime through `<activity-alias>` entries in the manifest.

## 9. Reusable Component Library

Everything in this section is a standalone composable you can copy into another project. Most have a `@Preview`.

### 9.1 Core components

`ui/component/` holds the app-wide building blocks:

| Group | Components |
|---|---|
| Top bars | `CoreTopBar`, `CoreTopBar2`–`4`, `CoreTopBarWithHomeShortcut`, `CoreTopBarWithScrollBehavior`, `CustomizeTopBar` |
| Bottom bar | `CoreBottomBar` |
| Dialogs and sheets | `CoreDialog`, `CoreDialogLayout`, `CoreAlertDialog`, `CoreMaterialDialog`, `CoreTextAnimationDialog`, `CoreBottomSheet` |
| Buttons | `CoreButton`, `CoreFloatingButton`, `CoreExpandableFloatingButton` |
| Lifecycle | `LifecycleComposable` (observe `ON_START` / `ON_STOP` … from Compose) |
| Pickers | `ui/dialog/WheelTimePickerDialog` (wheel time picker in the ELSA Speak style) |

### 9.2 Custom views

`ui/view/` holds self-contained widgets:

| Group | Components |
|---|---|
| Buttons and toggles | `GlowingButton`, `ButtonBookmark`, `ButtonFavorite`, `ButtonShare`, `ButtonTextSetting`, `CupertinoSwitch`, `CustomizedCheckbox`, `AnimatedThemeSwitcher` |
| Progress and loading | `AnimatedProgressBar`, `CustomizedProgressBar`, `GradientProgressIndicator`, `ProgressBar`, `AtomicLoader`, `LoadingDialog`, `AnimationCircularWave`, `DNAHelix` |
| Clocks and calendars | `AnalogueClock`, `DigitalClock`, `DigitalClock2`, `DigitalClock3`, `CollapsedCalendar`, `ExpandedCalendar` |
| Text effects | `ShimmerText`, `DottedTextAnimation`, `FlexibleTextField`, `wordbyword/` (char-by-char, word-by-word, line-by-line, chunked animations) |
| Cards and surfaces | `AnimatedBorderCard`, `RainbowCard`, `OverlappingRoundedBackground`, `HomePremium`, `SwipeToReveal` |
| Images | `ImageBeforeAndAfterSwipeAnimation`, `ImageBeforeAndAfterSwipeSection`, `PhotoSelection` |
| Layout | `TableLayout`, `TableLayoutRow`, `HeaderAndLayout`, `ContextualFlowRowSample`, `floatingdraggablebox/` (drag a bubble, drop it on a close area) |
| Data display | `RateBar`, `ColorSquare`, `IndexColorBar`, `HighlightIndexColor`, `WeatherSunrise` |
| Settings rows | `SettingItemWithDropdown`, `SubsettingElement` |
| Feedback | `CountdownSnackbar` |

### 9.3 Charts

`ui/fragment/primary/chart/chartcomponent/` — every chart is drawn with `Canvas`:

| Chart | File |
|---|---|
| Line chart | `LineChart.kt`, `LineChartComponent.kt` |
| Curvy line (cubic Bézier) | `CurvyLineChart.kt` |
| Bar chart | `BarChart.kt` |
| Blood pressure (range bars) | `BloodPressureChart.kt`, `BloodPressureChart2Element.kt` |
| Pie / donut with gaps | `PieChart.kt`, `DonutChart.kt`, `DonutChart2.kt` |
| Ring chart | `RingChart.kt`, `RingChart2.kt` |
| Hourly weather chart | `WeatherHourlyChart.kt`, `WeatherHourlyChartElement.kt` |

### 9.4 Modifiers

`ui/modifier/`:

| Modifier | Effect |
|---|---|
| `BorderWithAnimatedGradient` | Rotating gradient border |
| `DashedBorder` | Dashed outline |
| `ShimmerEffect` | Shimmer placeholder while loading |
| `PulseEffect` | Pulsing scale / glow |
| `Shadow` | Custom colored shadow |
| `PremiumWatermark` | Premium badge overlay |
| `DebounceClickable` | Ignores rapid repeated taps |
| `Conditional` | Apply a modifier only when a condition is true |

Tooltips live in `ui/theme/tooltip/` (`TooltipPopup` with alignment and offset providers).

### 9.5 Utilities

`util/`:

| File | Purpose |
|---|---|
| `AppUtil` | UUID, open website, open app settings, Figma-to-screen size scaling |
| `CanvasUtil` | dp ↔ px conversion |
| `CoroutineUtil` | Five ways to merge asynchronous results |
| `DateUtil`, `LocalDateUtil` | Date differences, formats, epoch helpers |
| `EncrytionUtil` | AES encrypt / decrypt example |
| `FileUtil` | File names, video folder, share video, MediaStore id |
| `FlowUtil` | `combine` for more than five flows |
| `LanguageUtil` | Apply a locale to a `Context` |
| `NavigationUtil` | `canNavigate` guard and `popUpTo` notes |
| `NetworkUtil` | Internet connectivity check |
| `PermissionUtil` | Check notification, camera, storage and multiple permissions |
| `SharingUtil` | Compose an email, copy to clipboard, share the app |
| `SmartFunction` | Validation and user-friendly error messages |
| `SystemBarUtil` | Hide status bar / navigation bar |
| `TuplesUtil` | `Quadruple` and larger tuples |

## 10. Notifications and Lock Screen

| Feature | Files | How it works |
|---|---|---|
| Daily notification | `notification/NotificationManager.kt`, `NotificationReceiver.kt` | `AlarmManager.setExact(RTC_WAKEUP)` schedules the reminder (falls back to `set` when exact alarms are not allowed); the receiver posts it |
| First-install notification | `NotificationAfter5MinuteReceiver.kt` | One notification shortly after the first install |
| Progress notification | `NotificationManager.fireProgressNotification` | Ongoing notification with a progress bar |
| Lock-screen notification | `LockscreenManager.kt`, `LockscreenReceiver.kt`, `ui/activity/LockscreenActivity.kt` | Full-screen intent opens an activity with `setShowWhenLocked`; falls back to a normal notification |
| Notification permission | `lifecycleobserver/NotificationLifecycleObserver.kt` | Requests `POST_NOTIFICATIONS` on Android 13+ with rationale and settings dialogs |

## 11. Localization and Theming

- **Languages**: English (default), German, French, Hindi, Japanese, Korean and Vietnamese (`res/values-*`). The device language is listed first. The choice is stored in DataStore and applied in `CoreActivity.attachBaseContext`.
- **Theme**: `ui/theme/` holds colors, typography (`Type.kt`), animation specs and `CustomizedTheme`.
- **Dark mode**: toggled in Settings and applied by `CoreFragment`.

## 12. Performance: Baseline Profile

The `:baselineprofile` module contains:

| File | Purpose |
|---|---|
| `BaselineProfileGenerator.kt` | Walks the startup path to generate a Baseline Profile for `:app` |
| `StartupBenchmarks.kt` | Macrobenchmark that compares startup time with and without the profile |

`:app` consumes the profile through `baselineProfile(project(":baselineprofile"))` and ships it with `profileinstaller`.

## 13. Project Structure

```
Jetpack-Compose/
├── app/                              # The application module
├── baselineProfile/                  # Baseline Profile generator + startup benchmarks
├── gradle/libs.versions.toml         # Version catalog: every library and plugin version
├── photo/                            # Images used by this README
└── .claude/                          # Coding rules and skills for AI assistants
```

Source root: `app/src/main/java/com/example/jetpack/`

```
com/example/jetpack/
├── backgroundwork/                   # Services and receivers that run outside a screen
│   ├── MediaPlayerService(2).kt      #   foreground + bound music service
│   ├── MediaPlayerReceiver(2).kt     #   play / pause / next from the notification
│   └── DownloadManagerReceiver.kt    #   "download finished" broadcast
├── configuration/                    # App-wide constants and static lists
│                                     #   (Constant, Language, Logo, CountryCode, BottomBarDestination)
├── core/base/                        # Base classes every screen extends
│                                     #   (CoreActivity, CoreFragment, CoreLayout, CoreBehavior)
├── data/                             # Local data
│   ├── database/                     #   Room: QuoteDatabase, dao/, entity/, converter/, mapper/
│   ├── datastore/                    #   DataStore: SettingDatastore, TutorialDatastore
│   └── repository/                   #   Quote, Setting, Tutorial, Weather, Dummy repositories
│       └── impl/                     #   repository implementations
├── domain/                           # Pure Kotlin business types
│   ├── catalog/                      #   SampleRegistry: index of every lesson
│   ├── enums/                        #   HomeShortcut, ChartShortcut, SortOption, Category …
│   ├── manager/                      #   MapManager: Fused Location wrapper
│   └── model/                        #   Quote, ChartElement, Status …
├── injection/                        # Hilt
│   ├── module/                       #   Database, Network, Repository, Dispatcher modules
│   └── qualifier/                    #   @IoDispatcher, @DefaultDispatcher
├── lifecycleobserver/                # Reusable permission requests (one, many, location, notification)
├── network/                          # Remote data
│   ├── adapter/                      #   AccuWeatherFactory: unwraps and logs a Retrofit Response
│   ├── dto/                          #   weather/, dummy/ response models
│   ├── interceptor/                  #   AccuWeatherInterceptor: adds the API key
│   ├── service/                      #   AccuWeatherService, DummyService
│   └── util/                         #   safeApiCall: Response<T> → Status<T>
├── notification/                     # Daily, first-install, progress and lock-screen notifications
├── ui/                               # Everything the user sees
│   ├── activity/                     #   LockscreenActivity
│   ├── component/                    #   Core* shared composables (top bars, dialogs, buttons)
│   ├── dialog/                       #   WheelTimePickerDialog
│   ├── fragment/
│   │   ├── primary/                  #   bottom bar tabs: home, chart (Insight), article, setting
│   │   └── secondary/                #   one package per lesson + splash, intro, language
│   ├── modifier/                     #   custom Modifier extensions
│   ├── theme/                        #   colors, typography, theme, tooltip/
│   └── view/                         #   standalone custom widgets (clocks, loaders, cards …)
├── util/                             # Helpers and extensions
│   └── inputtransformation/          #   DigitsOnly, VerificationCode input rules
├── worker/                           # DownloadFileWorker (WorkManager + Hilt)
├── JetpackApplication.kt             # @HiltAndroidApp application class
├── JetpackDocument.kt                # Reading list (see section 16)
└── MainActivity.kt                   # Single activity: NavHost + bottom bar
```

A lesson package looks like this:

```
ui/fragment/secondary/gallery/
├── component/                        # composables used only by this screen
│   ├── GalleryFolders.kt
│   ├── GalleryGridView.kt
│   ├── GalleryPermissionBottomSheet.kt
│   ├── PhotoTabElement.kt
│   └── RequestGalleryPermission.kt
├── GalleryFragment.kt                # entry point + stateless layout + previews
├── GalleryUiState.kt                 # immutable screen state
└── GalleryViewModel.kt               # loads photos, exposes StateFlow
```

## 14. Adding a New Lesson

1. Create a package under `ui/fragment/secondary/<lesson>/` with `XxxFragment`, `XxxViewModel` (if needed) and `component/`.
2. Add the destination and a `toXxx` action to `res/navigation/navigation_graph.xml`.
3. Add an entry to `domain/enums/HomeShortcut.kt` (icon + title string).
4. Append the title to the end of `res/values/strings.xml`.
5. Handle the shortcut in `HomeFragment` with `safeNavigate(R.id.toXxx)`.
6. Register a `SampleMeta` in `domain/catalog/SampleRegistry.kt` so search can find it.
7. Update the [Lessons](#7-lessons) table in this README.

## 15. Work in Progress

| Item | Status |
|---|---|
| Tooltip shortcut | Listed on Home but not wired yet (shows a toast). Code: `ui/theme/tooltip/TooltipPopup.kt` |
| Shared Element Transition shortcut | Listed on Home but has no screen yet |
| Mecca Compass | Destination exists; the screen is an empty placeholder |
| `secondary/basictextfield/` | Older copy of the Basic Text Field lesson, not in the navigation graph. `secondary/textfield/` is the live one |

## 16. Reading List

`JetpackDocument.kt` collects the articles behind many lessons.

<details>
<summary><b>Show all 42 articles</b></summary>

| # | Topic |
|---|---|
| 1 | [16 Kotlin Tips and Tricks for Efficient Programming](https://archive.is/SHiOv#selection-1151.0-1155.11) |
| 2 | [Theming like a Pro in Android Compose](https://freedium.cfd/https://medium.com/@niranjanky14/theming-in-android-compose-made-easy-09eb95fecf0e) |
| 3 | [How to Create an Animated Theme Switcher in Jetpack Compose](https://medium.com/@kappdev/how-to-create-an-animated-theme-switcher-in-jetpack-compose-32c9457ee6c3) |
| 4 | [Understanding and Handling 11 Side-Effects in Jetpack Compose](https://medium.com/@ramadan123sayed/understanding-and-handling-side-effects-in-jetpack-compose-a-comprehensive-guide-85b219e495f1) |
| 5 | [LiveData is dead! Long live StateFlow!](https://medium.com/@joostklitsie/livedata-is-dead-long-live-stateflow-ecd7e03b1777) |
| 6 | [How to Create an Animated Gender Selector in Jetpack Compose](https://medium.com/@kappdev/how-to-create-an-animated-gender-selector-in-jetpack-compose-a2f1cdb1afc0) |
| 7 | [How to Create Animated Gender Sign in Jetpack Compose](https://medium.com/@kappdev/how-to-create-animated-gender-sign-in-jetpack-compose-4d7e1809f4d1) |
| 8 | [Understanding Window Insets in Jetpack Compose](https://proandroiddev.com/understanding-window-insets-in-jetpack-compose-46245b9ceffa) |
| 9 | [Reducing recomposition on Bubbles Animations](https://engineering.traderepublic.com/jetpack-compose-reducing-recomposition-on-bubbles-animations-a68eafef6bf2) |
| 10 | [Shimmer & Shadow Loading Effect Animation](https://proandroiddev.com/shimmer-shadow-loading-effect-animation-with-jetpack-compose-f4b3de28dc2b) |
| 11 | [Create an Atomic Loader in Jetpack Compose](https://medium.com/@kappdev/how-to-create-an-atomic-loader-in-jetpack-compose-cbf0a74aa5fa) |
| 12 | [Handling State in Jetpack Compose](https://medium.com/@harmanpreet.khera/handling-state-in-jetpack-compose-best-practices-ccffc09ce46a) |
| 13 | [Sealed Class vs Sealed Interface vs Enum in Kotlin](https://abhinaygupta1998.medium.com/sealed-class-vs-sealed-interface-vs-enum-in-kotlin-f65a0d139141) |
| 14 | [Creating a Time Picker Based on the ELSA Speak App](https://proandroiddev.com/creating-a-time-picker-based-on-the-elsa-speak-app-using-jetpack-compose-de931876acac) |
| 15 | [FloatingActionButton Lost in Jetpack Compose? A Material 3 Bottom Sheet Rescue Mission](https://medium.com/app-dev-pro-tips/floatingactionbutton-lost-in-jetpack-compose-a-material-3-bottom-sheet-rescue-mission-d77c959ef596) |
| 16 | [Kotlin Coroutines: how to merge asynchronous results](https://medium.com/@jtlalka/kotlin-coroutines-how-to-merge-asynchronous-results-in-kotlin-674c3079edba) |
| 17 | [Advanced Multithreading and Concurrency in Android](https://medium.com/@harmanpreet.khera/advanced-multithreading-and-concurrency-in-android-5ef9c085face) |
| 18 | [Lines in Android Jetpack Compose](https://medium.com/@ridvanozcan48/lines-in-android-jetpack-compose-bd4bdc64ef4c) |
| 19 | [Kotlin Cheat Sheet for Android Developers](https://android-dev-nexus.medium.com/kotlin-cheat-sheet-for-android-developers-02882a996bc1) |
| 20 | [Mastering Kotlin Scope Functions](https://android-dev-nexus.medium.com/mastering-kotlin-scope-functions-94c8de1fc4ed) |
| 21 | [7 Kotlin Flow Operators that you must know](https://medium.com/@myofficework000/7-kotlin-flow-operators-that-you-must-know-62eb726e3ff4) |
| 22 | [Custom Sliders in Jetpack Compose](https://medium.com/@anandgaur22/custom-sliders-in-jetpack-compose-9bc8deb32482) |
| 23 | [7 ADB Commands Every Android Developer Should Know](https://proandroiddev.com/7-adb-commands-every-android-developer-should-know-fbfb419e8827) |
| 24 | [Coroutines Cheat Sheet for Android Developers](https://android-dev-nexus.medium.com/coroutines-cheat-sheet-for-android-developers-6ee2049b268c) |
| 25 | [Top 5 Icon Resources for all your Projects](https://medium.com/@stevdza-san/top-5-icon-resources-for-all-your-projects-82551fe7362d) |
| 26 | [Security: The two best ways to secure your API keys](https://proandroiddev.com/the-2-best-ways-to-secure-your-api-keys-in-android-projects-12f6a9939053) |
| 27 | [Should You Use UUIDs for Database Keys?](https://medium.com/@tecnicorabi/should-you-use-uuids-for-database-keys-597b15b000bb) |
| 28 | [Modeling ViewModel State in Android](https://medium.com/clean-android-dev/modeling-viewmodel-state-in-android-a-guide-to-clean-scalable-patterns-d42932ade940) |
| 29 | [Security: SSL Pinning in Android](https://medium.com/@anandgaur22/ssl-pinning-in-android-14851dc41703) |
| 30 | [Simple Event Bus in Android using coroutines and flows](https://blog.tinaciousdesign.com/simple-event-bus-android-coroutines-flows) |
| 31 | [Building a Custom Vertical Progress Bar in Jetpack Compose](https://proandroiddev.com/building-a-custom-vertical-progress-bar-in-jetpack-compose-433a387998ef) |
| 32 | [Effective exception handling in Kotlin coroutines](https://proandroiddev.com/effective-exception-handling-in-kotlin-coroutines-android-6925d0d95c21) |
| 33 | [Mastering Global State Management](https://proandroiddev.com/mastering-global-state-management-in-android-with-jetpack-compose-e99350fad822) |
| 34 | [Guide to Foreground Services on Android 14](https://medium.com/@domen.lanisnik/guide-to-foreground-services-on-android-9d0127dc8f9a) |
| 35 | [Android Studio Productivity Tips](https://proandroiddev.com/android-studio-productivity-tips-a5e49d4ef55c) |
| 36 | [Kotlin operator functions](https://nameisjayant.medium.com/8-kotlin-secrets-no-one-talks-about-but-you-should-know-8d40c941ba92) |
| 37 | [Tinder-like swipe gestures](https://blogs.kaffein.in/building-a-swipe-left--right-card-experience-in-jetpackcompose.html) |
| 38 | [Glance widget: load an image from a URI](https://stackoverflow.com/questions/74361073/how-to-load-images-from-the-internet-into-a-widget-with-jetpack-glance) |
| 39 | [Building an Animated Stacked Bar Chart in Jetpack Compose](https://jyotimoykashyap.medium.com/building-an-animated-stacked-bar-chart-in-jetpack-compose-9ad2b2acc5e1) |
| 40 | [How to Create Chrome-Inspired Custom Tabs in Jetpack Compose](https://medium.com/@kappdev/how-to-create-chrome-inspired-custom-tabs-in-jetpack-compose-c4cb8aa33e91) |
| 41 | [Shadows: What's new in Jetpack Compose (August '25)](https://android-developers.googleblog.com/2025/08/whats-new-in-jetpack-compose-august-25-release.html) |
| 42 | [How to Create a Custom Corner Badge in Jetpack Compose](https://medium.com/@kappdev/how-to-create-a-custom-corner-badge-in-jetpack-compose-acabd4cc04ca) |

</details>

## 17. AI-Assisted Development

The repository carries coding rules for AI assistants such as Claude Code:

| Path | Content |
|---|---|
| `CLAUDE.md` | Output style and Kotlin rules for the assistant |
| `.claude/rules/` | Project rules: skeleton layout, Compose UI, repository, use case and ViewModel layers, Figma design system |
| `.claude/skills/` | Reusable skills (TDD, diagnose, PRD and issue writing, architecture review …) |

---

<p align="center">
    Made with 💘 and Kotlin by <a href="https://github.com/Phong-Kaster">Phong-Kaster</a>
</p>
