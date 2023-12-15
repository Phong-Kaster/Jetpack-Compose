package com.example.jetpack.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.util.NumberUtil.toRadian
import com.example.jetpack.util.NumberUtil.toRomanNumber
import com.example.jetpack.util.ViewUtil
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.cos
import kotlin.math.sin


/**
# To calculate the angle of the hour hand based on the position of the minute hand, follow these steps:
# 1. A full clock face represents 360 degrees and is divided into 12 hours, so each hour corresponds to 30 degrees (360/12).
# 2. The hour hand moves 1/12th of 30 degrees per minute, which is 0.5 degrees per minute.
# 3. The minute hand moves 360 degrees in an hour, or 6 degrees per minute.

# Using the position of the minute hand, you can determine how many minutes have passed since the last hour mark. These minutes will affect the position of the hour hand.

# For example, if the minute hand points at 15 minutes (1/4 of the clock face):
# - The minute hand has moved 15 * 6 = 90 degrees from the 12 o'clock mark.
# - The hour hand will have moved 15 * 0.5 = 7.5 degrees from the last hour mark.

# Provide specific details on the position of the minute hand to calculate the precise angle of the hour hand.
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalogueClock(
    modifier: Modifier = Modifier
) {
    var time by remember { mutableStateOf(LocalTime.now()) }
    val second by remember(time) { mutableIntStateOf(time.second) }
    val minute by remember(time) { mutableIntStateOf(time.minute) }
    val hour by remember(time) { mutableIntStateOf(time.hour) }

    var hourAngle by remember{ mutableFloatStateOf(0.0F) }
    val textMeasurer = rememberTextMeasurer()

    /**
     *  (- 90) | We minus 90 degree because 0 degree is at 3 o'clock
     * (hour * 30) | One hour equals 30 degree because 360 degree / 12h = 30 degree
     * (minute / 60F) * 30F |
     */
    LaunchedEffect(Unit) {
        while (true) {
            hourAngle = (minute / 60F) * 30F + (hour * 30) - 90F
            time = LocalTime.now()
            delay(1000)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = modifier.fillMaxSize(),
            onDraw =
            {
                /**
                 * (- 90) | We minus 90 degree because 0 degree is at 3 o'clock
                 * (minute * 6.0F) | one minute equals 6 degree because 360 degree / 60 minutes = 6 degree
                 */
                val radius = size.width * 0.4F
                val minuteAngle = (second / 60F) * 6F - 90F + (minute * 6F)
                val secondAngle = (second / 60F) * 360F - 90F


                // draw Circle
                drawCircle(
                    color = PrimaryColor,
                    radius = radius,
                    center = Offset(x = center.x, y = center.y),
                    style = Stroke(width = 10F)
                )

                // draw central point
                drawCircle(
                    color = PrimaryColor,
                    radius = 5F,
                    center = Offset(x = center.x, y = center.y)
                )


                // draw hour hand
                drawLine(
                    color = PrimaryColor,
                    strokeWidth = radius * .02f,
                    cap = StrokeCap.Square,
                    start = Offset(x = center.x, y = center.y),
                    end = Offset(
                        x = cos(hourAngle.toRadian()).toFloat() * radius * 0.35F + center.x,
                        y = sin(hourAngle.toRadian()).toFloat() * radius * 0.35F + center.y
                    ),
                )

                // draw minute hand
                drawLine(
                    color = PrimaryColor,
                    strokeWidth = radius * 0.02f,
                    cap = StrokeCap.Square,
                    start = Offset(x = center.x, y = center.y),
                    end = Offset(
                        x = cos(minuteAngle.toRadian()).toFloat() * radius * 0.70F + center.x,
                        y = sin(minuteAngle.toRadian()).toFloat() * radius * 0.70F + center.y
                    ),
                )


                // draw second hand
                drawLine(
                    color = Color.Cyan,
                    strokeWidth = radius * .02f,
                    cap = StrokeCap.Square,
                    start = Offset(x = center.x, y = center.y),
                    end = Offset(
                        x = cos(secondAngle.toRadian()).toFloat() * radius * 0.70F + center.x,
                        y = sin(secondAngle.toRadian()).toFloat() * radius * 0.70F + center.y
                    ),
                )


                // draw 12 roman-styled hours
                for (minuteIndex in 0..60) {
                    if (minuteIndex == 0) { continue }
                    val minuteAngleRoman = -90.0F + (minuteIndex * 6.0F)
                    if (minuteIndex % 5 == 0) {
                        val text = (minuteIndex / 5).toRomanNumber()
                        val textLayout = textMeasurer.measure(text = text)
                        drawText(
                            textMeasurer = textMeasurer,
                            text = text,
                            style = TextStyle(color = PrimaryColor),
                            topLeft = Offset(
                                x = cos(minuteAngleRoman.toRadian()).toFloat() * (radius * 0.85F) + center.x - textLayout.size.width * 0.5F,
                                y = sin(minuteAngleRoman.toRadian()).toFloat() * (radius * 0.85F) + center.y - textLayout.size.height * 0.5F
                            )
                        )
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewAnalogueClock() {
    ViewUtil.PreviewContent {
        AnalogueClock()
    }
}