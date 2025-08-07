package com.example.jetpack.domain.enums

import androidx.annotation.DrawableRes
import com.example.jetpack.R

enum class ApplicationLogo(
    @DrawableRes val photo: Int,
){
    Logo1(photo = R.drawable.ic_nazi_eagle),
    Logo2(photo = R.drawable.ic_iron_cross),
    Logo3(photo = R.drawable.ic_iron_cross_bundeswehr),
    Logo4(photo = R.drawable.ic_nazi_wehtmatch),
    Logo5(photo = R.drawable.ic_heer),
    Logo6(photo = R.drawable.img_swastika_black_circle),
    ;

    companion object {
        fun generateRandomLogo(): ApplicationLogo {
            return entries.toTypedArray().random()
        }
    }
}