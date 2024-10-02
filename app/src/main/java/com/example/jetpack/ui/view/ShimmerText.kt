package com.example.jetpack.ui.view

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

/**
 * ShimmerText

 *
 * @param text The text that will be displayed
 * @param shimmerColor The color used for the shimmer effect
 * @param modifier A Modifier to be applied to the Text composable
 * @param textStyle The style of the text
 * @param animationSpec The animation specification for controlling the shimmer speed, delay, and easing
 *
 * [Document](https://medium.com/@kappdev/how-to-create-a-shimmering-text-animation-in-jetpack-compose-eb4a553d924c)
 */
@Composable
fun ShimmerText(
    text: String,
    shimmerColor: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(1000, 500, LinearEasing)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ShimmeringTextTransition")
    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "ShimmerProgress"
    )

    val brush = remember {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {

                val initial = -(size.width)

                val totalDistance = size.width * 2

                val current = initial + totalDistance * shimmerProgress

                return LinearGradientShader(
                    from = Offset(x = current, y = 0f),
                    to = Offset(x = current + size.width, y = 0f),
                    colors = listOf(Color.Transparent, shimmerColor, Color.Transparent),
                )
            }
        }
    }

    Text(
        text = text,
        textAlign = textAlign,
        style = textStyle.copy(brush = brush),
        modifier = modifier
    )
}