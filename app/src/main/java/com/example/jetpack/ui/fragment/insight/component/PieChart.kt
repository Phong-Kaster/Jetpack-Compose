package com.example.jetpack.ui.fragment.insight.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.animationSpec
import com.example.jetpack.util.ViewUtil

private val chartSize: Dp = 150.dp
private val innerChartSize: Dp = chartSize * 0.67F
private val filledAngle = 360F // A filled angle is an angle equal to 360° (entire circle)

@Composable
fun PieChart(
    centerColor: Color = Color.DarkGray
) {

    var sweepAngleRed by remember{ mutableFloatStateOf(0F) }
    var sweepAngleYellow by remember{ mutableFloatStateOf(0F) }
    var sweepAngleBlue by remember{ mutableFloatStateOf(0F) }

    LaunchedEffect(Unit){ sweepAngleRed = 145F }
    LaunchedEffect(Unit){ sweepAngleYellow = 185F }
    LaunchedEffect(Unit){ sweepAngleBlue = 35F }

    val animationRed by animateFloatAsState(targetValue = sweepAngleRed, label = "animationRed", animationSpec = animationSpec )
    val animationYellow by animateFloatAsState(targetValue = sweepAngleYellow, label = "animationYellow", animationSpec = animationSpec )
    val animationBlue by animateFloatAsState(targetValue = sweepAngleBlue, label = "animationBlue", animationSpec = animationSpec )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(chartSize)
                .clip(shape = CircleShape)
                .background(color = Color.Transparent)
                .drawBehind {
                    drawArc(
                        color = Color.Red,
                        startAngle = 0F,
                        sweepAngle = animationRed,
                        useCenter = true
                    )

                    drawArc(
                        color = Color.Blue,
                        startAngle = 145F,
                        sweepAngle = animationBlue,
                        useCenter = true
                    )

                    drawArc(
                        color = Color.Yellow,
                        startAngle = 175F,
                        sweepAngle = animationYellow,
                        useCenter = true
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .size(innerChartSize)
                    .clip(shape = CircleShape)
                    .background(color = centerColor)
            )
        }

    }
}

@Preview
@Composable
fun PreviewPieChart() {
    ViewUtil.PreviewContent {
        PieChart()
    }
}