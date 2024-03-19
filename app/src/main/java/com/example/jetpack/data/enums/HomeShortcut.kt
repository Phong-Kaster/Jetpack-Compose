package com.example.jetpack.data.enums

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
    Tutorial(drawable = R.drawable.ic_toturial, text = R.string.tutorial),
    /*Language(drawable = R.drawable.ic_language, text = R.string.language),
    Disclaimer(drawable = R.drawable.ic_disclaimer, text = R.string.disclaimer),
    Rate(drawable = R.drawable.ic_star_disable, text = R.string.rate),*/
    Quote(drawable = R.drawable.ic_quote, text = R.string.quote),
    AccuWeatherLocation(drawable = R.drawable.ic_weather, text = R.string.accu_weather),
    Permissions(drawable = R.drawable.ic_permission, text = R.string.permissions),
    Permissions2(drawable = R.drawable.ic_permission, text = R.string.permissions_2),
    MotionLayout(drawable = R.drawable.ic_motion_layout, text = R.string.motion_layout),
    Login(drawable = R.drawable.ic_private_policy, text = R.string.login),
    Bluetooth(drawable = R.drawable.ic_bluetooth, text = R.string.bluetooth),
    Tooltip(drawable = R.drawable.ic_tooltip, text = R.string.tooltip),
    Webview(drawable = R.drawable.ic_webview, text = R.string.webview),
    ForegroundService(drawable = R.drawable.ic_service, text = R.string.foreground_service),
    ;
}