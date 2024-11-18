package com.example.jetpack.ui.fragment.collapsibletopbar.collapsibletopbar3.header

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun MinimizedTopBar(
    title: String = stringResource(id = R.string.app_name),
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
            Text(
                text = title,
                style = customizedTextStyle(),
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                // As title in TopAppBar has extra inset on the left, need to do this: b/158829169
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
            val shareContentDescription = stringResource(R.string.icon)
            IconButton(
                onShareClick,
                Modifier
                    .align(Alignment.CenterVertically)
                    // Semantics in parent due to https://issuetracker.google.com/184825850
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
private fun PreviewExtendedHeader() {
    MinimizedTopBar(
        onShareClick = {},
        onBackClick = {},
    )
}