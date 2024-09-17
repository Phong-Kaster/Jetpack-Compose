package com.example.jetpack.ui.fragment.chart.chartcomponent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.theme.Border
import com.example.jetpack.ui.theme.ColorPressureLow
import com.example.jetpack.ui.theme.ColorUVIndexExtremeHigh
import com.example.jetpack.ui.theme.ColorUVIndexModerate
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.animationSpecFloat
import com.example.jetpack.ui.theme.animationSpecFloat1
import com.example.jetpack.ui.theme.animationSpecFloat2
import com.example.jetpack.ui.theme.animationSpecFloat3
import com.example.jetpack.util.ViewUtil


private const val startAngle = 90F
private const val strokeWidth: Float = 8F

@Composable
fun RingChart2(
    modifier: Modifier = Modifier,
    ringSize: Dp = 150.dp,
    data: List<ChartElement>,
) {
    var sweepAngleGreen by remember { mutableFloatStateOf(0F) }
    var sweepAngleYellow by remember { mutableFloatStateOf(0F) }
    var sweepAngleRed by remember { mutableFloatStateOf(0F) }
    var sweepAngleBlue by remember { mutableFloatStateOf(0F) }


    val animationSweepAngleGreen by animateFloatAsState(targetValue = sweepAngleGreen, animationSpec = animationSpecFloat, label = "sweepAngleGreen")
    val animationSweepAngleYellow by animateFloatAsState(targetValue = sweepAngleYellow, animationSpec = animationSpecFloat1, label = "animationSweepAngleYellow")
    val animationSweepAngleRed by animateFloatAsState(targetValue = sweepAngleRed, animationSpec = animationSpecFloat2, label = "animationSweepAngleRed")
    val animationSweepAngleBlue by animateFloatAsState(targetValue = sweepAngleBlue, animationSpec = animationSpecFloat3, label = "animationSweepAngleBlue")

    LaunchedEffect(key1 = data) {
        sweepAngleGreen = 270F
        sweepAngleYellow = 260F
        sweepAngleRed = 250F
        sweepAngleBlue = 240F
    }

    Row(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                // GREEN - FIRST LAYER
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(ringSize)
                    .align(Alignment.CenterHorizontally)
                    .drawBehind {
                        drawCircle(
                            color = Border,
                            radius = size.width / 2,
                            style = Stroke(width = strokeWidth)
                        )
                        drawArc(
                            color = ColorUVIndexModerate,
                            startAngle = startAngle,
                            sweepAngle = animationSweepAngleGreen,
                            useCenter = false,
                            style = Stroke(width = strokeWidth)
                        )
                    },

                ) {
                Box(
                    // YELLOW - SECOND LAYER
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(ringSize * 0.85F)
                        .drawBehind {
                            drawCircle(
                                color = Border,
                                radius = size.width / 2,
                                style = Stroke(width = strokeWidth)
                            )
                            drawArc(
                                color = PrimaryColor,
                                startAngle = startAngle,
                                sweepAngle = animationSweepAngleYellow,
                                useCenter = false,
                                style = Stroke(width = strokeWidth)
                            )
                        },
                ) {
                    Box(
                        // RED - THIRD LAYER
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(ringSize * 0.70F)
                            .drawBehind {
                                drawCircle(
                                    color = Border,
                                    radius = size.width / 2,
                                    style = Stroke(width = strokeWidth)
                                )
                                drawArc(
                                    color = ColorUVIndexExtremeHigh,
                                    startAngle = startAngle,
                                    sweepAngle = animationSweepAngleRed,
                                    useCenter = false,
                                    style = Stroke(width = strokeWidth)
                                )
                            },
                    ) {
                        Box(
                            // BLUE - FINAL LAYER
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(ringSize * 0.55F)
                                .drawBehind {
                                    drawCircle(
                                        color = Border,
                                        radius = size.width / 2,
                                        style = Stroke(width = strokeWidth)
                                    )
                                    drawArc(
                                        color = ColorPressureLow,
                                        startAngle = startAngle,
                                        sweepAngle = animationSweepAngleBlue,
                                        useCenter = false,
                                        style = Stroke(width = strokeWidth)
                                    )
                                },
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRingChart2() {
    ViewUtil.PreviewContent {
        RingChart2(
            modifier = Modifier.padding(16.dp),
            data = ChartElement.getFakeElements()
        )
    }
}