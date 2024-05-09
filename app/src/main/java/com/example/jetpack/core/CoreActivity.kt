package com.example.jetpack.core


import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.LanguageUtil
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
    open fun ComposeView() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hideNavigationBar()

        setContent {
            ComposeView()
        }
    }

    override fun hideNavigationBar() {
        AppUtil.hideNavigationBar(window = this.window)
    }

    override fun trackEvent(name: String) {}

    override fun showLoading() {}
    override fun makeStatusBarTransparent() {
        /*with(window) {
            setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }*/
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnected(): Boolean {
        return AppUtil.isInternetConnected(context = this)
    }
}