package com.example.jetpack.ui.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun DottedTextAnimation(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    cycleDuration: Int = 1000 // Milliseconds
) {
    val transition = rememberInfiniteTransition(label = "transition")

    val visibleDot = transition.animateValue(
        initialValue = 0,
        targetValue = 4,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = cycleDuration,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "visibleDot"
    )

    Text(
        text = text + ".".repeat(visibleDot.value),
        modifier = modifier,
        style = style,
    )
}

@Preview
@Composable
private fun PreviewDottedTextAnimation() {
    DottedTextAnimation(
        text = stringResource(id = R.string.app_name),
        style = customizedTextStyle(fontSize = 14, fontWeight = 600),
        cycleDuration = 1000,
        modifier = Modifier,
    )
}