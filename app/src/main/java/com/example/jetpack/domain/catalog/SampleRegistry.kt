package com.example.jetpack.domain.catalog

import com.example.jetpack.domain.enums.HomeShortcut

/**
 * Single index for “where is the sample?” — grep `SampleRegistry`, `SampleTopic`, or a shortcut name.
 * When adding a new home demo: register here + [HomeShortcut] + navigation + strings.
 */
object SampleRegistry {

    private val byShortcut: Map<HomeShortcut, SampleMeta> = mapOf(
        HomeShortcut.Animation to SampleMeta(
            shortcut = HomeShortcut.Animation,
            topics = setOf(SampleTopic.ANIMATION),
            primarySourcePath = "ui/fragment/secondary/animation/AnimationFragment.kt",
            searchAliases = setOf("animate", "transition", "compose animation"),
        ),
        HomeShortcut.Tutorial to SampleMeta(
            shortcut = HomeShortcut.Tutorial,
            topics = setOf(SampleTopic.TUTORIAL, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/secondary/tutorial/TutorialFragment.kt",
            searchAliases = setOf("onboarding", "guide"),
        ),
        HomeShortcut.Quote to SampleMeta(
            shortcut = HomeShortcut.Quote,
            topics = setOf(SampleTopic.DATASTORE_ROOM, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/secondary/quote/QuoteFragment.kt",
            searchAliases = setOf("room", "database", "quote"),
        ),
        HomeShortcut.AccuWeatherLocation to SampleMeta(
            shortcut = HomeShortcut.AccuWeatherLocation,
            topics = setOf(SampleTopic.LOCATION, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/secondary/accuweather/AccuWeatherLocationFragment.kt",
            searchAliases = setOf("weather", "api", "retrofit"),
        ),
        HomeShortcut.Permissions to SampleMeta(
            shortcut = HomeShortcut.Permissions,
            topics = setOf(SampleTopic.PERMISSION),
            primarySourcePath = "ui/fragment/secondary/permission/PermissionFragment.kt",
            extraHints = listOf("ui/fragment/secondary/permission/component/PermissionDialog.kt"),
            searchAliases = setOf("manifest", "runtime", "requestpermission"),
        ),
        HomeShortcut.Permissions2 to SampleMeta(
            shortcut = HomeShortcut.Permissions2,
            topics = setOf(SampleTopic.PERMISSION),
            primarySourcePath = "ui/fragment/secondary/permission/PermissionFragment2.kt",
            searchAliases = setOf("accompanist", "multiple permissions"),
        ),
        HomeShortcut.MotionLayout to SampleMeta(
            shortcut = HomeShortcut.MotionLayout,
            topics = setOf(SampleTopic.MOTION_LAYOUT, SampleTopic.ANIMATION),
            primarySourcePath = "ui/fragment/secondary/motionlayout/AnimationLayoutFragment.kt",
            searchAliases = setOf("constraintlayout", "motion scene"),
        ),
        HomeShortcut.Login to SampleMeta(
            shortcut = HomeShortcut.Login,
            topics = setOf(SampleTopic.AUTH, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/secondary/login/LoginFragment.kt",
        ),
        HomeShortcut.Bluetooth to SampleMeta(
            shortcut = HomeShortcut.Bluetooth,
            topics = setOf(SampleTopic.BLUETOOTH),
            primarySourcePath = "ui/fragment/secondary/bluetooth/BluetoothFragment.kt",
            searchAliases = setOf("ble", "classic", "pairing"),
        ),
        HomeShortcut.Tooltip to SampleMeta(
            shortcut = HomeShortcut.Tooltip,
            topics = setOf(SampleTopic.TOOLTIP, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/theme/tooltip/TooltipPopup.kt",
            notes = "Shortcut not wired in HomeFragment navigation yet; open file directly.",
            searchAliases = setOf("popup", "hint"),
        ),
        HomeShortcut.Webview to SampleMeta(
            shortcut = HomeShortcut.Webview,
            topics = setOf(SampleTopic.WEB),
            primarySourcePath = "ui/fragment/secondary/webview/WebviewFragment.kt",
            searchAliases = setOf("webview", "javascript", "url"),
        ),
        HomeShortcut.MusicPlayer to SampleMeta(
            shortcut = HomeShortcut.MusicPlayer,
            topics = setOf(SampleTopic.MEDIA),
            primarySourcePath = "ui/fragment/secondary/mediaplayer/MediaPlayerFragment.kt",
            searchAliases = setOf("exo", "media3", "audio"),
        ),
        HomeShortcut.MusicPlayer2 to SampleMeta(
            shortcut = HomeShortcut.MusicPlayer2,
            topics = setOf(SampleTopic.MEDIA),
            primarySourcePath = "ui/fragment/secondary/mediaplayer/MediaPlayerFragment2.kt",
        ),
        HomeShortcut.BasicTextField2 to SampleMeta(
            shortcut = HomeShortcut.BasicTextField2,
            topics = setOf(SampleTopic.TEXT_INPUT),
            primarySourcePath = "ui/fragment/secondary/textfield/BasicTextFieldFragment.kt",
            searchAliases = setOf("textfield", "keyboard", "input", "basic text field"),
        ),
        HomeShortcut.CollapsibleTopbar to SampleMeta(
            shortcut = HomeShortcut.CollapsibleTopbar,
            topics = setOf(SampleTopic.TOP_APP_BAR, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/secondary/collapsibletopbar/collapsible/CollapsibleTopBarFragment.kt",
            searchAliases = setOf("toolbar", "scroll", "app bar"),
        ),
        HomeShortcut.SharedElementTransition to SampleMeta(
            shortcut = HomeShortcut.SharedElementTransition,
            topics = setOf(SampleTopic.ANIMATION),
            primarySourcePath = "ui/fragment/secondary/animation/AnimationFragment.kt",
            notes = "Enum entry present; wire navigation when you add a dedicated shared-element screen.",
            searchAliases = setOf("shared element", "bounds", "transition"),
        ),
        HomeShortcut.CollapsibleTopbar2 to SampleMeta(
            shortcut = HomeShortcut.CollapsibleTopbar2,
            topics = setOf(SampleTopic.TOP_APP_BAR),
            primarySourcePath = "ui/fragment/secondary/collapsibletopbar/collapsible2/CollapsibleTopbar2Fragment.kt",
        ),
        HomeShortcut.CollapsibleTopbar3 to SampleMeta(
            shortcut = HomeShortcut.CollapsibleTopbar3,
            topics = setOf(SampleTopic.TOP_APP_BAR),
            primarySourcePath = "ui/fragment/secondary/collapsibletopbar/collapsible3/CollapsibleTopbar3Fragment.kt",
        ),
        HomeShortcut.CollapsibleTopbar4 to SampleMeta(
            shortcut = HomeShortcut.CollapsibleTopbar4,
            topics = setOf(SampleTopic.TOP_APP_BAR),
            primarySourcePath = "ui/fragment/secondary/collapsibletopbar/collapsible4/CollapsibleTopbar4Fragment.kt",
        ),
        HomeShortcut.CollapsibleTopbar5 to SampleMeta(
            shortcut = HomeShortcut.CollapsibleTopbar5,
            topics = setOf(SampleTopic.TOP_APP_BAR),
            primarySourcePath = "ui/fragment/secondary/collapsibletopbar/collapsible5/CollapsibleTopbar5Fragment.kt",
        ),
        HomeShortcut.LastKnownLocation to SampleMeta(
            shortcut = HomeShortcut.LastKnownLocation,
            topics = setOf(SampleTopic.LOCATION, SampleTopic.MAPS),
            primarySourcePath = "ui/fragment/secondary/location/LastKnownLocationFragment.kt",
            searchAliases = setOf("fused", "playservices", "gps"),
        ),
        HomeShortcut.InstagramCarousel to SampleMeta(
            shortcut = HomeShortcut.InstagramCarousel,
            topics = setOf(SampleTopic.COMPOSE_UI, SampleTopic.GESTURE),
            primarySourcePath = "ui/fragment/secondary/instagramcarousel/InstagramCarouselFragment.kt",
            searchAliases = setOf("pager", "carousel", "horizontal"),
        ),
        HomeShortcut.KotlinFlow to SampleMeta(
            shortcut = HomeShortcut.KotlinFlow,
            topics = setOf(SampleTopic.FLOW),
            primarySourcePath = "ui/fragment/secondary/kotlinflow/KotlinFlowFragment.kt",
            searchAliases = setOf("stateflow", "collect", "coroutine"),
        ),
        HomeShortcut.PitchToZoom to SampleMeta(
            shortcut = HomeShortcut.PitchToZoom,
            topics = setOf(SampleTopic.GESTURE, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/secondary/pitchtozoom/PitchToZoomFragment.kt",
            searchAliases = setOf("pinch", "zoom", "transform"),
        ),
        HomeShortcut.DownloadWithWorkerManager to SampleMeta(
            shortcut = HomeShortcut.DownloadWithWorkerManager,
            topics = setOf(SampleTopic.WORK_MANAGER, SampleTopic.DOWNLOAD),
            primarySourcePath = "ui/fragment/secondary/downloadwithworker/DownloadWithWorkerFragment.kt",
            searchAliases = setOf("workmanager", "hilt worker"),
        ),
        HomeShortcut.DownloadManager to SampleMeta(
            shortcut = HomeShortcut.DownloadManager,
            topics = setOf(SampleTopic.DOWNLOAD),
            primarySourcePath = "ui/fragment/secondary/downloadmanager/DownloadManagerFragment.kt",
            searchAliases = setOf("system downloadmanager"),
        ),
        HomeShortcut.PhotoFromDeviceStorage to SampleMeta(
            shortcut = HomeShortcut.PhotoFromDeviceStorage,
            topics = setOf(SampleTopic.STORAGE, SampleTopic.PERMISSION),
            primarySourcePath = "ui/fragment/secondary/devicephoto/GalleryFragment.kt",
            searchAliases = setOf("gallery", "pick visual media", "photo picker"),
        ),
        HomeShortcut.MeccaCompass to SampleMeta(
            shortcut = HomeShortcut.MeccaCompass,
            topics = setOf(SampleTopic.SENSOR, SampleTopic.MAPS),
            primarySourcePath = "ui/fragment/secondary/meccacompass/MeccaCompassFragment.kt",
            searchAliases = setOf("compass", "qibla", "rotation vector"),
        ),
    )

    /** Bottom navigation and other entry points without a [HomeShortcut]. */
    val standalonePlaygrounds: List<StandalonePlayground> = listOf(
        StandalonePlayground(
            title = "Home (bottom bar)",
            topics = setOf(SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/primary/home/HomeFragment.kt",
            searchAliases = setOf("home", "shortcuts", "main"),
        ),
        StandalonePlayground(
            title = "Article tab",
            topics = setOf(SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/primary/article/ArticleFragment.kt",
            searchAliases = setOf("article", "bottom bar"),
        ),
        StandalonePlayground(
            title = "Settings tab",
            topics = setOf(SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/primary/setting/SettingFragment.kt",
            searchAliases = setOf("setting", "preferences", "bottom bar"),
        ),
        StandalonePlayground(
            title = "Insight chart tab",
            topics = setOf(SampleTopic.CHART, SampleTopic.COMPOSE_UI),
            primarySourcePath = "ui/fragment/primary/chart/ChartFragment.kt",
            searchAliases = setOf("chart", "graph", "insight", "insightfragment"),
        ),
        StandalonePlayground(
            title = "Weather hourly chart composable",
            topics = setOf(SampleTopic.CHART, SampleTopic.CANVAS),
            primarySourcePath = "ui/fragment/primary/chart/chartcomponent/WeatherHourlyChart.kt",
            searchAliases = setOf("line chart", "baseline", "path"),
        ),
        StandalonePlayground(
            title = "Canvas sunrise sample",
            topics = setOf(SampleTopic.CANVAS),
            primarySourcePath = "ui/view/WeatherSunrise.kt",
            searchAliases = setOf("draw", "graphics"),
        ),
        StandalonePlayground(
            title = "Animated theme switcher (Canvas)",
            topics = setOf(SampleTopic.CANVAS, SampleTopic.ANIMATION),
            primarySourcePath = "ui/view/AnimatedThemeSwitcher.kt",
        ),
    )

    fun meta(shortcut: HomeShortcut): SampleMeta = requireNotNull(byShortcut[shortcut]) {
        "Missing SampleRegistry entry for $shortcut — add SampleMeta in SampleRegistry.kt"
    }

    fun allShortcutMetas(): Collection<SampleMeta> = byShortcut.values

    fun shortcutsMatching(normalizedQuery: String): List<HomeShortcut> {
        if (normalizedQuery.isBlank()) return HomeShortcut.entries
        return HomeShortcut.entries.filter { meta(it).matchesQuery(normalizedQuery) }
    }
}
