package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.header

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullTopBar(
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        modifier = modifier.statusBarsPadding(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = PrimaryColor
        ),
        actions = {
            IconButton(
                onBackClick,
                Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.icon),
                    tint = Color.Black
                )
            }
            
            // Empty space for content in the middle
            Modifier
                .weight(1f)
                .fillMaxSize()
            
            val shareContentDescription = stringResource(R.string.icon)
            IconButton(
                onShareClick,
                Modifier
                    .align(Alignment.CenterVertically)
                    .semantics { contentDescription = shareContentDescription }
            ) {
                Icon(
                    Icons.Filled.Share,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewFullTopBar() {
    FullTopBar(
        onShareClick = {},
        onBackClick = {},
    )
}