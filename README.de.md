<h1 align="center">Jetpack Compose Playground<br/>
    <sub>Ein Skeleton und Lektionskatalog für Android-Apps mit Kotlin und Jetpack Compose</sub>
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
## Inhaltsverzeichnis

<p align="center">
    <a href="README.md">English</a> ·
    <a href="README.vi.md">Tiếng Việt</a> ·
    <a href="README.es.md">Español</a> ·
    <b>Deutsch</b> ·
    <a href="README.fr.md">Français</a> ·
    <a href="README.hi.md">हिन्दी</a> ·
    <a href="README.ja.md">日本語</a> ·
    <a href="README.ko.md">한국어</a>
</p>

1. [Einführung](#1-introduction)
2. [Highlights](#2-highlights)
3. [Erste Schritte](#3-getting-started)
   - [Voraussetzungen](#31-requirements)
   - [Bauen und ausführen](#32-build-and-run)
   - [Konfiguration](#33-configuration)
4. [Tech-Stack](#4-tech-stack)
5. [Architektur](#5-architecture)
   - [Schichten](#51-layers)
   - [Aufbau eines Screens](#52-screen-anatomy)
   - [Navigation](#53-navigation)
   - [Beispielkatalog](#54-sample-catalog)
6. [App-Ablauf](#6-app-flow)
7. [Lektionen](#7-lessons)
   - [UI und Layout](#71-ui-and-layout)
   - [Animation und Grafik](#72-animation-and-graphics)
   - [Texteingabe](#73-text-input)
   - [Daten und Netzwerk](#74-data-and-networking)
   - [Berechtigungen und Gerätefunktionen](#75-permissions-and-device-features)
   - [Medien und Hintergrundarbeit](#76-media-and-background-work)
   - [Kotlin und Coroutines](#77-kotlin-and-coroutines)
8. [Tabs der Bottom Bar](#8-bottom-bar-tabs)
9. [Wiederverwendbare Komponentenbibliothek](#9-reusable-component-library)
   - [Kernkomponenten](#91-core-components)
   - [Custom Views](#92-custom-views)
   - [Diagramme](#93-charts)
   - [Modifier](#94-modifiers)
   - [Hilfsfunktionen](#95-utilities)
10. [Benachrichtigungen und Sperrbildschirm](#10-notifications-and-lock-screen)
11. [Lokalisierung und Theming](#11-localization-and-theming)
12. [Performance: Baseline Profile](#12-performance-baseline-profile)
13. [Projektstruktur](#13-project-structure)
14. [Eine neue Lektion hinzufügen](#14-adding-a-new-lesson)
15. [In Arbeit](#15-work-in-progress)
16. [Leseliste](#16-reading-list)
17. [KI-gestützte Entwicklung](#17-ai-assisted-development)

---

<a id="1-introduction"></a>
## 1. Einführung

Dieses Repository ist gleichzeitig ein **Jetpack-Compose-Playground** und ein **wiederverwendbares App-Skeleton**.

- **Als Playground** versammelt es mehr als 25 lauffähige Lektionen: Animationen, einklappbare Top Bars, Berechtigungen, Bluetooth, Standort, Medienwiedergabe, Downloads, Room, DataStore, Retrofit, Kotlin Flow und mehr. Jede Lektion ist ein Screen, den du aus dem Home-Raster öffnen, lesen und in dein eigenes Projekt kopieren kannst.
- **Als Skeleton** liefert es die Teile, die die meisten Produktions-Apps brauchen: Splash, Sprachauswahl, Onboarding, eine Bottom Bar mit vier Tabs, Einstellungen (Dark Mode, Sprache, App-Icon, Bewertung), tägliche Benachrichtigungen, eine Sperrbildschirm-Benachrichtigung, Dependency Injection und ein Baseline Profile.

Das Design ist von den [Android Architecture Samples](https://github.com/android/architecture-samples) inspiriert. Der Code ist zu 100 % Kotlin. Jeder Screen ist eine Compose-UI, die in einem Fragment gehostet wird.

<a id="2-highlights"></a>
## 2. Highlights

| Bereich | Was du bekommst |
|---|---|
| **UI** | Jetpack Compose + Material 3, über 40 Custom Views, 13 Canvas-Diagramme, 8 eigene Modifier |
| **Architektur** | Single Activity, Fragment- + Compose-Screens, MVVM, Repository-Pattern, Hilt DI |
| **Asynchronität** | Kotlin Coroutines, `Flow` / `StateFlow`, ein `safeApiCall`-Wrapper, der eine Retrofit-`Response` in einen `Status` umwandelt |
| **Daten** | Room (Zitate), DataStore Preferences (Einstellungen, Tutorial-Status), Retrofit + OkHttp (AccuWeather, DummyJSON) |
| **Gerät** | Laufzeitberechtigungen, Fused Location, Bluetooth Classic + BLE, Kamera, MediaStore, WebView |
| **Hintergrund** | Foreground-Medienservice, WorkManager (`@HiltWorker`), DownloadManager, exakte Alarme |
| **App-Gerüst** | Splash, Intro, Sprachauswahl, Bottom Bar, Einstellungen, dynamisches Launcher-Icon, In-App-Review |
| **Qualität** | Baseline Profile + Startup-Benchmark-Modul, `@Preview` für die meisten Composables |
| **Lokalisierung** | Englisch, Deutsch, Französisch, Hindi, Japanisch, Koreanisch, Vietnamesisch |

<a id="3-getting-started"></a>
## 3. Erste Schritte

<a id="31-requirements"></a>
### 3.1 Voraussetzungen

| Werkzeug | Version |
|---|---|
| Android Studio | Eine Version, die AGP 9.5 unterstützt |
| JDK | 17 oder neuer (das Projekt erzeugt Java-17-Bytecode) |
| Gradle | 9.9.2 (wird vom Wrapper heruntergeladen) |
| Android SDK | compileSdk 37, minSdk 26 |

<a id="32-build-and-run"></a>
### 3.2 Bauen und ausführen

```bash
git clone https://github.com/Phong-Kaster/Jetpack-Compose.git
cd Jetpack-Compose

# Den Debug-Build auf einem verbundenen Gerät oder Emulator installieren
./gradlew :app:installDebug

# Weitere nützliche Tasks
./gradlew :app:assembleRelease        # Release-APK (R8 aktiviert)
./gradlew :app:testDebugUnitTest      # Unit-Tests
./gradlew :baselineprofile:connectedCheck  # Baseline Profile erzeugen / Benchmarks ausführen
```

Jede APK wird vom Build in `Bundeswehr_v<versionName>(<versionCode>)_<timestamp>-<variant>.apk` umbenannt.

<a id="33-configuration"></a>
### 3.3 Konfiguration

| Einstellung | Ort | Hinweise |
|---|---|---|
| AccuWeather-API-Key | `configuration/Constant.kt` → `ACCU_WEATHER_KEY` | Durch deinen eigenen Key von [AccuWeather Developer](https://developer.accuweather.com/) ersetzen. In echten Projekten besser `local.properties` + `BuildConfig` verwenden. |
| AccuWeather-Basis-URL | `configuration/Constant.kt` → `ACCU_WEATHER_URL` | `https://dataservice.accuweather.com` |
| DummyJSON-Basis-URL | `injection/module/NetworkModule.kt` | `https://dummyjson.com/` |
| Bibliotheksversionen | `gradle/libs.versions.toml` | Ein einziger Version Catalog für alle Abhängigkeiten und Plugins |

<a id="4-tech-stack"></a>
## 4. Tech-Stack

| Kategorie | Bibliotheken |
|---|---|
| Sprache und Build | Kotlin 2.4.21, AGP 9.5.3, Gradle 9.9.2, KSP 2.3.14, Version Catalog |
| UI | Compose BOM 2026.08.00, Material 3, Material Icons Extended, ConstraintLayout Compose (MotionLayout), Accompanist Permissions |
| Architektur | AndroidX Lifecycle 2.10, Navigation Component 2.9.7 (Fragment + Safe Args), Hilt 2.60.1 |
| Daten | Room 2.8.4, DataStore Preferences 1.2.1, Kotlinx Collections Immutable |
| Netzwerk | Retrofit 2.12, OkHttp Logging Interceptor 5.3, Gson |
| Bilder und Animation | Coil 2 (+ GIF), Glide 4, Lottie 6 |
| Medien | `MediaPlayer` + `MediaStyle`-Benachrichtigung, Media3 ExoPlayer / UI / Session |
| Hintergrund | WorkManager 2.11 + Hilt Work, `AlarmManager`, `DownloadManager` |
| Google Play | Play Services Location, Play In-App Review |
| Performance | Baseline Profile 1.4, Macrobenchmark, ProfileInstaller |

<a id="5-architecture"></a>
## 5. Architektur

<a id="51-layers"></a>
### 5.1 Schichten

```
┌──────────────────────────────────────────────────────────┐
│ ui/        Fragments → Compose layouts → ViewModels      │  Präsentation
├──────────────────────────────────────────────────────────┤
│ domain/    models, enums, managers, sample catalog       │  Geschäftstypen
├──────────────────────────────────────────────────────────┤
│ data/      repositories → Room DAO / DataStore           │  lokale Daten
│ network/   Retrofit services, DTOs, interceptors         │  Remote-Daten
├──────────────────────────────────────────────────────────┤
│ injection/ Hilt modules wire everything together         │  DI
└──────────────────────────────────────────────────────────┘
```

- **ViewModels** stellen den Screen-Zustand als `StateFlow` bereit und fassen nie Android-Views an.
- **Repositories** sind die einzige Tür zu Room, DataStore und Retrofit. Ein Screen fragt ein Repository, nie ein DAO oder einen Service.
- **Netzwerkaufrufe** laufen über `safeApiCall` (`network/util/SafeApiFlow.kt`), das eine Retrofit-`Response` in einen `Status` (`Success` / `Failure`) verpackt; Repositories geben ihn als `Flow` an die ViewModels weiter.
- **Hilt** stellt die Datenbank, die beiden Retrofit-Clients, die Repositories und die Coroutine-Dispatcher (`@IoDispatcher`, `@DefaultDispatcher`) bereit.

<a id="52-screen-anatomy"></a>
### 5.2 Aufbau eines Screens

Jeder Screen hat dieselbe Form:

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

| Basisklasse | Aufgabe |
|---|---|
| `core/base/CoreActivity` | Wendet die gespeicherte Sprache in `attachBaseContext` an, Toast- und Konnektivitäts-Helfer |
| `core/base/CoreFragment` | Erstellt die `ComposeView`, wendet App-Theme und Dark Mode an, stellt `LocalNavController` und `LocalLocale` bereit |
| `core/base/CoreLayout` | Ein `Scaffold`-Wrapper mit Top Bar, Bottom Bar, FAB und Edge-to-Edge-Insets |
| `core/base/CoreBehavior` | Gemeinsamer Vertrag: `showToast`, `isInternetConnected`, `trackEvent` |

<a id="53-navigation"></a>
### 5.3 Navigation

- Eine Activity (`MainActivity`) enthält ein `NavHostFragment` und die Bottom Bar.
- Alle Ziele liegen in `res/navigation/navigation_graph.xml`. Das Startziel ist der Splash-Screen; die Intro-Screens bilden einen verschachtelten Graphen.
- Navigiert wird mit `safeNavigate(R.id.toXxx)` aus `util/NavigationUtil.kt`; es prüft zuerst das aktuelle Ziel, sodass ein Doppeltipp nie zum Absturz führt.

<a id="54-sample-catalog"></a>
### 5.4 Beispielkatalog

`domain/catalog/SampleRegistry.kt` ist der Index aller Lektionen. Jeder `HomeShortcut` hat einen `SampleMeta`-Eintrag mit:

- der Hauptquelldatei zum Lesen oder Kopieren,
- weiteren zugehörigen Dateien,
- Themen (`SampleTopic`: `ANIMATION`, `PERMISSION`, `MEDIA`, …),
- Such-Aliassen.

Die Suchleiste auf Home nutzt diese Registry, sodass die Eingabe von `retrofit`, `pinch` oder `qibla` die richtige Lektion findet.

<a id="6-app-flow"></a>
## 6. App-Ablauf

```
Splash ──(Erststart)─────► Intro language ──► Intro pages ──► Home
   │                                                         ▲
   └──────────────────(Wiederkehrer)─────────────────────────┘

Home ◄──► Insight ◄──► Article ◄──► Settings      (Bottom Bar)
  │
  └──► Lesson screens (Home-Raster, Suche, Sortierung)
```

| Screen | Package | Was er zeigt |
|---|---|---|
| Splash | `secondary/splash` | Entscheidet anhand von DataStore zwischen Intro und Home |
| Intro language | `secondary/intro` | Sprachauswahl beim ersten Start |
| Intro | `secondary/intro` | Onboarding-Pager mit eigenem Seitenindikator |
| Language | `secondary/language` | Die App-Sprache später über die Einstellungen ändern |

<a id="7-lessons"></a>
## 7. Lektionen

Jede der folgenden Lektionen öffnet sich aus dem **Home**-Raster. Pfade sind relativ zu `app/src/main/java/com/example/jetpack/ui/fragment/`.

<a id="71-ui-and-layout"></a>
### 7.1 UI und Layout

| Lektion | Package | Was du lernst |
|---|---|---|
| Collapsible Top Bar | `secondary/collapsibletopbar/collapsible` | Einen Header abhängig von der Scrollposition mit `derivedStateOf` einklappen |
| Collapsible Top Bar 2 | `secondary/collapsibletopbar/collapsible2` | Material 3 `TopAppBarDefaults.enterAlwaysScrollBehavior` + `nestedScroll` |
| Collapsible Top Bar 3 | `secondary/collapsibletopbar/collapsible3` | Eine selbstgebaute `NestedScrollConnection` mit vollem / minimiertem Header |
| Collapsible Top Bar 4 | `secondary/collapsibletopbar/collapsible4` | `LargeTopAppBar`, die abhängig von `firstVisibleItemIndex` umschaltet |
| Collapsible Top Bar 5 | `secondary/collapsibletopbar/collapsible5` | Ausgeklappte / eingeklappte Bars kombiniert mit `enterAlwaysScrollBehavior` |
| Instagram Carousel | `secondary/instagramcarousel` | Pager mit einem per Long-Press ziehbaren Punktindikator |
| Pinch to Zoom | `secondary/pitchtozoom` | `detectTransformGestures` + `graphicsLayer` für Zoomen, Verschieben und Drehen |
| Tutorial | `secondary/tutorial` | Ein schrittweises Spotlight-Tutorial mit `onGloballyPositioned` und `boundsInRoot`, Zustand in DataStore gespeichert |
| Login | `secondary/login` | Login-Formular: `FocusRequester`, `KeyboardOptions`, IME-Aktionen, `PasswordVisualTransformation` |
| WebView | `secondary/webview` | `WebView` in `AndroidView`, `WebViewClient`-Callbacks, Zurück-Navigation mit `BackHandler` |

<a id="72-animation-and-graphics"></a>
### 7.2 Animation und Grafik

| Lektion | Package | Was du lernst |
|---|---|---|
| Animation | `secondary/animation` | `tween`, `spring`, `keyframes`, `infiniteRepeatable`, `drawBehind`, `drawWithContent`, affine Transformationen |
| Motion Layout | `secondary/motionlayout` | Compose-`MotionLayout` mit JSON5-Motion-Scenes (`res/raw/*.json5`) und einem eigenen Zustand für eine einklappbare Toolbar |

<a id="73-text-input"></a>
### 7.3 Texteingabe

| Lektion | Package | Was du lernst |
|---|---|---|
| Basic Text Field (state) | `secondary/textfield` | `BasicTextField` mit `TextFieldState`: `InputTransformation`, eingebaute Transformationen, `OutputTransformation`, Autofill, Auto-Size, ein flexibles Textfeld |

Die wiederverwendbaren Eingaberegeln liegen in `util/inputtransformation/` (`DigitsOnlyTransformation`, `VerificationCodeTransformation`).

<a id="74-data-and-networking"></a>
### 7.4 Daten und Netzwerk

| Lektion | Package | Was du lernst |
|---|---|---|
| Quote | `secondary/quote` | Room-CRUD: Entity, DAO, Type Converter (`Date`, `Category`), Mapper, `Flow` von Zeilen |
| AccuWeather | `secondary/accuweather` | Retrofit + OkHttp-Interceptor, der den API-Key hinzufügt, Autovervollständigung bei der Städtesuche |
| Home (DummyJSON) | `primary/home` | Ein zweiter Retrofit-Client (`DummyService`), der aus dem Home-ViewModel aufgerufen wird |

<a id="75-permissions-and-device-features"></a>
### 7.5 Berechtigungen und Gerätefunktionen

| Lektion | Package | Was du lernst |
|---|---|---|
| Permissions | `secondary/permission` | Eine und mehrere Berechtigungen direkt in einem Fragment anfordern, Begründungsdialog, App-Einstellungen öffnen |
| Permissions 2 | `secondary/permission` | Dieselben Abläufe, ausgelagert in wiederverwendbare `LifecycleObserver`-Klassen (`lifecycleobserver/`) |
| Last Known Location | `secondary/location` | Fused Location Provider über `MapManager`: letzter Standort, Live-Updates, GPS einschalten |
| Bluetooth | `secondary/bluetooth` | Bluetooth Classic (gekoppelte Geräte, Suche, Verbinden) und BLE-Scan, Lottie-Scan-Animation |
| Photo from Device Storage | `secondary/gallery` | Fotos ordnerweise aus MediaStore lesen, Kameraaufnahme, Berechtigungs-Bottom-Sheet |

<a id="76-media-and-background-work"></a>
### 7.6 Medien und Hintergrundarbeit

| Lektion | Package | Was du lernst |
|---|---|---|
| Music Player | `secondary/mediaplayer` | `MediaPlayer` in einem Foreground- **und** Bound-Service, eigene `MediaStyle`-Benachrichtigung mit `RemoteViews`, Songs aus `res/raw` und MediaStore |
| Music Player 2 | `secondary/mediaplayer` | Eine zweite Version desselben Players mit eigenem Service (`MediaPlayerService2`) und Receiver |
| Download with WorkManager | `secondary/downloadwithworker` | `@HiltWorker`, der ein PDF oder Video herunterlädt, den Fortschritt in einer Benachrichtigung zeigt und die gespeicherte `Uri` zurückgibt |
| Download Manager | `secondary/downloadmanager` | Der System-`DownloadManager` plus ein `BroadcastReceiver` für den Abschluss |

<a id="77-kotlin-and-coroutines"></a>
### 7.7 Kotlin und Coroutines

| Lektion | Package | Was du lernst |
|---|---|---|
| Kotlin Flow | `secondary/kotlinflow` | Flow-Builder (`flow`, `flowOf`, `asFlow`), terminale Operatoren (`collect`, `first`, `toList`, `reduce`), `async` und `Job`-Steuerung |

Zugehörige Hilfsfunktionen: `util/CoroutineUtil.kt` (fünf Wege, asynchrone Ergebnisse zusammenzuführen), `util/FlowUtil.kt` (mehr als fünf Flows kombinieren).

<a id="8-bottom-bar-tabs"></a>
## 8. Tabs der Bottom Bar

| Tab | Fragment | Inhalt |
|---|---|---|
| **Home** | `primary/home/HomeFragment` | Lektionsraster mit Suche, Sortieroptionen, Shimmer-Ladeeffekt, einer schwebenden ziehbaren Box, einer Digitaluhr und einer Premium-Karte |
| **Insight** | `primary/chart/InsightFragment` | Chip-Tabs: Komponentengalerie, Kalender, Linien- / Ring- / Balken- / Flächendiagramme, Analoguhr, Farbpalette, Test der Fotoanpassung (Kontrast, Weißabgleich) |
| **Article** | `primary/article/ArticleFragment` | Schaufenster grafischer Views: Atomic Loader, DNA-Helix, Analoguhr, Sonnenaufgangs-Canvas, Vorher-/Nachher-Bildwischen |
| **Settings** | `primary/setting/SettingFragment` | Dark Mode, Sprache, App-Icon, Bewertungsdialog + Play In-App Review, Haftungsausschluss |

Die Einstellungen enthalten außerdem `SettingIconFragment`, das das Launcher-Icon zur Laufzeit über `<activity-alias>`-Einträge im Manifest umschaltet.

<a id="9-reusable-component-library"></a>
## 9. Wiederverwendbare Komponentenbibliothek

Alles in diesem Abschnitt ist ein eigenständiges Composable, das du in ein anderes Projekt kopieren kannst. Die meisten haben eine `@Preview`.

<a id="91-core-components"></a>
### 9.1 Kernkomponenten

`ui/component/` enthält die appweiten Bausteine:

| Gruppe | Komponenten |
|---|---|
| Top Bars | `CoreTopBar`, `CoreTopBar2`–`4`, `CoreTopBarWithHomeShortcut`, `CoreTopBarWithScrollBehavior`, `CustomizeTopBar` |
| Bottom Bar | `CoreBottomBar` |
| Dialoge und Sheets | `CoreDialog`, `CoreDialogLayout`, `CoreAlertDialog`, `CoreMaterialDialog`, `CoreTextAnimationDialog`, `CoreBottomSheet` |
| Buttons | `CoreButton`, `CoreFloatingButton`, `CoreExpandableFloatingButton` |
| Lifecycle | `LifecycleComposable` (`ON_START` / `ON_STOP` … aus Compose beobachten) |
| Picker | `ui/dialog/WheelTimePickerDialog` (Rad-Zeitauswahl im Stil von ELSA Speak) |

<a id="92-custom-views"></a>
### 9.2 Custom Views

`ui/view/` enthält in sich geschlossene Widgets:

| Gruppe | Komponenten |
|---|---|
| Buttons und Schalter | `GlowingButton`, `ButtonBookmark`, `ButtonFavorite`, `ButtonShare`, `ButtonTextSetting`, `CupertinoSwitch`, `CustomizedCheckbox`, `AnimatedThemeSwitcher` |
| Fortschritt und Laden | `AnimatedProgressBar`, `CustomizedProgressBar`, `GradientProgressIndicator`, `ProgressBar`, `AtomicLoader`, `LoadingDialog`, `AnimationCircularWave`, `DNAHelix` |
| Uhren und Kalender | `AnalogueClock`, `DigitalClock`, `DigitalClock2`, `DigitalClock3`, `CollapsedCalendar`, `ExpandedCalendar` |
| Texteffekte | `ShimmerText`, `DottedTextAnimation`, `FlexibleTextField`, `wordbyword/` (Animationen Zeichen für Zeichen, Wort für Wort, Zeile für Zeile, in Blöcken) |
| Karten und Flächen | `AnimatedBorderCard`, `RainbowCard`, `OverlappingRoundedBackground`, `HomePremium`, `SwipeToReveal` |
| Bilder | `ImageBeforeAndAfterSwipeAnimation`, `ImageBeforeAndAfterSwipeSection`, `PhotoSelection` |
| Layout | `TableLayout`, `TableLayoutRow`, `HeaderAndLayout`, `ContextualFlowRowSample`, `floatingdraggablebox/` (eine Blase ziehen und auf einem Schließbereich ablegen) |
| Datenanzeige | `RateBar`, `ColorSquare`, `IndexColorBar`, `HighlightIndexColor`, `WeatherSunrise` |
| Einstellungszeilen | `SettingItemWithDropdown`, `SubsettingElement` |
| Feedback | `CountdownSnackbar` |

<a id="93-charts"></a>
### 9.3 Diagramme

`ui/fragment/primary/chart/chartcomponent/` — jedes Diagramm wird mit `Canvas` gezeichnet:

| Diagramm | Datei |
|---|---|
| Liniendiagramm | `LineChart.kt`, `LineChartComponent.kt` |
| Geschwungene Linie (kubische Bézierkurve) | `CurvyLineChart.kt` |
| Balkendiagramm | `BarChart.kt` |
| Blutdruck (Bereichsbalken) | `BloodPressureChart.kt`, `BloodPressureChart2Element.kt` |
| Torte / Donut mit Lücken | `PieChart.kt`, `DonutChart.kt`, `DonutChart2.kt` |
| Ringdiagramm | `RingChart.kt`, `RingChart2.kt` |
| Stündliches Wetterdiagramm | `WeatherHourlyChart.kt`, `WeatherHourlyChartElement.kt` |

<a id="94-modifiers"></a>
### 9.4 Modifier

`ui/modifier/`:

| Modifier | Wirkung |
|---|---|
| `BorderWithAnimatedGradient` | Rotierender Verlaufsrahmen |
| `DashedBorder` | Gestrichelter Umriss |
| `ShimmerEffect` | Shimmer-Platzhalter während des Ladens |
| `PulseEffect` | Pulsierende Skalierung / pulsierendes Leuchten |
| `Shadow` | Eigener farbiger Schatten |
| `PremiumWatermark` | Premium-Badge als Overlay |
| `DebounceClickable` | Ignoriert schnell wiederholte Taps |
| `Conditional` | Einen Modifier nur anwenden, wenn eine Bedingung wahr ist |

Tooltips liegen in `ui/theme/tooltip/` (`TooltipPopup` mit Providern für Ausrichtung und Versatz).

<a id="95-utilities"></a>
### 9.5 Hilfsfunktionen

`util/`:

| Datei | Zweck |
|---|---|
| `AppUtil` | UUID, Website öffnen, App-Einstellungen öffnen, Skalierung von Figma- auf Bildschirmgrößen |
| `CanvasUtil` | Umrechnung dp ↔ px |
| `CoroutineUtil` | Fünf Wege, asynchrone Ergebnisse zusammenzuführen |
| `DateUtil`, `LocalDateUtil` | Datumsdifferenzen, Formate, Epoch-Helfer |
| `EncrytionUtil` | Beispiel für AES-Verschlüsselung / -Entschlüsselung |
| `FileUtil` | Dateinamen, Videoordner, Video teilen, MediaStore-ID |
| `FlowUtil` | `combine` für mehr als fünf Flows |
| `LanguageUtil` | Ein Locale auf einen `Context` anwenden |
| `NavigationUtil` | `canNavigate`-Schutz und Hinweise zu `popUpTo` |
| `NetworkUtil` | Prüfung der Internetverbindung |
| `PermissionUtil` | Benachrichtigungs-, Kamera-, Speicher- und Mehrfachberechtigungen prüfen |
| `SharingUtil` | Eine E-Mail verfassen, in die Zwischenablage kopieren, die App teilen |
| `SmartFunction` | Validierung und nutzerfreundliche Fehlermeldungen |
| `SystemBarUtil` | Statusleiste / Navigationsleiste ausblenden |
| `TuplesUtil` | `Quadruple` und größere Tupel |

<a id="10-notifications-and-lock-screen"></a>
## 10. Benachrichtigungen und Sperrbildschirm

| Funktion | Dateien | Funktionsweise |
|---|---|---|
| Tägliche Benachrichtigung | `notification/NotificationManager.kt`, `NotificationReceiver.kt` | `AlarmManager.setExact(RTC_WAKEUP)` plant die Erinnerung (weicht auf `set` aus, wenn exakte Alarme nicht erlaubt sind); der Receiver postet sie |
| Benachrichtigung nach der Erstinstallation | `NotificationAfter5MinuteReceiver.kt` | Eine Benachrichtigung kurz nach der ersten Installation |
| Fortschrittsbenachrichtigung | `NotificationManager.fireProgressNotification` | Laufende Benachrichtigung mit Fortschrittsbalken |
| Sperrbildschirm-Benachrichtigung | `LockscreenManager.kt`, `LockscreenReceiver.kt`, `ui/activity/LockscreenActivity.kt` | Ein Full-Screen-Intent öffnet eine Activity mit `setShowWhenLocked`; weicht auf eine normale Benachrichtigung aus |
| Benachrichtigungsberechtigung | `lifecycleobserver/NotificationLifecycleObserver.kt` | Fordert `POST_NOTIFICATIONS` ab Android 13 mit Begründungs- und Einstellungsdialogen an |

<a id="11-localization-and-theming"></a>
## 11. Lokalisierung und Theming

- **Sprachen**: Englisch (Standard), Deutsch, Französisch, Hindi, Japanisch, Koreanisch und Vietnamesisch (`res/values-*`). Die Gerätesprache steht an erster Stelle. Die Auswahl wird in DataStore gespeichert und in `CoreActivity.attachBaseContext` angewendet.
- **Theme**: `ui/theme/` enthält Farben, Typografie (`Type.kt`), Animationsspezifikationen und `CustomizedTheme`.
- **Dark Mode**: wird in den Einstellungen umgeschaltet und von `CoreFragment` angewendet.

<a id="12-performance-baseline-profile"></a>
## 12. Performance: Baseline Profile

Das Modul `:baselineprofile` enthält:

| Datei | Zweck |
|---|---|
| `BaselineProfileGenerator.kt` | Durchläuft den Startpfad, um ein Baseline Profile für `:app` zu erzeugen |
| `StartupBenchmarks.kt` | Macrobenchmark, der die Startzeit mit und ohne Profil vergleicht |

`:app` bindet das Profil über `baselineProfile(project(":baselineprofile"))` ein und liefert es mit `profileinstaller` aus.

<a id="13-project-structure"></a>
## 13. Projektstruktur

```
Jetpack-Compose/
├── app/                              # Das Anwendungsmodul
├── baselineProfile/                  # Baseline-Profile-Generator + Startup-Benchmarks
├── gradle/libs.versions.toml         # Version Catalog: alle Bibliotheks- und Plugin-Versionen
├── photo/                            # Bilder, die diese README verwendet
└── .claude/                          # Coding-Regeln und Skills für KI-Assistenten
```

Quellverzeichnis: `app/src/main/java/com/example/jetpack/`

```
com/example/jetpack/
├── backgroundwork/                   # Services und Receiver, die außerhalb eines Screens laufen
│   ├── MediaPlayerService(2).kt      #   Foreground- + Bound-Musikservice
│   ├── MediaPlayerReceiver(2).kt     #   Play / Pause / Weiter aus der Benachrichtigung
│   └── DownloadManagerReceiver.kt    #   Broadcast „Download abgeschlossen“
├── configuration/                    # Appweite Konstanten und statische Listen
│                                     #   (Constant, Language, Logo, CountryCode, BottomBarDestination)
├── core/base/                        # Basisklassen, die jeder Screen erweitert
│                                     #   (CoreActivity, CoreFragment, CoreLayout, CoreBehavior)
├── data/                             # Lokale Daten
│   ├── database/                     #   Room: QuoteDatabase, dao/, entity/, converter/, mapper/
│   ├── datastore/                    #   DataStore: SettingDatastore, TutorialDatastore
│   └── repository/                   #   Quote-, Setting-, Tutorial-, Weather-, Dummy-Repositories
│       └── impl/                     #   Repository-Implementierungen
├── domain/                           # Reine Kotlin-Geschäftstypen
│   ├── catalog/                      #   SampleRegistry: Index aller Lektionen
│   ├── enums/                        #   HomeShortcut, ChartShortcut, SortOption, Category …
│   ├── manager/                      #   MapManager: Wrapper um Fused Location
│   └── model/                        #   Quote, ChartElement, Status …
├── injection/                        # Hilt
│   ├── module/                       #   Database-, Network-, Repository-, Dispatcher-Module
│   └── qualifier/                    #   @IoDispatcher, @DefaultDispatcher
├── lifecycleobserver/                # Wiederverwendbare Berechtigungsanfragen (eine, mehrere, Standort, Benachrichtigung)
├── network/                          # Remote-Daten
│   ├── adapter/                      #   AccuWeatherFactory: entpackt und loggt eine Retrofit-Response
│   ├── dto/                          #   weather/, dummy/ Antwortmodelle
│   ├── interceptor/                  #   AccuWeatherInterceptor: fügt den API-Key hinzu
│   ├── service/                      #   AccuWeatherService, DummyService
│   └── util/                         #   safeApiCall: Response<T> → Status<T>
├── notification/                     # Tägliche, Erstinstallations-, Fortschritts- und Sperrbildschirm-Benachrichtigungen
├── ui/                               # Alles, was der Nutzer sieht
│   ├── activity/                     #   LockscreenActivity
│   ├── component/                    #   Gemeinsame Core*-Composables (Top Bars, Dialoge, Buttons)
│   ├── dialog/                       #   WheelTimePickerDialog
│   ├── fragment/
│   │   ├── primary/                  #   Tabs der Bottom Bar: home, chart (Insight), article, setting
│   │   └── secondary/                #   ein Package pro Lektion + splash, intro, language
│   ├── modifier/                     #   eigene Modifier-Extensions
│   ├── theme/                        #   Farben, Typografie, Theme, tooltip/
│   └── view/                         #   eigenständige Custom Widgets (Uhren, Loader, Karten …)
├── util/                             # Helfer und Extensions
│   └── inputtransformation/          #   Eingaberegeln DigitsOnly, VerificationCode
├── worker/                           # DownloadFileWorker (WorkManager + Hilt)
├── JetpackApplication.kt             # @HiltAndroidApp-Application-Klasse
├── JetpackDocument.kt                # Leseliste (siehe Abschnitt 16)
└── MainActivity.kt                   # Single Activity: NavHost + Bottom Bar
```

Ein Lektions-Package sieht so aus:

```
ui/fragment/secondary/gallery/
├── component/                        # Composables, die nur dieser Screen nutzt
│   ├── GalleryFolders.kt
│   ├── GalleryGridView.kt
│   ├── GalleryPermissionBottomSheet.kt
│   ├── PhotoTabElement.kt
│   └── RequestGalleryPermission.kt
├── GalleryFragment.kt                # Einstiegspunkt + zustandsloses Layout + Previews
├── GalleryUiState.kt                 # unveränderlicher Screen-Zustand
└── GalleryViewModel.kt               # lädt Fotos, stellt StateFlow bereit
```

<a id="14-adding-a-new-lesson"></a>
## 14. Eine neue Lektion hinzufügen

1. Ein Package unter `ui/fragment/secondary/<lesson>/` mit `XxxFragment`, `XxxViewModel` (falls nötig) und `component/` anlegen.
2. Das Ziel und eine `toXxx`-Action in `res/navigation/navigation_graph.xml` hinzufügen.
3. Einen Eintrag in `domain/enums/HomeShortcut.kt` hinzufügen (Icon + Titel-String).
4. Den Titel ans Ende von `res/values/strings.xml` anhängen.
5. Den Shortcut in `HomeFragment` mit `safeNavigate(R.id.toXxx)` behandeln.
6. Ein `SampleMeta` in `domain/catalog/SampleRegistry.kt` registrieren, damit die Suche die Lektion findet.
7. Die Tabelle [Lektionen](#7-lessons) in dieser README aktualisieren.

<a id="15-work-in-progress"></a>
## 15. In Arbeit

| Eintrag | Status |
|---|---|
| Tooltip-Shortcut | Auf Home aufgeführt, aber noch nicht verdrahtet (zeigt einen Toast). Code: `ui/theme/tooltip/TooltipPopup.kt` |
| Shortcut für Shared Element Transition | Auf Home aufgeführt, hat aber noch keinen Screen |
| Mecca Compass | Das Ziel existiert; der Screen ist ein leerer Platzhalter |
| `secondary/basictextfield/` | Ältere Kopie der Lektion Basic Text Field, nicht im Navigationsgraphen. `secondary/textfield/` ist die aktive Version |

<a id="16-reading-list"></a>
## 16. Leseliste

`JetpackDocument.kt` sammelt die Artikel, auf denen viele Lektionen beruhen.

<details>
<summary><b>Alle 42 Artikel anzeigen</b></summary>

| # | Thema |
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
## 17. KI-gestützte Entwicklung

Das Repository enthält Coding-Regeln für KI-Assistenten wie Claude Code:

| Pfad | Inhalt |
|---|---|
| `CLAUDE.md` | Ausgabestil und Kotlin-Regeln für den Assistenten |
| `.claude/rules/` | Projektregeln: Skeleton-Struktur, Compose-UI, Repository-, Use-Case- und ViewModel-Schicht, Figma-Designsystem |
| `.claude/skills/` | Wiederverwendbare Skills (TDD, Diagnose, Schreiben von PRDs und Issues, Architektur-Review …) |

---

<p align="center">
    Mit 💘 und Kotlin gemacht von <a href="https://github.com/Phong-Kaster">Phong-Kaster</a>
</p>
