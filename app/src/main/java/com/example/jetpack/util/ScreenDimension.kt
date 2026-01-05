package com.example.jetpack.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

object ScreenDimension {

    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    fun figmaWidthScale(): Float = LocalConfiguration.current.screenWidthDp / 390f

    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    fun figmaHeightScale(): Float = LocalConfiguration.current.screenHeightDp / 844f
}
