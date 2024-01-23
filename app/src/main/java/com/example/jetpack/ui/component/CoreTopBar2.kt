package com.example.jetpack.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoreTopBar2(
    @DrawableRes icon: Int? = null,
    title: String? = stringResource(id = R.string.fake_title),
    onClick: () -> Unit = {},
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
                onClick()
            },
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = PrimaryColor)
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = icon ?: R.drawable.ic_back),
                contentDescription = null,
                tint = OppositePrimaryColor,
                modifier = Modifier.size(15.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = title ?: "",
            maxLines = 1,
            style = customizedTextStyle(
                fontSize = 16,
                fontWeight = 700
            ),
            color = PrimaryColor,
            modifier = Modifier.basicMarquee(Int.MAX_VALUE)
        )
    }
}

@Preview
@Composable
fun PreviewCoreTopBar2() {
    ViewUtil.PreviewContent {
        CoreTopBar2(
            icon = R.drawable.ic_back,
            title = stringResource(id = R.string.fake_title)
        )
    }
}