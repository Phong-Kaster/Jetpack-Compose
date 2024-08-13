package com.example.jetpack.ui.fragment.article.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.DateUtil
import com.example.jetpack.util.DateUtil.formatWithPattern
import java.util.Calendar
import java.util.Date
import kotlin.math.cos
import kotlin.math.sin

/**
 * Công thức tổng quát:
 *
 * Nếu ta đặt:
 *
 * O(a, b): Tọa độ tâm của đường tròn
 * R: Bán kính của đường tròn
 * θ: Góc tạo bởi bán kính đi qua điểm M và trục hoành (góc này có thể âm hoặc dương tùy thuộc vào hướng của cung tròn)
 * Thì tọa độ của một điểm bất kỳ P(x, y) trên cung tròn có thể tính bằng công thức:
 *
 * + x = a + R * cos(θ)
 * + y = b + R * sin(θ)
 *
 * - Lưu ý:
 *
 * + Góc θ: Góc này thường được đo bằng radian. Để chuyển đổi từ độ sang radian, ta dùng công thức: radian = độ * π / 180.
 * + Hướng của cung tròn: Nếu cung tròn quay ngược chiều kim đồng hồ (hướng dương), góc θ sẽ tăng dần. Ngược lại, nếu cung tròn quay theo chiều kim đồng hồ (hướng âm), góc θ sẽ giảm dần.
 * + Điểm M: Điểm M sẽ xác định một giá trị góc θ cụ thể. Từ đó, ta có thể tính được tọa độ của các điểm khác trên cung tròn bằng cách thay đổi giá trị của góc θ.
 */
@Composable
fun WeatherSunrise(
    modifier: Modifier = Modifier
) {
    // sunrise
    val calendarSunrise = Calendar.getInstance()
    calendarSunrise.time = Date()
    calendarSunrise.set(Calendar.HOUR_OF_DAY, 6)
    calendarSunrise.set(Calendar.MINUTE, 0)
    calendarSunrise.set(Calendar.SECOND, 0)
    calendarSunrise.time

    // sunset
    val calendarSunset = Calendar.getInstance()
    calendarSunset.time = Date()
    calendarSunset.set(Calendar.HOUR_OF_DAY, 6)
    calendarSunset.set(Calendar.MINUTE, 15)
    calendarSunset.set(Calendar.SECOND, 30)
    calendarSunset.time


    val vector = ImageVector.vectorResource(id = R.drawable.ic_sunny)
    val painter = rememberVectorPainter(image = vector)


    val diameter = 200
    //val percent = 0.1// the sun rise 55% of circular arc
    val percent = DateUtil.calculatePercent(sunrise = calendarSunrise.time, sunset = calendarSunset.time)
    val canvasPercent = 1 - percent // because the canvas start from top left corner
    val radian = -(canvasPercent * 180) * (Math.PI / 180) // we places minus because the canvas start from top left corner



    Column(modifier = modifier.fillMaxWidth()) {
        // Moon Phase
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(
                    color = Color.White.copy(alpha = 0.3f)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {

            // Draw semi circle
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Text(
                        text = calendarSunrise.time.formatWithPattern(DateUtil.PATTERN_hh_mm_aa),
                        style = customizedTextStyle(fontSize = 14, fontWeight = 600),
                        color = Color.White,
                        modifier = Modifier,
                    )

                    Text(
                        text = "Sunrise",
                        style = customizedTextStyle(fontSize = 14, fontWeight = 400),
                        color = Color.White,
                        modifier = Modifier
                    )
                }


                Canvas(
                    modifier = Modifier
                        .width(diameter.dp)
                        .height((diameter * 0.5f).dp)
                ) {
                    val radius = size.width * 0.5
                    val centerPoint = Offset(x = size.width * 0.5f, y = size.height)
                    val sunX =
                        centerPoint.x + radius * cos(radian) - painter.intrinsicSize.width * 0.25f
                    val sunY =
                        centerPoint.y + radius * sin(radian) - painter.intrinsicSize.height * 0.3f
                    val startPointArc = Offset(x = 0f, y = size.height * 0f)

                    // Central Point of Circle
                    //drawCircle(color = Color.Red, radius = 4f, center = centerPoint)

                    drawArc(
                        color = Color.White,
                        startAngle = -180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = startPointArc,
                        size = Size(size.width, size.height * 2),
                        style = Stroke(
                            width = 10f,
                            cap = StrokeCap.Square,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                        )
                    )

                    // sun path with Yellow
                    drawArc(
                        color = Color.Yellow,
                        startAngle = -180f,
                        sweepAngle = (180f * percent).toFloat(),
                        useCenter = false,
                        size = Size(size.width, size.height * 2),
                        topLeft = startPointArc,
                        style = Stroke(
                            width = 10f,
                            cap = StrokeCap.Square,
                        ),
                    )

                    // draw sun
                    translate(
                        left = sunX.toFloat(),
                        top = sunY.toFloat()
                    ) {
                        with(painter) {
                            draw(painter.intrinsicSize * 0.5f)
                        }
                    }
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Text(
                        text = calendarSunset.time.formatWithPattern(DateUtil.PATTERN_hh_mm_aa),
                        style = customizedTextStyle(fontSize = 14, fontWeight = 600),
                        color = Color.White,
                        modifier = Modifier,
                    )

                    Text(
                        text = "Sunrise",
                        style = customizedTextStyle(fontSize = 14, fontWeight = 400),
                        color = Color.White,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherOverall() {
    WeatherSunrise()
}