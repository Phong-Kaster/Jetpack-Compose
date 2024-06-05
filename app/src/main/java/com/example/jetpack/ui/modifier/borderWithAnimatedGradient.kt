package com.example.jetpack.ui.modifier

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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle

/**
 * when we use borderWithAnimatedGradient then we do not use
 * Modifier.padding
 */
fun Modifier.borderWithAnimatedGradient(
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
    durationMillis: Int = 2000,
    width: Int = 2,
    backgroundColor: Color = Color.Transparent
) = composed {

    // Configure brush
    val brushSweep = Brush.sweepGradient(colors)


    // infinity rotation
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    drawBehind {
        rotate(degrees = angle) {
            drawCircle(
                brush = brushSweep,
                radius = size.width,
                blendMode = BlendMode.SrcIn,
            )
        }

        // draw solid color
        drawRoundRect(
            color = backgroundColor,
            topLeft = Offset(x = width.dp.toPx(), y = width.dp.toPx()),
            size = Size(
                width = size.width - (width * 2).dp.toPx(),
                height = size.height - (width * 2).dp.toPx()
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
private fun ExampleWithBorderWithAnimatedGradient() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.White)
            .borderWithAnimatedGradient(
                durationMillis = 2000,
                width = 5,
                backgroundColor = Color(0xFFF3F7FF)
            )
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
                text = stringResource(id = R.string.fake_message),
                style = customizedTextStyle(fontSize = 14, fontWeight = 500, color = Color.Black)
            )
        }
    }
}