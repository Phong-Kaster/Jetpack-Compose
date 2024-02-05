package com.example.jetpack.data.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpack.R

enum class HomeShortcut(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int,
)
{
    Tutorial(drawable = R.drawable.ic_toturial, text = R.string.tutorial),
    /*Language(drawable = R.drawable.ic_language, text = R.string.language),
    Disclaimer(drawable = R.drawable.ic_disclaimer, text = R.string.disclaimer),
    Rate(drawable = R.drawable.ic_star_disable, text = R.string.rate),*/
    Quote(drawable = R.drawable.ic_quote, text = R.string.quote),
    AccuWeatherLocation(drawable = R.drawable.ic_weather, text = R.string.accu_weather),
    Permissions(drawable = R.drawable.ic_permission, text = R.string.permissions),
    MotionLayout(drawable = R.drawable.ic_motion_layout, text = R.string.motion_layout),
    WeatherBackground(drawable = R.drawable.ic_arrow_forward_circle, text = R.string.accu_weather),
    ;
}