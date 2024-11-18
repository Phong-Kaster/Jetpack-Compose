package com.example.jetpack.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountdownSnackbarTime(
    timerProgress: Float,
    secondsRemaining: Int,
    color: Color = Color.Red,
){
    Box(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            // Define the stroke
            val strokeStyle = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
            // Draw the track
            drawCircle(
                color = color.copy(alpha = 0.12f),
                style = strokeStyle
            )
            // Draw the progress
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = (-360f * timerProgress),
                useCenter = false,
                style = strokeStyle
            )
        }
        // Display the remaining seconds
        Text(
            text = secondsRemaining.toString(),
            style = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = color
            )
        )
    }
}

@Preview
@Composable
private fun PreviewCountdownSnackbar() {
    CountdownSnackbarTime(
        timerProgress = 0.4f,
        secondsRemaining = 1,
    )
}