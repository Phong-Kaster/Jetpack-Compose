package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.fragment.chart.chartcomponent.GapPieChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.GapPieChart2
import com.example.jetpack.ui.fragment.chart.chartcomponent.RingChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.RingChart2
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun RingChartScreen() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Top),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item(key = "RingChart") {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Ring Chart",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                RingChart(data = ChartElement.getFakeElements(), centerColor = Background)
            }
        }
        item(key = "RingChart2") {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Ring Chart 2",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                RingChart2(data = ChartElement.getFakeElements())
            }
        }

        item(key = "GapPieChart") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Gap Pie Chart",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    GapPieChart()
                    GapPieChart2()
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewRingChartScreen() {
    RingChartScreen()
}