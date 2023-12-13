package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.database.model.ChartElement
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil
import kotlin.math.roundToInt

private val chartHeight = 400.dp
private const val chartElementCircle: Float = 10F
private val padding = 30.dp

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: List<ChartElement>
) {
    val maximum by remember(data) { mutableFloatStateOf(data.maxOf { it.value }) }
    val milestone = listOf(1F, 0.75F, 0.5F, 0.25F, 0F)

    val textMeasurer = rememberTextMeasurer()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .border(width = 0.1.dp, color = PrimaryColor)
            .padding(vertical = 16.dp)
    ) {
        // draw Y-AXIS
        /*Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp)
                .padding(bottom = padding * 0.5F)
        ) {
            milestone.forEach {
                val milestoneText = maximum * it
                Milestone(
                    text = "${milestoneText.roundToInt()}",
                    style =
                        customizedTextStyle(fontWeight = 700, fontSize = 14)
                        .copy(color = PrimaryColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                )
            }
        }*/

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
                            style = customizedTextStyle(fontWeight = 400, fontSize = 12).copy(color = PrimaryColor),
                            topLeft = Offset(
                                x = size.width * 0.5F - textMeasurer.measure(text).size.width * 0.5F,
                                y = (maximum * it) * spacing - (padding*0.45F).toPx()
                            )
                        )
                    }
                })

            Spacer(modifier = Modifier.height(padding/2F))
            Text(
                text = "X",
                style = customizedTextStyle(fontSize = 12, fontWeight = 400),
                color = Color.Transparent,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(
                items = data,
                key = { index: Int, item: ChartElement -> item.name },
                itemContent = { index: Int, item: ChartElement ->

                    val element = item
                    val nextElement = data.getOrNull(index = index + 1)

                    LineChartElement(
                        maximum = maximum,
                        element = element,
                        nextElement = nextElement
                    )
                }
            )
        }

    }
}

@Composable
fun LineChartElement(
    maximum: Float,
    element: ChartElement,
    nextElement: ChartElement?
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .width(padding*2F)
                .weight(1F),
            onDraw = {
                val spacing = size.height / maximum

                val x = size.width * 0.5F
                val y = (maximum - element.value) * spacing

                drawCircle(
                    color = PrimaryColor,
                    style = Stroke(width = (chartElementCircle * 0.35F).dp.toPx()),
                    radius = chartElementCircle,
                    center = Offset(x = x, y = y - spacing)
                )

                if (nextElement != null) {
                    val yNextElement =
                        (maximum - nextElement.value) * spacing
                    drawLine(
                        color = PrimaryColor,
                        start = Offset(x = size.width * 0.5F, y = y),
                        end = Offset(x = size.width * 0.5F + size.width, y = yNextElement - spacing)
                    )
                }
            })
        Spacer(modifier = Modifier.height(padding/2F))
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