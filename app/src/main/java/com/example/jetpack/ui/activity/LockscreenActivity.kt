package com.example.jetpack.ui.activity

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.CoreActivity
import com.example.jetpack.ui.view.DigitalClock
import com.example.jetpack.ui.view.TableLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockscreenActivity : CoreActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        showOnLockscreen()
    }

    private fun showOnLockscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
        } else {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
        }
    }

    private fun dismissKeyguard() {
        with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
            requestDismissKeyguard(this@LockscreenActivity, null)
        }
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        LockscreenLayout()
    }
}

@Composable
fun LockscreenLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray)
        ) {
            DigitalClock(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1F))
            TableLayout(modifier = Modifier.padding(16.dp))
        }

    }
}

@Preview
@Composable
fun PreviewLockscreenLayout() {
    LockscreenLayout()
}