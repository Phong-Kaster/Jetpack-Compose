package com.example.jetpack.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity


object AppUtil {
    fun logcat(tag: String = "Jetpack Compose", message: String){
        Log.d(tag, "----------------------------")
        Log.d(tag, "-> message: $message")
    }

    fun openWebsite(context: Context, url: String){
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(uri)
        context.startActivity(intent)
    }
}