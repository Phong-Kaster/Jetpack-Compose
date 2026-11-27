<h1 align="center">Jetpack Compose Playground<br/>
    <sub>Bộ khung (skeleton) và danh mục bài học cho ứng dụng Android viết bằng Kotlin và Jetpack Compose</sub>
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
## Mục lục

<p align="center">
    <a href="README.md">English</a> ·
    <b>Tiếng Việt</b> ·
    <a href="README.es.md">Español</a> ·
    <a href="README.de.md">Deutsch</a> ·
    <a href="README.fr.md">Français</a> ·
    <a href="README.hi.md">हिन्दी</a> ·
    <a href="README.ja.md">日本語</a> ·
    <a href="README.ko.md">한국어</a>
</p>

1. [Giới thiệu](#1-introduction)
2. [Điểm nổi bật](#2-highlights)
3. [Bắt đầu](#3-getting-started)
   - [Yêu cầu](#31-requirements)
   - [Build và chạy](#32-build-and-run)
   - [Cấu hình](#33-configuration)
4. [Công nghệ sử dụng](#4-tech-stack)
5. [Kiến trúc](#5-architecture)
   - [Các tầng](#51-layers)
   - [Cấu trúc một màn hình](#52-screen-anatomy)
   - [Điều hướng](#53-navigation)
   - [Danh mục mẫu](#54-sample-catalog)
6. [Luồng ứng dụng](#6-app-flow)
7. [Bài học](#7-lessons)
   - [UI và bố cục](#71-ui-and-layout)
   - [Animation và đồ họa](#72-animation-and-graphics)
   - [Nhập văn bản](#73-text-input)
   - [Dữ liệu và mạng](#74-data-and-networking)
   - [Quyền và tính năng thiết bị](#75-permissions-and-device-features)
   - [Media và tác vụ nền](#76-media-and-background-work)
   - [Kotlin và coroutine](#77-kotlin-and-coroutines)
8. [Các tab của thanh điều hướng dưới](#8-bottom-bar-tabs)
9. [Thư viện component tái sử dụng](#9-reusable-component-library)
   - [Component lõi](#91-core-components)
   - [Custom view](#92-custom-views)
   - [Biểu đồ](#93-charts)
   - [Modifier](#94-modifiers)
   - [Tiện ích](#95-utilities)
10. [Thông báo và màn hình khóa](#10-notifications-and-lock-screen)
11. [Đa ngôn ngữ và giao diện (theme)](#11-localization-and-theming)
12. [Hiệu năng: Baseline Profile](#12-performance-baseline-profile)
13. [Cấu trúc dự án](#13-project-structure)
14. [Thêm một bài học mới](#14-adding-a-new-lesson)
15. [Đang thực hiện](#15-work-in-progress)
16. [Danh sách bài đọc](#16-reading-list)
17. [Phát triển với sự hỗ trợ của AI](#17-ai-assisted-development)

---

<a id="1-introduction"></a>
## 1. Giới thiệu

Repository này vừa là một **sân chơi Jetpack Compose (playground)** vừa là một **bộ khung ứng dụng tái sử dụng được (skeleton)**.

- **Với vai trò playground**, dự án tập hợp hơn 25 bài học chạy được: animation, top bar thu gọn, quyền (permission), Bluetooth, vị trí, phát media, tải xuống, Room, DataStore, Retrofit, Kotlin Flow và nhiều nội dung khác. Mỗi bài học là một màn hình bạn có thể mở từ lưới Home, đọc và sao chép vào dự án của mình.
- **Với vai trò skeleton**, dự án có sẵn những phần mà hầu hết ứng dụng production đều cần: splash, chọn ngôn ngữ, onboarding, thanh điều hướng dưới (bottom bar) với bốn tab, cài đặt (dark mode, ngôn ngữ, biểu tượng ứng dụng, đánh giá), thông báo hằng ngày, thông báo trên màn hình khóa, dependency injection (tiêm phụ thuộc) và Baseline Profile.

Thiết kế lấy cảm hứng từ [Android Architecture Samples](https://github.com/android/architecture-samples). Mã nguồn 100% Kotlin. Mỗi màn hình là một UI Compose được đặt trong một Fragment.

<a id="2-highlights"></a>
## 2. Điểm nổi bật

| Lĩnh vực | Bạn nhận được gì |
|---|---|
| **UI** | Jetpack Compose + Material 3, hơn 40 custom view, 13 biểu đồ vẽ bằng Canvas, 8 modifier tự viết |
| **Kiến trúc** | Single activity, màn hình Fragment + Compose, MVVM, repository pattern, Hilt DI |
| **Bất đồng bộ** | Kotlin Coroutines, `Flow` / `StateFlow`, wrapper `safeApiCall` chuyển một `Response` của Retrofit thành `Status` |
| **Dữ liệu** | Room (trích dẫn), DataStore Preferences (cài đặt, trạng thái hướng dẫn), Retrofit + OkHttp (AccuWeather, DummyJSON) |
| **Thiết bị** | Runtime permission, Fused Location, Bluetooth Classic + BLE, camera, MediaStore, WebView |
| **Tác vụ nền** | Foreground service phát media, WorkManager (`@HiltWorker`), DownloadManager, exact alarm |
| **Khung ứng dụng** | Splash, giới thiệu, chọn ngôn ngữ, bottom bar, cài đặt, đổi biểu tượng launcher động, đánh giá trong ứng dụng (in-app review) |
| **Chất lượng** | Baseline Profile + module benchmark khởi động, `@Preview` cho hầu hết composable |
| **Đa ngôn ngữ** | Tiếng Anh, Đức, Pháp, Hindi, Nhật, Hàn, Việt |

<a id="3-getting-started"></a>
## 3. Bắt đầu

<a id="31-requirements"></a>
### 3.1 Yêu cầu

| Công cụ | Phiên bản |
|---|---|
| Android Studio | Bản phát hành hỗ trợ AGP 9.5 |
| JDK | 17 trở lên (dự án biên dịch ra bytecode Java 17) |
| Gradle | 9.9.2 (được wrapper tự tải về) |
| Android SDK | compileSdk 37, minSdk 26 |

<a id="32-build-and-run"></a>
### 3.2 Build và chạy

```bash
git clone https://github.com/Phong-Kaster/Jetpack-Compose.git
cd Jetpack-Compose

# Cài bản debug lên thiết bị hoặc máy ảo đang kết nối
./gradlew :app:installDebug

# Các task hữu ích khác
./gradlew :app:assembleRelease        # APK release (bật R8)
./gradlew :app:testDebugUnitTest      # unit test
./gradlew :baselineprofile:connectedCheck  # tạo Baseline Profile / chạy benchmark
```

Mỗi APK được bản build đổi tên thành `Bundeswehr_v<versionName>(<versionCode>)_<timestamp>-<variant>.apk`.

<a id="33-configuration"></a>
### 3.3 Cấu hình

| Thiết lập | Vị trí | Ghi chú |
|---|---|---|
| API key AccuWeather | `configuration/Constant.kt` → `ACCU_WEATHER_KEY` | Thay bằng key của riêng bạn lấy từ [AccuWeather Developer](https://developer.accuweather.com/). Với dự án thực tế, nên dùng `local.properties` + `BuildConfig`. |
| Base URL AccuWeather | `configuration/Constant.kt` → `ACCU_WEATHER_URL` | `https://dataservice.accuweather.com` |
| Base URL DummyJSON | `injection/module/NetworkModule.kt` | `https://dummyjson.com/` |
| Phiên bản thư viện | `gradle/libs.versions.toml` | Một version catalog duy nhất cho mọi dependency và plugin |

<a id="4-tech-stack"></a>
## 4. Công nghệ sử dụng

| Hạng mục | Thư viện |
|---|---|
| Ngôn ngữ và build | Kotlin 2.4.21, AGP 9.5.3, Gradle 9.9.2, KSP 2.3.14, version catalog |
| UI | Compose BOM 2026.08.00, Material 3, Material Icons Extended, ConstraintLayout Compose (MotionLayout), Accompanist Permissions |
| Kiến trúc | AndroidX Lifecycle 2.10, Navigation Component 2.9.7 (Fragment + Safe Args), Hilt 2.60.1 |
| Dữ liệu | Room 2.8.4, DataStore Preferences 1.2.1, Kotlinx Collections Immutable |
| Mạng | Retrofit 2.12, OkHttp logging interceptor 5.3, Gson |
| Hình ảnh và animation | Coil 2 (+ GIF), Glide 4, Lottie 6 |
| Media | `MediaPlayer` + thông báo `MediaStyle`, Media3 ExoPlayer / UI / Session |
| Tác vụ nền | WorkManager 2.11 + Hilt Work, `AlarmManager`, `DownloadManager` |
| Google Play | Play Services Location, Play In-App Review |
| Hiệu năng | Baseline Profile 1.4, Macrobenchmark, ProfileInstaller |

<a id="5-architecture"></a>
## 5. Kiến trúc

<a id="51-layers"></a>
### 5.1 Các tầng

```
┌──────────────────────────────────────────────────────────┐
│ ui/        Fragments → Compose layouts → ViewModels      │  trình bày
├──────────────────────────────────────────────────────────┤
│ domain/    models, enums, managers, sample catalog       │  kiểu nghiệp vụ
├──────────────────────────────────────────────────────────┤
│ data/      repositories → Room DAO / DataStore           │  dữ liệu cục bộ
│ network/   Retrofit services, DTOs, interceptors         │  dữ liệu từ xa
├──────────────────────────────────────────────────────────┤
│ injection/ Hilt modules wire everything together         │  DI
└──────────────────────────────────────────────────────────┘
```

- **ViewModel** cung cấp trạng thái màn hình dưới dạng `StateFlow` và không bao giờ đụng tới Android view.
- **Repository** là cửa duy nhất để đi vào Room, DataStore và Retrofit. Một màn hình chỉ hỏi repository, không bao giờ hỏi trực tiếp DAO hay service.
- **Lời gọi mạng** đi qua `safeApiCall` (`network/util/SafeApiFlow.kt`), hàm này bọc một `Response` của Retrofit thành `Status` (`Success` / `Failure`); repository cung cấp nó cho ViewModel dưới dạng `Flow`.
- **Hilt** cung cấp database, hai Retrofit client, các repository và các coroutine dispatcher (`@IoDispatcher`, `@DefaultDispatcher`).

<a id="52-screen-anatomy"></a>
### 5.2 Cấu trúc một màn hình

Mọi màn hình đều có cùng một hình dạng:

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

| Lớp cơ sở | Nhiệm vụ |
|---|---|
| `core/base/CoreActivity` | Áp dụng ngôn ngữ đã lưu trong `attachBaseContext`, cung cấp hàm hỗ trợ toast + kiểm tra kết nối |
| `core/base/CoreFragment` | Tạo `ComposeView`, áp dụng theme của ứng dụng và dark mode, cung cấp `LocalNavController` và `LocalLocale` |
| `core/base/CoreLayout` | Wrapper của `Scaffold` với top bar, bottom bar, FAB và inset edge-to-edge |
| `core/base/CoreBehavior` | Hợp đồng dùng chung: `showToast`, `isInternetConnected`, `trackEvent` |

<a id="53-navigation"></a>
### 5.3 Điều hướng

- Một activity duy nhất (`MainActivity`) chứa một `NavHostFragment` và bottom bar.
- Mọi destination nằm trong `res/navigation/navigation_graph.xml`. Destination bắt đầu là màn hình splash; các màn hình giới thiệu là một nested graph (đồ thị lồng).
- Điều hướng bằng `safeNavigate(R.id.toXxx)` trong `util/NavigationUtil.kt`; hàm này kiểm tra destination hiện tại trước, nên chạm hai lần liên tiếp cũng không bao giờ gây crash.

<a id="54-sample-catalog"></a>
### 5.4 Danh mục mẫu

`domain/catalog/SampleRegistry.kt` là mục lục của mọi bài học. Mỗi `HomeShortcut` có một mục `SampleMeta` gồm:

- file nguồn chính để đọc hoặc sao chép,
- các file liên quan khác,
- chủ đề (`SampleTopic`: `ANIMATION`, `PERMISSION`, `MEDIA`, …),
- các bí danh tìm kiếm.

Thanh tìm kiếm ở Home dùng registry này, nên khi gõ `retrofit`, `pinch` hay `qibla` bạn sẽ tìm thấy đúng bài học.

<a id="6-app-flow"></a>
## 6. Luồng ứng dụng

```
Splash ──(first launch)──► Intro language ──► Intro pages ──► Home
   │                                                         ▲
   └──────────────────(returning user)───────────────────────┘

Home ◄──► Insight ◄──► Article ◄──► Settings      (thanh điều hướng dưới)
  │
  └──► Lesson screens (Home grid, search, sort)
```

| Màn hình | Package | Hiển thị gì |
|---|---|---|
| Splash | `secondary/splash` | Dựa vào DataStore để quyết định mở màn hình giới thiệu hay Home |
| Intro language | `secondary/intro` | Chọn ngôn ngữ ở lần chạy đầu tiên |
| Intro | `secondary/intro` | Pager onboarding với page indicator tự viết |
| Language | `secondary/language` | Đổi ngôn ngữ ứng dụng sau này, từ Settings |

<a id="7-lessons"></a>
## 7. Bài học

Mọi bài học dưới đây đều mở từ lưới **Home**. Đường dẫn tính tương đối từ `app/src/main/java/com/example/jetpack/ui/fragment/`.

<a id="71-ui-and-layout"></a>
### 7.1 UI và bố cục

| Bài học | Package | Bạn học được gì |
|---|---|---|
| Collapsible Top Bar | `secondary/collapsibletopbar/collapsible` | Thu gọn header theo vị trí cuộn bằng `derivedStateOf` |
| Collapsible Top Bar 2 | `secondary/collapsibletopbar/collapsible2` | `TopAppBarDefaults.enterAlwaysScrollBehavior` của Material 3 + `nestedScroll` |
| Collapsible Top Bar 3 | `secondary/collapsibletopbar/collapsible3` | `NestedScrollConnection` tự viết với header đầy đủ / thu nhỏ |
| Collapsible Top Bar 4 | `secondary/collapsibletopbar/collapsible4` | `LargeTopAppBar` chuyển đổi theo `firstVisibleItemIndex` |
| Collapsible Top Bar 5 | `secondary/collapsibletopbar/collapsible5` | Thanh mở rộng / thu gọn kết hợp với `enterAlwaysScrollBehavior` |
| Instagram Carousel | `secondary/instagramcarousel` | Pager có dot indicator kéo được khi nhấn giữ |
| Pinch to Zoom | `secondary/pitchtozoom` | `detectTransformGestures` + `graphicsLayer` để phóng to, kéo và xoay |
| Tutorial | `secondary/tutorial` | Hướng dẫn từng bước kiểu spotlight, dựng bằng `onGloballyPositioned` và `boundsInRoot`, trạng thái lưu trong DataStore |
| Login | `secondary/login` | Form đăng nhập: `FocusRequester`, `KeyboardOptions`, IME action, `PasswordVisualTransformation` |
| WebView | `secondary/webview` | `WebView` bên trong `AndroidView`, các callback của `WebViewClient`, xử lý nút back bằng `BackHandler` |

<a id="72-animation-and-graphics"></a>
### 7.2 Animation và đồ họa

| Bài học | Package | Bạn học được gì |
|---|---|---|
| Animation | `secondary/animation` | `tween`, `spring`, `keyframes`, `infiniteRepeatable`, `drawBehind`, `drawWithContent`, phép biến đổi affine |
| Motion Layout | `secondary/motionlayout` | `MotionLayout` của Compose với motion scene JSON5 (`res/raw/*.json5`) và trạng thái toolbar thu gọn tự viết |

<a id="73-text-input"></a>
### 7.3 Nhập văn bản

| Bài học | Package | Bạn học được gì |
|---|---|---|
| Basic Text Field (state) | `secondary/textfield` | `BasicTextField` với `TextFieldState`: `InputTransformation`, các transformation có sẵn, `OutputTransformation`, autofill, tự co giãn cỡ chữ, một text field linh hoạt |

Các quy tắc nhập liệu tái sử dụng được nằm trong `util/inputtransformation/` (`DigitsOnlyTransformation`, `VerificationCodeTransformation`).

<a id="74-data-and-networking"></a>
### 7.4 Dữ liệu và mạng

| Bài học | Package | Bạn học được gì |
|---|---|---|
| Quote | `secondary/quote` | CRUD với Room: entity, DAO, type converter (`Date`, `Category`), mapper, `Flow` các dòng dữ liệu |
| AccuWeather | `secondary/accuweather` | Retrofit + OkHttp interceptor tự thêm API key, tìm kiếm thành phố có gợi ý tự động |
| Home (DummyJSON) | `primary/home` | Retrofit client thứ hai (`DummyService`) được gọi từ ViewModel của Home |

<a id="75-permissions-and-device-features"></a>
### 7.5 Quyền và tính năng thiết bị

| Bài học | Package | Bạn học được gì |
|---|---|---|
| Permissions | `secondary/permission` | Xin một hoặc nhiều quyền trực tiếp trong Fragment, dialog giải thích lý do (rationale), mở cài đặt ứng dụng |
| Permissions 2 | `secondary/permission` | Cùng các luồng đó nhưng chuyển vào các lớp `LifecycleObserver` tái sử dụng được (`lifecycleobserver/`) |
| Last Known Location | `secondary/location` | Fused Location Provider qua `MapManager`: vị trí gần nhất, cập nhật trực tiếp, bật GPS |
| Bluetooth | `secondary/bluetooth` | Bluetooth Classic (thiết bị đã ghép đôi, dò tìm, kết nối) và quét BLE, animation quét bằng Lottie |
| Photo from Device Storage | `secondary/gallery` | Đọc ảnh từ MediaStore theo thư mục, chụp bằng camera, bottom sheet xin quyền |

<a id="76-media-and-background-work"></a>
### 7.6 Media và tác vụ nền

| Bài học | Package | Bạn học được gì |
|---|---|---|
| Music Player | `secondary/mediaplayer` | `MediaPlayer` trong một service vừa foreground **vừa** bound, thông báo `MediaStyle` tự thiết kế bằng `RemoteViews`, bài hát từ `res/raw` và MediaStore |
| Music Player 2 | `secondary/mediaplayer` | Phiên bản thứ hai của cùng trình phát với service (`MediaPlayerService2`) và receiver riêng |
| Download with WorkManager | `secondary/downloadwithworker` | `@HiltWorker` tải một file PDF hoặc video, hiển thị tiến trình trong thông báo, trả về `Uri` đã lưu |
| Download Manager | `secondary/downloadmanager` | `DownloadManager` của hệ thống cùng một `BroadcastReceiver` nhận sự kiện hoàn tất |

<a id="77-kotlin-and-coroutines"></a>
### 7.7 Kotlin và coroutine

| Bài học | Package | Bạn học được gì |
|---|---|---|
| Kotlin Flow | `secondary/kotlinflow` | Các flow builder (`flow`, `flowOf`, `asFlow`), toán tử kết thúc (`collect`, `first`, `toList`, `reduce`), điều khiển `async` và `Job` |

Tiện ích liên quan: `util/CoroutineUtil.kt` (năm cách gộp kết quả bất đồng bộ), `util/FlowUtil.kt` (combine nhiều hơn năm flow).

<a id="8-bottom-bar-tabs"></a>
## 8. Các tab của thanh điều hướng dưới

| Tab | Fragment | Nội dung |
|---|---|---|
| **Home** | `primary/home/HomeFragment` | Lưới bài học có tìm kiếm, tùy chọn sắp xếp, loading shimmer, một hộp nổi kéo thả được, đồng hồ số và thẻ premium |
| **Insight** | `primary/chart/InsightFragment` | Tab dạng chip: thư viện component, lịch, biểu đồ đường / vòng / cột / vùng, đồng hồ kim, bảng màu, thử chỉnh ảnh (độ tương phản, cân bằng trắng) |
| **Article** | `primary/article/ArticleFragment` | Trưng bày các view đồ họa: Atomic loader, chuỗi xoắn DNA, đồng hồ kim, canvas bình minh, vuốt so sánh ảnh trước / sau |
| **Settings** | `primary/setting/SettingFragment` | Dark mode, ngôn ngữ, biểu tượng ứng dụng, dialog đánh giá + Play In-App Review, tuyên bố miễn trừ trách nhiệm |

Settings còn có `SettingIconFragment`, dùng để đổi biểu tượng launcher lúc chạy thông qua các mục `<activity-alias>` trong manifest.

<a id="9-reusable-component-library"></a>
## 9. Thư viện component tái sử dụng

Mọi thứ trong phần này đều là composable độc lập mà bạn có thể sao chép sang dự án khác. Hầu hết đều có `@Preview`.

<a id="91-core-components"></a>
### 9.1 Component lõi

`ui/component/` chứa các khối xây dựng dùng chung cho toàn ứng dụng:

| Nhóm | Component |
|---|---|
| Top bar | `CoreTopBar`, `CoreTopBar2`–`4`, `CoreTopBarWithHomeShortcut`, `CoreTopBarWithScrollBehavior`, `CustomizeTopBar` |
| Bottom bar | `CoreBottomBar` |
| Dialog và sheet | `CoreDialog`, `CoreDialogLayout`, `CoreAlertDialog`, `CoreMaterialDialog`, `CoreTextAnimationDialog`, `CoreBottomSheet` |
| Nút bấm | `CoreButton`, `CoreFloatingButton`, `CoreExpandableFloatingButton` |
| Lifecycle | `LifecycleComposable` (theo dõi `ON_START` / `ON_STOP` … từ Compose) |
| Bộ chọn | `ui/dialog/WheelTimePickerDialog` (bộ chọn giờ dạng bánh xe theo phong cách ELSA Speak) |

<a id="92-custom-views"></a>
### 9.2 Custom view

`ui/view/` chứa các widget độc lập:

| Nhóm | Component |
|---|---|
| Nút bấm và công tắc | `GlowingButton`, `ButtonBookmark`, `ButtonFavorite`, `ButtonShare`, `ButtonTextSetting`, `CupertinoSwitch`, `CustomizedCheckbox`, `AnimatedThemeSwitcher` |
| Tiến trình và loading | `AnimatedProgressBar`, `CustomizedProgressBar`, `GradientProgressIndicator`, `ProgressBar`, `AtomicLoader`, `LoadingDialog`, `AnimationCircularWave`, `DNAHelix` |
| Đồng hồ và lịch | `AnalogueClock`, `DigitalClock`, `DigitalClock2`, `DigitalClock3`, `CollapsedCalendar`, `ExpandedCalendar` |
| Hiệu ứng chữ | `ShimmerText`, `DottedTextAnimation`, `FlexibleTextField`, `wordbyword/` (animation theo từng ký tự, từng từ, từng dòng, từng cụm) |
| Thẻ và bề mặt | `AnimatedBorderCard`, `RainbowCard`, `OverlappingRoundedBackground`, `HomePremium`, `SwipeToReveal` |
| Hình ảnh | `ImageBeforeAndAfterSwipeAnimation`, `ImageBeforeAndAfterSwipeSection`, `PhotoSelection` |
| Bố cục | `TableLayout`, `TableLayoutRow`, `HeaderAndLayout`, `ContextualFlowRowSample`, `floatingdraggablebox/` (kéo một bong bóng, thả vào vùng đóng) |
| Hiển thị dữ liệu | `RateBar`, `ColorSquare`, `IndexColorBar`, `HighlightIndexColor`, `WeatherSunrise` |
| Dòng cài đặt | `SettingItemWithDropdown`, `SubsettingElement` |
| Phản hồi | `CountdownSnackbar` |

<a id="93-charts"></a>
### 9.3 Biểu đồ

`ui/fragment/primary/chart/chartcomponent/` — mọi biểu đồ đều được vẽ bằng `Canvas`:

| Biểu đồ | File |
|---|---|
| Biểu đồ đường | `LineChart.kt`, `LineChartComponent.kt` |
| Đường cong (Bézier bậc ba) | `CurvyLineChart.kt` |
| Biểu đồ cột | `BarChart.kt` |
| Huyết áp (cột khoảng giá trị) | `BloodPressureChart.kt`, `BloodPressureChart2Element.kt` |
| Biểu đồ tròn / donut có khoảng hở | `PieChart.kt`, `DonutChart.kt`, `DonutChart2.kt` |
| Biểu đồ vòng | `RingChart.kt`, `RingChart2.kt` |
| Biểu đồ thời tiết theo giờ | `WeatherHourlyChart.kt`, `WeatherHourlyChartElement.kt` |

<a id="94-modifiers"></a>
### 9.4 Modifier

`ui/modifier/`:

| Modifier | Hiệu ứng |
|---|---|
| `BorderWithAnimatedGradient` | Viền gradient xoay tròn |
| `DashedBorder` | Viền nét đứt |
| `ShimmerEffect` | Placeholder shimmer trong lúc tải |
| `PulseEffect` | Phóng to / phát sáng theo nhịp |
| `Shadow` | Bóng đổ màu tùy chỉnh |
| `PremiumWatermark` | Lớp phủ huy hiệu premium |
| `DebounceClickable` | Bỏ qua các lần chạm lặp lại quá nhanh |
| `Conditional` | Chỉ áp dụng một modifier khi điều kiện đúng |

Tooltip nằm trong `ui/theme/tooltip/` (`TooltipPopup` với các provider căn lề và độ lệch).

<a id="95-utilities"></a>
### 9.5 Tiện ích

`util/`:

| File | Mục đích |
|---|---|
| `AppUtil` | UUID, mở website, mở cài đặt ứng dụng, quy đổi kích thước từ Figma sang màn hình |
| `CanvasUtil` | Chuyển đổi dp ↔ px |
| `CoroutineUtil` | Năm cách gộp kết quả bất đồng bộ |
| `DateUtil`, `LocalDateUtil` | Chênh lệch ngày, định dạng, hàm hỗ trợ epoch |
| `EncrytionUtil` | Ví dụ mã hóa / giải mã AES |
| `FileUtil` | Tên file, thư mục video, chia sẻ video, id trong MediaStore |
| `FlowUtil` | `combine` cho nhiều hơn năm flow |
| `LanguageUtil` | Áp dụng locale cho một `Context` |
| `NavigationUtil` | Chốt chặn `canNavigate` và ghi chú về `popUpTo` |
| `NetworkUtil` | Kiểm tra kết nối Internet |
| `PermissionUtil` | Kiểm tra quyền thông báo, camera, bộ nhớ và nhiều quyền cùng lúc |
| `SharingUtil` | Soạn email, sao chép vào clipboard, chia sẻ ứng dụng |
| `SmartFunction` | Kiểm tra dữ liệu hợp lệ và thông báo lỗi thân thiện với người dùng |
| `SystemBarUtil` | Ẩn thanh trạng thái / thanh điều hướng |
| `TuplesUtil` | `Quadruple` và các tuple lớn hơn |

<a id="10-notifications-and-lock-screen"></a>
## 10. Thông báo và màn hình khóa

| Tính năng | File | Cách hoạt động |
|---|---|---|
| Thông báo hằng ngày | `notification/NotificationManager.kt`, `NotificationReceiver.kt` | `AlarmManager.setExact(RTC_WAKEUP)` lên lịch nhắc nhở (chuyển sang `set` khi exact alarm không được phép); receiver sẽ đăng thông báo |
| Thông báo sau lần cài đặt đầu tiên | `NotificationAfter5MinuteReceiver.kt` | Một thông báo ngay sau lần cài đặt đầu tiên |
| Thông báo tiến trình | `NotificationManager.fireProgressNotification` | Thông báo liên tục (ongoing) có thanh tiến trình |
| Thông báo trên màn hình khóa | `LockscreenManager.kt`, `LockscreenReceiver.kt`, `ui/activity/LockscreenActivity.kt` | Full-screen intent mở một activity có `setShowWhenLocked`; nếu không được thì chuyển sang thông báo thường |
| Quyền thông báo | `lifecycleobserver/NotificationLifecycleObserver.kt` | Xin quyền `POST_NOTIFICATIONS` trên Android 13+ kèm dialog giải thích lý do và dialog mở cài đặt |

<a id="11-localization-and-theming"></a>
## 11. Đa ngôn ngữ và giao diện (theme)

- **Ngôn ngữ**: tiếng Anh (mặc định), Đức, Pháp, Hindi, Nhật, Hàn và Việt (`res/values-*`). Ngôn ngữ của thiết bị được xếp đầu danh sách. Lựa chọn được lưu trong DataStore và áp dụng trong `CoreActivity.attachBaseContext`.
- **Theme**: `ui/theme/` chứa màu sắc, kiểu chữ (`Type.kt`), thông số animation và `CustomizedTheme`.
- **Dark mode**: bật/tắt trong Settings và được `CoreFragment` áp dụng.

<a id="12-performance-baseline-profile"></a>
## 12. Hiệu năng: Baseline Profile

Module `:baselineprofile` gồm:

| File | Mục đích |
|---|---|
| `BaselineProfileGenerator.kt` | Đi qua luồng khởi động để tạo Baseline Profile cho `:app` |
| `StartupBenchmarks.kt` | Macrobenchmark so sánh thời gian khởi động khi có và không có profile |

`:app` sử dụng profile thông qua `baselineProfile(project(":baselineprofile"))` và đóng gói nó cùng `profileinstaller`.

<a id="13-project-structure"></a>
## 13. Cấu trúc dự án

```
Jetpack-Compose/
├── app/                              # Module ứng dụng
├── baselineProfile/                  # Trình tạo Baseline Profile + benchmark khởi động
├── gradle/libs.versions.toml         # Version catalog: phiên bản mọi thư viện và plugin
├── photo/                            # Hình ảnh dùng trong README này
└── .claude/                          # Quy tắc viết code và skill cho trợ lý AI
```

Thư mục mã nguồn gốc: `app/src/main/java/com/example/jetpack/`

```
com/example/jetpack/
├── backgroundwork/                   # Service và receiver chạy bên ngoài màn hình
│   ├── MediaPlayerService(2).kt      #   service nhạc foreground + bound
│   ├── MediaPlayerReceiver(2).kt     #   phát / tạm dừng / bài tiếp theo từ thông báo
│   └── DownloadManagerReceiver.kt    #   broadcast "tải xong"
├── configuration/                    # Hằng số và danh sách tĩnh dùng toàn ứng dụng
│                                     #   (Constant, Language, Logo, CountryCode, BottomBarDestination)
├── core/base/                        # Lớp cơ sở mà mọi màn hình kế thừa
│                                     #   (CoreActivity, CoreFragment, CoreLayout, CoreBehavior)
├── data/                             # Dữ liệu cục bộ
│   ├── database/                     #   Room: QuoteDatabase, dao/, entity/, converter/, mapper/
│   ├── datastore/                    #   DataStore: SettingDatastore, TutorialDatastore
│   └── repository/                   #   repository Quote, Setting, Tutorial, Weather, Dummy
│       └── impl/                     #   phần cài đặt của repository
├── domain/                           # Kiểu nghiệp vụ thuần Kotlin
│   ├── catalog/                      #   SampleRegistry: mục lục mọi bài học
│   ├── enums/                        #   HomeShortcut, ChartShortcut, SortOption, Category …
│   ├── manager/                      #   MapManager: wrapper của Fused Location
│   └── model/                        #   Quote, ChartElement, Status …
├── injection/                        # Hilt
│   ├── module/                       #   module Database, Network, Repository, Dispatcher
│   └── qualifier/                    #   @IoDispatcher, @DefaultDispatcher
├── lifecycleobserver/                # Xin quyền tái sử dụng được (một, nhiều, vị trí, thông báo)
├── network/                          # Dữ liệu từ xa
│   ├── adapter/                      #   AccuWeatherFactory: mở gói và ghi log Response của Retrofit
│   ├── dto/                          #   model phản hồi weather/, dummy/
│   ├── interceptor/                  #   AccuWeatherInterceptor: thêm API key
│   ├── service/                      #   AccuWeatherService, DummyService
│   └── util/                         #   safeApiCall: Response<T> → Status<T>
├── notification/                     # Thông báo hằng ngày, sau cài đặt, tiến trình và màn hình khóa
├── ui/                               # Mọi thứ người dùng nhìn thấy
│   ├── activity/                     #   LockscreenActivity
│   ├── component/                    #   composable dùng chung Core* (top bar, dialog, nút bấm)
│   ├── dialog/                       #   WheelTimePickerDialog
│   ├── fragment/
│   │   ├── primary/                  #   tab bottom bar: home, chart (Insight), article, setting
│   │   └── secondary/                #   mỗi bài học một package + splash, intro, language
│   ├── modifier/                     #   các extension Modifier tự viết
│   ├── theme/                        #   màu sắc, kiểu chữ, theme, tooltip/
│   └── view/                         #   widget độc lập tự viết (đồng hồ, loader, thẻ …)
├── util/                             # Hàm hỗ trợ và extension
│   └── inputtransformation/          #   quy tắc nhập DigitsOnly, VerificationCode
├── worker/                           # DownloadFileWorker (WorkManager + Hilt)
├── JetpackApplication.kt             # Lớp application có @HiltAndroidApp
├── JetpackDocument.kt                # Danh sách bài đọc (xem mục 16)
└── MainActivity.kt                   # Single activity: NavHost + bottom bar
```

Một package bài học trông như sau:

```
ui/fragment/secondary/gallery/
├── component/                        # composable chỉ màn hình này dùng
│   ├── GalleryFolders.kt
│   ├── GalleryGridView.kt
│   ├── GalleryPermissionBottomSheet.kt
│   ├── PhotoTabElement.kt
│   └── RequestGalleryPermission.kt
├── GalleryFragment.kt                # điểm vào + layout không trạng thái + preview
├── GalleryUiState.kt                 # trạng thái màn hình bất biến
└── GalleryViewModel.kt               # tải ảnh, cung cấp StateFlow
```

<a id="14-adding-a-new-lesson"></a>
## 14. Thêm một bài học mới

1. Tạo package dưới `ui/fragment/secondary/<lesson>/` gồm `XxxFragment`, `XxxViewModel` (nếu cần) và `component/`.
2. Thêm destination và action `toXxx` vào `res/navigation/navigation_graph.xml`.
3. Thêm một mục vào `domain/enums/HomeShortcut.kt` (icon + chuỗi tiêu đề).
4. Thêm tiêu đề vào cuối `res/values/strings.xml`.
5. Xử lý shortcut trong `HomeFragment` bằng `safeNavigate(R.id.toXxx)`.
6. Đăng ký một `SampleMeta` trong `domain/catalog/SampleRegistry.kt` để chức năng tìm kiếm tìm thấy bài học.
7. Cập nhật bảng [Bài học](#7-lessons) trong README này.

<a id="15-work-in-progress"></a>
## 15. Đang thực hiện

| Hạng mục | Trạng thái |
|---|---|
| Shortcut Tooltip | Có trên Home nhưng chưa được nối (chỉ hiện toast). Code: `ui/theme/tooltip/TooltipPopup.kt` |
| Shortcut Shared Element Transition | Có trên Home nhưng chưa có màn hình |
| Mecca Compass | Đã có destination; màn hình chỉ là placeholder trống |
| `secondary/basictextfield/` | Bản sao cũ của bài Basic Text Field, không nằm trong navigation graph. `secondary/textfield/` là bản đang dùng |

<a id="16-reading-list"></a>
## 16. Danh sách bài đọc

`JetpackDocument.kt` tập hợp các bài viết làm nền tảng cho nhiều bài học.

<details>
<summary><b>Hiện tất cả 42 bài viết</b></summary>

| # | Chủ đề |
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
## 17. Phát triển với sự hỗ trợ của AI

Repository này có sẵn các quy tắc viết code dành cho trợ lý AI như Claude Code:

| Đường dẫn | Nội dung |
|---|---|
| `CLAUDE.md` | Phong cách đầu ra và quy tắc Kotlin cho trợ lý |
| `.claude/rules/` | Quy tắc dự án: bố cục skeleton, Compose UI, các tầng repository, use case và ViewModel, design system Figma |
| `.claude/skills/` | Các skill tái sử dụng được (TDD, chẩn đoán lỗi, viết PRD và issue, đánh giá kiến trúc …) |

---

<p align="center">
    Được làm với 💘 và Kotlin bởi <a href="https://github.com/Phong-Kaster">Phong-Kaster</a>
</p>
