package com.example.jetpack.ui.theme

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween

val animationSpecFloat = tween<Float>(durationMillis = 1000, easing = FastOutLinearInEasing)

val animationSpecInteger = tween<Int>(durationMillis = 1000, easing = LinearOutSlowInEasing)