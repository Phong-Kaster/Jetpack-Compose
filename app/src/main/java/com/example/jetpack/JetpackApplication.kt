package com.example.jetpack

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetpackApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("TAG", "onCreate - the A commit")
    }
}