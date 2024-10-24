package com.example.jetpack.ui.fragment.chart.chartcomponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.theme.customizedTextStyle

/**
 * [Learn Jetpack Compose Canvas Cubic and Quadratic Bezier And Its Usage](https://medium.com/mobile-app-development-publication/learn-jetpack-compose-canvas-cubic-and-quadratic-bezier-and-its-usage-96a4d9a7e3fb)
 *
 * [Plotting Gradient Bezier Trends with Jetpack Compose](https://medium.com/@kezhang404/either-compose-is-elegant-or-if-you-want-to-draw-something-with-an-android-view-you-have-to-7ce00dc7cc1)
 */
@Composable
fun WeatherHourlyChartElement(
    previousElement: ChartElement,
    element: ChartElement,
    maxTemperature: Float = 100f,
    minTemperature: Float = 0f,
    modifier: Modifier = Modifier
) {

    val textMeasurer = rememberTextMeasurer()

    val textToDraw by remember(element){ mutableStateOf(element.valueMax.toInt().toString()) }

    val style = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        background = Color.Transparent
    )

    val textLayoutResult = remember(textToDraw) { textMeasurer.measure(textToDraw, style) }
    val halfWidthTextLayout = remember(textLayoutResult) { textLayoutResult.size.width * 0.5f }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {},
        content = {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val path = Path()
                val halfWidthScreen = this.size.width*0.5f
                val spacing = size.height / maxTemperature

                val previousX = 0f
                val previousY = spacing * (maxTemperature - previousElement.valueMax)

                val currentX = size.width
                val currentY = spacing * (maxTemperature - element.valueMax)

                path.moveTo(x = previousX, y = previousY)
                path.cubicTo(
                    x1 = (previousX + currentX) * 0.5f,
                    y1 = previousY,
                    x2 = (previousX + currentX) * 0.5f,
                    y2 = currentY,
                    x3 = currentX,
                    y3 = currentY
                )

                drawPath(
                    path = path,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF9EFFFF),
                            Color(0xFF004BDC),
                            Color(0xFF2C3141),
                        )
                    ),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )

                val fillPath = android.graphics.Path(path.asAndroidPath())
                    .asComposePath()
                    .apply {
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)
                        close()
                    }
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF004BDC),
                            Color(0xFF9EFFFF),
                        )
                    ),
                )

                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = halfWidthScreen - halfWidthTextLayout,
                        y = currentY * 0.75f
                    ),
                )
            }

            Text(
                text = element.name,
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 600,
                    color = Color.White
                ),
            )
        },
    )
}

@Preview
@Composable
private fun Preview() {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
            .aspectRatio(16 / 10f)
            .padding(16.dp)
    ) {
        WeatherHourlyChartElement(
            previousElement = ChartElement.getFakeElement(),
            element = ChartElement.getFakeElement(),
            modifier = Modifier
        )
    }
}