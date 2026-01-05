package com.example.jetpack.domain.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpack.R
import javax.annotation.concurrent.Immutable

@Immutable
enum class HomeShortcut(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int,
    @StringRes val content: Int = 0,
)
{
    Animation(drawable = R.drawable.ic_nazi_wehtmatch, text = R.string.animation),
    Tutorial(drawable = R.drawable.ic_toturial, text = R.string.tutorial),
    Quote(drawable = R.drawable.ic_quote, text = R.string.quote),
    AccuWeatherLocation(drawable = R.drawable.ic_weather, text = R.string.accu_weather),
    Permissions(drawable = R.drawable.ic_permission, text = R.string.permissions),
    Permissions2(drawable = R.drawable.ic_permission, text = R.string.permissions_2),
    MotionLayout(drawable = R.drawable.ic_motion_layout, text = R.string.motion_layout),
    Login(drawable = R.drawable.ic_private_policy, text = R.string.login),
    Bluetooth(drawable = R.drawable.ic_bluetooth, text = R.string.bluetooth),
    Tooltip(drawable = R.drawable.ic_tooltip, text = R.string.tooltip),
    Webview(drawable = R.drawable.ic_webview, text = R.string.webview),
    MusicPlayer(drawable = R.drawable.ic_music_note, text = R.string.music_player),
    MusicPlayer2(drawable = R.drawable.ic_music_note, text = R.string.music_player_2),
    BasicTextField2(drawable = R.drawable.ic_basic_text_field_2, text = R.string.basic_text_field_with_state),

    CollapsibleTopbar(drawable = R.drawable.ic_collapsible_top_bar, text = R.string.collapsible_topbar),
    SharedElementTransition(drawable = R.drawable.ic_shared_element_transition, text = R.string.shared_element_transition),
    CollapsibleTopbar2(drawable = R.drawable.ic_bottom_article, text = R.string.collapsible_topbar_2),
    CollapsibleTopbar3(drawable = R.drawable.ic_bottom_article, text = R.string.collapsible_topbar_3),
    CollapsibleTopbar4(drawable = R.drawable.ic_bottom_article, text = R.string.collapsible_topbar_4),
    CollapsibleTopbar5(drawable = R.drawable.ic_bottom_article, text = R.string.collapsible_topbar_5),

    LastKnownLocation(drawable = R.drawable.ic_last_know_location, text = R.string.last_know_location),
    InstagramCarousel(drawable = R.drawable.ic_instagram, text = R.string.instagram_carousel),
    KotlinFlow(drawable = R.drawable.ic_kotlin, text = R.string.kotlin_flow),
    PitchToZoom(drawable = R.drawable.ic_pitch_to_zoom, text = R.string.pitch_to_zoom),
    DownloadWithWorkerManager(drawable = R.drawable.ic_download_with_worker, text = R.string.download_with_worker_manager),
    DownloadManager(drawable = R.drawable.ic_download_manager, text = R.string.download_manager),
    PhotoFromDeviceStorage(drawable = R.drawable.ic_camera, text = R.string.photo_from_device_storage),
    ;
}