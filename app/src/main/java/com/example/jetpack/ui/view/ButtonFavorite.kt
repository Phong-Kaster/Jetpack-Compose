package com.example.jetpack.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.util.ViewUtil

@Composable
fun ButtonFavorite(
    onClick: () -> Unit,
    buttonColor: Color = Color(0xFFFFCC00)
) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.ThumbUp,
            contentDescription = stringResource(R.string.icon),
            tint = buttonColor
        )
    }
}

@Preview
@Composable
private fun PreviewFavoriteButton() {
    ViewUtil.PreviewContent() {
        ButtonFavorite(onClick = {})
    }
}