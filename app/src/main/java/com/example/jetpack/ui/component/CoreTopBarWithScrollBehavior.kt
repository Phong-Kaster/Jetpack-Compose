package com.example.jetpack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.util.ViewUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreTopBarWithScrollBehavior(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior?,
    content: @Composable () -> Unit,
    navigationIconContent: @Composable () -> Unit,
    backgroundColor: Color = Color(0xFF000000),
    iconColor: Color = Color(0xFFFFCC00),
) {
    CenterAlignedTopAppBar(
        title = { content() },
        navigationIcon = navigationIconContent,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
            navigationIconContentColor = iconColor,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = PrimaryColor
        ),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewCoreTopBarWithScrollBehavior() {
    ViewUtil.PreviewContent {
        CoreTopBarWithScrollBehavior(
            modifier = Modifier,
            scrollBehavior = null,
            navigationIconContent = {
                IconButton(onClick = {}, content = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.fake_message),
                        tint = PrimaryColor
                    )
                })
            },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(color = Background)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_nazi_symbol),
                        contentDescription = null,
                        tint = PrimaryColor,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(36.dp)
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.labelLarge,
                        color = PrimaryColor,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        )
    }

}