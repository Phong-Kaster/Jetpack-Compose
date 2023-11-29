package com.example.jetpack.configuration

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.navigation.NavDirections
import com.example.jetpack.NavigationGraphDirections
import com.example.jetpack.R

enum class Menu (
    @StringRes val nameId: Int,
    @DrawableRes val drawableId: Int,
    @IdRes val destinationId: Int,
    val directions: NavDirections,
    @IdRes val homeDestinationId: Int,
) {
    Home(
        nameId = R.string.home,
        drawableId = R.drawable.ic_bottom_home,
        destinationId = R.id.homeFragment,
        directions = NavigationGraphDirections.toHome(),
        homeDestinationId = R.id.homeFragment,
    ),
    Insight(
        nameId = R.string.chart,
        drawableId = R.drawable.ic_bottom_insight,
        destinationId = R.id.insightFragment,
        directions = NavigationGraphDirections.toInsight(),
        homeDestinationId = R.id.insightFragment,
    ),
    Article(
        nameId = R.string.article,
        drawableId = R.drawable.ic_bottom_article,
        destinationId = R.id.articleFragment,
        directions = NavigationGraphDirections.toArticle(),
        homeDestinationId = R.id.articleFragment,
    ),
    Setting(
        nameId = R.string.setting,
        drawableId = R.drawable.ic_bottom_settings,
        destinationId = R.id.settingFragment,
        directions = NavigationGraphDirections.toSetting(),
        homeDestinationId = R.id.settingFragment,
    ),
}
