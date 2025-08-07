package com.example.jetpack.ui.theme

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color

val animationSpecFloat = tween<Float>(durationMillis = 1000, easing = FastOutLinearInEasing, delayMillis = 0)
val animationSpecFloat1 = tween<Float>(durationMillis = 1000, easing = FastOutLinearInEasing, delayMillis = 400)
val animationSpecFloat2 = tween<Float>(durationMillis = 1000, easing = FastOutLinearInEasing, delayMillis = 800)
val animationSpecFloat3 = tween<Float>(durationMillis = 1000, easing = FastOutLinearInEasing, delayMillis = 1200)

val animationSpecInteger = tween<Int>(durationMillis = 1000, easing = LinearOutSlowInEasing)

val animationInfiniteColor = infiniteRepeatable<Color>(animation = tween(durationMillis = 3000), repeatMode = RepeatMode.Reverse)

val animationInfiniteFloat = infiniteRepeatable<Float>(animation = tween(durationMillis = 1000), repeatMode = RepeatMode.Restart)
val animationInfiniteFloat2 = infiniteRepeatable<Float>(animation = tween(durationMillis = 2000), repeatMode = RepeatMode.Restart)
val animationInfiniteFloat3 = infiniteRepeatable<Float>(animation = tween(durationMillis = 3000), repeatMode = RepeatMode.Restart)
val animationInfiniteFloat4 = infiniteRepeatable<Float>(animation = tween(durationMillis = 4000), repeatMode = RepeatMode.Restart)
val animationInfiniteFloat5 = infiniteRepeatable<Float>(animation = tween(durationMillis = 5000), repeatMode = RepeatMode.Restart)
val animationInfiniteFloatSuperLong = infiniteRepeatable<Float>(animation = tween(durationMillis = 60000), repeatMode = RepeatMode.Restart)