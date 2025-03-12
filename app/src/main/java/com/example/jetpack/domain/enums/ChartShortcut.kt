package com.example.jetpack.domain.enums

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R

enum class ChartShortcut(
    @StringRes val text: Int
) {
    Calendar(text = R.string.calendar),
    LineChart(text = R.string.line_chart),
    Component(text = R.string.component),
    RingChart(text = R.string.ring_chart),
    BarChart(text = R.string.bar_chart),
    //ScatterPlotChart(text = R.string.line_chart),
    AreaChart(text = R.string.area_chart),
    //RadarChart(text = R.string.radar_chart),
    AnalogueClock(text = R.string.analogue_clock),
    //BubbleChart(text = R.string.bubble_chart),
    ColourScreen(text = R.string.colour),
    TestScreen(text = R.string.colour),
}

@Preview
@Composable
private fun Preview() {
    Text(
        text = stringResource(R.string.calendar)
    )
}