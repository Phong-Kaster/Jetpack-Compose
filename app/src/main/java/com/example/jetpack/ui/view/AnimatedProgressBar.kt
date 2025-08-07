package com.example.jetpack.ui.view

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
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
 * @param progressAnimationSpec Animation behavior for the progress line on value changes.
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
    progressAnimationSpec: AnimationSpec<Float> = tween(
        durationMillis = 720,
        easing = LinearOutSlowInEasing
    ),
    modifier: Modifier = Modifier,
) {
    // Create an infinite animation transition
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val initialValue = 0f
    val targetValue = 1f


    // Animates offset value transition from 0 to 1
    val offset by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
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
    val brush: ShaderBrush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val step = targetValue / colors.size // Calculate step size
                val start = step * 0.5f // Start position for each color


                val originalSpots =
                    colors.mapIndexed { index, color -> start + (step * index) } // originalSpot holds colors' start positions
                val transformedSpots = originalSpots.map { spot ->
                    val shiftedSpot = (spot + offset)

                    // keep shifted spots always less than 1f
                    if (shiftedSpot > 1f) shiftedSpot - 1f else shiftedSpot
                }


                // Combine colors with their transformed positions
                val pairs = colors. zip(transformedSpots)
                    .sortedBy { it.second } // For instance, result: [("Red", 0.1f), ("Green", 0.5f), ("Blue", 0.9f)]


                // To create a smooth, uninterrupted animation of the gradient, we
                // draw it wider than the actual progress bar.
                // This is achieved by adding a margin to both ends of the gradient.
                val margin = size.width * 0.5f

                return LinearGradientShader(
                    colors = pairs.map { it.first },
                    colorStops = pairs.map { it.second },
                    from = Offset(-margin, 0f),
                    to = Offset(size.width + margin, 0f),
                )
            }
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(initialValue, targetValue),
        animationSpec = progressAnimationSpec,
        label = "animatedProgress"
    )


    Canvas(
        modifier = modifier,
        onDraw = {
            val width = this.size.width
            val height = this.size.height

            // Create a Paint object
            val paint = Paint().apply {
                // Enable anti-aliasing for smoother lines
                isAntiAlias = true
                style = PaintingStyle.Stroke
                // Apply the animated gradient shader
                shader = brush.createShader(size)
            }
            paint.strokeCap = strokeCap
            paint.strokeWidth = strokeWidth.toPx()


            // Handle optional glow effect
            glowRadius?.let { radius ->
                paint.asFrameworkPaint().apply {
                    setShadowLayer(radius.toPx(), 0f, 0f, android.graphics.Color.WHITE)
                }
            }

            // draw track if it is specified
            trackBrush?.let { brush ->
                drawLine(
                    brush = brush,
                    start = Offset(x = 0f, y = height * 0.5f),
                    end = Offset(x = width, y = height * 0.5f),
                    cap = strokeCap,
                    strokeWidth = strokeWidth.toPx(),
                )
            }

            // Draw the progress line if progress is greater than 0
            if (animatedProgress > 0f) {
                drawIntoCanvas { canvas ->
                    canvas.drawLine(
                        p1 = Offset(0f, height / 2f),
                        p2 = Offset(width * animatedProgress, height / 2f),
                        paint = paint
                    )
                }
            }
        })
}

@Preview
@Composable
private fun PreviewAnimatedProgressBar() {
    ViewUtil.PreviewContent {
        AnimatedProgressBar(
            progress = 0f,
            modifier = Modifier.fillMaxWidth()
        )
    }

}