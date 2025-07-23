package com.example.jetpack.ui.view

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientProgressIndicator(
    brush: Brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF007AFF),
            Color(0xFF34C759),
            Color(0xFFFF9500),
            Color(0xFFFF2D55)
        )
    ),
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val progressAnimate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,

            ),
        label = "progressAnimate"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            //.clipToBounds() // prevent drawing outside the bounds
    ) {
        drawRoundRect(
            color = Color(0xFFE5E5EA),
            cornerRadius = CornerRadius(10.dp.toPx()),
            topLeft = Offset.Zero,
            size = Size(
                width = size.width,
                height = size.height
            ),
        )

        val progressWidth = size.width * 106f / 294f
        val progressDistance = size.width + size.width * 0.2f

        translate(
            left = progressAnimate * progressDistance,
            top = 0f,
            block = {
                drawRoundRect(
                    brush = brush,
                    cornerRadius = CornerRadius(10.dp.toPx()),
                    topLeft = Offset.Zero,
                    size = Size(
                        width = progressWidth,
                        height = size.height
                    ),
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewGradientProgressIndicator() {
    GradientProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}