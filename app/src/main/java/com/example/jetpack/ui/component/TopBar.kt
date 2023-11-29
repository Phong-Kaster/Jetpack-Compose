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
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.medium18

@Composable
fun BasicTopBarWithBackButton(
    title: String = stringResource(id = R.string.fake_title),
    @DrawableRes leftIcon: Int = R.drawable.ic_back,
    @ColorRes leftBackground: Color = OppositePrimaryColor,
    @DrawableRes rightIcon: Int = R.drawable.ic_forward,
    @ColorRes rightBackground: Color = OppositePrimaryColor,
    onClickLeft: () -> Unit = {},
    onClickRight: () -> Unit = {},
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Background)
            .statusBarsPadding()
            .padding(16.dp),
    ) {
        Text(
            text = title,
            style = medium18,
            color = PrimaryColor,
            modifier = Modifier
                .align(Alignment.Center)
        )

        IconButton(
            onClick = {
                onClickLeft()
            },
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = PrimaryColor)
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = leftIcon),
                contentDescription = null,
                tint = leftBackground,
                modifier = Modifier.size(15.dp)
            )
        }

        IconButton(
            onClick = {
                onClickRight()
            },
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = PrimaryColor)
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

@Preview
@Composable
fun PreviewBasicTopBarWithBackButton() {
    BasicTopBarWithBackButton()
}