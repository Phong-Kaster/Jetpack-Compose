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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.LocalNavController
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.IconColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body13
import com.example.jetpack.ui.theme.medium13
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.CenterColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar() {
    val coroutineScope = rememberCoroutineScope()
    val navController = LocalNavController.current ?: rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var showAddBottomSheet by remember { mutableStateOf(false) }
    val addBottomSheetState = rememberModalBottomSheetState()

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
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(48.dp)
                .clip(shape = CircleShape)
                .background(PrimaryColor)
                .clickable {
                    showAddBottomSheet = true
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White
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

    /*AddBottomSheet(
        showBottomSheet = showAddBottomSheet,
        onDismissRequest = {
            coroutineScope.launch {
                addBottomSheetState.hide()
                showAddBottomSheet = false
            }
        },
        sheetState = addBottomSheetState,
        onTrackBloodSugarClick = {
            coroutineScope.launch {
                addBottomSheetState.hide()
                showAddBottomSheet = false
            }
            navController.safeNavigate(AppGraphDirections.actionAddGlucose())
        },
        onTrackBloodPressureClick = {

            coroutineScope.launch {
                addBottomSheetState.hide()
                showAddBottomSheet = false
            }
            navController.safeNavigate(AppGraphDirections.toBloodPressureCreate(bloodPressureId = Constants.BLOOD_PRESSURE_ID_FOR_ADDING))
        },
        onConvertClick = {
            coroutineScope.launch {
                addBottomSheetState.hide()
                showAddBottomSheet = false
            }
            navController.safeNavigate(AppGraphDirections.actionBloodSugarConvert())
        }
    )*/
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
        BottomBar()
    }
}