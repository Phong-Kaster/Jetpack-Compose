package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.header

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.header.ToolbarState.Companion.isShown

@Composable
fun CombinableHeader(
    title: String = stringResource(id = R.string.app_name),
    toolbarState: ToolbarState,
    toolbarAlpha: () -> Float = { 1f },
    contentAlpha: () -> Float = { 1f },
) {
    if (toolbarState.isShown) {
        MinimizedTopBar(
            title = title,
            onBackClick = {},
            onShareClick = {},
            modifier = Modifier
                .alpha(toolbarAlpha())
        )
    } else {
        FullTopBar(
            onBackClick = {},
            onShareClick = {},
            modifier = Modifier
                .alpha(contentAlpha())
        )
    }
}

@Preview
@Composable
private fun PreviewCombinableHeaderShow() {
    CombinableHeader(
        toolbarState = ToolbarState.SHOWN,
    )
}

@Preview
@Composable
private fun PreviewCombinableHeaderHidden() {
    CombinableHeader(
        toolbarState = ToolbarState.HIDDEN,
    )
}