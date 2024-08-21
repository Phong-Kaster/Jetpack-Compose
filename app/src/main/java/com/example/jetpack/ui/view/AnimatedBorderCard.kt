package com.example.jetpack.ui.view

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
import com.example.jetpack.ui.theme.Background2
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun AnimatedBorderCard() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(25.dp))
            .borderWithAnimatedGradient(
                width = 5.dp,
                shape = RoundedCornerShape(25.dp),
                colors = listOf(
                    Color(0xFF004BDC),
                    Color(0xFF004BDC),
                    Color(0xFF9EFFFF),
                    Color(0xFF9EFFFF),
                    Color(0xFF9EFFFF),
                    Color(0xFF9EFFFF),
                    Color(0xFF004BDC),
                    Color(0xFF004BDC)
                ),
            )
            .background(color = Background2, shape = RoundedCornerShape(25.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
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
                style = customizedTextStyle(fontSize = 14, fontWeight = 500, color = PrimaryColor)
            )

            Text(
                text = stringResource(id = R.string.app_name),
                style = customizedTextStyle(fontSize = 14, fontWeight = 500, color = PrimaryColor)
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