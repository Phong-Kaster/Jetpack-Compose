package com.example.jetpack.ui.fragment.animation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun DrawWithContentExmaple(){
    Text(
        text = "DrawWithContentExmaple",
        style = customizedTextStyle(
            fontSize = 14,
            fontWeight = 600,
            color = Color.White
        ),
        modifier = Modifier
            .drawWithContent {
                drawRoundRect(
                    color = Color.Blue,
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
                drawContent() // is the key of drawing modifier
            }
            .padding(4.dp)
    )
}