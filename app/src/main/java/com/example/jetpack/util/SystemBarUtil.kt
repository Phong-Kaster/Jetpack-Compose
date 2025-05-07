package com.example.jetpack.util

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object SystemBarUtil {
    /**
     * - hide status bar of device
     * @param window the window where the status bar should be hidden
     * @author Phong-Kaster
     */
    fun hideStatusBar(window: Window){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
        }
    }

    /**
     * - hide both navigation bar & status bar
     * @param window the window where the status bar should be hidden
     * @author Phong-Kaster
     */
    fun hideSystemBars(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
        }
    }

    /**
     * - hide navigation bar
     * @param window the window where the status bar should be hidden
     * @author Phong-Kaster
     */
    fun hideNavigationBar(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            isAppearanceLightNavigationBars = false
            hide(WindowInsetsCompat.Type.navigationBars())
        }
    }
}