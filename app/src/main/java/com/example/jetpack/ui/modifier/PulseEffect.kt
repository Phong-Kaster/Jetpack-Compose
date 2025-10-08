package com.example.jetpack.ui.modifier

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.scale

/**
 * # [How to Create a Pulse Effect in Jetpack Compose](https://medium.com/@kappdev/how-to-create-a-pulse-effect-in-jetpack-compose-265d49aad044)
 * # [Video](https://www.youtube.com/watch?v=D04A7pDr5IQ)
 */
@Composable
fun Modifier.doublePulseEffect(
    targetScale: Float = 1.5f,
    initialScale: Float = 1f,
    brush: Brush = SolidColor(Color.Black.copy(alpha = 0.2f)),
    shape: RoundedCornerShape = CircleShape,
    duration: Int = 1200,
): Modifier {
    return this
        .pulseEffect(
            initialScale = initialScale,
            targetScale = targetScale,
            brush = brush,
            shape = shape,
            animationSpec = tween(
                durationMillis = duration,
                delayMillis = 0,
                easing = FastOutSlowInEasing
            )
        )
        .pulseEffect(
            initialScale = initialScale,
            targetScale = targetScale,
            brush = brush,
            shape = shape,
            animationSpec = tween(
                durationMillis = (duration * 0.7f).toInt(),
                delayMillis = (duration * 0.3f).toInt(),
                easing = FastOutSlowInEasing
            )
        )
}

@Composable
fun Modifier.pulseEffect(
    targetScale: Float = 1.5f,
    initialScale: Float = 1f,
    brush: Brush = SolidColor(Color.Black.copy(alpha = 0.2f)),
    shape: RoundedCornerShape = CircleShape,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(1200)
): Modifier {
    val pulseTransition = rememberInfiniteTransition("pulseTransition")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = initialScale,
        targetValue = targetScale,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "PulseScale"
    )

    val pulseAlpha by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "pulseAlpha"
    )


    return this.drawBehind {
        val outline = shape.createOutline(size, layoutDirection, this)
        scale(pulseScale) {
            drawOutline(outline = outline, brush = brush, pulseAlpha)
        }
    }
}

/**
 * Usage:
 *
 *
 *         Spacer(
 *             modifier = Modifier
 *                 .fillMaxSize()
 *                 .background(Color.Transparent)
 *                 .pulseEffect(
 *                     enabled = isFullyVisible,
 *                     color = Color.White,
 *                     animDuration = 400,
 *                     intervalMillis = (interval - 400).toInt(),
 *                     centerFraction = Offset(0.5f, 0.3f) // 50% from left, 25% from top
 *                 )
 *                 .clipToBounds()
 *         )
 */
@Composable
fun Modifier.pulseEffect(
    enabled: Boolean = true,
    color: Color = Color.White.copy(alpha = 0.3f),
    initialRadiusFraction: Float = 0f,
    targetRadiusFraction: Float = 2f,
    centerFraction: Offset = Offset(0.5f, 0.5f),
    animDuration: Int = 2000, // renamed to avoid conflict
    intervalMillis: Int = 500 // ⏱ delay between pulses
): Modifier {
    if (!enabled) return this // 🚫 don't draw or animate when disabled

    val totalDuration = animDuration + intervalMillis

    val transition = rememberInfiniteTransition(label = "pulseTransition")

    val pulseProgress by transition.animateFloat(
        initialValue = initialRadiusFraction,
        targetValue = targetRadiusFraction,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                // assign total duration for the whole cycle
                this.durationMillis = totalDuration

                // pulse grows during the first phase
                targetRadiusFraction at animDuration with LinearEasing

                // hold steady (pause)
                targetRadiusFraction at totalDuration
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseRadius"
    )

    val alpha by transition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = totalDuration

                // fade out during pulse
                0f at animDuration with LinearEasing

                // remain transparent during pause
                0f at totalDuration
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseAlpha"
    )

    return this
        .clipToBounds() // 👈 this makes sure drawing stays inside layout
        .drawBehind {
            val maxRadius = size.maxDimension / 2
            val radius = pulseProgress * maxRadius

            val customCenter = Offset(
                size.width * centerFraction.x,
                size.height * centerFraction.y
            )

            drawCircle(
                color = color.copy(alpha = alpha),
                radius = radius,
                center = customCenter
            )
        }
}