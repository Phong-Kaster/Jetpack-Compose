package com.example.jetpack.ui.fragment.chart

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.data.enums.ChartShortcut
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.chart.component.ChartTopBar
import com.example.jetpack.ui.fragment.chart.component.LineChartScreen
import com.example.jetpack.ui.fragment.chart.component.RingChartScreen
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.view.AnalogueClock
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
    var chosenChip: ChartShortcut by rememberSaveable { mutableStateOf(ChartShortcut.LineChart) }
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
                    ChartShortcut.RingChart -> RingChartScreen()
                }
            })
    }
}

@Preview
@Composable
fun PreviewInsightLayout() {
    InsightLayout()
}