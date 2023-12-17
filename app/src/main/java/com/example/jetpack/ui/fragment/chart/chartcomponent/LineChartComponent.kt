package com.example.jetpack.ui.fragment.chart.chartcomponent

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun Milestone(
    modifier: Modifier,
    text: String = "2077",
    style: TextStyle = customizedTextStyle(fontSize = 12, fontWeight = 400),
    alignment: Alignment = Alignment.Center
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = text,
            style = style,
            modifier = Modifier
                .align(alignment)
        )
    }
}