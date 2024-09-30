package com.example.jetpack.ui.fragment.animation.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun InfiniteRepeatableAnimationExample() {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Transition")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Blue,
        targetValue = Color.Red,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = "Infinite Repeatable Animation"
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color)
    )
}