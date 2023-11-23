package com.example.jetpack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.medium18

@Composable
fun BasicTopBarWithBackButton(
    title: String,
    rightContent: @Composable () -> Unit = {},
    onBackClick: () -> Unit = {},
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
                onBackClick()
            },
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = PrimaryColor)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = Color.White,
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            rightContent()
        }
    }
}

@Preview
@Composable
fun PreviewBasicTopBarWithBackButton() {
    BasicTopBarWithBackButton(
        title = stringResource(id = R.string.fake_title),
        onBackClick = {},
        rightContent = {
            Icon(
                imageVector = Icons.Rounded.Check,
                tint = PrimaryColor,
                contentDescription = null
            )
        }

    )
}