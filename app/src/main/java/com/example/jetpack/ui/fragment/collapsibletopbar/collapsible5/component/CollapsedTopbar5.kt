package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible5.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedTopbar5(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) = LargeTopAppBar(
    title = {
        Text(
            text = stringResource(R.string.app_name),
            style = customizedTextStyle(
                fontSize = 22,
                fontWeight = 800,
                color = LocalTheme.current.textColor
            ),
        )
    },
    actions = {
        Icon(
            imageVector = Icons.Rounded.Share,
            tint = LocalTheme.current.primary,
            contentDescription = "Icon",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = LocalTheme.current.background)
                .padding(3.dp)
                .clickable { onBack() }
                .size(20.dp)
        )
    },
    navigationIcon = {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            tint = LocalTheme.current.primary,
            contentDescription = "Icon",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = LocalTheme.current.background)
                .padding(3.dp)
                .clickable { onBack() }
                .size(20.dp)
        )
    },
    colors = TopAppBarDefaults.mediumTopAppBarColors(
        scrolledContainerColor = LocalTheme.current.secondary,
        titleContentColor = LocalTheme.current.textColor,
        containerColor = LocalTheme.current.secondary
    ),
    scrollBehavior = scrollBehavior,
    modifier = modifier
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewCollapsedTopbar() {
    CollapsedTopbar5(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        onBack = {},
        modifier = Modifier,
    )
}