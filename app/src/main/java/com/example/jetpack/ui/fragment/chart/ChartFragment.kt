package com.example.jetpack.ui.fragment.chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.database.enums.ChartShortcut
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.chart.component.ChartTopBar
import com.example.jetpack.ui.fragment.chart.component.LineChartScreen
import com.example.jetpack.ui.fragment.chart.component.RingChartScreen
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.view.AnalogueClock
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightFragment : CoreFragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        InsightLayout()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InsightLayout() {
    var chosenChip: ChartShortcut by remember { mutableStateOf(ChartShortcut.AnalogueClock) }
    CoreLayout(
        topBar = {
            ChartTopBar(
                chosenChip = chosenChip,
                onChange = { chosenChip = it }
            )
        },
        bottomBar = { CoreBottomBar() },
        backgroundColor = Background
    ) {
        AnimatedContent(
            targetState = chosenChip,
            label = stringResource(id = R.string.fake_title),
            content = { chosenChip ->
                when (chosenChip) {
                    ChartShortcut.AnalogueClock -> AnalogueClock()
                    ChartShortcut.LineChart -> LineChartScreen()
                    ChartShortcut.BarChart -> {}
                    ChartShortcut.AreaChart -> {}
                    ChartShortcut.RadarChart -> {}
                    ChartShortcut.RingChart -> RingChartScreen()
                    ChartShortcut.ScatterPlotChart -> {}
                }
            })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewInsightLayout() {
    InsightLayout()
}