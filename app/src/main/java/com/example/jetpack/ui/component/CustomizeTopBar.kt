package com.example.jetpack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun CustomizeTopBar(
    title: String,
    leftContent: @Composable () -> Unit = {},
    rightContent: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
    ) {
        Text(
            text = title,
            style = customizedTextStyle(
                fontSize = 20,
                fontWeight = 600,
                color = Color(0xFF1E3F24)
            ),
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
        CustomizeTopBar(
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