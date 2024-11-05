package com.example.jetpack.ui.fragment.animation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.customizedTextStyle

/**
 * [Modifier.drawBehind: Drawing behind a composable](https://developer.android.com/develop/ui/compose/graphics/draw/modifiers#drawbehind)
 * Modifier.drawBehind lets you perform DrawScope operations behind the composable content that is drawn on screen
 * */
@Composable
fun DrawBehindExample(){
    Text(
        text = "DrawBehindExample",
        style = customizedTextStyle(
            fontSize = 14,
            fontWeight = 600,
            color = Color.White
        ),
        modifier = Modifier
            .drawBehind {
                drawRoundRect(
                    color = Color(0xFFBBAAEE),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
            .padding(4.dp)
    )
}