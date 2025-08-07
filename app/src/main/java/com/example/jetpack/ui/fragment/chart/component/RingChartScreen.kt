package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.fragment.chart.chartcomponent.DonutChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.DonutChart2
import com.example.jetpack.ui.fragment.chart.chartcomponent.PieChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.RingChart
import com.example.jetpack.ui.fragment.chart.chartcomponent.RingChart2
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun RingChartScreen() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Top),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item(key = "Ring Chart",
            span = { GridItemSpan(2) }) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.ring_chart),
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                RingChart(data = ChartElement.getFakeElements(6), centerColor = Background)
            }
        }
        item(key = "RingChart2",
            span = { GridItemSpan(1) }) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${stringResource(R.string.ring_chart)} 2",
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                RingChart2(data = ChartElement.getFakeElements())
            }
        }


        item(key = "PieChart") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.pie_chart),
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                PieChart(
                    records = ChartElement.getFakeElements(4),
                    modifier = Modifier
                )
            }
        }

        item(key = "DonutChart",
            span = { GridItemSpan(2) }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.donut_chart),
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DonutChart2()
                    DonutChart()
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