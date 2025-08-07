package com.example.jetpack.ui.fragment.chart.chartcomponent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Constant.LIST_OF_COLOUR
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.theme.animationSpecFloat1
import kotlinx.coroutines.launch

/**
 * Gap Pie Chart
 */
@Composable
fun DonutChart2(
    gapDegrees: Int = 20,
    records: List<ChartElement> = ChartElement.getFakeElements(4),
    modifier: Modifier = Modifier,
) {
    val fullDegrees = 360 // A full circle
    val leftDegrees by remember(records) {
        derivedStateOf { fullDegrees - (records.size * gapDegrees) }
    }

    val listOfNumber by remember(records) {
        derivedStateOf { records.map { element -> element.valueMax } }
    }

    val sum by remember(listOfNumber) {
        derivedStateOf { listOfNumber.sum() }
    }

    var currentSum by remember { mutableFloatStateOf(0f) }
    val listOfPie: List<GapPie2> by remember(listOfNumber, sum) {
        derivedStateOf {
            listOfNumber.mapIndexed { index: Int, number: Float ->

                val startAngle = currentSum + (gapDegrees * index)
                currentSum += (number / sum) * leftDegrees

                val sweepAngle = (number / sum) * leftDegrees


                GapPie2(
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    color = LIST_OF_COLOUR[index],
                    animation = Animatable(0f)
                )
            }
        }
    }

    LaunchedEffect(listOfPie) {
        listOfPie.map { pie ->
            launch {
                pie.animation.animateTo(
                    targetValue = pie.sweepAngle,
                    animationSpec = animationSpecFloat1,
                )
            }
        }
    }




    Canvas(
        modifier = modifier
            .size(150.dp)
            .padding(10.dp)
    ) {
        listOfPie.reversed().forEach { pie ->
            drawArc(
                color = pie.color,
                startAngle = pie.startAngle,
                sweepAngle = pie.animation.value,
                useCenter = false,
                style = Stroke(width = 50f, cap = StrokeCap.Round)
            )
        }
    }
}

/**
 * Gap Pie
 * @param animation is the key to enable Jetpack Compose animation
 * @param startAngle is the start angle of arc
 * @param sweepAngle is the sweep angle of arc
 * @param color is the color of arc
 */
private class GapPie2(
    val animation: Animatable<Float, AnimationVector1D>,
    val startAngle: Float = -90f,
    val sweepAngle: Float = 0f,
    val color: Color = Color.Blue
)

@Preview
@Composable
private fun PreviewGapPieChart2() {
    DonutChart2()
}