package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.jetpack.core.LocalLocale
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.DateUtil
import com.example.jetpack.util.DateUtil.formatWithPattern
import com.example.jetpack.util.ViewUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.Date

@Composable
fun DigitalClock2(
    modifier: Modifier = Modifier,
    textColor: Color = PrimaryColor
) {
    val locale = LocalLocale.current
    var time by remember { mutableStateOf(Date()) }
    val hour by remember { derivedStateOf { time.formatWithPattern(pattern = DateUtil.PATTERN_hh_mm_ss, locale = locale)}}
    val weekday by remember { derivedStateOf { time.formatWithPattern(pattern = DateUtil.PATTERN_EEEE, locale =locale)}}
    val year by remember { derivedStateOf { time.formatWithPattern(pattern = DateUtil.PATTERN_YYYY, locale = locale)}}
    val day by remember { derivedStateOf { time.formatWithPattern(pattern = DateUtil.PATTERN_dd_MMM, locale = locale)}}

    LaunchedEffect(Unit) {
        while (true) {
            if (!isActive) break
            delay(1000)
            time = Date()
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        Text(text = hour, color = textColor, style = customizedTextStyle(fontSize = 32, fontWeight = 700))
        Text(text = weekday, color = textColor, style = customizedTextStyle(fontSize = 16, fontWeight = 600))
        Spacer(modifier = Modifier
            .width(30.dp)
            .height(3.dp)
            .background(color = textColor)
            .padding(vertical = 10.dp))
        Text(text = "$year | $day", color = textColor, style = customizedTextStyle(fontSize = 14, fontWeight = 400))
    }
}

@Preview
@Composable
fun PreviewDigitalClock2() {
    ViewUtil.PreviewContent {
        DigitalClock2(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}