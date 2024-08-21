package com.example.jetpack.ui.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.Background2
import kotlin.math.sin

/**
 * # [How to Create a DNA Helix Animation in Jetpack Compose](https://medium.com/@kappdev/how-to-create-a-dna-helix-animation-in-jetpack-compose-b43244cf734f)
 */

/**
 * Parameters:
 * 🧬 modifier 👉 Modifier to be applied to the Canvas.
 *
 * 🧬 firstColor 👉 Color of the first set of points.
 *
 * 🧬 secondColor 👉 Color of the second set of points.
 *
 * 🧬 pointSize 👉 Diameter of the points.
 *
 * 🧬 lineWidth 👉 Width of the lines connecting the points.
 *
 * 🧬 spacing 👉 Distance between consecutive points.
 *
 * 🧬 shifting 👉 Horizontal shift for the points. Allows distortion of the helix.
 *
 * 🧬 curvature 👉 Curvature of the helix. Affects how often curls repeat.
 *
 * 🧬 cycleDuration 👉 Duration of one animation cycle in milliseconds.
 *
 * 🧬 lineBrush 👉 Function to define the brush for the lines. Default is a linear gradient from firstColor to secondColor
 */
@Composable
fun DNAHelix(
    modifier: Modifier = Modifier,
    firstColor: Color,
    secondColor: Color,
    pointSize: Dp = 5.dp,
    lineWidth: Dp = 1.5.dp,
    spacing: Dp = 10.dp,
    shifting: Dp = 0.dp,
    curvature: Float = 16f,
    cycleDuration: Int = 3000,
    lineBrush: (firstPoint: Offset, secondPoint: Offset) -> Brush = { fp, sp ->
        Brush.linearGradient(
            colors = listOf(firstColor, secondColor),
            start = fp,
            end = sp
        )
    }
){
    val helixTransition = rememberInfiniteTransition(label = "helixTransition")
    val animatedAngle by helixTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = cycleDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "animatedAngle"
    )

    Canvas(
        modifier = modifier
        .background(color = Background2)
    ) {
        val spacingPx = spacing.toPx()
        val pointsCount = (size.width / spacingPx).toInt()
        val helixRadius = size.height / 2

        val pointRadiusPx = pointSize.toPx() / 2
        val lineWidthPx = lineWidth.toPx()
        val shiftingPx = shifting.toPx()

        // Loop through each point to draw the helix
        for (i in 1 until pointsCount) {
            // Calculate the current angle for the helix animation
            val currentAngle = (animatedAngle + i * curvature) % 360
            // Calculate the x offset for the current point
            val xOffset = i * spacingPx

            // Calculate the coordinates of the first point
            val firstPoint = calculateCoordinates(currentAngle, helixRadius, xOffset - shiftingPx, helixRadius)
            // Calculate the coordinates of the second point (180 degrees apart)
            val secondPoint = calculateCoordinates(currentAngle + 180, helixRadius, xOffset + shiftingPx, helixRadius)

            // Draw the line connecting the two points
            drawLine(
                brush = lineBrush(firstPoint, secondPoint),
                strokeWidth = lineWidthPx,
                start = firstPoint,
                end = secondPoint
            )

            // Draw the first point
            drawCircle(
                color = firstColor,
                radius = pointRadiusPx,
                center = firstPoint
            )

            // Draw the second point
            drawCircle(
                color = secondColor,
                radius = pointRadiusPx,
                center = secondPoint
            )
        }
    }
}

private fun calculateCoordinates(angle: Float, radius: Float, centerX: Float, centerY: Float): Offset {
    val y = centerY + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
    return Offset(centerX, y)
}


@Preview
@Composable
private fun PreviewDNAHelix() {
    DNAHelix(
        firstColor = Color.Blue,
        secondColor = Color.Yellow,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    )
}