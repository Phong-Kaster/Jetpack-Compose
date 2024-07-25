package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.fragment.chart.chartcomponent.BloodPressureChart2
import com.example.jetpack.ui.fragment.chart.chartcomponent.LineChart
import kotlinx.collections.immutable.toImmutableList

@Composable
fun LineChartScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LineChart(data = ChartElement.getFakeElements())
        Divider(modifier = Modifier.padding(vertical = 10.dp))
        BloodPressureChart2(records = ChartElement.getFakeElements().toImmutableList(), modifier = Modifier)
    }
}

@Preview
@Composable
fun PreviewLineChartScreen() {
    LineChartScreen()
}