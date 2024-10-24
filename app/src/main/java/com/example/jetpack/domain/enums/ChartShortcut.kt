package com.example.jetpack.domain.enums

import androidx.annotation.StringRes
import com.example.jetpack.R

enum class ChartShortcut(
    @StringRes val text: Int
) {
    LineChart(text = R.string.line_chart),
    Component(text = R.string.component),
    RingChart(text = R.string.ring_chart),
    /*BarChart(text = R.string.bar_chart),
    ScatterPlotChart(text = R.string.line_chart),
    AreaChart(text = R.string.area_chart),
    RadarChart(text = R.string.radar_chart),*/
    AnalogueClock(text = R.string.analogue_clock),
    BubbleChart(text = R.string.bubble_chart),

}