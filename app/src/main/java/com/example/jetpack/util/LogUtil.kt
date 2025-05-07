package com.example.jetpack.util

import android.util.Log

object LogUtil {
    fun logcat(message: String, tag: String = "Jetpack Compose", enableDivider: Boolean = false) {
        if(enableDivider){
            Log.d(tag, "----------------------------")
        }
        Log.d(tag, "-> message = $message")

    }
}