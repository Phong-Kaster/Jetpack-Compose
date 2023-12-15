package com.example.jetpack.ui.fragment.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.database.model.ChartElement
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.chart.component.LineChart
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.fragment.chart.component.RingChart
import com.example.jetpack.ui.fragment.chart.component.RingChart2
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightFragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        InsightLayout()
    }
}

@Composable
fun InsightLayout() {
    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Insight.nameId)) },
        bottomBar = { CoreBottomBar() },
        backgroundColor = Background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            RingChart(centerColor = Background, data = ChartElement.getFakeData())
            Divider(modifier = Modifier.padding(vertical = 16.dp), color = PrimaryColor)
            LineChart(data = ChartElement.getFakeData())
            Divider(modifier = Modifier.padding(vertical = 16.dp), color = PrimaryColor)
            RingChart2(data = ChartElement.getFakeData(), modifier = Modifier)
        }
    }
}

@Preview
@Composable
fun PreviewInsightLayout() {
    InsightLayout()
}