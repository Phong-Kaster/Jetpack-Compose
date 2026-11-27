<h1 align="center">Jetpack Compose Playground<br/>
    <sub>Un squelette et un catalogue de leçons pour les applications Android développées avec Kotlin et Jetpack Compose</sub>
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
## Table des matières

<p align="center">
    <a href="README.md">English</a> ·
    <a href="README.vi.md">Tiếng Việt</a> ·
    <a href="README.es.md">Español</a> ·
    <a href="README.de.md">Deutsch</a> ·
    <b>Français</b> ·
    <a href="README.hi.md">हिन्दी</a> ·
    <a href="README.ja.md">日本語</a> ·
    <a href="README.ko.md">한국어</a>
</p>

1. [Introduction](#1-introduction)
2. [Points forts](#2-highlights)
3. [Premiers pas](#3-getting-started)
   - [Prérequis](#31-requirements)
   - [Compiler et lancer](#32-build-and-run)
   - [Configuration](#33-configuration)
4. [Stack technique](#4-tech-stack)
5. [Architecture](#5-architecture)
   - [Couches](#51-layers)
   - [Anatomie d'un écran](#52-screen-anatomy)
   - [Navigation](#53-navigation)
   - [Catalogue des exemples](#54-sample-catalog)
6. [Parcours de l'application](#6-app-flow)
7. [Leçons](#7-lessons)
   - [UI et mise en page](#71-ui-and-layout)
   - [Animation et graphismes](#72-animation-and-graphics)
   - [Saisie de texte](#73-text-input)
   - [Données et réseau](#74-data-and-networking)
   - [Permissions et fonctionnalités de l'appareil](#75-permissions-and-device-features)
   - [Médias et tâches en arrière-plan](#76-media-and-background-work)
   - [Kotlin et coroutines](#77-kotlin-and-coroutines)
8. [Onglets de la barre inférieure](#8-bottom-bar-tabs)
9. [Bibliothèque de composants réutilisables](#9-reusable-component-library)
   - [Composants de base](#91-core-components)
   - [Vues personnalisées](#92-custom-views)
   - [Graphiques](#93-charts)
   - [Modifiers](#94-modifiers)
   - [Utilitaires](#95-utilities)
10. [Notifications et écran de verrouillage](#10-notifications-and-lock-screen)
11. [Localisation et thèmes](#11-localization-and-theming)
12. [Performances : Baseline Profile](#12-performance-baseline-profile)
13. [Structure du projet](#13-project-structure)
14. [Ajouter une nouvelle leçon](#14-adding-a-new-lesson)
15. [Travaux en cours](#15-work-in-progress)
16. [Liste de lecture](#16-reading-list)
17. [Développement assisté par IA](#17-ai-assisted-development)

---

<a id="1-introduction"></a>
## 1. Introduction

Ce dépôt est à la fois un **terrain de jeu Jetpack Compose** et un **squelette d'application réutilisable**.

- **En tant que terrain de jeu**, il rassemble plus de 25 leçons exécutables : animations, top bars rétractables, permissions, Bluetooth, localisation, lecture multimédia, téléchargements, Room, DataStore, Retrofit, Kotlin Flow, et bien plus. Chaque leçon est un écran que vous pouvez ouvrir depuis la grille de Home, lire et copier dans votre propre projet.
- **En tant que squelette**, il fournit les éléments dont la plupart des applications en production ont besoin : splash, sélecteur de langue, onboarding, une barre inférieure à quatre onglets, des réglages (mode sombre, langue, icône de l'application, notation), des notifications quotidiennes, une notification sur l'écran de verrouillage, l'injection de dépendances et un Baseline Profile.

La conception s'inspire des [Android Architecture Samples](https://github.com/android/architecture-samples). Le code est 100 % Kotlin. Chaque écran est une UI Compose hébergée dans un Fragment.

<a id="2-highlights"></a>
## 2. Points forts

| Domaine | Ce que vous obtenez |
|---|---|
| **UI** | Jetpack Compose + Material 3, plus de 40 vues personnalisées, 13 graphiques Canvas, 8 modifiers personnalisés |
| **Architecture** | Activité unique, écrans Fragment + Compose, MVVM, pattern repository, DI avec Hilt |
| **Asynchrone** | Kotlin Coroutines, `Flow` / `StateFlow`, un wrapper `safeApiCall` qui transforme une `Response` Retrofit en `Status` |
| **Données** | Room (citations), DataStore Preferences (réglages, état du tutoriel), Retrofit + OkHttp (AccuWeather, DummyJSON) |
| **Appareil** | Permissions à l'exécution, Fused Location, Bluetooth Classic + BLE, caméra, MediaStore, WebView |
| **Arrière-plan** | Service multimédia au premier plan, WorkManager (`@HiltWorker`), DownloadManager, alarmes exactes |
| **Coquille de l'app** | Splash, intro, sélecteur de langue, barre inférieure, réglages, icône de lanceur dynamique, avis intégré à l'application |
| **Qualité** | Baseline Profile + module de benchmark de démarrage, `@Preview` pour la plupart des composables |
| **Localisation** | Anglais, allemand, français, hindi, japonais, coréen, vietnamien |

<a id="3-getting-started"></a>
## 3. Premiers pas

<a id="31-requirements"></a>
### 3.1 Prérequis

| Outil | Version |
|---|---|
| Android Studio | Une version compatible avec AGP 9.5 |
| JDK | 17 ou plus récent (le projet cible le bytecode Java 17) |
| Gradle | 9.9.2 (téléchargé par le wrapper) |
| Android SDK | compileSdk 36, minSdk 26 |

<a id="32-build-and-run"></a>
### 3.2 Compiler et lancer

```bash
git clone https://github.com/Phong-Kaster/Jetpack-Compose.git
cd Jetpack-Compose

# Installer le build debug sur un appareil connecté ou un émulateur
./gradlew :app:installDebug

# Autres tâches utiles
./gradlew :app:assembleRelease        # APK release (R8 activé)
./gradlew :app:testDebugUnitTest      # tests unitaires
./gradlew :baselineprofile:connectedCheck  # générer le Baseline Profile / lancer les benchmarks
```

Chaque APK est renommé par le build en `Bundeswehr_v<versionName>(<versionCode>)_<timestamp>-<variant>.apk`.

<a id="33-configuration"></a>
### 3.3 Configuration

| Paramètre | Emplacement | Remarques |
|---|---|---|
| Clé API AccuWeather | `configuration/Constant.kt` → `ACCU_WEATHER_KEY` | Remplacez-la par votre propre clé obtenue sur [AccuWeather Developer](https://developer.accuweather.com/). Pour de vrais projets, préférez `local.properties` + `BuildConfig`. |
| URL de base AccuWeather | `configuration/Constant.kt` → `ACCU_WEATHER_URL` | `https://dataservice.accuweather.com` |
| URL de base DummyJSON | `injection/module/NetworkModule.kt` | `https://dummyjson.com/` |
| Versions des bibliothèques | `gradle/libs.versions.toml` | Un seul version catalog pour toutes les dépendances et tous les plugins |

<a id="4-tech-stack"></a>
## 4. Stack technique

| Catégorie | Bibliothèques |
|---|---|
| Langage et build | Kotlin 2.4.21, AGP 9.5.3, Gradle 9.9.2, KSP 2.3.14, version catalog |
| UI | Compose BOM 2026.03.01, Material 3, Material Icons Extended, ConstraintLayout Compose (MotionLayout), Accompanist Permissions |
| Architecture | AndroidX Lifecycle 2.10, Navigation Component 2.9.7 (Fragment + Safe Args), Hilt 2.60.1 |
| Données | Room 2.8.4, DataStore Preferences 1.2.1, Kotlinx Collections Immutable |
| Réseau | Retrofit 2.12, OkHttp logging interceptor 5.3, Gson |
| Images et animation | Coil 2 (+ GIF), Glide 4, Lottie 6 |
| Médias | `MediaPlayer` + notification `MediaStyle`, Media3 ExoPlayer / UI / Session |
| Arrière-plan | WorkManager 2.11 + Hilt Work, `AlarmManager`, `DownloadManager` |
| Google Play | Play Services Location, Play In-App Review |
| Performances | Baseline Profile 1.4, Macrobenchmark, ProfileInstaller |

<a id="5-architecture"></a>
## 5. Architecture

<a id="51-layers"></a>
### 5.1 Couches

```
┌──────────────────────────────────────────────────────────┐
│ ui/        Fragments → Compose layouts → ViewModels      │  présentation
├──────────────────────────────────────────────────────────┤
│ domain/    models, enums, managers, sample catalog       │  types métier
├──────────────────────────────────────────────────────────┤
│ data/      repositories → Room DAO / DataStore           │  données locales
│ network/   Retrofit services, DTOs, interceptors         │  données distantes
├──────────────────────────────────────────────────────────┤
│ injection/ Hilt modules wire everything together         │  DI
└──────────────────────────────────────────────────────────┘
```

- Les **ViewModels** exposent l'état de l'écran sous forme de `StateFlow` et ne touchent jamais aux vues Android.
- Les **repositories** sont la seule porte d'accès à Room, DataStore et Retrofit. Un écran interroge un repository, jamais un DAO ni un service.
- Les **appels réseau** passent par `safeApiCall` (`network/util/SafeApiFlow.kt`), qui enveloppe une `Response` Retrofit dans un `Status` (`Success` / `Failure`) ; les repositories l'exposent aux ViewModels sous forme de `Flow`.
- **Hilt** fournit la base de données, les deux clients Retrofit, les repositories et les dispatchers de coroutines (`@IoDispatcher`, `@DefaultDispatcher`).

<a id="52-screen-anatomy"></a>
### 5.2 Anatomie d'un écran

Chaque écran suit la même structure :

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

| Classe de base | Rôle |
|---|---|
| `core/base/CoreActivity` | Applique la langue enregistrée dans `attachBaseContext`, helpers pour les toasts et la connectivité |
| `core/base/CoreFragment` | Crée la `ComposeView`, applique le thème de l'application et le mode sombre, fournit `LocalNavController` et `LocalLocale` |
| `core/base/CoreLayout` | Un wrapper de `Scaffold` avec top bar, barre inférieure, FAB et insets edge-to-edge |
| `core/base/CoreBehavior` | Contrat partagé : `showToast`, `isInternetConnected`, `trackEvent` |

<a id="53-navigation"></a>
### 5.3 Navigation

- Une seule activité (`MainActivity`) contient un `NavHostFragment` et la barre inférieure.
- Toutes les destinations se trouvent dans `res/navigation/navigation_graph.xml`. La destination de départ est l'écran splash ; les écrans d'intro forment un graphe imbriqué.
- Naviguez avec `safeNavigate(R.id.toXxx)` depuis `util/NavigationUtil.kt` ; il vérifie d'abord la destination courante, si bien qu'un double appui ne provoque jamais de crash.

<a id="54-sample-catalog"></a>
### 5.4 Catalogue des exemples

`domain/catalog/SampleRegistry.kt` est l'index de toutes les leçons. Chaque `HomeShortcut` possède une entrée `SampleMeta` avec :

- le fichier source principal à lire ou à copier,
- des fichiers liés supplémentaires,
- des thèmes (`SampleTopic` : `ANIMATION`, `PERMISSION`, `MEDIA`, …),
- des alias de recherche.

La barre de recherche de Home utilise ce registre : taper `retrofit`, `pinch` ou `qibla` trouve la bonne leçon.

<a id="6-app-flow"></a>
## 6. Parcours de l'application

```
Splash ─(1er lancement)──► Intro language ──► Intro pages ──► Home
   │                                                         ▲
   └─────────────────(utilisateur connu)─────────────────────┘

Home ◄──► Insight ◄──► Article ◄──► Settings      (barre inférieure)
  │
  └──► Écrans de leçon (grille Home, recherche, tri)
```

| Écran | Package | Ce qu'il affiche |
|---|---|---|
| Splash | `secondary/splash` | Choisit entre l'intro et Home à partir de DataStore |
| Intro language | `secondary/intro` | Sélecteur de langue au premier lancement |
| Intro | `secondary/intro` | Pager d'onboarding avec un indicateur de page personnalisé |
| Language | `secondary/language` | Changer la langue de l'application plus tard, depuis les réglages |

<a id="7-lessons"></a>
## 7. Leçons

Chaque leçon ci-dessous s'ouvre depuis la grille de **Home**. Les chemins sont relatifs à `app/src/main/java/com/example/jetpack/ui/fragment/`.

<a id="71-ui-and-layout"></a>
### 7.1 UI et mise en page

| Leçon | Package | Ce que vous apprenez |
|---|---|---|
| Collapsible Top Bar | `secondary/collapsibletopbar/collapsible` | Réduire un en-tête selon la position de défilement avec `derivedStateOf` |
| Collapsible Top Bar 2 | `secondary/collapsibletopbar/collapsible2` | `TopAppBarDefaults.enterAlwaysScrollBehavior` de Material 3 + `nestedScroll` |
| Collapsible Top Bar 3 | `secondary/collapsibletopbar/collapsible3` | Une `NestedScrollConnection` faite à la main avec des en-têtes complet / réduit |
| Collapsible Top Bar 4 | `secondary/collapsibletopbar/collapsible4` | `LargeTopAppBar` qui bascule selon `firstVisibleItemIndex` |
| Collapsible Top Bar 5 | `secondary/collapsibletopbar/collapsible5` | Barres déployée / réduite combinées avec `enterAlwaysScrollBehavior` |
| Instagram Carousel | `secondary/instagramcarousel` | Pager avec un indicateur à points déplaçable par appui long |
| Pinch to Zoom | `secondary/pitchtozoom` | `detectTransformGestures` + `graphicsLayer` pour zoomer, déplacer et faire pivoter |
| Tutorial | `secondary/tutorial` | Un tutoriel pas à pas avec effet projecteur, construit avec `onGloballyPositioned` et `boundsInRoot`, état enregistré dans DataStore |
| Login | `secondary/login` | Formulaire de connexion : `FocusRequester`, `KeyboardOptions`, actions IME, `PasswordVisualTransformation` |
| WebView | `secondary/webview` | `WebView` dans `AndroidView`, callbacks de `WebViewClient`, gestion du retour avec `BackHandler` |

<a id="72-animation-and-graphics"></a>
### 7.2 Animation et graphismes

| Leçon | Package | Ce que vous apprenez |
|---|---|---|
| Animation | `secondary/animation` | `tween`, `spring`, `keyframes`, `infiniteRepeatable`, `drawBehind`, `drawWithContent`, transformations affines |
| Motion Layout | `secondary/motionlayout` | `MotionLayout` de Compose avec des scènes de mouvement JSON5 (`res/raw/*.json5`) et un état de barre d'outils rétractable personnalisé |

<a id="73-text-input"></a>
### 7.3 Saisie de texte

| Leçon | Package | Ce que vous apprenez |
|---|---|---|
| Basic Text Field (state) | `secondary/textfield` | `BasicTextField` avec `TextFieldState` : `InputTransformation`, transformations intégrées, `OutputTransformation`, saisie automatique, taille automatique, un champ de texte flexible |

Les règles de saisie réutilisables se trouvent dans `util/inputtransformation/` (`DigitsOnlyTransformation`, `VerificationCodeTransformation`).

<a id="74-data-and-networking"></a>
### 7.4 Données et réseau

| Leçon | Package | Ce que vous apprenez |
|---|---|---|
| Quote | `secondary/quote` | CRUD avec Room : entité, DAO, type converters (`Date`, `Category`), mapper, `Flow` de lignes |
| AccuWeather | `secondary/accuweather` | Retrofit + intercepteur OkHttp qui ajoute la clé API, recherche de ville avec autocomplétion |
| Home (DummyJSON) | `primary/home` | Un second client Retrofit (`DummyService`) appelé depuis le ViewModel de Home |

<a id="75-permissions-and-device-features"></a>
### 7.5 Permissions et fonctionnalités de l'appareil

| Leçon | Package | Ce que vous apprenez |
|---|---|---|
| Permissions | `secondary/permission` | Demander une ou plusieurs permissions directement dans un Fragment, boîte de dialogue d'explication, ouverture des réglages de l'application |
| Permissions 2 | `secondary/permission` | Les mêmes flux déplacés dans des classes `LifecycleObserver` réutilisables (`lifecycleobserver/`) |
| Last Known Location | `secondary/location` | Fused Location Provider via `MapManager` : dernière position, mises à jour en direct, activation du GPS |
| Bluetooth | `secondary/bluetooth` | Bluetooth Classic (appareils appairés, découverte, connexion) et scan BLE, animation de scan Lottie |
| Photo from Device Storage | `secondary/gallery` | Lire les photos de MediaStore par dossier, prise de photo avec la caméra, bottom sheet de permission |

<a id="76-media-and-background-work"></a>
### 7.6 Médias et tâches en arrière-plan

| Leçon | Package | Ce que vous apprenez |
|---|---|---|
| Music Player | `secondary/mediaplayer` | `MediaPlayer` dans un service au premier plan **et** lié, notification `MediaStyle` personnalisée avec `RemoteViews`, morceaux depuis `res/raw` et MediaStore |
| Music Player 2 | `secondary/mediaplayer` | Une seconde version du même lecteur avec son propre service (`MediaPlayerService2`) et son propre receiver |
| Download with WorkManager | `secondary/downloadwithworker` | `@HiltWorker` qui télécharge un PDF ou une vidéo, affiche la progression dans une notification et renvoie l'`Uri` enregistrée |
| Download Manager | `secondary/downloadmanager` | Le `DownloadManager` du système plus un `BroadcastReceiver` pour la fin du téléchargement |

<a id="77-kotlin-and-coroutines"></a>
### 7.7 Kotlin et coroutines

| Leçon | Package | Ce que vous apprenez |
|---|---|---|
| Kotlin Flow | `secondary/kotlinflow` | Builders de Flow (`flow`, `flowOf`, `asFlow`), opérateurs terminaux (`collect`, `first`, `toList`, `reduce`), contrôle avec `async` et `Job` |

Utilitaires associés : `util/CoroutineUtil.kt` (cinq façons de fusionner des résultats asynchrones), `util/FlowUtil.kt` (combiner plus de cinq flows).

<a id="8-bottom-bar-tabs"></a>
## 8. Onglets de la barre inférieure

| Onglet | Fragment | Contenu |
|---|---|---|
| **Home** | `primary/home/HomeFragment` | Grille de leçons avec recherche, options de tri, chargement shimmer, une bulle flottante déplaçable, une horloge numérique et une carte premium |
| **Insight** | `primary/chart/InsightFragment` | Onglets en chips : galerie de composants, calendrier, graphiques Line / Ring / Bar / Area, horloge analogique, palette de couleurs, test d'ajustement photo (contraste, balance des blancs) |
| **Article** | `primary/article/ArticleFragment` | Vitrine de vues graphiques : Atomic loader, hélice d'ADN, horloge analogique, canvas de lever de soleil, balayage d'image avant / après |
| **Settings** | `primary/setting/SettingFragment` | Mode sombre, langue, icône de l'application, boîte de dialogue de notation + Play In-App Review, avertissement |

Les réglages contiennent aussi `SettingIconFragment`, qui change l'icône du lanceur à l'exécution grâce à des entrées `<activity-alias>` dans le manifest.

<a id="9-reusable-component-library"></a>
## 9. Bibliothèque de composants réutilisables

Tout ce qui figure dans cette section est un composable autonome que vous pouvez copier dans un autre projet. La plupart ont un `@Preview`.

<a id="91-core-components"></a>
### 9.1 Composants de base

`ui/component/` contient les briques de base utilisées dans toute l'application :

| Groupe | Composants |
|---|---|
| Top bars | `CoreTopBar`, `CoreTopBar2`–`4`, `CoreTopBarWithHomeShortcut`, `CoreTopBarWithScrollBehavior`, `CustomizeTopBar` |
| Barre inférieure | `CoreBottomBar` |
| Boîtes de dialogue et sheets | `CoreDialog`, `CoreDialogLayout`, `CoreAlertDialog`, `CoreMaterialDialog`, `CoreTextAnimationDialog`, `CoreBottomSheet` |
| Boutons | `CoreButton`, `CoreFloatingButton`, `CoreExpandableFloatingButton` |
| Cycle de vie | `LifecycleComposable` (observer `ON_START` / `ON_STOP` … depuis Compose) |
| Sélecteurs | `ui/dialog/WheelTimePickerDialog` (sélecteur d'heure à molette dans le style d'ELSA Speak) |

<a id="92-custom-views"></a>
### 9.2 Vues personnalisées

`ui/view/` contient des widgets autonomes :

| Groupe | Composants |
|---|---|
| Boutons et interrupteurs | `GlowingButton`, `ButtonBookmark`, `ButtonFavorite`, `ButtonShare`, `ButtonTextSetting`, `CupertinoSwitch`, `CustomizedCheckbox`, `AnimatedThemeSwitcher` |
| Progression et chargement | `AnimatedProgressBar`, `CustomizedProgressBar`, `GradientProgressIndicator`, `ProgressBar`, `AtomicLoader`, `LoadingDialog`, `AnimationCircularWave`, `DNAHelix` |
| Horloges et calendriers | `AnalogueClock`, `DigitalClock`, `DigitalClock2`, `DigitalClock3`, `CollapsedCalendar`, `ExpandedCalendar` |
| Effets de texte | `ShimmerText`, `DottedTextAnimation`, `FlexibleTextField`, `wordbyword/` (animations caractère par caractère, mot par mot, ligne par ligne, par blocs) |
| Cartes et surfaces | `AnimatedBorderCard`, `RainbowCard`, `OverlappingRoundedBackground`, `HomePremium`, `SwipeToReveal` |
| Images | `ImageBeforeAndAfterSwipeAnimation`, `ImageBeforeAndAfterSwipeSection`, `PhotoSelection` |
| Mise en page | `TableLayout`, `TableLayoutRow`, `HeaderAndLayout`, `ContextualFlowRowSample`, `floatingdraggablebox/` (faire glisser une bulle, la déposer sur une zone de fermeture) |
| Affichage de données | `RateBar`, `ColorSquare`, `IndexColorBar`, `HighlightIndexColor`, `WeatherSunrise` |
| Lignes de réglages | `SettingItemWithDropdown`, `SubsettingElement` |
| Retour utilisateur | `CountdownSnackbar` |

<a id="93-charts"></a>
### 9.3 Graphiques

`ui/fragment/primary/chart/chartcomponent/` — chaque graphique est dessiné avec `Canvas` :

| Graphique | Fichier |
|---|---|
| Graphique linéaire | `LineChart.kt`, `LineChartComponent.kt` |
| Ligne courbe (Bézier cubique) | `CurvyLineChart.kt` |
| Graphique à barres | `BarChart.kt` |
| Tension artérielle (barres d'intervalle) | `BloodPressureChart.kt`, `BloodPressureChart2Element.kt` |
| Camembert / anneau avec espaces | `PieChart.kt`, `DonutChart.kt`, `DonutChart2.kt` |
| Graphique en anneau | `RingChart.kt`, `RingChart2.kt` |
| Graphique météo horaire | `WeatherHourlyChart.kt`, `WeatherHourlyChartElement.kt` |

<a id="94-modifiers"></a>
### 9.4 Modifiers

`ui/modifier/` :

| Modifier | Effet |
|---|---|
| `BorderWithAnimatedGradient` | Bordure à dégradé rotatif |
| `DashedBorder` | Contour en pointillés |
| `ShimmerEffect` | Placeholder shimmer pendant le chargement |
| `PulseEffect` | Échelle / halo pulsant |
| `Shadow` | Ombre colorée personnalisée |
| `PremiumWatermark` | Badge premium en surimpression |
| `DebounceClickable` | Ignore les appuis répétés trop rapides |
| `Conditional` | Applique un modifier uniquement quand une condition est vraie |

Les tooltips se trouvent dans `ui/theme/tooltip/` (`TooltipPopup` avec des providers d'alignement et de décalage).

<a id="95-utilities"></a>
### 9.5 Utilitaires

`util/` :

| Fichier | Rôle |
|---|---|
| `AppUtil` | UUID, ouvrir un site web, ouvrir les réglages de l'application, mise à l'échelle des tailles Figma vers l'écran |
| `CanvasUtil` | Conversion dp ↔ px |
| `CoroutineUtil` | Cinq façons de fusionner des résultats asynchrones |
| `DateUtil`, `LocalDateUtil` | Écarts entre dates, formats, helpers pour l'epoch |
| `EncrytionUtil` | Exemple de chiffrement / déchiffrement AES |
| `FileUtil` | Noms de fichiers, dossier vidéo, partage de vidéo, identifiant MediaStore |
| `FlowUtil` | `combine` pour plus de cinq flows |
| `LanguageUtil` | Appliquer une locale à un `Context` |
| `NavigationUtil` | Garde `canNavigate` et notes sur `popUpTo` |
| `NetworkUtil` | Vérification de la connexion Internet |
| `PermissionUtil` | Vérifier les permissions de notification, caméra, stockage et plusieurs permissions à la fois |
| `SharingUtil` | Rédiger un e-mail, copier dans le presse-papiers, partager l'application |
| `SmartFunction` | Validation et messages d'erreur compréhensibles pour l'utilisateur |
| `SystemBarUtil` | Masquer la barre d'état / la barre de navigation |
| `TuplesUtil` | `Quadruple` et tuples plus grands |

<a id="10-notifications-and-lock-screen"></a>
## 10. Notifications et écran de verrouillage

| Fonctionnalité | Fichiers | Fonctionnement |
|---|---|---|
| Notification quotidienne | `notification/NotificationManager.kt`, `NotificationReceiver.kt` | `AlarmManager.setExact(RTC_WAKEUP)` programme le rappel (repli sur `set` quand les alarmes exactes ne sont pas autorisées) ; le receiver le publie |
| Notification après la première installation | `NotificationAfter5MinuteReceiver.kt` | Une notification peu après la première installation |
| Notification de progression | `NotificationManager.fireProgressNotification` | Notification persistante avec une barre de progression |
| Notification sur l'écran de verrouillage | `LockscreenManager.kt`, `LockscreenReceiver.kt`, `ui/activity/LockscreenActivity.kt` | Un full-screen intent ouvre une activité avec `setShowWhenLocked` ; repli sur une notification normale |
| Permission de notification | `lifecycleobserver/NotificationLifecycleObserver.kt` | Demande `POST_NOTIFICATIONS` sur Android 13+ avec des boîtes de dialogue d'explication et de réglages |

<a id="11-localization-and-theming"></a>
## 11. Localisation et thèmes

- **Langues** : anglais (par défaut), allemand, français, hindi, japonais, coréen et vietnamien (`res/values-*`). La langue de l'appareil est listée en premier. Le choix est enregistré dans DataStore et appliqué dans `CoreActivity.attachBaseContext`.
- **Thème** : `ui/theme/` contient les couleurs, la typographie (`Type.kt`), les spécifications d'animation et `CustomizedTheme`.
- **Mode sombre** : activé dans les réglages et appliqué par `CoreFragment`.

<a id="12-performance-baseline-profile"></a>
## 12. Performances : Baseline Profile

Le module `:baselineprofile` contient :

| Fichier | Rôle |
|---|---|
| `BaselineProfileGenerator.kt` | Parcourt le chemin de démarrage pour générer un Baseline Profile pour `:app` |
| `StartupBenchmarks.kt` | Macrobenchmark qui compare le temps de démarrage avec et sans le profil |

`:app` consomme le profil via `baselineProfile(project(":baselineprofile"))` et le livre avec `profileinstaller`.

<a id="13-project-structure"></a>
## 13. Structure du projet

```
Jetpack-Compose/
├── app/                              # Le module de l'application
├── baselineProfile/                  # Générateur de Baseline Profile + benchmarks de démarrage
├── gradle/libs.versions.toml         # Version catalog : la version de chaque bibliothèque et plugin
├── photo/                            # Images utilisées par ce README
└── .claude/                          # Règles de code et skills pour les assistants IA
```

Racine des sources : `app/src/main/java/com/example/jetpack/`

```
com/example/jetpack/
├── backgroundwork/                   # Services et receivers qui tournent en dehors d'un écran
│   ├── MediaPlayerService(2).kt      #   service musical au premier plan + lié
│   ├── MediaPlayerReceiver(2).kt     #   lecture / pause / suivant depuis la notification
│   └── DownloadManagerReceiver.kt    #   broadcast « téléchargement terminé »
├── configuration/                    # Constantes et listes statiques de l'application
│                                     #   (Constant, Language, Logo, CountryCode, BottomBarDestination)
├── core/base/                        # Classes de base dont hérite chaque écran
│                                     #   (CoreActivity, CoreFragment, CoreLayout, CoreBehavior)
├── data/                             # Données locales
│   ├── database/                     #   Room : QuoteDatabase, dao/, entity/, converter/, mapper/
│   ├── datastore/                    #   DataStore : SettingDatastore, TutorialDatastore
│   └── repository/                   #   repositories Quote, Setting, Tutorial, Weather, Dummy
│       └── impl/                     #   implémentations des repositories
├── domain/                           # Types métier en Kotlin pur
│   ├── catalog/                      #   SampleRegistry : index de toutes les leçons
│   ├── enums/                        #   HomeShortcut, ChartShortcut, SortOption, Category …
│   ├── manager/                      #   MapManager : wrapper de Fused Location
│   └── model/                        #   Quote, ChartElement, Status …
├── injection/                        # Hilt
│   ├── module/                       #   modules Database, Network, Repository, Dispatcher
│   └── qualifier/                    #   @IoDispatcher, @DefaultDispatcher
├── lifecycleobserver/                # Demandes de permission réutilisables (une, plusieurs, localisation, notification)
├── network/                          # Données distantes
│   ├── adapter/                      #   AccuWeatherFactory : déballe et journalise une Response Retrofit
│   ├── dto/                          #   modèles de réponse weather/, dummy/
│   ├── interceptor/                  #   AccuWeatherInterceptor : ajoute la clé API
│   ├── service/                      #   AccuWeatherService, DummyService
│   └── util/                         #   safeApiCall : Response<T> → Status<T>
├── notification/                     # Notifications quotidienne, de première installation, de progression et d'écran de verrouillage
├── ui/                               # Tout ce que l'utilisateur voit
│   ├── activity/                     #   LockscreenActivity
│   ├── component/                    #   composables partagés Core* (top bars, boîtes de dialogue, boutons)
│   ├── dialog/                       #   WheelTimePickerDialog
│   ├── fragment/
│   │   ├── primary/                  #   onglets de la barre inférieure : home, chart (Insight), article, setting
│   │   └── secondary/                #   un package par leçon + splash, intro, language
│   ├── modifier/                     #   extensions Modifier personnalisées
│   ├── theme/                        #   couleurs, typographie, thème, tooltip/
│   └── view/                         #   widgets personnalisés autonomes (horloges, loaders, cartes …)
├── util/                             # Helpers et extensions
│   └── inputtransformation/          #   règles de saisie DigitsOnly, VerificationCode
├── worker/                           # DownloadFileWorker (WorkManager + Hilt)
├── JetpackApplication.kt             # Classe application @HiltAndroidApp
├── JetpackDocument.kt                # Liste de lecture (voir la section 16)
└── MainActivity.kt                   # Activité unique : NavHost + barre inférieure
```

Un package de leçon ressemble à ceci :

```
ui/fragment/secondary/gallery/
├── component/                        # composables utilisés uniquement par cet écran
│   ├── GalleryFolders.kt
│   ├── GalleryGridView.kt
│   ├── GalleryPermissionBottomSheet.kt
│   ├── PhotoTabElement.kt
│   └── RequestGalleryPermission.kt
├── GalleryFragment.kt                # point d'entrée + layout sans état + previews
├── GalleryUiState.kt                 # état d'écran immuable
└── GalleryViewModel.kt               # charge les photos, expose un StateFlow
```

<a id="14-adding-a-new-lesson"></a>
## 14. Ajouter une nouvelle leçon

1. Créez un package sous `ui/fragment/secondary/<lesson>/` avec `XxxFragment`, `XxxViewModel` (si nécessaire) et `component/`.
2. Ajoutez la destination et une action `toXxx` dans `res/navigation/navigation_graph.xml`.
3. Ajoutez une entrée dans `domain/enums/HomeShortcut.kt` (icône + chaîne de titre).
4. Ajoutez le titre à la fin de `res/values/strings.xml`.
5. Gérez le raccourci dans `HomeFragment` avec `safeNavigate(R.id.toXxx)`.
6. Enregistrez un `SampleMeta` dans `domain/catalog/SampleRegistry.kt` pour que la recherche puisse le trouver.
7. Mettez à jour le tableau des [Leçons](#7-lessons) dans ce README.

<a id="15-work-in-progress"></a>
## 15. Travaux en cours

| Élément | Statut |
|---|---|
| Raccourci Tooltip | Listé sur Home mais pas encore branché (affiche un toast). Code : `ui/theme/tooltip/TooltipPopup.kt` |
| Raccourci Shared Element Transition | Listé sur Home mais n'a pas encore d'écran |
| Mecca Compass | La destination existe ; l'écran est un placeholder vide |
| `secondary/basictextfield/` | Ancienne copie de la leçon Basic Text Field, absente du graphe de navigation. `secondary/textfield/` est la version active |

<a id="16-reading-list"></a>
## 16. Liste de lecture

`JetpackDocument.kt` rassemble les articles sur lesquels s'appuient de nombreuses leçons.

<details>
<summary><b>Afficher les 42 articles</b></summary>

| # | Sujet |
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
## 17. Développement assisté par IA

Le dépôt contient des règles de code destinées aux assistants IA comme Claude Code :

| Chemin | Contenu |
|---|---|
| `CLAUDE.md` | Style de sortie et règles Kotlin pour l'assistant |
| `.claude/rules/` | Règles du projet : structure du squelette, UI Compose, couches repository, use case et ViewModel, design system Figma |
| `.claude/skills/` | Skills réutilisables (TDD, diagnostic, rédaction de PRD et d'issues, revue d'architecture …) |

---

<p align="center">
    Fait avec 💘 et Kotlin par <a href="https://github.com/Phong-Kaster">Phong-Kaster</a>
</p>
