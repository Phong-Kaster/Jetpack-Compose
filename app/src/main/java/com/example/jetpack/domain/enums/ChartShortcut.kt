package com.example.jetpack.domain.enums

import androidx.annotation.StringRes
import com.example.jetpack.R

enum class ChartShortcut(
    @StringRes val text: Int
) {
    Component(text = R.string.component),
    LineChart(text = R.string.line_chart),
    RingChart(text = R.string.ring_chart),
    /*BarChart(text = R.string.bar_chart),
    ScatterPlotChart(text = R.string.line_chart),
    AreaChart(text = R.string.area_chart),
    RadarChart(text = R.string.radar_chart),*/
    AnalogueClock(text = R.string.analogue_clock),
    BubbleChart(text = R.string.bubble_chart),

}