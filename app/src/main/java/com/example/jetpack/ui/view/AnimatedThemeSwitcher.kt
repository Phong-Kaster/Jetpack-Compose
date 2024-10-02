package com.example.jetpack.ui.view


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.animationInfiniteFloat5
import com.example.jetpack.ui.theme.animationSpecFloat
import kotlin.math.cos
import kotlin.math.sin

/**
 * # [How to Create an Animated Theme Switcher in Jetpack Compose](https://medium.com/@kappdev/how-to-create-an-animated-theme-switcher-in-jetpack-compose-32c9457ee6c3)
 */
@Composable
fun AnimatedThemeSwitcher(
    enableDarkMode: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
) {

    val progress by animateFloatAsState(
        targetValue =  if(enableDarkMode) 1f else 0f,
        animationSpec = animationSpecFloat,
        label = "Theme switcher progress"
    )

    Canvas(
        modifier = modifier
            .size(48.dp)
            .aspectRatio(1f)
    ) {
        val width = size.width
        val height = size.height
        val baseRadius = width * 0.25f
        val extraRadius = width * 0.2f * progress
        val radius = baseRadius + extraRadius

        // Rotate canvas based on the progress
        rotate(
            degrees = 180f * (1-progress),
            block = {
                // Calculate progress for drawing rays
                val raysProgress = if (progress < 0.5f) (progress / 0.85f) else 0f

                // Draw rays for the sun shape
                drawRays(
                    color = color,
                    alpha = if (progress < 0.5f) 1f else 0f,
                    radius = (radius * 1.5f) * (1f - raysProgress),
                    rayWidth = radius * 0.3f,
                    rayLength = radius * 0.2f
                )

                drawMoonToSun(
                    radius = radius,
                    progress = progress,
                    color = color
                )
            }
        )

        // Calculate progress for drawing stars
        val starProgress = if (progress > 0.8f) ((progress - 0.8f) / 0.2f) else 0f

        // Draw stars for the moon
        drawStar(
            color = color,
            centerOffset = Offset(width * 0.4f, height * 0.4f),
            radius = (height * 0.05f) * starProgress,
            alpha = starProgress
        )
        drawStar(
            color = color,
            centerOffset = Offset(width * 0.2f, height * 0.2f),
            radius = (height * 0.1f) * starProgress,
            alpha = starProgress
        )
    }
}

/**
 * Moon-to-Sun
 *
 * This defines a function named drawMoonToSun as an extension function on the DrawScope object. This means you can call this function directly within a Canvas in Compose
 *
 * @param radius The radius of the circles used in the drawing
 * @param progress A value between 0 and 1 that represents the animation progress (0 = moon, 1 = sun)
 * @param color The color to use for drawing the shapes
 */
fun DrawScope.drawMoonToSun(radius: Float, progress: Float, color: Color) {
    val mainRect = Rect(center, radius)
    // Create the main circle
    val mainCircle = Path().apply { addOval(mainRect) }

    // Calculate the initial position of the subtracting circle
    val initialOffset: Offset = center - Offset(radius * 2.3f, radius * 2.3f)

    // Calculate the offset for the subtracting circle based on the progress
    val offset = (radius * 1.8f) * progress

    // Create the subtracting circle
    val subtractRect = Rect(initialOffset + Offset(offset, offset), radius)
    val subtractCircle = Path().apply { addOval(subtractRect) }

    // Create the final path by subtracting the subtracting circle from the main circle
    val moonToSunPath = Path().apply {
        op(mainCircle, subtractCircle, PathOperation.Difference)
    }

    // Draw the resulting path with the specified color
    drawPath(moonToSunPath, color)

    //drawPath(mainCircle, color)
}

/**
 * Rays
 *
 * This Kotlin code snippet defines a function drawRays that draws rays emanating from a central point within a DrawScope in Jetpack Compose. This is commonly used to create sunburst or starburst effects
 * @param color The color of the rays.
 * @param radius The distance from the center to the start of the rays.
 * @param rayWidth The width or thickness of each ray.
 * @param rayLength The length of each ray.
 * @param alpha The transparency of the rays (0 = fully transparent, 1 = fully opaque).
 * @param rayCount The number of rays to draw.
 */
private fun DrawScope.drawRays(
    color: Color,
    radius: Float,
    rayWidth: Float,
    rayLength: Float,
    alpha: Float = 1f,
    rayCount: Int = 8
) {
    // Loop to draw each ray
    for (i in 0 until rayCount) {
        // Calculate the angle for the current ray
        val angle = (2 * Math.PI * i / rayCount).toFloat()

        // Calculate the starting position of the ray
        val startX = center.x + radius * cos(angle)
        val startY = center.y + radius * sin(angle)

        // Calculate the ending position of the ray
        val endX = center.x + (radius + rayLength) * cos(angle)
        val endY = center.y + (radius + rayLength) * sin(angle)

        // Draw the ray from the starting to the ending position
        drawLine(
            color = color,
            alpha = alpha,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            cap = StrokeCap.Round,
            strokeWidth = rayWidth
        )
    }
}

private fun DrawScope.drawStar(
    color: Color,
    centerOffset: Offset,
    radius: Float,
    alpha: Float = 1f,
) {
    val leverage = radius * 0.1f

    val starPath = Path().apply {
        // Move to the leftmost point of the star
        moveTo(centerOffset.x - radius, centerOffset.y)

        // Draw the upper left curve of the star
        quadraticTo(
            x1 = centerOffset.x - leverage, y1 = centerOffset.y - leverage,
            x2 = centerOffset.x, y2 = centerOffset.y - radius
        )

        // Draw the upper right curve of the star
        quadraticTo(
            x1 = centerOffset.x + leverage, y1 = centerOffset.y - leverage,
            x2 = centerOffset.x + radius, y2 = centerOffset.y
        )

        // Draw the lower right curve of the star
        quadraticTo(
            x1 = centerOffset.x + leverage, y1 = centerOffset.y + leverage,
            x2 = centerOffset.x, y2 = centerOffset.y + radius
        )

        // Draw the lower left curve of the star
        quadraticTo(
            x1 = centerOffset.x - leverage, y1 = centerOffset.y + leverage,
            x2 = centerOffset.x - radius, y2 = centerOffset.y
        )
    }

    // Draw the star path
    drawPath(starPath, color, alpha)
}

@Preview
@Composable
private fun PreviewAnimatedThemeSwitcherTrue() {
    AnimatedThemeSwitcher(
        enableDarkMode = true,
        modifier = Modifier
            .background(color = Color.White)
            .padding(16.dp)
    )
}

@Preview
@Composable
private fun PreviewAnimatedThemeSwitcherFalse() {
    AnimatedThemeSwitcher(
        enableDarkMode = false,
        modifier = Modifier
            .background(color = Color.White)
            .padding(16.dp)
    )
}