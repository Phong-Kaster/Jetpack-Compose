package com.example.jetpack.ui.component

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.example.jetpack.ui.theme.medium18

@Composable
fun CoreTopBar(
    title: String? = null,
    @DrawableRes leftIcon: Int? = null,
    @ColorRes leftBackground: Color = LocalTheme.current.secondary,
    @DrawableRes rightIcon: Int? = null,
    @ColorRes rightBackground: Color =  LocalTheme.current.secondary,
    onClickLeft: () -> Unit = {},
    onClickRight: () -> Unit = {},
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
            .statusBarsPadding()
            .padding(16.dp),
    ) {
        if (title != null) {
            Text(
                text = title,
                style = medium18,
                color = LocalTheme.current.primary,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }


        if (leftIcon != null) {
            IconButton(
                onClick = {
                    onClickLeft()
                },
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = LocalTheme.current.primary)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = leftIcon),
                    contentDescription = null,
                    tint = leftBackground,
                    modifier = Modifier.size(15.dp),
                )
            }
        }


        if (rightIcon != null) {
            IconButton(
                onClick = {
                    onClickRight()
                },
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = LocalTheme.current.primary)
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = rightIcon),
                    contentDescription = null,
                    tint = rightBackground,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewBasicTopBarWithBackButton() {
    CoreTopBar(
        title = stringResource(id = R.string.fake_title),
        leftIcon = R.drawable.ic_back,
        rightIcon = R.drawable.ic_forward
    )
}