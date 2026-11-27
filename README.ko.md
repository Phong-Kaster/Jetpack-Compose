<h1 align="center">Jetpack Compose Playground<br/>
    <sub>Kotlin과 Jetpack Compose로 만드는 Android 앱을 위한 스켈레톤이자 레슨 카탈로그</sub>
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
## 목차

<p align="center">
    <a href="README.md">English</a> ·
    <a href="README.vi.md">Tiếng Việt</a> ·
    <a href="README.es.md">Español</a> ·
    <a href="README.de.md">Deutsch</a> ·
    <a href="README.fr.md">Français</a> ·
    <a href="README.hi.md">हिन्दी</a> ·
    <a href="README.ja.md">日本語</a> ·
    <b>한국어</b>
</p>

1. [소개](#1-introduction)
2. [주요 특징](#2-highlights)
3. [시작하기](#3-getting-started)
   - [요구 사항](#31-requirements)
   - [빌드 및 실행](#32-build-and-run)
   - [설정](#33-configuration)
4. [기술 스택](#4-tech-stack)
5. [아키텍처](#5-architecture)
   - [레이어](#51-layers)
   - [화면 구조](#52-screen-anatomy)
   - [내비게이션](#53-navigation)
   - [샘플 카탈로그](#54-sample-catalog)
6. [앱 흐름](#6-app-flow)
7. [레슨](#7-lessons)
   - [UI 및 레이아웃](#71-ui-and-layout)
   - [애니메이션 및 그래픽](#72-animation-and-graphics)
   - [텍스트 입력](#73-text-input)
   - [데이터 및 네트워킹](#74-data-and-networking)
   - [권한 및 디바이스 기능](#75-permissions-and-device-features)
   - [미디어 및 백그라운드 작업](#76-media-and-background-work)
   - [Kotlin 및 코루틴](#77-kotlin-and-coroutines)
8. [하단 바 탭](#8-bottom-bar-tabs)
9. [재사용 가능한 컴포넌트 라이브러리](#9-reusable-component-library)
   - [핵심 컴포넌트](#91-core-components)
   - [커스텀 뷰](#92-custom-views)
   - [차트](#93-charts)
   - [Modifier](#94-modifiers)
   - [유틸리티](#95-utilities)
10. [알림 및 잠금 화면](#10-notifications-and-lock-screen)
11. [현지화 및 테마](#11-localization-and-theming)
12. [성능: Baseline Profile](#12-performance-baseline-profile)
13. [프로젝트 구조](#13-project-structure)
14. [새 레슨 추가하기](#14-adding-a-new-lesson)
15. [진행 중인 작업](#15-work-in-progress)
16. [읽을거리](#16-reading-list)
17. [AI 지원 개발](#17-ai-assisted-development)

---

<a id="1-introduction"></a>
## 1. 소개

이 저장소는 **Jetpack Compose 플레이그라운드**이면서 동시에 **재사용 가능한 앱 스켈레톤**입니다.

- **플레이그라운드로서**, 실행 가능한 25개 이상의 레슨을 모았습니다. 애니메이션, 접히는 상단 바(collapsing top bar), 권한, Bluetooth, 위치, 미디어 재생, 다운로드, Room, DataStore, Retrofit, Kotlin Flow 등이 있습니다. 각 레슨은 Home 그리드에서 열어 읽어 보고 자신의 프로젝트에 복사할 수 있는 하나의 화면입니다.
- **스켈레톤으로서**, 대부분의 프로덕션 앱에 필요한 부분을 제공합니다. 스플래시, 언어 선택기, 온보딩, 네 개의 탭이 있는 하단 바, 설정(다크 모드, 언어, 앱 아이콘, 평가), 일일 알림, 잠금 화면 알림, 의존성 주입, 그리고 Baseline Profile입니다.

디자인은 [Android Architecture Samples](https://github.com/android/architecture-samples)에서 영감을 받았습니다. 코드는 100% Kotlin입니다. 모든 화면은 Fragment에 호스팅된 Compose UI입니다.

<a id="2-highlights"></a>
## 2. 주요 특징

| 영역 | 제공하는 것 |
|---|---|
| **UI** | Jetpack Compose + Material 3, 40개 이상의 커스텀 뷰, 13개의 Canvas 차트, 8개의 커스텀 Modifier |
| **아키텍처** | 단일 Activity, Fragment + Compose 화면, MVVM, Repository 패턴, Hilt DI |
| **비동기** | Kotlin Coroutines, `Flow` / `StateFlow`, Retrofit `Response`를 `Status`로 바꾸는 `safeApiCall` 래퍼 |
| **데이터** | Room(명언), DataStore Preferences(설정, 튜토리얼 상태), Retrofit + OkHttp(AccuWeather, DummyJSON) |
| **디바이스** | 런타임 권한, Fused Location, Bluetooth Classic + BLE, 카메라, MediaStore, WebView |
| **백그라운드** | 포그라운드 미디어 서비스, WorkManager(`@HiltWorker`), DownloadManager, 정확한 알람(exact alarm) |
| **앱 셸** | 스플래시, 인트로, 언어 선택기, 하단 바, 설정, 동적 런처 아이콘, 인앱 리뷰 |
| **품질** | Baseline Profile + 시작 벤치마크 모듈, 대부분의 Composable에 `@Preview` 제공 |
| **현지화** | 영어, 독일어, 프랑스어, 힌디어, 일본어, 한국어, 베트남어 |

<a id="3-getting-started"></a>
## 3. 시작하기

<a id="31-requirements"></a>
### 3.1 요구 사항

| 도구 | 버전 |
|---|---|
| Android Studio | AGP 9.5를 지원하는 릴리스 |
| JDK | 17 이상(프로젝트는 Java 17 바이트코드를 대상으로 합니다) |
| Gradle | 9.9.2(래퍼가 다운로드합니다) |
| Android SDK | compileSdk 36, minSdk 26 |

<a id="32-build-and-run"></a>
### 3.2 빌드 및 실행

```bash
git clone https://github.com/Phong-Kaster/Jetpack-Compose.git
cd Jetpack-Compose

# 연결된 기기 또는 에뮬레이터에 디버그 빌드를 설치합니다
./gradlew :app:installDebug

# 그 밖의 유용한 태스크
./gradlew :app:assembleRelease        # 릴리스 APK (R8 활성화)
./gradlew :app:testDebugUnitTest      # 단위 테스트
./gradlew :baselineprofile:connectedCheck  # Baseline Profile 생성 / 벤치마크 실행
```

빌드는 모든 APK의 이름을 `Bundeswehr_v<versionName>(<versionCode>)_<timestamp>-<variant>.apk` 형식으로 바꿉니다.

<a id="33-configuration"></a>
### 3.3 설정

| 설정 | 위치 | 참고 |
|---|---|---|
| AccuWeather API 키 | `configuration/Constant.kt` → `ACCU_WEATHER_KEY` | [AccuWeather Developer](https://developer.accuweather.com/)에서 발급받은 본인의 키로 교체하십시오. 실제 프로젝트에서는 `local.properties` + `BuildConfig`를 사용하는 것이 좋습니다. |
| AccuWeather 기본 URL | `configuration/Constant.kt` → `ACCU_WEATHER_URL` | `https://dataservice.accuweather.com` |
| DummyJSON 기본 URL | `injection/module/NetworkModule.kt` | `https://dummyjson.com/` |
| 라이브러리 버전 | `gradle/libs.versions.toml` | 모든 의존성과 플러그인을 관리하는 단일 버전 카탈로그 |

<a id="4-tech-stack"></a>
## 4. 기술 스택

| 분류 | 라이브러리 |
|---|---|
| 언어 및 빌드 | Kotlin 2.4.21, AGP 9.5.3, Gradle 9.9.2, KSP 2.3.14, 버전 카탈로그 |
| UI | Compose BOM 2026.03.01, Material 3, Material Icons Extended, ConstraintLayout Compose (MotionLayout), Accompanist Permissions |
| 아키텍처 | AndroidX Lifecycle 2.10, Navigation Component 2.9.7 (Fragment + Safe Args), Hilt 2.60.1 |
| 데이터 | Room 2.8.4, DataStore Preferences 1.2.1, Kotlinx Collections Immutable |
| 네트워크 | Retrofit 2.12, OkHttp logging interceptor 5.3, Gson |
| 이미지 및 애니메이션 | Coil 2 (+ GIF), Glide 4, Lottie 6 |
| 미디어 | `MediaPlayer` + `MediaStyle` 알림, Media3 ExoPlayer / UI / Session |
| 백그라운드 | WorkManager 2.11 + Hilt Work, `AlarmManager`, `DownloadManager` |
| Google Play | Play Services Location, Play In-App Review |
| 성능 | Baseline Profile 1.4, Macrobenchmark, ProfileInstaller |

<a id="5-architecture"></a>
## 5. 아키텍처

<a id="51-layers"></a>
### 5.1 레이어

```
┌──────────────────────────────────────────────────────────┐
│ ui/        Fragments → Compose layouts → ViewModels      │  프레젠테이션
├──────────────────────────────────────────────────────────┤
│ domain/    models, enums, managers, sample catalog       │  비즈니스 타입
├──────────────────────────────────────────────────────────┤
│ data/      repositories → Room DAO / DataStore           │  로컬 데이터
│ network/   Retrofit services, DTOs, interceptors         │  원격 데이터
├──────────────────────────────────────────────────────────┤
│ injection/ Hilt modules wire everything together         │  DI
└──────────────────────────────────────────────────────────┘
```

- **ViewModel**은 화면 상태를 `StateFlow`로 노출하며, Android View를 직접 다루지 않습니다.
- **Repository**는 Room, DataStore, Retrofit으로 통하는 유일한 문입니다. 화면은 DAO나 서비스가 아니라 항상 Repository에 요청합니다.
- **네트워크 호출**은 `safeApiCall`(`network/util/SafeApiFlow.kt`)을 거칩니다. 이 함수는 Retrofit `Response`를 `Status`(`Success` / `Failure`)로 감싸며, Repository는 이를 `Flow`로 ViewModel에 노출합니다.
- **Hilt**는 데이터베이스, 두 개의 Retrofit 클라이언트, Repository, 그리고 코루틴 디스패처(`@IoDispatcher`, `@DefaultDispatcher`)를 제공합니다.

<a id="52-screen-anatomy"></a>
### 5.2 화면 구조

모든 화면은 같은 형태를 따릅니다.

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

| 기본 클래스 | 역할 |
|---|---|
| `core/base/CoreActivity` | `attachBaseContext`에서 저장된 언어를 적용하고, 토스트 및 네트워크 연결 헬퍼를 제공합니다 |
| `core/base/CoreFragment` | `ComposeView`를 생성하고, 앱 테마와 다크 모드를 적용하며, `LocalNavController`와 `LocalLocale`을 제공합니다 |
| `core/base/CoreLayout` | 상단 바, 하단 바, FAB, edge-to-edge 인셋을 갖춘 `Scaffold` 래퍼입니다 |
| `core/base/CoreBehavior` | 공통 계약: `showToast`, `isInternetConnected`, `trackEvent` |

<a id="53-navigation"></a>
### 5.3 내비게이션

- 하나의 Activity(`MainActivity`)가 `NavHostFragment`와 하단 바를 가집니다.
- 모든 목적지(destination)는 `res/navigation/navigation_graph.xml`에 있습니다. 시작 목적지는 스플래시 화면이며, 인트로 화면들은 중첩 그래프입니다.
- 이동은 `util/NavigationUtil.kt`의 `safeNavigate(R.id.toXxx)`로 합니다. 먼저 현재 목적지를 확인하므로 두 번 탭해도 크래시가 발생하지 않습니다.

<a id="54-sample-catalog"></a>
### 5.4 샘플 카탈로그

`domain/catalog/SampleRegistry.kt`는 모든 레슨의 색인입니다. 각 `HomeShortcut`에는 다음 정보를 담은 `SampleMeta` 항목이 있습니다.

- 읽거나 복사할 주요 소스 파일,
- 관련된 추가 파일,
- 주제(`SampleTopic`: `ANIMATION`, `PERMISSION`, `MEDIA`, …),
- 검색 별칭.

Home 검색 바는 이 레지스트리를 사용하므로, `retrofit`, `pinch`, `qibla`를 입력하면 해당 레슨을 찾을 수 있습니다.

<a id="6-app-flow"></a>
## 6. 앱 흐름

```
Splash ──(first launch)──► Intro language ──► Intro pages ──► Home
   │                                                         ▲
   └──────────────────(returning user)───────────────────────┘

Home ◄──► Insight ◄──► Article ◄──► Settings      (하단 바)
  │
  └──► Lesson screens (Home grid, search, sort)
```

| 화면 | 패키지 | 보여 주는 내용 |
|---|---|---|
| 스플래시 | `secondary/splash` | DataStore 값을 보고 인트로와 Home 중 어디로 갈지 결정합니다 |
| 인트로 언어 | `secondary/intro` | 첫 실행 시 표시되는 언어 선택기 |
| 인트로 | `secondary/intro` | 커스텀 페이지 인디케이터가 있는 온보딩 페이저 |
| 언어 | `secondary/language` | 나중에 설정에서 앱 언어를 변경합니다 |

<a id="7-lessons"></a>
## 7. 레슨

아래의 모든 레슨은 **Home** 그리드에서 열립니다. 경로는 `app/src/main/java/com/example/jetpack/ui/fragment/` 기준의 상대 경로입니다.

<a id="71-ui-and-layout"></a>
### 7.1 UI 및 레이아웃

| 레슨 | 패키지 | 배우는 내용 |
|---|---|---|
| Collapsible Top Bar | `secondary/collapsibletopbar/collapsible` | `derivedStateOf`로 스크롤 위치에 따라 헤더를 접습니다 |
| Collapsible Top Bar 2 | `secondary/collapsibletopbar/collapsible2` | Material 3 `TopAppBarDefaults.enterAlwaysScrollBehavior` + `nestedScroll` |
| Collapsible Top Bar 3 | `secondary/collapsibletopbar/collapsible3` | 전체 / 최소화 헤더를 가진, 직접 만든 `NestedScrollConnection` |
| Collapsible Top Bar 4 | `secondary/collapsibletopbar/collapsible4` | `firstVisibleItemIndex`에 따라 전환되는 `LargeTopAppBar` |
| Collapsible Top Bar 5 | `secondary/collapsibletopbar/collapsible5` | `enterAlwaysScrollBehavior`와 결합한 펼침 / 접힘 바 |
| Instagram Carousel | `secondary/instagramcarousel` | 길게 눌러 드래그할 수 있는 점 인디케이터가 있는 페이저 |
| Pinch to Zoom | `secondary/pitchtozoom` | 확대, 이동, 회전을 위한 `detectTransformGestures` + `graphicsLayer` |
| Tutorial | `secondary/tutorial` | `onGloballyPositioned`와 `boundsInRoot`로 만든 단계별 스포트라이트 튜토리얼이며, 상태는 DataStore에 저장됩니다 |
| Login | `secondary/login` | 로그인 폼: `FocusRequester`, `KeyboardOptions`, IME 액션, `PasswordVisualTransformation` |
| WebView | `secondary/webview` | `AndroidView` 안의 `WebView`, `WebViewClient` 콜백, `BackHandler`를 이용한 뒤로 가기 처리 |

<a id="72-animation-and-graphics"></a>
### 7.2 애니메이션 및 그래픽

| 레슨 | 패키지 | 배우는 내용 |
|---|---|---|
| Animation | `secondary/animation` | `tween`, `spring`, `keyframes`, `infiniteRepeatable`, `drawBehind`, `drawWithContent`, 아핀 변환 |
| Motion Layout | `secondary/motionlayout` | JSON5 모션 씬(`res/raw/*.json5`)과 커스텀 접히는 툴바 상태를 사용하는 Compose `MotionLayout` |

<a id="73-text-input"></a>
### 7.3 텍스트 입력

| 레슨 | 패키지 | 배우는 내용 |
|---|---|---|
| Basic Text Field (state) | `secondary/textfield` | `TextFieldState`를 사용하는 `BasicTextField`: `InputTransformation`, 내장 변환, `OutputTransformation`, 자동 완성(autofill), 자동 크기 조정, 유연한 텍스트 필드 |

재사용 가능한 입력 규칙은 `util/inputtransformation/`(`DigitsOnlyTransformation`, `VerificationCodeTransformation`)에 있습니다.

<a id="74-data-and-networking"></a>
### 7.4 데이터 및 네트워킹

| 레슨 | 패키지 | 배우는 내용 |
|---|---|---|
| Quote | `secondary/quote` | Room CRUD: 엔티티, DAO, 타입 컨버터(`Date`, `Category`), 매퍼, 행(row)의 `Flow` |
| AccuWeather | `secondary/accuweather` | API 키를 추가하는 Retrofit + OkHttp 인터셉터, 도시 자동 완성 검색 |
| Home (DummyJSON) | `primary/home` | Home ViewModel에서 호출하는 두 번째 Retrofit 클라이언트(`DummyService`) |

<a id="75-permissions-and-device-features"></a>
### 7.5 권한 및 디바이스 기능

| 레슨 | 패키지 | 배우는 내용 |
|---|---|---|
| Permissions | `secondary/permission` | Fragment에서 직접 단일 및 다중 권한 요청, 권한 설명(rationale) 대화상자, 앱 설정 열기 |
| Permissions 2 | `secondary/permission` | 같은 흐름을 재사용 가능한 `LifecycleObserver` 클래스(`lifecycleobserver/`)로 옮긴 버전 |
| Last Known Location | `secondary/location` | `MapManager`를 통한 Fused Location Provider: 마지막 위치, 실시간 업데이트, GPS 켜기 |
| Bluetooth | `secondary/bluetooth` | Bluetooth Classic(페어링된 기기, 검색, 연결)과 BLE 스캔, Lottie 스캔 애니메이션 |
| Photo from Device Storage | `secondary/gallery` | 폴더별로 MediaStore에서 사진 읽기, 카메라 촬영, 권한 바텀 시트 |

<a id="76-media-and-background-work"></a>
### 7.6 미디어 및 백그라운드 작업

| 레슨 | 패키지 | 배우는 내용 |
|---|---|---|
| Music Player | `secondary/mediaplayer` | 포그라운드 서비스 **이자** 바운드 서비스에서 동작하는 `MediaPlayer`, `RemoteViews`를 사용한 커스텀 `MediaStyle` 알림, `res/raw`와 MediaStore의 음악 |
| Music Player 2 | `secondary/mediaplayer` | 자체 서비스(`MediaPlayerService2`)와 리시버를 가진, 같은 플레이어의 두 번째 버전 |
| Download with WorkManager | `secondary/downloadwithworker` | PDF나 동영상을 다운로드하고, 알림에 진행률을 표시하며, 저장된 `Uri`를 반환하는 `@HiltWorker` |
| Download Manager | `secondary/downloadmanager` | 시스템 `DownloadManager`와 완료를 수신하는 `BroadcastReceiver` |

<a id="77-kotlin-and-coroutines"></a>
### 7.7 Kotlin 및 코루틴

| 레슨 | 패키지 | 배우는 내용 |
|---|---|---|
| Kotlin Flow | `secondary/kotlinflow` | Flow 빌더(`flow`, `flowOf`, `asFlow`), 종단 연산자(`collect`, `first`, `toList`, `reduce`), `async`와 `Job` 제어 |

관련 유틸리티: `util/CoroutineUtil.kt`(비동기 결과를 합치는 다섯 가지 방법), `util/FlowUtil.kt`(다섯 개를 넘는 Flow 결합).

<a id="8-bottom-bar-tabs"></a>
## 8. 하단 바 탭

| 탭 | Fragment | 내용 |
|---|---|---|
| **Home** | `primary/home/HomeFragment` | 검색, 정렬 옵션, 시머(shimmer) 로딩, 드래그 가능한 플로팅 박스, 디지털 시계, 프리미엄 카드가 있는 레슨 그리드 |
| **Insight** | `primary/chart/InsightFragment` | 칩 탭: 컴포넌트 갤러리, 캘린더, 라인 / 링 / 바 / 영역 차트, 아날로그 시계, 색상 팔레트, 사진 보정 테스트(대비, 화이트 밸런스) |
| **Article** | `primary/article/ArticleFragment` | 그래픽 뷰 쇼케이스: Atomic loader, DNA 나선, 아날로그 시계, 일출 캔버스, 전 / 후 이미지 스와이프 |
| **Settings** | `primary/setting/SettingFragment` | 다크 모드, 언어, 앱 아이콘, 평가 대화상자 + Play In-App Review, 면책 조항 |

Settings에는 `SettingIconFragment`도 있으며, 매니페스트의 `<activity-alias>` 항목을 통해 런타임에 런처 아이콘을 전환합니다.

<a id="9-reusable-component-library"></a>
## 9. 재사용 가능한 컴포넌트 라이브러리

이 섹션의 모든 항목은 다른 프로젝트에 그대로 복사할 수 있는 독립적인 Composable입니다. 대부분 `@Preview`가 있습니다.

<a id="91-core-components"></a>
### 9.1 핵심 컴포넌트

`ui/component/`에는 앱 전반에서 쓰이는 기본 구성 요소가 있습니다.

| 그룹 | 컴포넌트 |
|---|---|
| 상단 바 | `CoreTopBar`, `CoreTopBar2`–`4`, `CoreTopBarWithHomeShortcut`, `CoreTopBarWithScrollBehavior`, `CustomizeTopBar` |
| 하단 바 | `CoreBottomBar` |
| 대화상자 및 시트 | `CoreDialog`, `CoreDialogLayout`, `CoreAlertDialog`, `CoreMaterialDialog`, `CoreTextAnimationDialog`, `CoreBottomSheet` |
| 버튼 | `CoreButton`, `CoreFloatingButton`, `CoreExpandableFloatingButton` |
| 생명주기 | `LifecycleComposable`(Compose에서 `ON_START` / `ON_STOP` … 관찰) |
| 선택기 | `ui/dialog/WheelTimePickerDialog`(ELSA Speak 스타일의 휠 시간 선택기) |

<a id="92-custom-views"></a>
### 9.2 커스텀 뷰

`ui/view/`에는 독립적으로 동작하는 위젯이 있습니다.

| 그룹 | 컴포넌트 |
|---|---|
| 버튼 및 토글 | `GlowingButton`, `ButtonBookmark`, `ButtonFavorite`, `ButtonShare`, `ButtonTextSetting`, `CupertinoSwitch`, `CustomizedCheckbox`, `AnimatedThemeSwitcher` |
| 진행률 및 로딩 | `AnimatedProgressBar`, `CustomizedProgressBar`, `GradientProgressIndicator`, `ProgressBar`, `AtomicLoader`, `LoadingDialog`, `AnimationCircularWave`, `DNAHelix` |
| 시계 및 캘린더 | `AnalogueClock`, `DigitalClock`, `DigitalClock2`, `DigitalClock3`, `CollapsedCalendar`, `ExpandedCalendar` |
| 텍스트 효과 | `ShimmerText`, `DottedTextAnimation`, `FlexibleTextField`, `wordbyword/`(글자 단위, 단어 단위, 줄 단위, 청크 단위 애니메이션) |
| 카드 및 서피스 | `AnimatedBorderCard`, `RainbowCard`, `OverlappingRoundedBackground`, `HomePremium`, `SwipeToReveal` |
| 이미지 | `ImageBeforeAndAfterSwipeAnimation`, `ImageBeforeAndAfterSwipeSection`, `PhotoSelection` |
| 레이아웃 | `TableLayout`, `TableLayoutRow`, `HeaderAndLayout`, `ContextualFlowRowSample`, `floatingdraggablebox/`(버블을 드래그해 닫기 영역에 놓기) |
| 데이터 표시 | `RateBar`, `ColorSquare`, `IndexColorBar`, `HighlightIndexColor`, `WeatherSunrise` |
| 설정 행 | `SettingItemWithDropdown`, `SubsettingElement` |
| 피드백 | `CountdownSnackbar` |

<a id="93-charts"></a>
### 9.3 차트

`ui/fragment/primary/chart/chartcomponent/` — 모든 차트는 `Canvas`로 그립니다.

| 차트 | 파일 |
|---|---|
| 라인 차트 | `LineChart.kt`, `LineChartComponent.kt` |
| 곡선 라인(3차 베지어) | `CurvyLineChart.kt` |
| 바 차트 | `BarChart.kt` |
| 혈압(범위 바) | `BloodPressureChart.kt`, `BloodPressureChart2Element.kt` |
| 간격이 있는 파이 / 도넛 | `PieChart.kt`, `DonutChart.kt`, `DonutChart2.kt` |
| 링 차트 | `RingChart.kt`, `RingChart2.kt` |
| 시간별 날씨 차트 | `WeatherHourlyChart.kt`, `WeatherHourlyChartElement.kt` |

<a id="94-modifiers"></a>
### 9.4 Modifier

`ui/modifier/`:

| Modifier | 효과 |
|---|---|
| `BorderWithAnimatedGradient` | 회전하는 그라디언트 테두리 |
| `DashedBorder` | 점선 외곽선 |
| `ShimmerEffect` | 로딩 중 시머 플레이스홀더 |
| `PulseEffect` | 맥동하는 크기 / 글로우 |
| `Shadow` | 커스텀 색상 그림자 |
| `PremiumWatermark` | 프리미엄 배지 오버레이 |
| `DebounceClickable` | 빠르게 반복되는 탭을 무시합니다 |
| `Conditional` | 조건이 참일 때만 Modifier를 적용합니다 |

툴팁은 `ui/theme/tooltip/`(정렬 및 오프셋 프로바이더를 가진 `TooltipPopup`)에 있습니다.

<a id="95-utilities"></a>
### 9.5 유틸리티

`util/`:

| 파일 | 용도 |
|---|---|
| `AppUtil` | UUID, 웹사이트 열기, 앱 설정 열기, Figma 기준 화면 크기 스케일링 |
| `CanvasUtil` | dp ↔ px 변환 |
| `CoroutineUtil` | 비동기 결과를 합치는 다섯 가지 방법 |
| `DateUtil`, `LocalDateUtil` | 날짜 차이, 형식, epoch 헬퍼 |
| `EncrytionUtil` | AES 암호화 / 복호화 예제 |
| `FileUtil` | 파일 이름, 동영상 폴더, 동영상 공유, MediaStore id |
| `FlowUtil` | 다섯 개를 넘는 Flow를 위한 `combine` |
| `LanguageUtil` | `Context`에 로케일 적용 |
| `NavigationUtil` | `canNavigate` 가드와 `popUpTo` 관련 메모 |
| `NetworkUtil` | 인터넷 연결 확인 |
| `PermissionUtil` | 알림, 카메라, 저장소 및 다중 권한 확인 |
| `SharingUtil` | 이메일 작성, 클립보드에 복사, 앱 공유 |
| `SmartFunction` | 유효성 검사와 사용자 친화적인 오류 메시지 |
| `SystemBarUtil` | 상태 바 / 내비게이션 바 숨기기 |
| `TuplesUtil` | `Quadruple` 및 더 큰 튜플 |

<a id="10-notifications-and-lock-screen"></a>
## 10. 알림 및 잠금 화면

| 기능 | 파일 | 동작 방식 |
|---|---|---|
| 일일 알림 | `notification/NotificationManager.kt`, `NotificationReceiver.kt` | `AlarmManager.setExact(RTC_WAKEUP)`가 리마인더를 예약하고(정확한 알람이 허용되지 않으면 `set`으로 대체합니다), 리시버가 알림을 게시합니다 |
| 첫 설치 알림 | `NotificationAfter5MinuteReceiver.kt` | 처음 설치한 직후 한 번 보내는 알림 |
| 진행률 알림 | `NotificationManager.fireProgressNotification` | 진행률 바가 있는 진행 중(ongoing) 알림 |
| 잠금 화면 알림 | `LockscreenManager.kt`, `LockscreenReceiver.kt`, `ui/activity/LockscreenActivity.kt` | 전체 화면 인텐트가 `setShowWhenLocked`가 설정된 Activity를 엽니다. 불가능하면 일반 알림으로 대체합니다 |
| 알림 권한 | `lifecycleobserver/NotificationLifecycleObserver.kt` | Android 13 이상에서 권한 설명 및 설정 대화상자와 함께 `POST_NOTIFICATIONS`를 요청합니다 |

<a id="11-localization-and-theming"></a>
## 11. 현지화 및 테마

- **언어**: 영어(기본), 독일어, 프랑스어, 힌디어, 일본어, 한국어, 베트남어(`res/values-*`). 기기 언어가 맨 앞에 표시됩니다. 선택한 언어는 DataStore에 저장되고 `CoreActivity.attachBaseContext`에서 적용됩니다.
- **테마**: `ui/theme/`에는 색상, 타이포그래피(`Type.kt`), 애니메이션 스펙, `CustomizedTheme`이 있습니다.
- **다크 모드**: Settings에서 전환하며 `CoreFragment`가 적용합니다.

<a id="12-performance-baseline-profile"></a>
## 12. 성능: Baseline Profile

`:baselineprofile` 모듈에는 다음이 들어 있습니다.

| 파일 | 용도 |
|---|---|
| `BaselineProfileGenerator.kt` | 앱 시작 경로를 따라가며 `:app`용 Baseline Profile을 생성합니다 |
| `StartupBenchmarks.kt` | 프로필 적용 유무에 따른 시작 시간을 비교하는 Macrobenchmark |

`:app`은 `baselineProfile(project(":baselineprofile"))`로 프로필을 사용하며, `profileinstaller`와 함께 배포합니다.

<a id="13-project-structure"></a>
## 13. 프로젝트 구조

```
Jetpack-Compose/
├── app/                              # 애플리케이션 모듈
├── baselineProfile/                  # Baseline Profile 생성기 + 시작 벤치마크
├── gradle/libs.versions.toml         # 버전 카탈로그: 모든 라이브러리와 플러그인 버전
├── photo/                            # 이 README에서 사용하는 이미지
└── .claude/                          # AI 어시스턴트를 위한 코딩 규칙과 스킬
```

소스 루트: `app/src/main/java/com/example/jetpack/`

```
com/example/jetpack/
├── backgroundwork/                   # 화면 밖에서 실행되는 서비스와 리시버
│   ├── MediaPlayerService(2).kt      #   포그라운드 + 바운드 음악 서비스
│   ├── MediaPlayerReceiver(2).kt     #   알림에서 재생 / 일시정지 / 다음 곡
│   └── DownloadManagerReceiver.kt    #   "다운로드 완료" 브로드캐스트
├── configuration/                    # 앱 전역 상수와 정적 목록
│                                     #   (Constant, Language, Logo, CountryCode, BottomBarDestination)
├── core/base/                        # 모든 화면이 상속하는 기본 클래스
│                                     #   (CoreActivity, CoreFragment, CoreLayout, CoreBehavior)
├── data/                             # 로컬 데이터
│   ├── database/                     #   Room: QuoteDatabase, dao/, entity/, converter/, mapper/
│   ├── datastore/                    #   DataStore: SettingDatastore, TutorialDatastore
│   └── repository/                   #   Quote, Setting, Tutorial, Weather, Dummy Repository
│       └── impl/                     #   Repository 구현체
├── domain/                           # 순수 Kotlin 비즈니스 타입
│   ├── catalog/                      #   SampleRegistry: 모든 레슨의 색인
│   ├── enums/                        #   HomeShortcut, ChartShortcut, SortOption, Category …
│   ├── manager/                      #   MapManager: Fused Location 래퍼
│   └── model/                        #   Quote, ChartElement, Status …
├── injection/                        # Hilt
│   ├── module/                       #   Database, Network, Repository, Dispatcher 모듈
│   └── qualifier/                    #   @IoDispatcher, @DefaultDispatcher
├── lifecycleobserver/                # 재사용 가능한 권한 요청(단일, 다중, 위치, 알림)
├── network/                          # 원격 데이터
│   ├── adapter/                      #   AccuWeatherFactory: Retrofit Response를 풀어내고 로그를 남김
│   ├── dto/                          #   weather/, dummy/ 응답 모델
│   ├── interceptor/                  #   AccuWeatherInterceptor: API 키를 추가
│   ├── service/                      #   AccuWeatherService, DummyService
│   └── util/                         #   safeApiCall: Response<T> → Status<T>
├── notification/                     # 일일, 첫 설치, 진행률, 잠금 화면 알림
├── ui/                               # 사용자가 보는 모든 것
│   ├── activity/                     #   LockscreenActivity
│   ├── component/                    #   Core* 공용 Composable(상단 바, 대화상자, 버튼)
│   ├── dialog/                       #   WheelTimePickerDialog
│   ├── fragment/
│   │   ├── primary/                  #   하단 바 탭: home, chart (Insight), article, setting
│   │   └── secondary/                #   레슨마다 하나의 패키지 + splash, intro, language
│   ├── modifier/                     #   커스텀 Modifier 확장
│   ├── theme/                        #   색상, 타이포그래피, 테마, tooltip/
│   └── view/                         #   독립적인 커스텀 위젯(시계, 로더, 카드 …)
├── util/                             # 헬퍼와 확장 함수
│   └── inputtransformation/          #   DigitsOnly, VerificationCode 입력 규칙
├── worker/                           # DownloadFileWorker (WorkManager + Hilt)
├── JetpackApplication.kt             # @HiltAndroidApp 애플리케이션 클래스
├── JetpackDocument.kt                # 읽을거리 목록(섹션 16 참고)
└── MainActivity.kt                   # 단일 Activity: NavHost + 하단 바
```

레슨 패키지는 다음과 같은 형태입니다.

```
ui/fragment/secondary/gallery/
├── component/                        # 이 화면에서만 사용하는 Composable
│   ├── GalleryFolders.kt
│   ├── GalleryGridView.kt
│   ├── GalleryPermissionBottomSheet.kt
│   ├── PhotoTabElement.kt
│   └── RequestGalleryPermission.kt
├── GalleryFragment.kt                # 진입점 + 상태 없는 레이아웃 + 프리뷰
├── GalleryUiState.kt                 # 불변 화면 상태
└── GalleryViewModel.kt               # 사진을 불러오고 StateFlow로 노출
```

<a id="14-adding-a-new-lesson"></a>
## 14. 새 레슨 추가하기

1. `ui/fragment/secondary/<lesson>/` 아래에 `XxxFragment`, `XxxViewModel`(필요한 경우), `component/`를 포함한 패키지를 만듭니다.
2. `res/navigation/navigation_graph.xml`에 목적지와 `toXxx` 액션을 추가합니다.
3. `domain/enums/HomeShortcut.kt`에 항목(아이콘 + 제목 문자열)을 추가합니다.
4. 제목을 `res/values/strings.xml`의 맨 끝에 추가합니다.
5. `HomeFragment`에서 `safeNavigate(R.id.toXxx)`로 바로가기를 처리합니다.
6. 검색에서 찾을 수 있도록 `domain/catalog/SampleRegistry.kt`에 `SampleMeta`를 등록합니다.
7. 이 README의 [레슨](#7-lessons) 표를 업데이트합니다.

<a id="15-work-in-progress"></a>
## 15. 진행 중인 작업

| 항목 | 상태 |
|---|---|
| Tooltip 바로가기 | Home에 표시되지만 아직 연결되지 않았습니다(토스트만 표시합니다). 코드: `ui/theme/tooltip/TooltipPopup.kt` |
| Shared Element Transition 바로가기 | Home에 표시되지만 아직 화면이 없습니다 |
| Mecca Compass | 목적지는 있지만 화면은 비어 있는 플레이스홀더입니다 |
| `secondary/basictextfield/` | 내비게이션 그래프에 포함되지 않은 Basic Text Field 레슨의 이전 사본입니다. 실제로 사용되는 것은 `secondary/textfield/`입니다 |

<a id="16-reading-list"></a>
## 16. 읽을거리

`JetpackDocument.kt`는 여러 레슨의 바탕이 된 글을 모아 둡니다.

<details>
<summary><b>42개 글 모두 보기</b></summary>

| # | 주제 |
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
## 17. AI 지원 개발

이 저장소에는 Claude Code 같은 AI 어시스턴트를 위한 코딩 규칙이 포함되어 있습니다.

| 경로 | 내용 |
|---|---|
| `CLAUDE.md` | 어시스턴트를 위한 출력 스타일과 Kotlin 규칙 |
| `.claude/rules/` | 프로젝트 규칙: 스켈레톤 레이아웃, Compose UI, Repository, UseCase 및 ViewModel 레이어, Figma 디자인 시스템 |
| `.claude/skills/` | 재사용 가능한 스킬(TDD, 진단, PRD 및 이슈 작성, 아키텍처 리뷰 …) |

---

<p align="center">
    <a href="https://github.com/Phong-Kaster">Phong-Kaster</a>가 💘와 Kotlin으로 만들었습니다
</p>
