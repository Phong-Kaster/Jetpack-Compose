package com.example.jetpack.ui.modifier

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle

/**
 * Animate borders in Jetpack Compose - https://proandroiddev.com/animate-borders-in-jetpack-compose-ca359deed7d5
 *
 * when we use borderWithAnimatedGradient then we do not use
 * Modifier.padding, Modifier.background & Modifier.clip
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.borderWithAnimatedGradient(
    width: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(20.dp),
    colors: List<Color> = listOf(
        Color(0xFF004BDC),
        Color(0xFF004BDC),
        Color(0xFF9EFFFF),
        Color(0xFF9EFFFF),
        Color(0xFF9EFFFF),
        Color(0xFF9EFFFF),
        Color(0xFF004BDC),
        Color(0xFF004BDC)
    ),
) = composed {

    // Configure brush
    val brushSweep = Brush.sweepGradient(colors)


    // infinity rotation
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    clip(shape = shape)
        .padding(width)
        .drawBehind {
            rotate(degrees = angle) {
                drawCircle(
                    brush = brushSweep,
                    radius = size.width,
                    blendMode = BlendMode.SrcIn,
                )
            }

            drawRoundRect(
                color = Color.White,
                topLeft = Offset(1.dp.toPx(), 1.dp.toPx()),
                size = Size(
                    width = size.width - 2.dp.toPx(),
                    height = size.height - 2.dp.toPx()
                ),
                cornerRadius = CornerRadius(
                    x = 19.dp.toPx(),
                    y = 19.dp.toPx()
                )
            )
        }
}

@Preview
@Composable
private fun ExampleWithBorderWithAnimatedGradient() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
            .padding(16.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(25.dp))
                .borderWithAnimatedGradient(
                    width = 5.dp,
                    shape = RoundedCornerShape(25.dp),
                    colors = listOf(Color(0xFF004BDC), Color(0xFF004BDC), Color(0xFF9EFFFF), Color(0xFF9EFFFF), Color(0xFF9EFFFF), Color(0xFF9EFFFF), Color(0xFF004BDC), Color(0xFF004BDC)),
                )
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(25.dp))
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_lightbulb),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = customizedTextStyle(fontSize = 14, fontWeight = 500, color = Color.Black)
                )

                Text(
                    text = "borderWithAnimatedGradient",
                    style = customizedTextStyle(fontSize = 14, fontWeight = 500, color = Color.Black)
                )
            }
        }
    }
}