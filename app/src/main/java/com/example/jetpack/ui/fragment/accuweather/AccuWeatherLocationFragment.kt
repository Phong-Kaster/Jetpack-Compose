package com.example.jetpack.ui.fragment.accuweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.network.dto.LocationAuto
import com.example.jetpack.ui.fragment.accuweather.component.ManageLocationLayoutForSearch
import com.example.jetpack.ui.fragment.accuweather.component.SearchBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.AppUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccuWeatherLocationFragment : CoreFragment() {

    private val viewModel: AccuWeatherViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        LocationLayout(
            locationAuto = viewModel.locationAuto.collectAsState().value,
            onSearch = {
                viewModel.searchAutocomplete(keyword = it)
            },
            onClick = {
                AppUtil.logcat(message = "${it.LocalizedName}")
            }
        )
    }
}

@Composable
fun LocationLayout(
    locationAuto: List<LocationAuto> = listOf(),
    onSearch: (String) -> Unit = {},
    onClick: (LocationAuto) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    CoreLayout(
        topBar = {
            Column(modifier = Modifier.background(color = Background)) {
                SearchBar(
                    queryValue = query,
                    onSearch = { onSearch(it) },
                )
            }

        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(16.dp)
            ) {
                ManageLocationLayoutForSearch(
                    visible = true,
                    locations = locationAuto,
                    showLoading = false,
                    onClick = onClick
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewLocationLayout() {
    LocationLayout()
}