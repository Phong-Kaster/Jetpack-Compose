package com.example.jetpack.ui.fragment.chart.chartcomponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.domain.model.ChartElement.Companion.getMaxValue
import com.example.jetpack.domain.model.ChartElement.Companion.getMinValue
import com.example.jetpack.ui.theme.ColorWindSpeedCalm
import com.example.jetpack.ui.theme.ColorWindSpeedFresh
import com.example.jetpack.ui.theme.ColorWindSpeedGale
import com.example.jetpack.ui.theme.ColorWindSpeedGentle
import com.example.jetpack.ui.theme.ColorWindSpeedHurricaneForce
import com.example.jetpack.ui.theme.ColorWindSpeedLight
import com.example.jetpack.ui.theme.ColorWindSpeedLightAir
import com.example.jetpack.ui.theme.ColorWindSpeedModerate
import com.example.jetpack.ui.theme.ColorWindSpeedNearGale
import com.example.jetpack.ui.theme.ColorWindSpeedStorm
import com.example.jetpack.ui.theme.ColorWindSpeedStrong
import com.example.jetpack.ui.theme.ColorWindSpeedStrongGale
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.DateUtil
import com.example.jetpack.util.DateUtil.formatWithPattern
import java.util.Date
import kotlin.math.roundToInt

private val colors = listOf(
    ColorWindSpeedCalm,
    ColorWindSpeedLightAir,
    ColorWindSpeedLight,
    ColorWindSpeedGentle,
    ColorWindSpeedModerate,
    ColorWindSpeedFresh,
    ColorWindSpeedStrong,
    ColorWindSpeedNearGale,
    ColorWindSpeedGale,
    ColorWindSpeedStrongGale,
    ColorWindSpeedStorm,
    ColorWindSpeedHurricaneForce,
)

/**
 * the key of problem depends on dateHeight
 * when we draw baseline, we minus dateHeight to spare space for datetime text.
 * So that we draw Element for chart, we have to minus dateHeight againt to match with
 * the height of baseline
 */
