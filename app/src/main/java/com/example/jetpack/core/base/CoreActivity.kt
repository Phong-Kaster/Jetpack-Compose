package com.example.jetpack.core.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.example.jetpack.core.base.CoreBehavior
import com.example.jetpack.util.LanguageUtil
import com.example.jetpack.util.NetworkUtil
import com.example.jetpack.util.SystemBarUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class CoreActivity
@Inject
constructor() : AppCompatActivity(), CoreBehavior {

    override fun attachBaseContext(newBase: Context?) {
        // Set locale
        var context = newBase
        if (newBase != null) context = LanguageUtil(newBase).setLanguage()
        super.attachBaseContext(context)
    }

    @Composable
    open fun ComposeView() { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppUtil.hideStatusBar(window = this.window)
        SystemBarUtil.hideNavigationBar(window = this.window)
        setContent { ComposeView() }
    }

    override fun trackEvent(name: String) {}

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnected(): Boolean {
        return NetworkUtil.isInternetConnected(context = this)
    }
}