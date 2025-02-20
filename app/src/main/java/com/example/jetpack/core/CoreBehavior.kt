package com.example.jetpack.core

interface CoreBehavior {

    fun showToast(message: String)

    fun isInternetConnected(): Boolean

    fun trackEvent(name: String)
}