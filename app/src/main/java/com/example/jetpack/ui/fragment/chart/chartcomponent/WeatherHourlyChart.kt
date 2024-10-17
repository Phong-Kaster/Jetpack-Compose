package com.example.jetpack.ui.fragment.chart.chartcomponent

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.theme.brushSunset
import com.example.jetpack.ui.theme.customizedTextStyle

private val alignmentLinesPathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
private val dateHeight: Dp = 30.dp

@Composable
fun WeatherHourlyChart(
    records: List<ChartElement>,
    modifier: Modifier = Modifier,
) {
    var selectedChartElement: ChartElement? by remember { mutableStateOf(null) }
    val state = rememberLazyListState(records.size)

    /**
     * specify max and min range*/
    val alignment: Pair<Float, Float> by remember(records) {
        derivedStateOf {
            val defaultAlignment: Pair<Float, Float> = Constant.defaultRange

            val minValue = records.minOfOrNull { it.valueMin } ?: 0f
            val maxValue = records.maxOfOrNull { it.valueMax } ?: 100f


            Log.d("TAG", "WeatherHourlyChart - minValue = $minValue")
            Log.d("TAG", "WeatherHourlyChart - maxValue = $maxValue")

            // Take default alignments if max and min value are inside the range
            if (
                minValue in defaultAlignment.first..defaultAlignment.second &&
                maxValue in defaultAlignment.first..defaultAlignment.second
            ) {
                defaultAlignment
            } else {
                var minAlignment = defaultAlignment.first
                var maxAlignment = defaultAlignment.second

                if (minValue < minAlignment) {
                    // Keep dividing min alignment until min value is inside the range
                    while (minValue < minAlignment && minAlignment > 0.01f) minAlignment /= 2
                } else {
                    while (true) {
                        val tmpMin = minAlignment * 2
                        if (tmpMin < minValue) minAlignment = tmpMin
                        else break
                    }
                }

                if (maxValue > maxAlignment) {
                    // Keep multiplying max alignment until max value is inside the range
                    while (maxValue > maxAlignment && maxAlignment < 10000f) maxAlignment *= 1.5f
                }


                minValue to maxValue
            }
        }
    }

    LaunchedEffect(key1 = records) { state.animateScrollToItem(records.size) }


    AnimatedVisibility(
        visible = records.isEmpty(),
        content = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 10f)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = customizedTextStyle
                        (
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                        .drawChartBaseline(
                            textMeasurer = rememberTextMeasurer(),
                            minAlignment = alignment.first,
                            maxAlignment = alignment.second,
                            numberLine = 4,
                            dateHeight = dateHeight,
                            enableDashedLine = false
                        )

                ) {

                }
            }
        }
    )

    AnimatedVisibility(
        visible = records.isNotEmpty(),
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 10f)
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .drawChartBaseline(
                            textMeasurer = rememberTextMeasurer(),
                            minAlignment = alignment.first,
                            maxAlignment = alignment.second,
                            numberLine = 4,
                            dateHeight = dateHeight,
                            enableDashedLine = false
                        )
                ) {

                }

                LazyRow(
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(
                        items = records,
                        key = { index, _ -> index },
                        itemContent = { index: Int, chartElement: ChartElement ->
                            WeatherHourlyChartElement(
                                previousElement = records.getOrNull(index - 1) ?: ChartElement.getFakeElement(),
                                element = chartElement,
                                maxTemperature = alignment.second,
                                minTemperature = alignment.first,
                                modifier = Modifier
                                    .width(100.dp)
                                    .fillParentMaxHeight(),
                            )
                        }
                    )
                }
            }
        }
    )
}

/**
 * @param maxAlignment is the maximum value of the chart
 * @param minAlignment is the minimum value of chart, it is usually 0
 * @param numberLine is the number of dashed line that chart will draw
 * @param dateHeight is the minimum of height that text of date can be shown fully
 */
private fun Modifier.drawChartBaseline(
    textMeasurer: TextMeasurer,
    maxAlignment: Float,
    minAlignment: Float,
    numberLine: Int,
    dateHeight: Dp,
    enableDashedLine: Boolean,
): Modifier = drawWithCache {
    val spacing: Float = (maxAlignment - minAlignment) / numberLine

    val lineCoordinates = (0..numberLine).map { index: Int ->
        minAlignment + index * spacing
    }

    val textCoordinates = lineCoordinates.map { number: Float ->
        textMeasurer.measure(
            text = "${number.toInt()}°", style = customizedTextStyle(
                fontSize = 10
            )
        )
    }

    val maxValueWidth: Int = textCoordinates.maxOf { it.size.width }


    onDrawBehind {

        for (index in 0..numberLine) {
            val yCoordinate = lineCoordinates[index]
            val textLayoutResult = textCoordinates[index]

            val yOffset = (numberLine - index) * (size.height - dateHeight.toPx()) / numberLine

            drawText(
                textLayoutResult = textLayoutResult,
                color = Color.White,
                topLeft = Offset(
                    x = 0F - textLayoutResult.size.width * 0.5f,
                    y = yOffset - textLayoutResult.size.height * 0.5F
                )
            )

            if (enableDashedLine) {
                drawLine(
                    color = Color(0xFFD9D9D9),
                    start = Offset(x = maxValueWidth * 1.5F, y = yOffset),
                    end = Offset(x = size.width, y = yOffset),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = alignmentLinesPathEffect,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherHourlyChartEmpty() {
    WeatherHourlyChart(
        records = listOf(),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
    )
}

@Preview
@Composable
private fun PreviewWeatherHourlyChart() {
    WeatherHourlyChart(
        records = ChartElement.getFakeElements(),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
    )
}

