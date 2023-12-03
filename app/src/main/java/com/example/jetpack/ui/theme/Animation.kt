package com.example.jetpack.ui.theme

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween

val animationSpec = tween<Float>(
    durationMillis = 1000,
    easing = FastOutLinearInEasing
)