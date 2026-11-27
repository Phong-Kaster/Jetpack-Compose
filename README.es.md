<h1 align="center">Jetpack Compose Playground<br/>
    <sub>Un esqueleto y un catálogo de lecciones para apps Android hechas con Kotlin y Jetpack Compose</sub>
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
## Tabla de contenidos

<p align="center">
    <a href="README.md">English</a> ·
    <a href="README.vi.md">Tiếng Việt</a> ·
    <b>Español</b> ·
    <a href="README.de.md">Deutsch</a> ·
    <a href="README.fr.md">Français</a> ·
    <a href="README.hi.md">हिन्दी</a> ·
    <a href="README.ja.md">日本語</a> ·
    <a href="README.ko.md">한국어</a>
</p>

1. [Introducción](#1-introduction)
2. [Puntos destacados](#2-highlights)
3. [Primeros pasos](#3-getting-started)
   - [Requisitos](#31-requirements)
   - [Compilar y ejecutar](#32-build-and-run)
   - [Configuración](#33-configuration)
4. [Stack tecnológico](#4-tech-stack)
5. [Arquitectura](#5-architecture)
   - [Capas](#51-layers)
   - [Anatomía de una pantalla](#52-screen-anatomy)
   - [Navegación](#53-navigation)
   - [Catálogo de ejemplos](#54-sample-catalog)
6. [Flujo de la app](#6-app-flow)
7. [Lecciones](#7-lessons)
   - [UI y layout](#71-ui-and-layout)
   - [Animación y gráficos](#72-animation-and-graphics)
   - [Entrada de texto](#73-text-input)
   - [Datos y red](#74-data-and-networking)
   - [Permisos y funciones del dispositivo](#75-permissions-and-device-features)
   - [Multimedia y trabajo en segundo plano](#76-media-and-background-work)
   - [Kotlin y corrutinas](#77-kotlin-and-coroutines)
8. [Pestañas de la barra inferior](#8-bottom-bar-tabs)
9. [Biblioteca de componentes reutilizables](#9-reusable-component-library)
   - [Componentes base](#91-core-components)
   - [Vistas personalizadas](#92-custom-views)
   - [Gráficos](#93-charts)
   - [Modifiers](#94-modifiers)
   - [Utilidades](#95-utilities)
10. [Notificaciones y pantalla de bloqueo](#10-notifications-and-lock-screen)
11. [Localización y temas](#11-localization-and-theming)
12. [Rendimiento: Baseline Profile](#12-performance-baseline-profile)
13. [Estructura del proyecto](#13-project-structure)
14. [Añadir una nueva lección](#14-adding-a-new-lesson)
15. [Trabajo en curso](#15-work-in-progress)
16. [Lista de lectura](#16-reading-list)
17. [Desarrollo asistido por IA](#17-ai-assisted-development)

---

<a id="1-introduction"></a>
## 1. Introducción

Este repositorio es a la vez un **playground de Jetpack Compose** y un **esqueleto de app reutilizable**.

- **Como playground**, reúne más de 25 lecciones ejecutables: animaciones, top bars colapsables, permisos, Bluetooth, ubicación, reproducción multimedia, descargas, Room, DataStore, Retrofit, Kotlin Flow y más. Cada lección es una pantalla que puedes abrir desde la cuadrícula de Home, leer y copiar a tu propio proyecto.
- **Como esqueleto**, incluye las piezas que necesitan la mayoría de las apps en producción: splash, selector de idioma, onboarding, una barra inferior con cuatro pestañas, ajustes (modo oscuro, idioma, icono de la app, valoración), notificaciones diarias, una notificación en la pantalla de bloqueo, inyección de dependencias y un Baseline Profile.

El diseño está inspirado en [Android Architecture Samples](https://github.com/android/architecture-samples). El código es 100 % Kotlin. Cada pantalla es una UI de Compose alojada en un Fragment.

<a id="2-highlights"></a>
## 2. Puntos destacados

| Área | Qué obtienes |
|---|---|
| **UI** | Jetpack Compose + Material 3, más de 40 vistas personalizadas, 13 gráficos con Canvas, 8 modifiers personalizados |
| **Arquitectura** | Una sola activity, pantallas Fragment + Compose, MVVM, patrón repository, DI con Hilt |
| **Asincronía** | Kotlin Coroutines, `Flow` / `StateFlow`, un wrapper `safeApiCall` que convierte un `Response` de Retrofit en un `Status` |
| **Datos** | Room (citas), DataStore Preferences (ajustes, estado del tutorial), Retrofit + OkHttp (AccuWeather, DummyJSON) |
| **Dispositivo** | Permisos en tiempo de ejecución, Fused Location, Bluetooth Classic + BLE, cámara, MediaStore, WebView |
| **Segundo plano** | Servicio multimedia en primer plano, WorkManager (`@HiltWorker`), DownloadManager, alarmas exactas |
| **Estructura de la app** | Splash, intro, selector de idioma, barra inferior, ajustes, icono de lanzador dinámico, reseña dentro de la app |
| **Calidad** | Baseline Profile + módulo de benchmark de arranque, `@Preview` en la mayoría de los composables |
| **Localización** | Inglés, alemán, francés, hindi, japonés, coreano, vietnamita |

<a id="3-getting-started"></a>
## 3. Primeros pasos

<a id="31-requirements"></a>
### 3.1 Requisitos

| Herramienta | Versión |
|---|---|
| Android Studio | Una versión compatible con AGP 9.5 |
| JDK | 17 o superior (el proyecto genera bytecode de Java 17) |
| Gradle | 9.9.2 (lo descarga el wrapper) |
| Android SDK | compileSdk 36, minSdk 26 |

<a id="32-build-and-run"></a>
### 3.2 Compilar y ejecutar

```bash
git clone https://github.com/Phong-Kaster/Jetpack-Compose.git
cd Jetpack-Compose

# Instala la build de debug en un dispositivo conectado o en un emulador
./gradlew :app:installDebug

# Otras tareas útiles
./gradlew :app:assembleRelease        # APK de release (R8 activado)
./gradlew :app:testDebugUnitTest      # tests unitarios
./gradlew :baselineprofile:connectedCheck  # genera el Baseline Profile / ejecuta los benchmarks
```

La build renombra cada APK a `Bundeswehr_v<versionName>(<versionCode>)_<timestamp>-<variant>.apk`.

<a id="33-configuration"></a>
### 3.3 Configuración

| Ajuste | Ubicación | Notas |
|---|---|---|
| Clave de API de AccuWeather | `configuration/Constant.kt` → `ACCU_WEATHER_KEY` | Sustitúyela por tu propia clave de [AccuWeather Developer](https://developer.accuweather.com/). En proyectos reales, es preferible usar `local.properties` + `BuildConfig`. |
| URL base de AccuWeather | `configuration/Constant.kt` → `ACCU_WEATHER_URL` | `https://dataservice.accuweather.com` |
| URL base de DummyJSON | `injection/module/NetworkModule.kt` | `https://dummyjson.com/` |
| Versiones de las bibliotecas | `gradle/libs.versions.toml` | Un único version catalog para todas las dependencias y plugins |

<a id="4-tech-stack"></a>
## 4. Stack tecnológico

| Categoría | Bibliotecas |
|---|---|
| Lenguaje y build | Kotlin 2.4.21, AGP 9.5.3, Gradle 9.9.2, KSP 2.3.14, version catalog |
| UI | Compose BOM 2026.03.01, Material 3, Material Icons Extended, ConstraintLayout Compose (MotionLayout), Accompanist Permissions |
| Arquitectura | AndroidX Lifecycle 2.10, Navigation Component 2.9.7 (Fragment + Safe Args), Hilt 2.60.1 |
| Datos | Room 2.8.4, DataStore Preferences 1.2.1, Kotlinx Collections Immutable |
| Red | Retrofit 2.12, OkHttp logging interceptor 5.3, Gson |
| Imágenes y animación | Coil 2 (+ GIF), Glide 4, Lottie 6 |
| Multimedia | `MediaPlayer` + notificación `MediaStyle`, Media3 ExoPlayer / UI / Session |
| Segundo plano | WorkManager 2.11 + Hilt Work, `AlarmManager`, `DownloadManager` |
| Google Play | Play Services Location, Play In-App Review |
| Rendimiento | Baseline Profile 1.4, Macrobenchmark, ProfileInstaller |

<a id="5-architecture"></a>
## 5. Arquitectura

<a id="51-layers"></a>
### 5.1 Capas

```
┌──────────────────────────────────────────────────────────┐
│ ui/        Fragments → Compose layouts → ViewModels      │  presentación
├──────────────────────────────────────────────────────────┤
│ domain/    models, enums, managers, sample catalog       │  tipos de negocio
├──────────────────────────────────────────────────────────┤
│ data/      repositories → Room DAO / DataStore           │  datos locales
│ network/   Retrofit services, DTOs, interceptors         │  datos remotos
├──────────────────────────────────────────────────────────┤
│ injection/ Hilt modules wire everything together         │  DI
└──────────────────────────────────────────────────────────┘
```

- Los **ViewModels** exponen el estado de la pantalla como `StateFlow` y nunca tocan vistas de Android.
- Los **repositorios** son la única puerta a Room, DataStore y Retrofit. Una pantalla le pide los datos a un repositorio, nunca a un DAO ni a un servicio.
- Las **llamadas de red** pasan por `safeApiCall` (`network/util/SafeApiFlow.kt`), que envuelve un `Response` de Retrofit en un `Status` (`Success` / `Failure`); los repositorios lo exponen a los ViewModels como un `Flow`.
- **Hilt** proporciona la base de datos, los dos clientes de Retrofit, los repositorios y los dispatchers de corrutinas (`@IoDispatcher`, `@DefaultDispatcher`).

<a id="52-screen-anatomy"></a>
### 5.2 Anatomía de una pantalla

Todas las pantallas siguen la misma forma:

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

| Clase base | Función |
|---|---|
| `core/base/CoreActivity` | Aplica el idioma guardado en `attachBaseContext`, helpers de toast y de conectividad |
| `core/base/CoreFragment` | Crea el `ComposeView`, aplica el tema de la app y el modo oscuro, proporciona `LocalNavController` y `LocalLocale` |
| `core/base/CoreLayout` | Un wrapper de `Scaffold` con top bar, barra inferior, FAB e insets edge-to-edge |
| `core/base/CoreBehavior` | Contrato compartido: `showToast`, `isInternetConnected`, `trackEvent` |

<a id="53-navigation"></a>
### 5.3 Navegación

- Una sola activity (`MainActivity`) contiene un `NavHostFragment` y la barra inferior.
- Todos los destinos están en `res/navigation/navigation_graph.xml`. El destino inicial es la pantalla de splash; las pantallas de introducción forman un grafo anidado.
- Navega con `safeNavigate(R.id.toXxx)` de `util/NavigationUtil.kt`; primero comprueba el destino actual, así que un doble toque nunca provoca un crash.

<a id="54-sample-catalog"></a>
### 5.4 Catálogo de ejemplos

`domain/catalog/SampleRegistry.kt` es el índice de todas las lecciones. Cada `HomeShortcut` tiene una entrada `SampleMeta` con:

- el archivo de código principal que conviene leer o copiar,
- otros archivos relacionados,
- temas (`SampleTopic`: `ANIMATION`, `PERMISSION`, `MEDIA`, …),
- alias de búsqueda.

La barra de búsqueda de Home usa este registro, así que escribir `retrofit`, `pinch` o `qibla` encuentra la lección correcta.

<a id="6-app-flow"></a>
## 6. Flujo de la app

```
Splash ─(primer inicio)──► Intro language ──► Intro pages ──► Home
   │                                                         ▲
   └────────────────(usuario recurrente)─────────────────────┘

Home ◄──► Insight ◄──► Article ◄──► Settings      (barra inferior)
  │
  └──► Pantallas de lecciones (cuadrícula de Home, búsqueda, orden)
```

| Pantalla | Paquete | Qué muestra |
|---|---|---|
| Splash | `secondary/splash` | Decide entre la intro y Home según DataStore |
| Intro language | `secondary/intro` | Selector de idioma de la primera ejecución |
| Intro | `secondary/intro` | Pager de onboarding con un indicador de página personalizado |
| Language | `secondary/language` | Cambia el idioma de la app más tarde, desde Settings |

<a id="7-lessons"></a>
## 7. Lecciones

Todas las lecciones siguientes se abren desde la cuadrícula de **Home**. Las rutas son relativas a `app/src/main/java/com/example/jetpack/ui/fragment/`.

<a id="71-ui-and-layout"></a>
### 7.1 UI y layout

| Lección | Paquete | Qué aprendes |
|---|---|---|
| Collapsible Top Bar | `secondary/collapsibletopbar/collapsible` | Colapsar una cabecera según la posición del scroll con `derivedStateOf` |
| Collapsible Top Bar 2 | `secondary/collapsibletopbar/collapsible2` | `TopAppBarDefaults.enterAlwaysScrollBehavior` de Material 3 + `nestedScroll` |
| Collapsible Top Bar 3 | `secondary/collapsibletopbar/collapsible3` | Un `NestedScrollConnection` hecho a mano con cabeceras completa / minimizada |
| Collapsible Top Bar 4 | `secondary/collapsibletopbar/collapsible4` | `LargeTopAppBar` que cambia según `firstVisibleItemIndex` |
| Collapsible Top Bar 5 | `secondary/collapsibletopbar/collapsible5` | Barras expandida / colapsada combinadas con `enterAlwaysScrollBehavior` |
| Instagram Carousel | `secondary/instagramcarousel` | Pager con un indicador de puntos arrastrable mediante pulsación larga |
| Pinch to Zoom | `secondary/pitchtozoom` | `detectTransformGestures` + `graphicsLayer` para zoom, desplazamiento y rotación |
| Tutorial | `secondary/tutorial` | Un tutorial paso a paso con foco (spotlight) hecho con `onGloballyPositioned` y `boundsInRoot`, con el estado guardado en DataStore |
| Login | `secondary/login` | Formulario de login: `FocusRequester`, `KeyboardOptions`, acciones IME, `PasswordVisualTransformation` |
| WebView | `secondary/webview` | `WebView` dentro de `AndroidView`, callbacks de `WebViewClient`, gestión del botón atrás con `BackHandler` |

<a id="72-animation-and-graphics"></a>
### 7.2 Animación y gráficos

| Lección | Paquete | Qué aprendes |
|---|---|---|
| Animation | `secondary/animation` | `tween`, `spring`, `keyframes`, `infiniteRepeatable`, `drawBehind`, `drawWithContent`, transformaciones afines |
| Motion Layout | `secondary/motionlayout` | `MotionLayout` de Compose con escenas de movimiento en JSON5 (`res/raw/*.json5`) y un estado personalizado de toolbar colapsable |

<a id="73-text-input"></a>
### 7.3 Entrada de texto

| Lección | Paquete | Qué aprendes |
|---|---|---|
| Basic Text Field (state) | `secondary/textfield` | `BasicTextField` con `TextFieldState`: `InputTransformation`, transformaciones integradas, `OutputTransformation`, autocompletado, tamaño automático, un campo de texto flexible |

Las reglas de entrada reutilizables están en `util/inputtransformation/` (`DigitsOnlyTransformation`, `VerificationCodeTransformation`).

<a id="74-data-and-networking"></a>
### 7.4 Datos y red

| Lección | Paquete | Qué aprendes |
|---|---|---|
| Quote | `secondary/quote` | CRUD con Room: entity, DAO, type converters (`Date`, `Category`), mapper, `Flow` de filas |
| AccuWeather | `secondary/accuweather` | Retrofit + un interceptor de OkHttp que añade la clave de API, búsqueda de ciudades con autocompletado |
| Home (DummyJSON) | `primary/home` | Un segundo cliente de Retrofit (`DummyService`) llamado desde el ViewModel de Home |

<a id="75-permissions-and-device-features"></a>
### 7.5 Permisos y funciones del dispositivo

| Lección | Paquete | Qué aprendes |
|---|---|---|
| Permissions | `secondary/permission` | Pedir uno o varios permisos directamente en un Fragment, diálogo de justificación, abrir los ajustes de la app |
| Permissions 2 | `secondary/permission` | Los mismos flujos trasladados a clases `LifecycleObserver` reutilizables (`lifecycleobserver/`) |
| Last Known Location | `secondary/location` | Fused Location Provider mediante `MapManager`: última ubicación, actualizaciones en directo, activar el GPS |
| Bluetooth | `secondary/bluetooth` | Bluetooth Classic (dispositivos vinculados, descubrimiento, conexión) y escaneo BLE, animación de escaneo con Lottie |
| Photo from Device Storage | `secondary/gallery` | Leer fotos de MediaStore por carpeta, captura con la cámara, bottom sheet de permisos |

<a id="76-media-and-background-work"></a>
### 7.6 Multimedia y trabajo en segundo plano

| Lección | Paquete | Qué aprendes |
|---|---|---|
| Music Player | `secondary/mediaplayer` | `MediaPlayer` en un servicio en primer plano **y** vinculado (bound), notificación `MediaStyle` personalizada con `RemoteViews`, canciones de `res/raw` y de MediaStore |
| Music Player 2 | `secondary/mediaplayer` | Una segunda versión del mismo reproductor con su propio servicio (`MediaPlayerService2`) y receiver |
| Download with WorkManager | `secondary/downloadwithworker` | `@HiltWorker` que descarga un PDF o un vídeo, muestra el progreso en una notificación y devuelve el `Uri` guardado |
| Download Manager | `secondary/downloadmanager` | El `DownloadManager` del sistema más un `BroadcastReceiver` para detectar el final de la descarga |

<a id="77-kotlin-and-coroutines"></a>
### 7.7 Kotlin y corrutinas

| Lección | Paquete | Qué aprendes |
|---|---|---|
| Kotlin Flow | `secondary/kotlinflow` | Builders de Flow (`flow`, `flowOf`, `asFlow`), operadores terminales (`collect`, `first`, `toList`, `reduce`), control con `async` y `Job` |

Utilidades relacionadas: `util/CoroutineUtil.kt` (cinco formas de combinar resultados asíncronos), `util/FlowUtil.kt` (combinar más de cinco flows).

<a id="8-bottom-bar-tabs"></a>
## 8. Pestañas de la barra inferior

| Pestaña | Fragment | Contenido |
|---|---|---|
| **Home** | `primary/home/HomeFragment` | Cuadrícula de lecciones con búsqueda, opciones de orden, carga con shimmer, una caja flotante arrastrable, un reloj digital y una tarjeta premium |
| **Insight** | `primary/chart/InsightFragment` | Pestañas en chips: galería de componentes, calendario, gráficos de línea / anillo / barras / área, reloj analógico, paleta de colores, prueba de ajuste de fotos (contraste, balance de blancos) |
| **Article** | `primary/article/ArticleFragment` | Escaparate de vistas gráficas: Atomic loader, hélice de ADN, reloj analógico, canvas de amanecer, comparación de imagen antes / después deslizando |
| **Settings** | `primary/setting/SettingFragment` | Modo oscuro, idioma, icono de la app, diálogo de valoración + Play In-App Review, aviso legal |

Settings también contiene `SettingIconFragment`, que cambia el icono del lanzador en tiempo de ejecución mediante entradas `<activity-alias>` del manifest.

<a id="9-reusable-component-library"></a>
## 9. Biblioteca de componentes reutilizables

Todo lo de esta sección es un composable independiente que puedes copiar a otro proyecto. La mayoría tiene un `@Preview`.

<a id="91-core-components"></a>
### 9.1 Componentes base

`ui/component/` contiene los bloques de construcción comunes a toda la app:

| Grupo | Componentes |
|---|---|
| Top bars | `CoreTopBar`, `CoreTopBar2`–`4`, `CoreTopBarWithHomeShortcut`, `CoreTopBarWithScrollBehavior`, `CustomizeTopBar` |
| Barra inferior | `CoreBottomBar` |
| Diálogos y sheets | `CoreDialog`, `CoreDialogLayout`, `CoreAlertDialog`, `CoreMaterialDialog`, `CoreTextAnimationDialog`, `CoreBottomSheet` |
| Botones | `CoreButton`, `CoreFloatingButton`, `CoreExpandableFloatingButton` |
| Ciclo de vida | `LifecycleComposable` (observa `ON_START` / `ON_STOP` … desde Compose) |
| Selectores | `ui/dialog/WheelTimePickerDialog` (selector de hora en rueda al estilo de ELSA Speak) |

<a id="92-custom-views"></a>
### 9.2 Vistas personalizadas

`ui/view/` contiene widgets autónomos:

| Grupo | Componentes |
|---|---|
| Botones e interruptores | `GlowingButton`, `ButtonBookmark`, `ButtonFavorite`, `ButtonShare`, `ButtonTextSetting`, `CupertinoSwitch`, `CustomizedCheckbox`, `AnimatedThemeSwitcher` |
| Progreso y carga | `AnimatedProgressBar`, `CustomizedProgressBar`, `GradientProgressIndicator`, `ProgressBar`, `AtomicLoader`, `LoadingDialog`, `AnimationCircularWave`, `DNAHelix` |
| Relojes y calendarios | `AnalogueClock`, `DigitalClock`, `DigitalClock2`, `DigitalClock3`, `CollapsedCalendar`, `ExpandedCalendar` |
| Efectos de texto | `ShimmerText`, `DottedTextAnimation`, `FlexibleTextField`, `wordbyword/` (animaciones carácter a carácter, palabra a palabra, línea a línea y por bloques) |
| Tarjetas y superficies | `AnimatedBorderCard`, `RainbowCard`, `OverlappingRoundedBackground`, `HomePremium`, `SwipeToReveal` |
| Imágenes | `ImageBeforeAndAfterSwipeAnimation`, `ImageBeforeAndAfterSwipeSection`, `PhotoSelection` |
| Layout | `TableLayout`, `TableLayoutRow`, `HeaderAndLayout`, `ContextualFlowRowSample`, `floatingdraggablebox/` (arrastra una burbuja y suéltala en una zona de cierre) |
| Visualización de datos | `RateBar`, `ColorSquare`, `IndexColorBar`, `HighlightIndexColor`, `WeatherSunrise` |
| Filas de ajustes | `SettingItemWithDropdown`, `SubsettingElement` |
| Feedback | `CountdownSnackbar` |

<a id="93-charts"></a>
### 9.3 Gráficos

`ui/fragment/primary/chart/chartcomponent/` — todos los gráficos se dibujan con `Canvas`:

| Gráfico | Archivo |
|---|---|
| Gráfico de líneas | `LineChart.kt`, `LineChartComponent.kt` |
| Línea curva (Bézier cúbica) | `CurvyLineChart.kt` |
| Gráfico de barras | `BarChart.kt` |
| Presión arterial (barras de rango) | `BloodPressureChart.kt`, `BloodPressureChart2Element.kt` |
| Tarta / donut con huecos | `PieChart.kt`, `DonutChart.kt`, `DonutChart2.kt` |
| Gráfico de anillo | `RingChart.kt`, `RingChart2.kt` |
| Gráfico del tiempo por horas | `WeatherHourlyChart.kt`, `WeatherHourlyChartElement.kt` |

<a id="94-modifiers"></a>
### 9.4 Modifiers

`ui/modifier/`:

| Modifier | Efecto |
|---|---|
| `BorderWithAnimatedGradient` | Borde con degradado giratorio |
| `DashedBorder` | Contorno discontinuo |
| `ShimmerEffect` | Placeholder con shimmer mientras se carga |
| `PulseEffect` | Escala / brillo pulsante |
| `Shadow` | Sombra de color personalizada |
| `PremiumWatermark` | Insignia premium superpuesta |
| `DebounceClickable` | Ignora los toques repetidos rápidos |
| `Conditional` | Aplica un modifier solo cuando una condición es verdadera |

Los tooltips están en `ui/theme/tooltip/` (`TooltipPopup` con proveedores de alineación y desplazamiento).

<a id="95-utilities"></a>
### 9.5 Utilidades

`util/`:

| Archivo | Propósito |
|---|---|
| `AppUtil` | UUID, abrir un sitio web, abrir los ajustes de la app, escalado de tamaños de Figma a la pantalla |
| `CanvasUtil` | Conversión dp ↔ px |
| `CoroutineUtil` | Cinco formas de combinar resultados asíncronos |
| `DateUtil`, `LocalDateUtil` | Diferencias entre fechas, formatos, helpers de epoch |
| `EncrytionUtil` | Ejemplo de cifrado / descifrado AES |
| `FileUtil` | Nombres de archivo, carpeta de vídeos, compartir un vídeo, id de MediaStore |
| `FlowUtil` | `combine` para más de cinco flows |
| `LanguageUtil` | Aplica un locale a un `Context` |
| `NavigationUtil` | Guarda `canNavigate` y notas sobre `popUpTo` |
| `NetworkUtil` | Comprobación de la conexión a internet |
| `PermissionUtil` | Comprueba los permisos de notificaciones, cámara, almacenamiento y varios permisos a la vez |
| `SharingUtil` | Redactar un correo, copiar al portapapeles, compartir la app |
| `SmartFunction` | Validación y mensajes de error fáciles de entender |
| `SystemBarUtil` | Ocultar la barra de estado / la barra de navegación |
| `TuplesUtil` | `Quadruple` y tuplas más grandes |

<a id="10-notifications-and-lock-screen"></a>
## 10. Notificaciones y pantalla de bloqueo

| Función | Archivos | Cómo funciona |
|---|---|---|
| Notificación diaria | `notification/NotificationManager.kt`, `NotificationReceiver.kt` | `AlarmManager.setExact(RTC_WAKEUP)` programa el recordatorio (recurre a `set` cuando no se permiten alarmas exactas); el receiver la publica |
| Notificación tras la primera instalación | `NotificationAfter5MinuteReceiver.kt` | Una notificación poco después de la primera instalación |
| Notificación de progreso | `NotificationManager.fireProgressNotification` | Notificación persistente con una barra de progreso |
| Notificación en la pantalla de bloqueo | `LockscreenManager.kt`, `LockscreenReceiver.kt`, `ui/activity/LockscreenActivity.kt` | Un full-screen intent abre una activity con `setShowWhenLocked`; si no es posible, muestra una notificación normal |
| Permiso de notificaciones | `lifecycleobserver/NotificationLifecycleObserver.kt` | Pide `POST_NOTIFICATIONS` en Android 13+ con diálogos de justificación y de ajustes |

<a id="11-localization-and-theming"></a>
## 11. Localización y temas

- **Idiomas**: inglés (por defecto), alemán, francés, hindi, japonés, coreano y vietnamita (`res/values-*`). El idioma del dispositivo aparece primero en la lista. La elección se guarda en DataStore y se aplica en `CoreActivity.attachBaseContext`.
- **Tema**: `ui/theme/` contiene los colores, la tipografía (`Type.kt`), las especificaciones de animación y `CustomizedTheme`.
- **Modo oscuro**: se activa en Settings y lo aplica `CoreFragment`.

<a id="12-performance-baseline-profile"></a>
## 12. Rendimiento: Baseline Profile

El módulo `:baselineprofile` contiene:

| Archivo | Propósito |
|---|---|
| `BaselineProfileGenerator.kt` | Recorre el camino de arranque para generar un Baseline Profile para `:app` |
| `StartupBenchmarks.kt` | Macrobenchmark que compara el tiempo de arranque con y sin el perfil |

`:app` consume el perfil mediante `baselineProfile(project(":baselineprofile"))` y lo distribuye con `profileinstaller`.

<a id="13-project-structure"></a>
## 13. Estructura del proyecto

```
Jetpack-Compose/
├── app/                              # El módulo de la aplicación
├── baselineProfile/                  # Generador del Baseline Profile + benchmarks de arranque
├── gradle/libs.versions.toml         # Version catalog: versión de cada biblioteca y plugin
├── photo/                            # Imágenes usadas por este README
└── .claude/                          # Reglas de código y skills para asistentes de IA
```

Raíz del código fuente: `app/src/main/java/com/example/jetpack/`

```
com/example/jetpack/
├── backgroundwork/                   # Servicios y receivers que funcionan fuera de una pantalla
│   ├── MediaPlayerService(2).kt      #   servicio de música en primer plano + vinculado
│   ├── MediaPlayerReceiver(2).kt     #   reproducir / pausar / siguiente desde la notificación
│   └── DownloadManagerReceiver.kt    #   broadcast de "descarga terminada"
├── configuration/                    # Constantes y listas estáticas de toda la app
│                                     #   (Constant, Language, Logo, CountryCode, BottomBarDestination)
├── core/base/                        # Clases base que extiende cada pantalla
│                                     #   (CoreActivity, CoreFragment, CoreLayout, CoreBehavior)
├── data/                             # Datos locales
│   ├── database/                     #   Room: QuoteDatabase, dao/, entity/, converter/, mapper/
│   ├── datastore/                    #   DataStore: SettingDatastore, TutorialDatastore
│   └── repository/                   #   repositorios Quote, Setting, Tutorial, Weather, Dummy
│       └── impl/                     #   implementaciones de los repositorios
├── domain/                           # Tipos de negocio en Kotlin puro
│   ├── catalog/                      #   SampleRegistry: índice de todas las lecciones
│   ├── enums/                        #   HomeShortcut, ChartShortcut, SortOption, Category …
│   ├── manager/                      #   MapManager: wrapper de Fused Location
│   └── model/                        #   Quote, ChartElement, Status …
├── injection/                        # Hilt
│   ├── module/                       #   módulos Database, Network, Repository, Dispatcher
│   └── qualifier/                    #   @IoDispatcher, @DefaultDispatcher
├── lifecycleobserver/                # Peticiones de permisos reutilizables (uno, varios, ubicación, notificaciones)
├── network/                          # Datos remotos
│   ├── adapter/                      #   AccuWeatherFactory: desenvuelve y registra un Response de Retrofit
│   ├── dto/                          #   modelos de respuesta weather/, dummy/
│   ├── interceptor/                  #   AccuWeatherInterceptor: añade la clave de API
│   ├── service/                      #   AccuWeatherService, DummyService
│   └── util/                         #   safeApiCall: Response<T> → Status<T>
├── notification/                     # Notificaciones diaria, de primera instalación, de progreso y de pantalla de bloqueo
├── ui/                               # Todo lo que ve el usuario
│   ├── activity/                     #   LockscreenActivity
│   ├── component/                    #   composables compartidos Core* (top bars, diálogos, botones)
│   ├── dialog/                       #   WheelTimePickerDialog
│   ├── fragment/
│   │   ├── primary/                  #   pestañas de la barra inferior: home, chart (Insight), article, setting
│   │   └── secondary/                #   un paquete por lección + splash, intro, language
│   ├── modifier/                     #   extensiones de Modifier personalizadas
│   ├── theme/                        #   colores, tipografía, tema, tooltip/
│   └── view/                         #   widgets personalizados independientes (relojes, loaders, tarjetas …)
├── util/                             # Helpers y extensiones
│   └── inputtransformation/          #   reglas de entrada DigitsOnly, VerificationCode
├── worker/                           # DownloadFileWorker (WorkManager + Hilt)
├── JetpackApplication.kt             # clase Application con @HiltAndroidApp
├── JetpackDocument.kt                # Lista de lectura (ver sección 16)
└── MainActivity.kt                   # Activity única: NavHost + barra inferior
```

Un paquete de lección tiene este aspecto:

```
ui/fragment/secondary/gallery/
├── component/                        # composables que solo usa esta pantalla
│   ├── GalleryFolders.kt
│   ├── GalleryGridView.kt
│   ├── GalleryPermissionBottomSheet.kt
│   ├── PhotoTabElement.kt
│   └── RequestGalleryPermission.kt
├── GalleryFragment.kt                # punto de entrada + layout sin estado + previews
├── GalleryUiState.kt                 # estado inmutable de la pantalla
└── GalleryViewModel.kt               # carga las fotos, expone StateFlow
```

<a id="14-adding-a-new-lesson"></a>
## 14. Añadir una nueva lección

1. Crea un paquete en `ui/fragment/secondary/<lesson>/` con `XxxFragment`, `XxxViewModel` (si hace falta) y `component/`.
2. Añade el destino y una acción `toXxx` a `res/navigation/navigation_graph.xml`.
3. Añade una entrada a `domain/enums/HomeShortcut.kt` (icono + string del título).
4. Añade el título al final de `res/values/strings.xml`.
5. Gestiona el acceso directo en `HomeFragment` con `safeNavigate(R.id.toXxx)`.
6. Registra un `SampleMeta` en `domain/catalog/SampleRegistry.kt` para que la búsqueda lo encuentre.
7. Actualiza la tabla de [Lecciones](#7-lessons) de este README.

<a id="15-work-in-progress"></a>
## 15. Trabajo en curso

| Elemento | Estado |
|---|---|
| Acceso directo Tooltip | Aparece en Home pero aún no está conectado (muestra un toast). Código: `ui/theme/tooltip/TooltipPopup.kt` |
| Acceso directo Shared Element Transition | Aparece en Home pero todavía no tiene pantalla |
| Mecca Compass | El destino existe; la pantalla es un placeholder vacío |
| `secondary/basictextfield/` | Copia antigua de la lección Basic Text Field, fuera del grafo de navegación. La activa es `secondary/textfield/` |

<a id="16-reading-list"></a>
## 16. Lista de lectura

`JetpackDocument.kt` recopila los artículos en los que se basan muchas de las lecciones.

<details>
<summary><b>Mostrar los 42 artículos</b></summary>

| # | Tema |
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
## 17. Desarrollo asistido por IA

El repositorio incluye reglas de código para asistentes de IA como Claude Code:

| Ruta | Contenido |
|---|---|
| `CLAUDE.md` | Estilo de salida y reglas de Kotlin para el asistente |
| `.claude/rules/` | Reglas del proyecto: estructura del esqueleto, UI con Compose, capas de repository, use case y ViewModel, design system de Figma |
| `.claude/skills/` | Skills reutilizables (TDD, diagnóstico, redacción de PRD e issues, revisión de arquitectura …) |

---

<p align="center">
    Hecho con 💘 y Kotlin por <a href="https://github.com/Phong-Kaster">Phong-Kaster</a>
</p>
