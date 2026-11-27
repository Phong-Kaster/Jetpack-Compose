<h1 align="center">Jetpack Compose Playground<br/>
    <sub>Kotlin और Jetpack Compose से बने Android apps के लिए एक skeleton और lesson catalog</sub>
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

<a id="table-of-contents"></a>
## विषय-सूची

<p align="center">
    <a href="README.md">English</a> ·
    <a href="README.vi.md">Tiếng Việt</a> ·
    <a href="README.es.md">Español</a> ·
    <a href="README.de.md">Deutsch</a> ·
    <a href="README.fr.md">Français</a> ·
    <b>हिन्दी</b> ·
    <a href="README.ja.md">日本語</a> ·
    <a href="README.ko.md">한국어</a>
</p>

1. [परिचय](#1-introduction)
2. [मुख्य बातें](#2-highlights)
3. [शुरुआत कैसे करें](#3-getting-started)
   - [ज़रूरतें](#31-requirements)
   - [Build और run](#32-build-and-run)
   - [Configuration](#33-configuration)
4. [Tech Stack](#4-tech-stack)
5. [Architecture](#5-architecture)
   - [Layers](#51-layers)
   - [Screen की बनावट](#52-screen-anatomy)
   - [Navigation](#53-navigation)
   - [Sample catalog](#54-sample-catalog)
6. [App Flow](#6-app-flow)
7. [Lessons](#7-lessons)
   - [UI और layout](#71-ui-and-layout)
   - [Animation और graphics](#72-animation-and-graphics)
   - [Text input](#73-text-input)
   - [Data और networking](#74-data-and-networking)
   - [Permissions और device features](#75-permissions-and-device-features)
   - [Media और background work](#76-media-and-background-work)
   - [Kotlin और coroutines](#77-kotlin-and-coroutines)
8. [Bottom Bar Tabs](#8-bottom-bar-tabs)
9. [दोबारा इस्तेमाल होने वाली Component Library](#9-reusable-component-library)
   - [Core components](#91-core-components)
   - [Custom views](#92-custom-views)
   - [Charts](#93-charts)
   - [Modifiers](#94-modifiers)
   - [Utilities](#95-utilities)
10. [Notifications और Lock Screen](#10-notifications-and-lock-screen)
11. [Localization और Theming](#11-localization-and-theming)
12. [Performance: Baseline Profile](#12-performance-baseline-profile)
13. [Project की संरचना](#13-project-structure)
14. [नया Lesson जोड़ना](#14-adding-a-new-lesson)
15. [जिन पर काम चल रहा है](#15-work-in-progress)
16. [पढ़ने की सूची](#16-reading-list)
17. [AI की मदद से Development](#17-ai-assisted-development)

---

<a id="1-introduction"></a>
## 1. परिचय

यह repository एक साथ **Jetpack Compose playground** भी है और एक **reusable app skeleton** भी।

- **Playground के रूप में**, इसमें 25 से ज़्यादा runnable lessons हैं: animations, collapsing top bars, permissions, Bluetooth, location, media playback, downloads, Room, DataStore, Retrofit, Kotlin Flow, और भी बहुत कुछ। हर lesson एक screen है जिसे आप Home grid से खोल सकते हैं, पढ़ सकते हैं और अपने project में copy कर सकते हैं।
- **Skeleton के रूप में**, इसमें वे हिस्से पहले से मौजूद हैं जिनकी ज़्यादातर production apps को ज़रूरत होती है: splash, language picker, onboarding, चार tabs वाला bottom bar, settings (dark mode, language, app icon, rating), daily notifications, lock-screen notification, dependency injection, और एक Baseline Profile।

इसका design [Android Architecture Samples](https://github.com/android/architecture-samples) से प्रेरित है। Code 100% Kotlin में है। हर screen एक Compose UI है जो एक Fragment के अंदर host होती है।

<a id="2-highlights"></a>
## 2. मुख्य बातें

| क्षेत्र | आपको क्या मिलता है |
|---|---|
| **UI** | Jetpack Compose + Material 3, 40+ custom views, 13 Canvas charts, 8 custom modifiers |
| **Architecture** | Single activity, Fragment + Compose screens, MVVM, repository pattern, Hilt DI |
| **Async** | Kotlin Coroutines, `Flow` / `StateFlow`, एक `safeApiCall` wrapper जो Retrofit `Response` को `Status` में बदल देता है |
| **Data** | Room (quotes), DataStore Preferences (settings, tutorial state), Retrofit + OkHttp (AccuWeather, DummyJSON) |
| **Device** | Runtime permissions, Fused Location, Bluetooth Classic + BLE, camera, MediaStore, WebView |
| **Background** | Foreground media service, WorkManager (`@HiltWorker`), DownloadManager, exact alarms |
| **App shell** | Splash, intro, language picker, bottom bar, settings, dynamic launcher icon, in-app review |
| **Quality** | Baseline Profile + startup benchmark module, ज़्यादातर composables के लिए `@Preview` |
| **Localization** | English, German, French, Hindi, Japanese, Korean, Vietnamese |

<a id="3-getting-started"></a>
## 3. शुरुआत कैसे करें

<a id="31-requirements"></a>
### 3.1 ज़रूरतें

| Tool | Version |
|---|---|
| Android Studio | ऐसा release जो AGP 9.5 को support करता हो |
| JDK | 17 या नया (project Java 17 bytecode को target करता है) |
| Gradle | 9.9.2 (wrapper अपने आप download करता है) |
| Android SDK | compileSdk 36, minSdk 26 |

<a id="32-build-and-run"></a>
### 3.2 Build और run

```bash
git clone https://github.com/Phong-Kaster/Jetpack-Compose.git
cd Jetpack-Compose

# जुड़े हुए device या emulator पर debug build install करें
./gradlew :app:installDebug

# दूसरे काम के tasks
./gradlew :app:assembleRelease        # release APK (R8 enabled)
./gradlew :app:testDebugUnitTest      # unit tests
./gradlew :baselineprofile:connectedCheck  # Baseline Profile generate करें / benchmarks चलाएँ
```

Build हर APK का नाम बदलकर `Bundeswehr_v<versionName>(<versionCode>)_<timestamp>-<variant>.apk` कर देता है।

<a id="33-configuration"></a>
### 3.3 Configuration

| Setting | जगह | Notes |
|---|---|---|
| AccuWeather API key | `configuration/Constant.kt` → `ACCU_WEATHER_KEY` | इसे [AccuWeather Developer](https://developer.accuweather.com/) से मिली अपनी key से बदलें। असली projects में `local.properties` + `BuildConfig` का इस्तेमाल बेहतर है। |
| AccuWeather base URL | `configuration/Constant.kt` → `ACCU_WEATHER_URL` | `https://dataservice.accuweather.com` |
| DummyJSON base URL | `injection/module/NetworkModule.kt` | `https://dummyjson.com/` |
| Library versions | `gradle/libs.versions.toml` | हर dependency और plugin के लिए एक ही version catalog |

<a id="4-tech-stack"></a>
## 4. Tech Stack

| Category | Libraries |
|---|---|
| Language और build | Kotlin 2.4.21, AGP 9.5.3, Gradle 9.9.2, KSP 2.3.14, version catalog |
| UI | Compose BOM 2026.03.01, Material 3, Material Icons Extended, ConstraintLayout Compose (MotionLayout), Accompanist Permissions |
| Architecture | AndroidX Lifecycle 2.10, Navigation Component 2.9.7 (Fragment + Safe Args), Hilt 2.60.1 |
| Data | Room 2.8.4, DataStore Preferences 1.2.1, Kotlinx Collections Immutable |
| Network | Retrofit 2.12, OkHttp logging interceptor 5.3, Gson |
| Images और animation | Coil 2 (+ GIF), Glide 4, Lottie 6 |
| Media | `MediaPlayer` + `MediaStyle` notification, Media3 ExoPlayer / UI / Session |
| Background | WorkManager 2.11 + Hilt Work, `AlarmManager`, `DownloadManager` |
| Google Play | Play Services Location, Play In-App Review |
| Performance | Baseline Profile 1.4, Macrobenchmark, ProfileInstaller |

<a id="5-architecture"></a>
## 5. Architecture

<a id="51-layers"></a>
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

- **ViewModels** screen state को `StateFlow` के रूप में expose करते हैं और Android views को कभी नहीं छूते।
- **Repositories** ही Room, DataStore और Retrofit तक पहुँचने का इकलौता रास्ता हैं। Screen हमेशा repository से माँगती है, कभी सीधे DAO या service से नहीं।
- **Network calls** `safeApiCall` (`network/util/SafeApiFlow.kt`) से होकर जाती हैं, जो Retrofit `Response` को `Status` (`Success` / `Failure`) में wrap करता है; repositories इसे ViewModels को `Flow` के रूप में देती हैं।
- **Hilt** database, दोनों Retrofit clients, repositories और coroutine dispatchers (`@IoDispatcher`, `@DefaultDispatcher`) provide करता है।

<a id="52-screen-anatomy"></a>
### 5.2 Screen की बनावट

हर screen एक ही ढाँचे पर चलती है:

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

| Base class | काम |
|---|---|
| `core/base/CoreActivity` | `attachBaseContext` में saved language लागू करता है, toast + connectivity helpers देता है |
| `core/base/CoreFragment` | `ComposeView` बनाता है, app theme और dark mode लागू करता है, `LocalNavController` और `LocalLocale` provide करता है |
| `core/base/CoreLayout` | Top bar, bottom bar, FAB और edge-to-edge insets के साथ एक `Scaffold` wrapper |
| `core/base/CoreBehavior` | Shared contract: `showToast`, `isInternetConnected`, `trackEvent` |

<a id="53-navigation"></a>
### 5.3 Navigation

- एक ही activity (`MainActivity`) में एक `NavHostFragment` और bottom bar होते हैं।
- सारे destinations `res/navigation/navigation_graph.xml` में हैं। Start destination splash screen है; intro screens एक nested graph हैं।
- `util/NavigationUtil.kt` के `safeNavigate(R.id.toXxx)` से navigate करें; यह पहले current destination check करता है, इसलिए double tap से कभी crash नहीं होता।

<a id="54-sample-catalog"></a>
### 5.4 Sample catalog

`domain/catalog/SampleRegistry.kt` हर lesson का index है। हर `HomeShortcut` की एक `SampleMeta` entry होती है जिसमें ये होते हैं:

- पढ़ने या copy करने के लिए main source file,
- उससे जुड़ी अतिरिक्त files,
- topics (`SampleTopic`: `ANIMATION`, `PERMISSION`, `MEDIA`, …),
- search aliases।

Home का search bar इसी registry का इस्तेमाल करता है, इसलिए `retrofit`, `pinch` या `qibla` type करने पर सही lesson मिल जाता है।

<a id="6-app-flow"></a>
## 6. App Flow

```
Splash ──(first launch)──► Intro language ──► Intro pages ──► Home
   │                                                         ▲
   └──────────────────(returning user)───────────────────────┘

Home ◄──► Insight ◄──► Article ◄──► Settings      (bottom bar)
  │
  └──► Lesson screens (Home grid, search, sort)
```

| Screen | Package | क्या दिखाती है |
|---|---|---|
| Splash | `secondary/splash` | DataStore के आधार पर तय करती है कि intro खोलना है या Home |
| Intro language | `secondary/intro` | पहली बार चलाने पर language picker |
| Intro | `secondary/intro` | Custom page indicator वाला onboarding pager |
| Language | `secondary/language` | बाद में Settings से app की language बदलना |

<a id="7-lessons"></a>
## 7. Lessons

नीचे दिया हर lesson **Home** grid से खुलता है। Paths `app/src/main/java/com/example/jetpack/ui/fragment/` के relative हैं।

<a id="71-ui-and-layout"></a>
### 7.1 UI और layout

| Lesson | Package | आप क्या सीखते हैं |
|---|---|---|
| Collapsible Top Bar | `secondary/collapsibletopbar/collapsible` | `derivedStateOf` से scroll position के आधार पर header को collapse करना |
| Collapsible Top Bar 2 | `secondary/collapsibletopbar/collapsible2` | Material 3 `TopAppBarDefaults.enterAlwaysScrollBehavior` + `nestedScroll` |
| Collapsible Top Bar 3 | `secondary/collapsibletopbar/collapsible3` | Full / minimized headers के साथ हाथ से लिखा गया `NestedScrollConnection` |
| Collapsible Top Bar 4 | `secondary/collapsibletopbar/collapsible4` | `firstVisibleItemIndex` पर switch होने वाला `LargeTopAppBar` |
| Collapsible Top Bar 5 | `secondary/collapsibletopbar/collapsible5` | `enterAlwaysScrollBehavior` के साथ expanded / collapsed bars का combination |
| Instagram Carousel | `secondary/instagramcarousel` | Long-press वाले, draggable dot indicator के साथ pager |
| Pinch to Zoom | `secondary/pitchtozoom` | Zoom, pan और rotate के लिए `detectTransformGestures` + `graphicsLayer` |
| Tutorial | `secondary/tutorial` | `onGloballyPositioned` और `boundsInRoot` से बना step-by-step spotlight tutorial, state DataStore में save होती है |
| Login | `secondary/login` | Login form: `FocusRequester`, `KeyboardOptions`, IME actions, `PasswordVisualTransformation` |
| WebView | `secondary/webview` | `AndroidView` के अंदर `WebView`, `WebViewClient` callbacks, `BackHandler` से back handling |

<a id="72-animation-and-graphics"></a>
### 7.2 Animation और graphics

| Lesson | Package | आप क्या सीखते हैं |
|---|---|---|
| Animation | `secondary/animation` | `tween`, `spring`, `keyframes`, `infiniteRepeatable`, `drawBehind`, `drawWithContent`, affine transforms |
| Motion Layout | `secondary/motionlayout` | JSON5 motion scenes (`res/raw/*.json5`) और custom collapsing toolbar state के साथ Compose `MotionLayout` |

<a id="73-text-input"></a>
### 7.3 Text input

| Lesson | Package | आप क्या सीखते हैं |
|---|---|---|
| Basic Text Field (state) | `secondary/textfield` | `TextFieldState` के साथ `BasicTextField`: `InputTransformation`, built-in transformations, `OutputTransformation`, autofill, auto-size, एक flexible text field |

दोबारा इस्तेमाल होने वाले input rules `util/inputtransformation/` (`DigitsOnlyTransformation`, `VerificationCodeTransformation`) में हैं।

<a id="74-data-and-networking"></a>
### 7.4 Data और networking

| Lesson | Package | आप क्या सीखते हैं |
|---|---|---|
| Quote | `secondary/quote` | Room CRUD: entity, DAO, type converters (`Date`, `Category`), mapper, rows का `Flow` |
| AccuWeather | `secondary/accuweather` | Retrofit + OkHttp interceptor जो API key जोड़ता है, city autocomplete search |
| Home (DummyJSON) | `primary/home` | Home ViewModel से call होने वाला दूसरा Retrofit client (`DummyService`) |

<a id="75-permissions-and-device-features"></a>
### 7.5 Permissions और device features

| Lesson | Package | आप क्या सीखते हैं |
|---|---|---|
| Permissions | `secondary/permission` | Fragment में सीधे एक और कई permissions माँगना, rationale dialog, app settings खोलना |
| Permissions 2 | `secondary/permission` | वही flows, reusable `LifecycleObserver` classes (`lifecycleobserver/`) में ले जाए गए |
| Last Known Location | `secondary/location` | `MapManager` के ज़रिए Fused Location Provider: last location, live updates, GPS चालू करना |
| Bluetooth | `secondary/bluetooth` | Bluetooth Classic (paired devices, discovery, connect) और BLE scanning, Lottie scan animation |
| Photo from Device Storage | `secondary/gallery` | MediaStore से folder के हिसाब से photos पढ़ना, camera capture, permission bottom sheet |

<a id="76-media-and-background-work"></a>
### 7.6 Media और background work

| Lesson | Package | आप क्या सीखते हैं |
|---|---|---|
| Music Player | `secondary/mediaplayer` | Foreground **और** bound service में `MediaPlayer`, `RemoteViews` के साथ custom `MediaStyle` notification, `res/raw` और MediaStore से songs |
| Music Player 2 | `secondary/mediaplayer` | उसी player का दूसरा version, अपनी service (`MediaPlayerService2`) और receiver के साथ |
| Download with WorkManager | `secondary/downloadwithworker` | `@HiltWorker` जो PDF या video download करता है, notification में progress दिखाता है, saved `Uri` लौटाता है |
| Download Manager | `secondary/downloadmanager` | System का `DownloadManager` और completion के लिए एक `BroadcastReceiver` |

<a id="77-kotlin-and-coroutines"></a>
### 7.7 Kotlin और coroutines

| Lesson | Package | आप क्या सीखते हैं |
|---|---|---|
| Kotlin Flow | `secondary/kotlinflow` | Flow builders (`flow`, `flowOf`, `asFlow`), terminal operators (`collect`, `first`, `toList`, `reduce`), `async` और `Job` control |

जुड़ी हुई utilities: `util/CoroutineUtil.kt` (async results को merge करने के पाँच तरीके), `util/FlowUtil.kt` (पाँच से ज़्यादा flows को combine करना)।

<a id="8-bottom-bar-tabs"></a>
## 8. Bottom Bar Tabs

| Tab | Fragment | Content |
|---|---|---|
| **Home** | `primary/home/HomeFragment` | Search, sort options, shimmer loading, एक floating draggable box, एक digital clock और एक premium card के साथ lesson grid |
| **Insight** | `primary/chart/InsightFragment` | Chip tabs: Component gallery, Calendar, Line / Ring / Bar / Area charts, Analogue clock, Colour palette, Photo adjust test (contrast, white balance) |
| **Article** | `primary/article/ArticleFragment` | Graphic views का showcase: Atomic loader, DNA helix, analogue clock, sunrise canvas, before / after image swipe |
| **Settings** | `primary/setting/SettingFragment` | Dark mode, language, app icon, rate dialog + Play In-App Review, disclaimer |

Settings में `SettingIconFragment` भी है, जो manifest की `<activity-alias>` entries के ज़रिए runtime पर launcher icon बदलता है।

<a id="9-reusable-component-library"></a>
## 9. दोबारा इस्तेमाल होने वाली Component Library

इस section की हर चीज़ एक standalone composable है जिसे आप किसी दूसरे project में copy कर सकते हैं। ज़्यादातर के साथ `@Preview` है।

<a id="91-core-components"></a>
### 9.1 Core components

`ui/component/` में पूरे app में इस्तेमाल होने वाले building blocks हैं:

| Group | Components |
|---|---|
| Top bars | `CoreTopBar`, `CoreTopBar2`–`4`, `CoreTopBarWithHomeShortcut`, `CoreTopBarWithScrollBehavior`, `CustomizeTopBar` |
| Bottom bar | `CoreBottomBar` |
| Dialogs और sheets | `CoreDialog`, `CoreDialogLayout`, `CoreAlertDialog`, `CoreMaterialDialog`, `CoreTextAnimationDialog`, `CoreBottomSheet` |
| Buttons | `CoreButton`, `CoreFloatingButton`, `CoreExpandableFloatingButton` |
| Lifecycle | `LifecycleComposable` (Compose से `ON_START` / `ON_STOP` … observe करना) |
| Pickers | `ui/dialog/WheelTimePickerDialog` (ELSA Speak style का wheel time picker) |

<a id="92-custom-views"></a>
### 9.2 Custom views

`ui/view/` में self-contained widgets हैं:

| Group | Components |
|---|---|
| Buttons और toggles | `GlowingButton`, `ButtonBookmark`, `ButtonFavorite`, `ButtonShare`, `ButtonTextSetting`, `CupertinoSwitch`, `CustomizedCheckbox`, `AnimatedThemeSwitcher` |
| Progress और loading | `AnimatedProgressBar`, `CustomizedProgressBar`, `GradientProgressIndicator`, `ProgressBar`, `AtomicLoader`, `LoadingDialog`, `AnimationCircularWave`, `DNAHelix` |
| Clocks और calendars | `AnalogueClock`, `DigitalClock`, `DigitalClock2`, `DigitalClock3`, `CollapsedCalendar`, `ExpandedCalendar` |
| Text effects | `ShimmerText`, `DottedTextAnimation`, `FlexibleTextField`, `wordbyword/` (char-by-char, word-by-word, line-by-line, chunked animations) |
| Cards और surfaces | `AnimatedBorderCard`, `RainbowCard`, `OverlappingRoundedBackground`, `HomePremium`, `SwipeToReveal` |
| Images | `ImageBeforeAndAfterSwipeAnimation`, `ImageBeforeAndAfterSwipeSection`, `PhotoSelection` |
| Layout | `TableLayout`, `TableLayoutRow`, `HeaderAndLayout`, `ContextualFlowRowSample`, `floatingdraggablebox/` (एक bubble को drag करके close area पर drop करना) |
| Data display | `RateBar`, `ColorSquare`, `IndexColorBar`, `HighlightIndexColor`, `WeatherSunrise` |
| Settings rows | `SettingItemWithDropdown`, `SubsettingElement` |
| Feedback | `CountdownSnackbar` |

<a id="93-charts"></a>
### 9.3 Charts

`ui/fragment/primary/chart/chartcomponent/` — हर chart `Canvas` से draw किया गया है:

| Chart | File |
|---|---|
| Line chart | `LineChart.kt`, `LineChartComponent.kt` |
| Curvy line (cubic Bézier) | `CurvyLineChart.kt` |
| Bar chart | `BarChart.kt` |
| Blood pressure (range bars) | `BloodPressureChart.kt`, `BloodPressureChart2Element.kt` |
| Gaps वाला Pie / donut | `PieChart.kt`, `DonutChart.kt`, `DonutChart2.kt` |
| Ring chart | `RingChart.kt`, `RingChart2.kt` |
| Hourly weather chart | `WeatherHourlyChart.kt`, `WeatherHourlyChartElement.kt` |

<a id="94-modifiers"></a>
### 9.4 Modifiers

`ui/modifier/`:

| Modifier | असर |
|---|---|
| `BorderWithAnimatedGradient` | घूमता हुआ gradient border |
| `DashedBorder` | Dashed outline |
| `ShimmerEffect` | Loading के दौरान shimmer placeholder |
| `PulseEffect` | Pulse करता scale / glow |
| `Shadow` | Custom रंग वाली shadow |
| `PremiumWatermark` | Premium badge overlay |
| `DebounceClickable` | जल्दी-जल्दी बार-बार किए गए taps को ignore करता है |
| `Conditional` | कोई condition true होने पर ही modifier लागू करता है |

Tooltips `ui/theme/tooltip/` (alignment और offset providers के साथ `TooltipPopup`) में हैं।

<a id="95-utilities"></a>
### 9.5 Utilities

`util/`:

| File | उद्देश्य |
|---|---|
| `AppUtil` | UUID, website खोलना, app settings खोलना, Figma-से-screen size scaling |
| `CanvasUtil` | dp ↔ px conversion |
| `CoroutineUtil` | Asynchronous results को merge करने के पाँच तरीके |
| `DateUtil`, `LocalDateUtil` | Dates का अंतर, formats, epoch helpers |
| `EncrytionUtil` | AES encrypt / decrypt का उदाहरण |
| `FileUtil` | File names, video folder, video share करना, MediaStore id |
| `FlowUtil` | पाँच से ज़्यादा flows के लिए `combine` |
| `LanguageUtil` | किसी `Context` पर locale लागू करना |
| `NavigationUtil` | `canNavigate` guard और `popUpTo` notes |
| `NetworkUtil` | Internet connectivity check |
| `PermissionUtil` | Notification, camera, storage और multiple permissions check करना |
| `SharingUtil` | Email लिखना, clipboard पर copy करना, app share करना |
| `SmartFunction` | Validation और user-friendly error messages |
| `SystemBarUtil` | Status bar / navigation bar छिपाना |
| `TuplesUtil` | `Quadruple` और उससे बड़े tuples |

<a id="10-notifications-and-lock-screen"></a>
## 10. Notifications और Lock Screen

| Feature | Files | यह कैसे काम करता है |
|---|---|---|
| Daily notification | `notification/NotificationManager.kt`, `NotificationReceiver.kt` | `AlarmManager.setExact(RTC_WAKEUP)` reminder schedule करता है (exact alarms की अनुमति न होने पर `set` पर fall back करता है); receiver उसे post करता है |
| First-install notification | `NotificationAfter5MinuteReceiver.kt` | पहली बार install होने के कुछ देर बाद एक notification |
| Progress notification | `NotificationManager.fireProgressNotification` | Progress bar के साथ ongoing notification |
| Lock-screen notification | `LockscreenManager.kt`, `LockscreenReceiver.kt`, `ui/activity/LockscreenActivity.kt` | Full-screen intent `setShowWhenLocked` वाली एक activity खोलता है; नहीं तो normal notification पर fall back करता है |
| Notification permission | `lifecycleobserver/NotificationLifecycleObserver.kt` | Android 13+ पर rationale और settings dialogs के साथ `POST_NOTIFICATIONS` माँगता है |

<a id="11-localization-and-theming"></a>
## 11. Localization और Theming

- **Languages**: English (default), German, French, Hindi, Japanese, Korean और Vietnamese (`res/values-*`)। Device की language सबसे ऊपर दिखती है। चुनी गई language DataStore में save होती है और `CoreActivity.attachBaseContext` में लागू होती है।
- **Theme**: `ui/theme/` में colors, typography (`Type.kt`), animation specs और `CustomizedTheme` हैं।
- **Dark mode**: Settings में on/off होता है और `CoreFragment` इसे लागू करता है।

<a id="12-performance-baseline-profile"></a>
## 12. Performance: Baseline Profile

`:baselineprofile` module में ये हैं:

| File | उद्देश्य |
|---|---|
| `BaselineProfileGenerator.kt` | `:app` के लिए Baseline Profile generate करने हेतु startup path पर चलता है |
| `StartupBenchmarks.kt` | Macrobenchmark जो profile के साथ और उसके बिना startup time की तुलना करता है |

`:app` इस profile को `baselineProfile(project(":baselineprofile"))` के ज़रिए इस्तेमाल करता है और `profileinstaller` के साथ ship करता है।

<a id="13-project-structure"></a>
## 13. Project की संरचना

```
Jetpack-Compose/
├── app/                              # Application module
├── baselineProfile/                  # Baseline Profile generator + startup benchmarks
├── gradle/libs.versions.toml         # Version catalog: हर library और plugin का version
├── photo/                            # इस README में इस्तेमाल हुई images
└── .claude/                          # AI assistants के लिए coding rules और skills
```

Source root: `app/src/main/java/com/example/jetpack/`

```
com/example/jetpack/
├── backgroundwork/                   # Services और receivers जो screen के बाहर चलते हैं
│   ├── MediaPlayerService(2).kt      #   foreground + bound music service
│   ├── MediaPlayerReceiver(2).kt     #   notification से play / pause / next
│   └── DownloadManagerReceiver.kt    #   "download finished" broadcast
├── configuration/                    # पूरे app के constants और static lists
│                                     #   (Constant, Language, Logo, CountryCode, BottomBarDestination)
├── core/base/                        # Base classes जिन्हें हर screen extend करती है
│                                     #   (CoreActivity, CoreFragment, CoreLayout, CoreBehavior)
├── data/                             # Local data
│   ├── database/                     #   Room: QuoteDatabase, dao/, entity/, converter/, mapper/
│   ├── datastore/                    #   DataStore: SettingDatastore, TutorialDatastore
│   └── repository/                   #   Quote, Setting, Tutorial, Weather, Dummy repositories
│       └── impl/                     #   repository implementations
├── domain/                           # Pure Kotlin business types
│   ├── catalog/                      #   SampleRegistry: हर lesson का index
│   ├── enums/                        #   HomeShortcut, ChartShortcut, SortOption, Category …
│   ├── manager/                      #   MapManager: Fused Location wrapper
│   └── model/                        #   Quote, ChartElement, Status …
├── injection/                        # Hilt
│   ├── module/                       #   Database, Network, Repository, Dispatcher modules
│   └── qualifier/                    #   @IoDispatcher, @DefaultDispatcher
├── lifecycleobserver/                # Reusable permission requests (एक, कई, location, notification)
├── network/                          # Remote data
│   ├── adapter/                      #   AccuWeatherFactory: Retrofit Response को unwrap और log करता है
│   ├── dto/                          #   weather/, dummy/ response models
│   ├── interceptor/                  #   AccuWeatherInterceptor: API key जोड़ता है
│   ├── service/                      #   AccuWeatherService, DummyService
│   └── util/                         #   safeApiCall: Response<T> → Status<T>
├── notification/                     # Daily, first-install, progress और lock-screen notifications
├── ui/                               # User को दिखने वाली हर चीज़
│   ├── activity/                     #   LockscreenActivity
│   ├── component/                    #   Core* shared composables (top bars, dialogs, buttons)
│   ├── dialog/                       #   WheelTimePickerDialog
│   ├── fragment/
│   │   ├── primary/                  #   bottom bar tabs: home, chart (Insight), article, setting
│   │   └── secondary/                #   हर lesson का एक package + splash, intro, language
│   ├── modifier/                     #   custom Modifier extensions
│   ├── theme/                        #   colors, typography, theme, tooltip/
│   └── view/                         #   standalone custom widgets (clocks, loaders, cards …)
├── util/                             # Helpers और extensions
│   └── inputtransformation/          #   DigitsOnly, VerificationCode input rules
├── worker/                           # DownloadFileWorker (WorkManager + Hilt)
├── JetpackApplication.kt             # @HiltAndroidApp application class
├── JetpackDocument.kt                # पढ़ने की सूची (section 16 देखें)
└── MainActivity.kt                   # Single activity: NavHost + bottom bar
```

एक lesson package कुछ ऐसा दिखता है:

```
ui/fragment/secondary/gallery/
├── component/                        # सिर्फ़ इसी screen में इस्तेमाल होने वाले composables
│   ├── GalleryFolders.kt
│   ├── GalleryGridView.kt
│   ├── GalleryPermissionBottomSheet.kt
│   ├── PhotoTabElement.kt
│   └── RequestGalleryPermission.kt
├── GalleryFragment.kt                # entry point + stateless layout + previews
├── GalleryUiState.kt                 # immutable screen state
└── GalleryViewModel.kt               # photos load करता है, StateFlow expose करता है
```

<a id="14-adding-a-new-lesson"></a>
## 14. नया Lesson जोड़ना

1. `ui/fragment/secondary/<lesson>/` के अंदर `XxxFragment`, `XxxViewModel` (ज़रूरत हो तो) और `component/` के साथ एक package बनाएँ।
2. `res/navigation/navigation_graph.xml` में destination और एक `toXxx` action जोड़ें।
3. `domain/enums/HomeShortcut.kt` में एक entry जोड़ें (icon + title string)।
4. Title को `res/values/strings.xml` के आख़िर में जोड़ें।
5. `HomeFragment` में `safeNavigate(R.id.toXxx)` से shortcut को handle करें।
6. `domain/catalog/SampleRegistry.kt` में एक `SampleMeta` register करें ताकि search उसे ढूँढ सके।
7. इस README की [Lessons](#7-lessons) table update करें।

<a id="15-work-in-progress"></a>
## 15. जिन पर काम चल रहा है

| Item | Status |
|---|---|
| Tooltip shortcut | Home पर दिखता है लेकिन अभी जुड़ा नहीं है (एक toast दिखाता है)। Code: `ui/theme/tooltip/TooltipPopup.kt` |
| Shared Element Transition shortcut | Home पर दिखता है लेकिन इसकी अभी कोई screen नहीं है |
| Mecca Compass | Destination मौजूद है; screen एक खाली placeholder है |
| `secondary/basictextfield/` | Basic Text Field lesson की पुरानी copy, navigation graph में नहीं है। Live वाला `secondary/textfield/` है |

<a id="16-reading-list"></a>
## 16. पढ़ने की सूची

`JetpackDocument.kt` में वे articles इकट्ठा हैं जिन पर कई lessons आधारित हैं।

<details>
<summary><b>सभी 42 articles दिखाएँ</b></summary>

| # | विषय |
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

<a id="17-ai-assisted-development"></a>
## 17. AI की मदद से Development

इस repository में Claude Code जैसे AI assistants के लिए coding rules शामिल हैं:

| Path | Content |
|---|---|
| `CLAUDE.md` | Assistant के लिए output style और Kotlin rules |
| `.claude/rules/` | Project rules: skeleton layout, Compose UI, repository, use case और ViewModel layers, Figma design system |
| `.claude/skills/` | Reusable skills (TDD, diagnose, PRD और issue लिखना, architecture review …) |

---

<p align="center">
    <a href="https://github.com/Phong-Kaster">Phong-Kaster</a> ने 💘 और Kotlin के साथ बनाया
</p>
