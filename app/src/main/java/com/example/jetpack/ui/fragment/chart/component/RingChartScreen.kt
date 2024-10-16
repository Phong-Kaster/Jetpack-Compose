package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.fragment.chart.chartcomponent.RingChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.RingChart2
import com.example.jetpack.ui.theme.Background

@Composable
fun RingChartScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        RingChart(data = ChartElement.getFakeElements(), centerColor = Background)
        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
        RingChart2(data = ChartElement.getFakeElements())
        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
    }
}

@Preview
@Composable
fun PreviewRingChartScreen() {
    RingChartScreen()
}