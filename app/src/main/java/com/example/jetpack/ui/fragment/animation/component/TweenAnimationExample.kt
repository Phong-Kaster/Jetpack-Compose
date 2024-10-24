package com.example.jetpack.ui.fragment.animation.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
fun TweenAnimationExample() {
    var size by remember { mutableStateOf(100.dp) }
    val animatedSize by animateDpAsState(
        targetValue = size,
        animationSpec = tween(
            durationMillis = 1000, // 1-second duration
            easing = LinearEasing // Optional: Use a different easing curve
        ), label = "Tween Animation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(animatedSize)
                .background(Color.Blue)
        )

        Button(onClick = { size = if (size == 100.dp) 200.dp else 100.dp },
            content = {
                Text("Tween Animation Example")
            })
    }
}