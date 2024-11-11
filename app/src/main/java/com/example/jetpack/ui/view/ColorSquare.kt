package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun ColorSquare(
    backgroundTitle: String = "C20000",
    textTitle: String = "D9D9D9",
    backgroundColor: Color = Color(0xFFC20000),
    textColor: Color = Color(0xFFD9D9D9),
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.CenterVertically),
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(25.dp))
            .clickable { }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(25.dp)
            )
            .aspectRatio(1f)
    ) {
        Text(
            text = "${stringResource(R.string.background)}: #${backgroundTitle}",
            color = textColor,
            style = customizedTextStyle(
                fontSize = 14,
                fontWeight = 600,
            ),
            modifier = Modifier,
        )

        Text(
            text = "${stringResource(R.string.text)}: #${textTitle}",
            color = textColor,
            style = customizedTextStyle(
                fontSize = 14,
                fontWeight = 600,
            ),
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun PreviewColorSquare() {
    ColorSquare()
}