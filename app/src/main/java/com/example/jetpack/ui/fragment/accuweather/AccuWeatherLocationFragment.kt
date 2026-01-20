package com.example.jetpack.ui.fragment.accuweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.network.dto.weather.LocationAuto
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.fragment.accuweather.component.ManageLocationLayoutForSearch
import com.example.jetpack.ui.fragment.accuweather.component.SearchBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.view.LoadingDialog
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.LogUtil
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
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
                LogUtil.logcat(message = "${it.LocalizedName}")
            },
            onBack = { safeNavigateUp() }
        )

        LoadingDialog(enable = viewModel.loading.collectAsState().value)
    }
}

@Composable
fun LocationLayout(
    locationAuto: List<LocationAuto> = listOf(),
    onSearch: (String) -> Unit = {},
    onClick: (LocationAuto) -> Unit = {},
    onBack: ()->Unit = {}
) {
    CoreLayout(
        topBar = {
            Column(modifier = Modifier.background(color = Background)) {
                CoreTopBar2(
                    onLeftClick = onBack,
                    title = stringResource(id = R.string.accu_weather)
                )

                SearchBar(
                    onSearchKeyword = { onSearch(it) },
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