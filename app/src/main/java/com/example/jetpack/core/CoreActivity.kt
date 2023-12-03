package com.example.jetpack.core


import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.LanguageUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
abstract class CoreActivity :
    AppCompatActivity(), CoreBehavior {

    override fun attachBaseContext(newBase: Context?) {
        // Set locale
        var context = newBase
        if (newBase != null) context = LanguageUtil(newBase).setLanguage()
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        hideNavigationBar()
    }

    override fun hideNavigationBar() {
        AppUtil.hideNavigationBar(window = this.window)
    }

    override fun trackEvent(name: String) {}

    override fun showLoading() {}

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnected(): Boolean {
        return AppUtil.isInternetConnected(context = this)
    }
}