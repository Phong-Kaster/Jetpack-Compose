package com.example.jetpack.database.enums

import androidx.annotation.DrawableRes
import com.example.jetpack.R

enum class Star(
    val value: Int,
    @DrawableRes val enableDrawable: Int,
    @DrawableRes val disableDrawable: Int) {
    ONE(value = 1, enableDrawable = R.drawable.ic_star_enable, disableDrawable = R.drawable.ic_star_disable),
    TWO(value = 2, enableDrawable = R.drawable.ic_star_enable, disableDrawable = R.drawable.ic_star_disable),
    THREE(value = 3, enableDrawable = R.drawable.ic_star_enable, disableDrawable = R.drawable.ic_star_disable),
    FOUR(value = 4, enableDrawable = R.drawable.ic_star_enable, disableDrawable = R.drawable.ic_star_disable),
    FIVE(value = 5, enableDrawable = R.drawable.ic_star_special_enable, disableDrawable = R.drawable.ic_star_special_disable)
}