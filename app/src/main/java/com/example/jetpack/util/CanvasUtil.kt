package com.example.jetpack.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

object CanvasUtil {

    /**
     * How to convert from dp to px ?
     * */
    @Composable
    fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


    /**
     * How to convert from px to dp?
     */
    @Composable
    fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
}