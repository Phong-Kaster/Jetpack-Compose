package com.example.jetpack.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpack.R
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.LocalNavController
import com.example.jetpack.ui.fragment.home.component.HomeBottomSheet
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.IconColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body13
import com.example.jetpack.ui.theme.medium13
import com.example.jetpack.ui.fragment.tutorial.component.LocalTutorial
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.CenterColumn

@Composable
fun CoreBottomBar() {
    // For navigating to other destinations
    val navController = LocalNavController.current ?: rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var showBottomSheet by remember { mutableStateOf(false) }

    // For storing size of button that has tutorials
    val tutorial = LocalTutorial.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color = Background)
            .border(0.dp, Color.LightGray)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        listOf(Menu.Home, Menu.Insight).forEach { item ->
            BottomBarItem(
                selected = currentDestination?.hierarchy?.any { it.id == item.destinationId } == true,
                drawableId = item.drawableId,
                stringId = item.nameId,
                modifier = Modifier.weight(1f)
            ) {
                if (currentDestination?.id != item.homeDestinationId) {
                    navController.navigate(directions = item.directions)
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(48.dp)
                .clip(shape = CircleShape)
                .background(PrimaryColor)
                .onGloballyPositioned {
                    tutorial.addButtonSize = it.boundsInRoot()
                }
                .clickable { showBottomSheet = !showBottomSheet },
        ) {
            Icon(
                painterResource(id = R.drawable.ic_nazi_symbol),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = OppositePrimaryColor
            )
        }

        listOf(Menu.Article, Menu.Setting).forEach { item ->
            BottomBarItem(
                selected = currentDestination?.hierarchy?.any { it.id == item.destinationId } == true,
                drawableId = item.drawableId,
                stringId = item.nameId,
                modifier = Modifier.weight(1f)
            ) {
                if (currentDestination?.id != item.homeDestinationId) {
                    navController.navigate(directions = item.directions)
                }
            }
        }
    }


    HomeBottomSheet(
        enable = showBottomSheet,
        onDismissRequest = { showBottomSheet = false },
        openLanguage = {
            val destination = R.id.toLanguage
            navController.navigate(destination)
        },
        openDisclaimer = {
            val destination = R.id.toDisclaimer
            navController.navigate(destination)
        }
    )
}

@Composable
private fun BottomBarItem(
    selected: Boolean,
    @DrawableRes drawableId: Int,
    @StringRes stringId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    CenterColumn(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 35.dp),
                onClick = onClick
            )
            .padding(top = 4.dp, bottom = 12.dp),
        itemSpacing = 4.dp
    ) {
        Icon(
            painter = painterResource(drawableId),
            contentDescription = stringResource(id = stringId),
            modifier = Modifier.size(24.dp),
            tint = if (selected) PrimaryColor else IconColor,
        )

        Text(
            text = stringResource(stringId),
            style = if (!selected) body13 else medium13,
            color = if (selected) PrimaryColor else IconColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    ViewUtil.PreviewContent {
        CoreBottomBar()
    }
}