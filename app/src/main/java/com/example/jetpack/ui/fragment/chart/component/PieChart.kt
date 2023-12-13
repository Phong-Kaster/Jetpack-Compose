@file:Suppress("AnimateAsStateLabel")

package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.database.model.ChartElement
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
import com.example.jetpack.ui.theme.animationSpecFloat
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NumberUtil.toPercentage
import com.example.jetpack.util.ViewUtil

private val chartSize: Dp = 150.dp
private val innerChartSize: Dp = chartSize * 0.67F
private const val filledAngle = 360F // A filled angle is an angle equal to 360° (entire circle)
private val colors = listOf<Color>(
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
 * Language Object
 * 1.@StringRes name: Int
 * 2.value: Float
 */
/**
 * @param centerColor is the color of inner circle
 * @param data is the map that contains Type<String, Float> with
 * String is the name and Float is the value
 *
 *Note: prefer using immutable list
 */
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    centerColor: Color = Color.DarkGray,
    data: List<ChartElement>,
) {
    /**
     * Note:
     * val PERCENT_ANIMATION by animateFloatAsState(targetValue = TARGET_VALUE, animationSpec = animationSpecFloat)
     * */


    // Total is the sum of entire data
    val total: Float by remember(data) {
        derivedStateOf {
            data.map { it -> it.value }.sum()
        }
    }

    // Percent List is a list that contains percent(%) for every single element in data
    val percentList by remember(data) {
        derivedStateOf {
            data.map {
                it.value / total
            }
        }
    }

    // Percent Target Values is a list that hold TARGET VALUES in Animation ( TARGET_VALUE )
    val percentTargetValues = remember {
        mutableStateListOf<Float>().apply {
            percentList.forEach {
                add(0F)
            }
        }
    }

    // Percent Animations is a list represents for entire animations ( PERCENT_ANIMATION )
    val percentAnimations = percentTargetValues.map {
        animateFloatAsState(targetValue = it, animationSpec = animationSpecFloat)
    }


    // Set value in order to enable animation
    LaunchedEffect(key1 = data) {
        for (index in percentTargetValues.indices) {
            percentTargetValues[index] = percentList[index]
        }
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(chartSize),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(chartSize)
                .clip(shape = CircleShape)
                .background(color = Color.Transparent)
                .drawBehind {
                    var startAngle = -90F
                    percentAnimations.forEachIndexed { index, animValue ->
                        drawArc(
                            color = colors[index % colors.size],
                            startAngle = startAngle,
                            sweepAngle = animValue.value * filledAngle,
                            useCenter = true
                        )

                        startAngle += animValue.value * filledAngle
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .size(innerChartSize)
                    .clip(shape = CircleShape)
                    .background(color = centerColor)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 5.dp)
        ) {
            data.forEachIndexed { index, element ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${element.name} (${percentTargetValues[index].toPercentage()} %)",
                        color = colors[index % colors.size],
                        style = customizedTextStyle(fontSize = 12, fontWeight = 400)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPieChart() {
    ViewUtil.PreviewContent {
        PieChart( data = ChartElement.getFakeData())
    }
}