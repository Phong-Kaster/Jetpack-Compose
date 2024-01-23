package com.example.jetpack.core

interface CoreBehavior {

    fun showToast(message: String)

    fun isInternetConnected(): Boolean

    fun hideNavigationBar()

    fun trackEvent(name: String)

    fun showLoading()

    fun makeStatusBarTransparent()
}