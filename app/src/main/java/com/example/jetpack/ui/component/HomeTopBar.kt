package com.example.jetpack.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun HomeTopBar(
    name: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .statusBarsPadding()
            .padding(top = 16.dp)
    ) {
        Text(
            text = name,
            style = medium18,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewHomeTopBar() {
    ViewUtil.PreviewContent {
        HomeTopBar(name = stringResource(id = R.string.fake_title))
    }
}