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
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.HomeShortcut
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun CoreTopBarWithHomeShortcut(
    @DrawableRes iconLeft: Int? = null,
    @DrawableRes iconRight: Int? = null,
    homeShortcut: HomeShortcut = HomeShortcut.AccuWeatherLocation,
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
            onClick = onLeftClick,
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1F)
        ) {
            Icon(
                painter = painterResource(id = homeShortcut.drawable),
                tint = LocalTheme.current.primary,
                contentDescription = "Icon",
                modifier = Modifier
            )

            Text(
                text = stringResource(id = homeShortcut.text),
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
private fun PreviewCoreTopBarWithHomeShortcut() {
    CoreTopBarWithHomeShortcut(
        iconLeft = R.drawable.ic_back,
    )
}