package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.ChartShortcut
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun ChartTopBar(
    chosenChip: ChartShortcut,
    onChange: (ChartShortcut) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
    ) {
        HomeTopBar(name = stringResource(id = Menu.Insight.nameId))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = ChartShortcut.entries,
                itemContent = {
                    FilterChip(
                        selected = chosenChip == it,
                        onClick = { onChange(it) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.Transparent,
                            labelColor = Color.Blue,
                            iconColor = OppositePrimaryColor,
                            selectedContainerColor = PrimaryColor,
                            selectedLabelColor = OppositePrimaryColor,
                            disabledContainerColor = Color.Transparent,
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = true,
                            selectedBorderColor = PrimaryColor,

                        ),
                        label = {
                            Text(
                                text = stringResource(id = it.text),
                                style = customizedTextStyle(),
                                color = if (chosenChip == it) OppositePrimaryColor else PrimaryColor
                            )
                        },
                        leadingIcon =
                        if (chosenChip == it) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = stringResource(id = R.string.icon),
                                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                                    tint = OppositePrimaryColor
                                )
                            }
                        } else {
                            null
                        },
                    )
                })
        }
    }
}

@Preview
@Composable
fun PreviewChartTopBar() {
    ViewUtil.PreviewContent {
        ChartTopBar(chosenChip = ChartShortcut.LineChart)
    }
}