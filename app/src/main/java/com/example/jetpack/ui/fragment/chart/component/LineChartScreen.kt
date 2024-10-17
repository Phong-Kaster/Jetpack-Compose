package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.fragment.chart.chartcomponent.BloodPressureChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.CurvyLineChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.LineChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.WeatherHourlyChart
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlinx.collections.immutable.toImmutableList

@Composable
fun LineChartScreen() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item(key = "WeatherHourlyChart") {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "WeatherHourlyChart",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                WeatherHourlyChart(
                    records = ChartElement.getFakeElements().toImmutableList(),
                    modifier = Modifier
                )
            }
        }

        item(key = "CurvyLineChart") {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "CurvyLineChart",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                CurvyLineChart(
                    records = ChartElement.getFakeElements().toImmutableList(),
                    modifier = Modifier
                )
            }
        }

        item(key = "LineChart") {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Line Chart",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                LineChart(data = ChartElement.getFakeElements())
            }
        }


        item(key = "BloodPressureChart") {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Blood Pressure Chart",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                BloodPressureChart(
                    records = ChartElement.getFakeElements().toImmutableList(),
                    modifier = Modifier
                )
            }
        }


    }
}

@Preview
@Composable
fun PreviewLineChartScreen() {
    LineChartScreen()
}