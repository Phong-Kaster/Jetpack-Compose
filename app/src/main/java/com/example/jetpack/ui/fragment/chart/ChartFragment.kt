package com.example.jetpack.ui.fragment.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.fragment.chart.component.PieChart
import com.example.jetpack.ui.theme.Background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightFragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        InsightLayout()
    }
}

@Preview
@Composable
fun InsightLayout() {
    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Insight.nameId)) },
        bottomBar = { CoreBottomBar() },
        backgroundColor = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            PieChart(centerColor = Background)
        }
    }
}

@Preview
@Composable
fun PreviewInsightLayout() {
    InsightLayout()
}