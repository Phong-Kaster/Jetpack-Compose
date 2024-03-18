package com.example.jetpack.ui.fragment.chart.chartcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Constant
import com.example.jetpack.data.model.ChartElement
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

private val alignmentLinesPathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
private val dateHeight: Dp = 30.dp

@Composable
fun BloodPressureChart2(
    records: ImmutableList<ChartElement>,
    modifier: Modifier = Modifier,
) {
    var selectedChartElement: ChartElement? by remember { mutableStateOf(null) }
    val state = rememberLazyListState(records.size)

    /**
     * specify max and min range*/
    val alignment: Pair<Float, Float> by remember(records) {
        derivedStateOf {
            val defaultAlignment: Pair<Float, Float> = Constant.defaultRange

            val minValue = records.minOf { it.valueMin }
            val maxValue = records.maxOf { it.valueMin }


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


                minAlignment to maxAlignment
            }
        }
    }

    LaunchedEffect(key1 = records) { state.animateScrollToItem(records.size) }

    Box(
        modifier = modifier
            .aspectRatio(1F)
    ) {
        if (records.isEmpty()) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = Background)
                    .padding(start = 0.dp, end = 0.dp, bottom = 32.dp, top = 32.dp)
                    .drawChartBaseline(
                        textMeasurer = rememberTextMeasurer(),
                        minAlignment = alignment.first,
                        maxAlignment = alignment.second,
                        numberLine = 4,
                        dateHeight = dateHeight,
                        enableDashline = records.isNotEmpty()
                    )
            ) {
                /*BloodPressureEmpty(
                    modifier = Modifier.fillMaxSize(),
                    onClick = bloodPressureOnLog
                )*/
            }

        } else {
            LazyRow(
                contentPadding = PaddingValues(start = 0.dp, end = 12.dp),
                modifier = Modifier
                    .matchParentSize()
                    .background(color = Background)
                    .padding(start = 0.dp, end = 0.dp, bottom = 32.dp, top = 32.dp)
                    .drawChartBaseline(
                        textMeasurer = rememberTextMeasurer(),
                        minAlignment = alignment.first,
                        maxAlignment = alignment.second,
                        numberLine = 4,
                        dateHeight = dateHeight,
                        enableDashline = records.isNotEmpty()
                    )
                    .padding(start = 28.dp),
                state = state,
            ) {
                items(
                    items = records,
                    key = { it.name },
                    itemContent = { element: ChartElement ->
                        BloodPressureChart2Element(
                            minRange = alignment.first,
                            maxRange = alignment.second,
                            dateHeight = dateHeight,
                            chartElement = element,
                            enableInfo = (element.name == selectedChartElement?.name),
                            onClick = {
                                selectedChartElement = element
                            }
                        )
                    }
                )
            }
        }
    }
}


/**
 * @param maxAlignment is the maximum value of the chart
 * @param minAlignment is the minimum value of chart, it is usually 0
 * @param numberLine is the number of dashed line that chart will draw
 * @param dateHeight is the minimum of height that text of date can be shown fully
 */
fun Modifier.drawChartBaseline(
    textMeasurer: TextMeasurer,
    maxAlignment: Float,
    minAlignment: Float,
    numberLine: Int,
    dateHeight: Dp,
    enableDashline: Boolean,
): Modifier = drawWithCache {
    val spacing: Float = (maxAlignment - minAlignment) / numberLine

    val lineCoordinates = (0..numberLine).map { index: Int ->
        minAlignment + index * spacing
    }

    val textCoordinates = lineCoordinates.map { number: Float ->
        textMeasurer.measure(text = number.toInt().toString(), style = customizedTextStyle(
            fontSize = 10
        ))
    }

    val maxValueWidth: Int = textCoordinates.maxOf { it.size.width }


    onDrawBehind {

        for (index in 0..numberLine) {
            val yCoordinate = lineCoordinates[index]
            val textLayoutResult = textCoordinates[index]

            val yOffset = (numberLine - index) * (size.height - dateHeight.toPx()) / numberLine

            drawText(
                textLayoutResult = textLayoutResult,
                color = PrimaryColor,
                topLeft = Offset(x = 0F, y = yOffset - textLayoutResult.size.height * 0.5F)
            )

            if(enableDashline){
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
private fun PreviewBloodPressureChart2Data() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .background(color = Color.DarkGray)
    ) {
        BloodPressureChart2(
            modifier = Modifier.matchParentSize(),
            records = ChartElement.getFakeElements().toImmutableList()
        )
    }
}

@Preview
@Composable
private fun PreviewBloodPressureChart2Empty() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .background(color = Background)
    ) {
        BloodPressureChart2(
            modifier = Modifier.matchParentSize(),
            records = persistentListOf()
        )
    }
}