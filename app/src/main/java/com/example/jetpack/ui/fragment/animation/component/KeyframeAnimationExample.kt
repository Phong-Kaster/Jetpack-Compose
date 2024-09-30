package com.example.jetpack.ui.fragment.animation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun KeyframeAnimationExample() {
    var size by remember { mutableStateOf(100.dp) }
    val animatedSize by animateDpAsState(
        targetValue = size,
        animationSpec = keyframes {
            durationMillis = 1500 // Total duration of the animation

            // Define values at specific time points
            100.dp at 0 using LinearEasing // Start at 100.dp
            200.dp at 500 using FastOutSlowInEasing // Grow to 200.dp
            150.dp at 1000 using LinearEasing // Shrink to 150.dp
            size at 1500 using FastOutSlowInEasing // End at targetValue
        }, label = "Keyframes Animation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(animatedSize)
                .background(Color.Green)
        )

        Button(onClick = { size = if (size == 100.dp) 200.dp else 100.dp }) {
            Text("Keyframe Animation Example")
        }
    }
}