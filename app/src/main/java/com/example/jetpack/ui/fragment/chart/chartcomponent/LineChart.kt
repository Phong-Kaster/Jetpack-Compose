package com.example.jetpack.ui.fragment.chart.chartcomponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.data.model.ChartElement
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil
import kotlin.math.roundToInt

private val chartHeight = 200.dp
private const val chartElementCircle: Float = 8F
private val padding = 30.dp

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: List<ChartElement>
) {
    // define chart
    val maximumInData by remember(data) { mutableFloatStateOf(data.maxOf { it.value }) }
    val maximum = maximumInData * 1.2F
    val milestone = listOf(1F, 0.75F, 0.5F, 0.25F, 0F)
    val textMeasurer = rememberTextMeasurer()
    val state = rememberLazyListState()


    // Automatically scroll to the latest item
    LaunchedEffect(key1 = data) {
        state.animateScrollToItem(
            index = data.size,
            scrollOffset = data.size - 3
        )
    }

    var chosenElement by remember { mutableStateOf(data[data.size - 1]) }

    // Chart
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .padding(vertical = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(padding)
                    .weight(1F),
                onDraw = {
                    val spacing = size.height / maximum

                    milestone.forEach {
                        val reverse = maximum - it * maximum
                        val text = (reverse).roundToInt().toString()
                        drawText(
                            textMeasurer = textMeasurer,
                            text = text,
                            style = customizedTextStyle(
                                fontWeight = 400,
                                fontSize = 12
                            ).copy(color = PrimaryColor),
                            topLeft = Offset(
                                x = size.width * 0.5F - textMeasurer.measure(text).size.width * 0.5F,
                                y = (maximum * it) * spacing - (padding * 0.45F).toPx()
                            )
                        )
                    }
                })

            Spacer(modifier = Modifier.height(padding / 2F))
            Text(
                text = "X",
                style = customizedTextStyle(fontSize = 12, fontWeight = 400),
                color = Color.Transparent,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = state
        ) {
            itemsIndexed(
                items = data,
                key = { index: Int, item: ChartElement -> item.name },
                itemContent = { index: Int, item: ChartElement ->

                    val element = item
                    val nextElement = data.getOrNull(index = index + 1)

                    LineChartElement(
                        enable = chosenElement.value == item.value,
                        maximum = maximum,
                        element = element,
                        nextElement = nextElement,
                        onClick = {
                            chosenElement = it
                        }
                    )
                }
            )
        }

    }
}

@Composable
fun LineChartElement(
    enable: Boolean,
    maximum: Float,
    element: ChartElement,
    nextElement: ChartElement?,
    onClick: (ChartElement) -> Unit = {}
) {
    val textMeasurer = rememberTextMeasurer()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .width(padding * 2F)
                .weight(1F)
                .clickable(
                    indication = rememberRipple(bounded = true, color = Color.Blue),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onClick(element) }),
            onDraw = {
                val spacing = size.height / maximum

                val x = size.width * 0.5F
                val y = (maximum - element.value) * spacing

                drawCircle(
                    color = PrimaryColor,
                    /*style = Stroke(width = (chartElementCircle * 0.35F).dp.toPx()),*/
                    radius = chartElementCircle,
                    center = Offset(x = x, y = y)
                )

                if (enable) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = element.value.toInt().toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = PrimaryColor,
                            background = Color.Transparent
                        ),
                        topLeft = Offset(
                            x = center.x - textMeasurer.measure(
                                text = element.value.toInt().toString()
                            ).size.width / 2F,
                            y = y - size.height * 0.15F
                        )
                    )
                }


                if (nextElement != null) {
                    val yNextElement =
                        (maximum - nextElement.value) * spacing
                    drawLine(
                        color = PrimaryColor,
                        start = Offset(x = size.width * 0.5F, y = y),
                        end = Offset(x = size.width * 0.5F + size.width, y = yNextElement)
                    )
                }
            })
        Spacer(modifier = Modifier.height(padding / 2F))
        Text(
            text = element.name,
            style = customizedTextStyle(fontSize = 12, fontWeight = 400),
            color = PrimaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun PreviewLineChart() {
    ViewUtil.PreviewContent {
        LineChart(data = ChartElement.getFakeData())
    }
}