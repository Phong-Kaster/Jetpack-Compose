package com.example.jetpack.ui.fragment.accuweather.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.network.dto.LocationAuto
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun ManageLocationLayoutForSearch(
    visible: Boolean,
    locations: List<LocationAuto>?,
    showLoading: Boolean,
    onClick: (LocationAuto) -> Unit = {}
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(initialAlpha = 0.4f),
        exit = fadeOut(animationSpec = tween(durationMillis = 250))
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            /*LoadingAnimationCircular(visible = showLoading)*/

            if (locations != null && locations.isEmpty()) {

            } else if (!locations.isNullOrEmpty()) {
                LazyColumn {
                    items(
                        items = locations,
                        itemContent = {
                            AutocompleteLocationItem(
                                location = it,
                                onClick = onClick
                            )
                        })
                }
            }
        }
    }
}

@Composable
fun AutocompleteLocationItem(
    location: LocationAuto,
    onClick: (LocationAuto) -> Unit
) {
    val name by remember(location) {
        derivedStateOf {
            val districtName = location.LocalizedName
            val cityName = location.AdministrativeArea?.LocalizedName
            val countryName = location.Country?.LocalizedName
            if (districtName.equals(cityName)) "$districtName, $countryName" else "$districtName, $cityName, $countryName"
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = name,
            color = PrimaryColor,
            style = customizedTextStyle(fontWeight = 400, fontSize = 14),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(location) }
                .padding(vertical = 18.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color(0xFF363636))
        )
    }
}

@Preview
@Composable
fun PreviewManageLocationLayoutForSearch() {
    ManageLocationLayoutForSearch(
        visible = true,
        locations = LocationAuto.getFakeLocationAPI(),
        showLoading = true,
        onClick = {}
    )
}