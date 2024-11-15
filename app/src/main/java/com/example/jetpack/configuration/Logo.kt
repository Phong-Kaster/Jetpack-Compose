package com.example.jetpack.configuration

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpack.R

enum class Logo
constructor(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int,
    val alias: String,
) {
    Origin(drawable = R.mipmap.ic_launcher_foreground, text = R.string.origin, alias = "com.example.jetpack.MainActivity"),
    Bundeswehr(drawable = R.drawable.ic_bundeswehr, text = R.string.app_name, alias ="com.example.jetpack.LogoBundeswehr"),
    NaziEagle(drawable = R.drawable.ic_nazi_eagle, text = R.string.nazi_eagle, alias = "com.example.jetpack.LogoNaziEagle"),
    NaziSymbol(drawable = R.drawable.ic_nazi_swastika, text = R.string.nazi_symbol, alias = "com.example.jetpack.LogoNaziSymbol"),
    Heer(drawable = R.drawable.ic_heer, text = R.string.heer, alias = "com.example.jetpack.LogoHeer"),

    ;

    companion object {
        fun findByName(name: String): Logo {
            return try {
                valueOf(name)
            } catch (ex: Exception) {
                Origin
            }
        }
    }
}