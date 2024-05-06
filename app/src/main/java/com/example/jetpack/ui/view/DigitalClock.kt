package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.DateUtil.formatWithPattern
import com.example.jetpack.util.ViewUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.Date

@Composable
fun DigitalClock(modifier: Modifier = Modifier) {
    var time by remember { mutableStateOf(Date()) }
    val clock by remember { derivedStateOf { time.formatWithPattern("HH:mm") } }
    val date by remember { derivedStateOf { time.formatWithPattern("EEE, MMMM dd") } }

    LaunchedEffect(Unit) {
        while (true) {
            if (!isActive) break
            delay(1000)
            time = Date()
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = clock,
            style = customizedTextStyle(fontWeight = 400, fontSize = 72),
            color = Color.White
        )
        Text(
            text = date,
            style = customizedTextStyle(fontWeight = 400, fontSize = 16),
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewDigitalClock() {
    ViewUtil.PreviewContent {
        DigitalClock(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}