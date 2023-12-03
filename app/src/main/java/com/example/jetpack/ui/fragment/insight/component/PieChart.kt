package com.example.jetpack.ui.fragment.insight.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.animationSpecFloat
import com.example.jetpack.util.NumberUtil.toPercentage
import com.example.jetpack.util.ViewUtil

private val chartSize: Dp = 150.dp
private val innerChartSize: Dp = chartSize * 0.67F
private const val filledAngle = 360F // A filled angle is an angle equal to 360° (entire circle)

/**
 * @param centerColor is the color of inner circle
 * @param data is the map that contains Type<String, Float> with
 * String is the name and Float is the value
 */
@Composable
fun PieChart(
    centerColor: Color = Color.DarkGray,
    data: Map<String, Float> = mapOf(
        "Red" to 35.15F,
        "Yellow" to 15.23F,
        "Blue" to 50.32F
    )
) {
    var percentageRed  by remember { mutableFloatStateOf(0F) } // 25 %
    var percentageBlue  by remember { mutableFloatStateOf(0F) } // 25 %
    var percentageYellow by remember { mutableFloatStateOf(0F) } // 50 %
    val total: Float by remember {
        derivedStateOf {
            var sumValue = 0F
            data.entries.map {
                sumValue += it.value
            }
            sumValue
        }
    }

    // Note: the start position of next color is the destination of current color
    var startAngleYellow: Float by remember { mutableFloatStateOf(0F) }
    var startAngleBlue: Float by remember { mutableFloatStateOf(0F) }

    var sweepAngleRed: Float by remember { mutableFloatStateOf(0F) }
    var sweepAngleYellow: Float by remember { mutableFloatStateOf(0F) }
    var sweepAngleBlue: Float by remember { mutableFloatStateOf(0F) }



    // Enable float animation
    LaunchedEffect(key1 = data) {
        percentageRed = if(data.isEmpty()) 0.25F else (data.getValue("Red").toFloat() / total) // 0.25
        percentageYellow = if(data.isEmpty()) 0.25F else (data.getValue("Yellow").toFloat() / total) // 0.25F
        percentageBlue = if(data.isEmpty()) 0.5F else (data.getValue("Blue").toFloat() / total) // 0.5F

        sweepAngleRed = percentageRed * filledAngle
        sweepAngleYellow = percentageYellow * filledAngle
        sweepAngleBlue = percentageBlue * filledAngle

        startAngleBlue = sweepAngleRed
        startAngleYellow = startAngleBlue + sweepAngleBlue
    }


    // Establish animation as state
    val animationSweepAngleRed by animateFloatAsState(targetValue = sweepAngleRed, label = "animationRed", animationSpec = animationSpecFloat)
    val animationSweepAngleYellow by animateFloatAsState(targetValue = sweepAngleYellow, label = "animationYellow", animationSpec = animationSpecFloat)
    val animationSweepAngleBlue by animateFloatAsState(targetValue = sweepAngleBlue, label = "animationBlue", animationSpec = animationSpecFloat)

    val animationPercentageRed by animateFloatAsState(targetValue = percentageRed, label = "animationRed", animationSpec = animationSpecFloat)
    val animationPercentageYellow by animateFloatAsState(targetValue = percentageYellow, label = "animationYellow", animationSpec = animationSpecFloat)
    val animationPercentageBlue by animateFloatAsState(targetValue = percentageBlue, label = "animationBlue", animationSpec = animationSpecFloat)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(chartSize),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(chartSize)
                .clip(shape = CircleShape)
                .background(color = Color.Transparent)
                .drawBehind {
                    drawArc(
                        color = Color(0xFFF93800),
                        startAngle = 0F,
                        sweepAngle = animationSweepAngleRed,
                        useCenter = true
                    )

                    drawArc(
                        color = Color(0xFF283350),
                        startAngle = startAngleBlue,
                        sweepAngle = animationSweepAngleBlue,
                        useCenter = true
                    )

                    drawArc(
                        color = Color(0xFFFFB500),
                        startAngle = startAngleYellow,
                        sweepAngle = animationSweepAngleYellow,
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


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            data.entries.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Spacer(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .size(15.dp)
                            .background(
                                color = when (it.key) {
                                    "Red" -> Color(0xFFF93800)
                                    "Yellow" -> Color(0xFFFFB500)
                                    else -> Color(0xFF283350)
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = when (it.key) {
                            "Red" -> "${stringResource(id = R.string.vietnamese)} (${animationPercentageRed.toPercentage()}%)"
                            "Yellow" -> "${stringResource(id = R.string.japanese)} (${animationPercentageYellow.toPercentage()}%)"
                            else -> "${stringResource(id = R.string.german)} (${animationPercentageBlue.toPercentage()})%)"
                        },
                        color = when (it.key) {
                            "Red" -> Color(0xFFF93800)
                            "Yellow" -> Color(0xFFFFB500)
                            else -> Color(0xFF283350)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewPieChart() {
    ViewUtil.PreviewContent {
        PieChart(
            data = mapOf(
                "Red" to 25F,
                "Yellow" to 25F,
                "Blue" to 50F
            )
        )
    }
}