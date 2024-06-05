package com.example.jetpack.ui.fragment.article.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun AnimatedBorderCard() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.White)
            .borderWithAnimatedGradient(
                durationMillis = 2000,
                width = 5,
                backgroundColor = Color(0xFFF3F7FF)
            )
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_lightbulb),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "AnimatedBorderCard",
                style = customizedTextStyle(fontSize = 14, fontWeight = 500, color = Color.Black)
            )

            Text(
                text = stringResource(id = R.string.app_name),
                style = customizedTextStyle(fontSize = 14, fontWeight = 500, color = Color.Black)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAnimatedBorder() {
    ViewUtil.PreviewContent(modifier = Modifier.background(color = Color.White),
        content = {
            AnimatedBorderCard()
        })
}