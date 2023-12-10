package com.example.jetpack.ui.theme

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color

val animationSpecFloat = tween<Float>(durationMillis = 1000, easing = FastOutLinearInEasing)

val animationSpecInteger = tween<Int>(durationMillis = 1000, easing = LinearOutSlowInEasing)

val animationInfiniteColor = infiniteRepeatable<Color>(animation = tween(durationMillis = 3000), repeatMode = RepeatMode.Reverse)