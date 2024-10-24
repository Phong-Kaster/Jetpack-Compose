package com.example.jetpack.ui.view

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.util.ViewUtil

/**
 * [Creating a Smooth Animated Progress Bar](https://medium.com/@kappdev/creating-a-smooth-animated-progress-bar-in-jetpack-compose-canvas-drawing-and-gradient-animation-ddf07f77bb56)
 *
 * Animated Progress Bar
 *
 * @author Phong-Kaster
 * @param progress The current progress value (0f to 1f).
 * @param colors List of colors defining the gradient animation
 * @param trackBrush Brush for the static track behind the progress line. If null, no track will be drawn
 * @param strokeWidth Controls the thickness of the progress line
 * @param strokeCap Defines the style of the line ends
 * @param glowRadius Adds a glow effect to the progress line. If null, no effect will be applied
 * @param gradientAnimationSpeed Duration (in milliseconds) of one loop of the gradient animation
 * @param progressAnimSpec Animation behavior for the progress line on value changes.
 * @param modifier The modifier to be applied to the progress bar
 *
 */
@Composable
fun AnimatedProgressBar(
    progress: Float = 0f,
    colors: List<Color> = listOf(Color.Red, Color.Yellow, Color.Blue),
    trackBrush: Brush? = SolidColor(Color.Black.copy(alpha = 0.5f)),
    strokeWidth: Dp = 5.dp,
    strokeCap: StrokeCap = StrokeCap.Round,
    glowRadius: Dp? = 5.dp,
    gradientAnimationSpeed: Int = 2500,
    progressAnimationSpec: AnimationSpec<Float> = tween(durationMillis = 720, easing = LinearOutSlowInEasing),
    modifier: Modifier = Modifier,
) {
    // Creat an infinite animation transition
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

    // Animates offset value transition from 0 to 1
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = gradientAnimationSpeed,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "offset"
    )

    // Creates a brush that updates based on the animated offset

}

@Preview
@Composable
private fun PreviewAnimatedProgressBar() {
    ViewUtil.PreviewContent {
        AnimatedProgressBar(progress = 0f)
    }

}