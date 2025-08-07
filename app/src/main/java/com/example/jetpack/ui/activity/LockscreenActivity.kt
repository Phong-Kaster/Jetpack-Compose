package com.example.jetpack.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.base.CoreActivity
import com.example.jetpack.ui.view.DigitalClock
import com.example.jetpack.ui.view.TableLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockscreenActivity : CoreActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableLockscreen()
    }


    private fun enableLockscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
        } else {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
    ){
        DigitalClock(modifier = Modifier.padding(top = 40.dp))
        Spacer(modifier = Modifier.weight(1F))
        TableLayout(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
    }
}

@Preview
@Composable
fun PreviewLockscreenLayout() {
    LockscreenLayout()
}