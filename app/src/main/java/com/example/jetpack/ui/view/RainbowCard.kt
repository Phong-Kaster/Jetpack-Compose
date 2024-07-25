package com.example.jetpack.ui.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.util.ViewUtil

@Composable
fun RainbowCard() {
    // Rainbow colors
    val colors = listOf(
        Color(0xFFE40303),
        Color(0xFFFF8C00),
        Color(0xFFFFED00),
        Color(0xFF008026),
        Color(0xFF24408E),
        Color(0xFF732982),
    )

    // Configure brush
    val brushSweep = Brush.sweepGradient(colors)


    // infinity rotation
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )


    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .aspectRatio(2F)
            .background(color = Color(0xFF2C3141), shape = RoundedCornerShape(20.dp))
    ) {

        rotate(degrees = angle) {
            drawCircle(
                brush = brushSweep,
                radius = size.width,
                blendMode = BlendMode.SrcIn,
            )
        }

        // draw solid color
        drawRoundRect(
            color = Color(0xFF2C3141),
            topLeft = Offset(x = 1.dp.toPx(), y = 1.dp.toPx()),
            size = Size(
                width = size.width - 2.dp.toPx(),
                height = size.height - 2.dp.toPx()
            ),
            cornerRadius = CornerRadius(
                x = 20.dp.toPx(),
                y = 20.dp.toPx()
            )
        )
    }
}

@Preview
@Composable
private fun PreviewRainbowCard() {
    ViewUtil.PreviewContent(
        modifier = Modifier.background(color = Color.White),
        content = {
            RainbowCard()
        }
    )
}