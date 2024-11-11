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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.domain.model.ChartElement
import com.example.jetpack.ui.fragment.chart.chartcomponent.BarChart
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun BarChartScreen() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item(key = "BarChart") {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.bar_chart),
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    )
                )
                BarChart(records = ChartElement.getFakeElements(), modifier = Modifier)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBarChartScreen() {
    BarChartScreen()
}