package com.example.jetpack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.medium18
import com.example.jetpack.util.ViewUtil

@Composable
fun BasicTopBar(
    title: String,
    leftContent: @Composable () -> Unit = {},
    rightContent: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Background)
            .statusBarsPadding()
            .padding(16.dp)
            .padding(top = 16.dp),
    ) {
        Text(
            text = title,
            style = medium18,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
        )

        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            leftContent()
        }

        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            rightContent()
        }
    }
}

@Preview
@Composable
fun PreviewBasicTopBar() {
    ViewUtil.PreviewContent {
        BasicTopBar(
            title = stringResource(id = R.string.fake_title),
            leftContent = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            rightContent = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
}