@Composable
fun BloodPressureChart2Element(
    minRange: Float,
    maxRange: Float,
    chartElement: ChartElement,
    dateHeight: Dp,
    enableInfo: Boolean = false,
    paddingHorizontal: Dp = 32.dp,
    paddingVertical: Dp = 5.dp,
    columnWidth: Dp = 12.dp,
    onClick: () -> Unit = {},
) {

    Layout(
        modifier = Modifier,
        content = {
        // Column
        Spacer(modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = colors.get(index = colors.indices.random()))
            .clickable { onClick() })

        // Max value
        Box {
            AnimatedVisibility(
                visible = enableInfo,
                enter = slideInVertically { it / 2 } + fadeIn(),
                exit = slideOutVertically { it / 2 } + fadeOut(),
            ) {
                Text(
                    text = chartElement.getMaxValue().toString(),
                    style = customizedTextStyle(fontSize = 14, fontWeight =  600),
                    modifier = Modifier.background(Color.White),
                )
            }
        }

        // Min value
        Box {
            AnimatedVisibility(
                visible = enableInfo,
                enter = slideInVertically { -it / 2 } + fadeIn(),
                exit = slideOutVertically { -it / 2 } + fadeOut(),
            ) {
                Text(
                    text = chartElement.getMinValue().toString(),
                    style = customizedTextStyle(fontSize = 14, fontWeight = 600),
                    modifier = Modifier.background(Color.White),
                )
            }
        }

        // Date
        Box {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = Date().formatWithPattern(pattern = DateUtil.PATTERN_MMM), style = customizedTextStyle(fontSize = 10, fontWeight = 400).copy(color = PrimaryColor)
                    )
                    Text(
                        text = Date().formatWithPattern(pattern = DateUtil.PATTERN_dd), style = customizedTextStyle(fontSize = 10, fontWeight = 400).copy(color = PrimaryColor)
                    )
                }
            }
        }

        // Status box
        Box {
            AnimatedVisibility(
                visible = enableInfo,
                enter = slideInVertically { it / 2 } + fadeIn(),
                exit = slideOutVertically { it / 2 } + fadeOut(),
            ) {
                Text(
                    text =chartElement.name,
                    style = customizedTextStyle(fontSize = 10, fontWeight= 400),
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .border(0.dp, Color.LightGray, RoundedCornerShape(5.dp))
                        .padding(vertical = 6.dp, horizontal = 8.dp)
                )
            }
        }
    }, measurePolicy = { measurables, constraints ->
        // Calculate column max position
        var maxPosition = calculateVerticalCoordinate(
            value = chartElement.getMaxValue().roundToInt(),
            minRange = minRange,
            maxRange = maxRange,
            maxHeight = constraints.maxHeight - dateHeight.roundToPx()
        )

        // Calculate column min position
        var minPosition = calculateVerticalCoordinate(
            value = chartElement.getMinValue().roundToInt(),
            maxHeight = constraints.maxHeight - dateHeight.roundToPx(),
            minRange = minRange,
            maxRange = maxRange
        )


        val different =
            chartElement.getMaxValue().roundToInt() - chartElement.getMinValue().roundToInt()
        if (different in 1..10) {
            minPosition = calculateVerticalCoordinate(
                value = chartElement.getMinValue().roundToInt() - 10,
                maxHeight = constraints.maxHeight - dateHeight.roundToPx(),
                minRange = minRange,
                maxRange = maxRange
            )
        } else if (different == 0 && chartElement.getMaxValue() < maxRange) {
            maxPosition = calculateVerticalCoordinate(
                value = chartElement.getMaxValue().roundToInt() + 10,
                maxHeight = constraints.maxHeight - dateHeight.roundToPx(),
                minRange = minRange,
                maxRange = maxRange
            )
        } else if (different == 0 && chartElement.getMaxValue() == maxRange) {
            minPosition = calculateVerticalCoordinate(
                value = chartElement.getMinValue().roundToInt() - 10,
                maxHeight = constraints.maxHeight - dateHeight.roundToPx(),
                minRange = minRange,
                maxRange = maxRange
            )
        }


        val columnPlaceable = measurables[0].measure(
            Constraints.fixed(
                width = columnWidth.roundToPx(), height = (minPosition - maxPosition).toInt()
            )
        )
        val maxValuePlaceable = measurables[1].measure(constraints)
        val minValuePlaceable = measurables[2].measure(constraints)
        val datePlaceable = measurables[3].measure(constraints)
        val statusPlaceable = measurables[4].measure(constraints)


        val layoutWidth = maxOf(columnPlaceable.width, datePlaceable.width) + paddingHorizontal.roundToPx()
        layout(
            width = layoutWidth, height = constraints.maxHeight
        ) {
            // Place column
            columnPlaceable.placeRelative(
                x = (layoutWidth - columnPlaceable.width) / 2, y = maxPosition.roundToInt()
            )


            // Place max value text at the top of column
            maxValuePlaceable.placeRelative(
                x = (layoutWidth - maxValuePlaceable.width) / 2,
                y = maxPosition.roundToInt() - maxValuePlaceable.height - paddingVertical.roundToPx(),
            )


            // Place min value text at the bottom of column
            minValuePlaceable.placeRelative(
                x = (layoutWidth - minValuePlaceable.width) / 2,
                y = minPosition.roundToInt() + paddingVertical.roundToPx(),
            )


            // Place status at the top of max value
            statusPlaceable.placeRelative(
                x = (layoutWidth - statusPlaceable.width) / 2,
                y = maxPosition.roundToInt() - maxValuePlaceable.height - statusPlaceable.height - (paddingVertical * 2F).roundToPx(),
            )


            // Place date at the bottom
            datePlaceable.placeRelative(
                x = (layoutWidth - datePlaceable.width) / 2,
                y = constraints.maxHeight - datePlaceable.height / 2,
            )
        }
    })
}

@Preview
@Composable
private fun PreviewBloodPressureChart2Element() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(alignment = Alignment.CenterHorizontally, space = 15.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        BloodPressureChart2Element(
            enableInfo = true,
            minRange = 20F,
            maxRange = 300F,
            dateHeight = 30.dp,
            chartElement = ChartElement.getFakeElement()
        )
    }
}


private fun calculateVerticalCoordinate(
    maxRange: Float, minRange: Float, value: Int, maxHeight: Int
): Float {
    val maximumOfHeight = if (value < maxRange) {
        maxHeight
    } else {
        (maxHeight + maxRange * 0.05F).toInt()
    }

    val rate = value / (maxRange - minRange)
    val reversedRate = 1 - rate

    val outcome = reversedRate.coerceAtMost(1F) * maximumOfHeight
    return outcome
}