package com.example.jetpack.core.base

interface CoreBehavior {

    fun showToast(message: String)

    fun isInternetConnected(): Boolean

    fun trackEvent(name: String)
}