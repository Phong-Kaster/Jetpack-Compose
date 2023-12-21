package com.example.jetpack.configuration

import androidx.annotation.DrawableRes
import com.example.jetpack.R

enum class Icon
constructor(
    @DrawableRes val drawable: Int
) {
    LauncherForeground(drawable = R.mipmap.ic_launcher_foreground),
    Bundeswehr(drawable = R.drawable.ic_bundeswehr),
    NaziEagle(drawable = R.drawable.ic_nazi_eagle),
    NaziSymbol(drawable = R.drawable.ic_nazi_symbol),



    ;
}