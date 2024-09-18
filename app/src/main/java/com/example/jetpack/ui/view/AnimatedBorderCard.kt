package com.example.jetpack.ui.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.Background2
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
                colorBackground = LocalTheme.current.background,
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
            ShimmerText(
                text = "Phong Kaster",
                shimmerColor = LocalTheme.current.primary,
                textStyle = customizedTextStyle(
                    fontSize = 32,
                    fontWeight = 600,
                ),
                animationSpec = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing),
                modifier = Modifier.fillMaxWidth()
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