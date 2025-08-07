package com.example.jetpack.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun CoreTopBar2(
    @DrawableRes iconLeft: Int? = null,
    @DrawableRes iconRight: Int? = null,
    title: String? = stringResource(id = R.string.fake_title),
    titleArrangement: Arrangement.Horizontal = Arrangement.Start,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(color = Color.Transparent)
            .padding(16.dp)
    ) {
        IconButton(
            onClick = {
                onLeftClick()
            },
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = LocalTheme.current.primary)
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = iconLeft ?: R.drawable.ic_back),
                contentDescription = null,
                tint = LocalTheme.current.onPrimary,
                modifier = Modifier.size(15.dp)
            )
        }



        Row(
            horizontalArrangement = titleArrangement,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1F)
        ) {
            Text(
                text = title ?: "",
                maxLines = 1,
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 700
                ),
                color = LocalTheme.current.primary,
                modifier = Modifier
                    .basicMarquee(Int.MAX_VALUE)

            )
        }




        if (iconRight != null) {
            IconButton(
                onClick = { onRightClick() },
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = LocalTheme.current.primary)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconRight),
                    contentDescription = null,
                    tint = LocalTheme.current.onPrimary,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCoreTopBar2ArrangementLeft() {
    ViewUtil.PreviewContent {
        CoreTopBar2(
            iconLeft = R.drawable.ic_back,
            title = stringResource(id = R.string.fake_title)
        )
    }
}

@Preview
@Composable
fun PreviewCoreTopBar2ArrangementCenter() {
    ViewUtil.PreviewContent {
        CoreTopBar2(
            iconLeft = R.drawable.ic_back,
            title = stringResource(id = R.string.fake_title),
            titleArrangement = Arrangement.Center,
            iconRight = R.drawable.ic_arrow_forward_circle,
        )
    }
}


@Preview
@Composable
fun PreviewCoreTopBar2ArrangementRight() {
    ViewUtil.PreviewContent {
        CoreTopBar2(
            iconLeft = R.drawable.ic_back,
            title = stringResource(id = R.string.fake_title),
            titleArrangement = Arrangement.End,
            iconRight = R.drawable.ic_arrow_forward_circle,
        )
    }
}