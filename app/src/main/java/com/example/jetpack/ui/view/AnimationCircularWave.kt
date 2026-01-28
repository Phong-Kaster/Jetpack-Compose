package com.example.jetpack.ui.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R

/**
 * An animation that waves spread out and fade out
 * @see [Wave Blue](https://lottiefiles.com/animations/wave-blue-yFlA6fBdir)
 */
@Composable
fun AnimationCircularWave(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing, delayMillis = 500),
            repeatMode = RepeatMode.Restart,
        ),
        label = "animationProgress"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1F)
            .drawBehind {
                drawCircle(
                    color = Color(0xFFE1F6FF),
                    radius = size.width * 0.50F + size.width * 0.30F * animationProgress.coerceIn(0f, 1f),
                    alpha = ((1f - animationProgress) * 2).coerceIn(0f, 1f),
                )

                drawCircle(
                    color = Color(0xFFE1F6FF),
                    radius = size.width * 0.50F + size.width * 0.30F * (animationProgress - 0.25f).coerceIn(0f, 1f),
                    alpha = ((1.25f - animationProgress) * 2).coerceIn(0f, 1f),
                )

                drawCircle(
                    color = Color(0xFFE1F6FF),
                    radius = size.width * 0.50F + size.width * 0.30F * (animationProgress - 0.5f).coerceIn(0f, 1f),
                    alpha = ((1.5f - animationProgress) * 2).coerceIn(0f, 1f),
                )

                drawCircle(
                    color = Color(0xFFC6EEFF),
                    radius = size.width * 0.50F
                )


            },
    ) {

        content()
    }
}

@Preview
@Composable
private fun PreviewCircularWave() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimationCircularWave(
            modifier = Modifier.size(100.dp),
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bluetooth),
                    contentDescription = null,
                    tint = Color(0xFF1044FF),
                    modifier = Modifier
                        .fillMaxSize(0.35F)
                )
            }
        )
    }
}