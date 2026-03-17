package com.example.jetpack.ui.view

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.AppUtil

@Composable
fun HomePremium(
    enable: Boolean = true,
    onClick: () -> Unit = {},
) {
    if (!enable) return

    val infiniteTransition = rememberInfiniteTransition(label = "pro-badge-scale")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin.Center
            }
            .width(100.dp * AppUtil.figmaScaleWidth)
            .height(28.dp * AppUtil.figmaScaleHeight)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFF4DEB1)
            )
            .clip(shape = RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFF0D193),
                        Color(0xFFF2D794),
                        Color(0xFFC49253),
                    ),
                )
            )


    ) {

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .shimmer()
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.matchParentSize()
        ) {

            Image(
                painter = painterResource(R.drawable.img_crown),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Premium",
                style = customizedTextStyle(
                    fontSize = 14,
                    fontWeight = 400,
                    color = Color(0xFF533B18),
                ),
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun Modifier.shimmer(cornerRadius: Dp = 0.dp): Modifier {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.3f)
    )

    val transition = rememberInfiniteTransition(label = "Shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -400f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000, // slower = smoother
                easing = FastOutSlowInEasing // smoother easing
            )
        ),
        label = "Translate"
    )

    return this.drawWithCache {
        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnim, 0f),
            // wider gradient
            end = Offset(translateAnim + size.width / 1.5f, size.height)
        )
        val cornerPx = cornerRadius.toPx()
        onDrawWithContent {
            drawRoundRect(
                brush = brush,
                cornerRadius = CornerRadius(cornerPx, cornerPx),
                size = size
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Column(modifier = Modifier.padding(16.dp)) {
        HomePremium()
    }

}