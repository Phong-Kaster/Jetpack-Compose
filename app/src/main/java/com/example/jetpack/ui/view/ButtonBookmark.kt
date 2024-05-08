package com.example.jetpack.ui.view

import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.util.ViewUtil

@Composable
fun ButtonBookmark(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color(0xFFFFCC00)
) {
    val clickLabel = stringResource(
        if (isBookmarked) R.string.unbookmark else R.string.bookmark
    )
    IconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier.semantics {
            // Use a custom click label that accessibility services can communicate to the user.
            // We only want to override the label, not the actual action, so for the action we pass null.
            this.onClick(label = clickLabel, action = null)
        }
    ) {
        Icon(
            painter =
            if (isBookmarked)
                painterResource(id = R.drawable.ic_bookmark)
            else
                painterResource(id = R.drawable.ic_unbookmark),
            contentDescription = null, // handled by click label of parent
            tint = buttonColor
        )
    }
}

@Preview
@Composable
private fun PreviewButtonBookmark() {
    ViewUtil.PreviewContent() {
        ButtonBookmark(
            isBookmarked = true,
            onClick = {},
            modifier = Modifier
        )
    }
}