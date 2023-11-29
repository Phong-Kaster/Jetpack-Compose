package com.example.jetpack.util

import android.util.Log

object AppUtil {
    fun logcat(tag: String = "Jetpack Compose", message: String){
        Log.d(tag, "----------------------------")
        Log.d(tag, "-> message: $message")
    }
}