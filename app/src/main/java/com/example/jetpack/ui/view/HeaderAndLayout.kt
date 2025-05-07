package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun HeaderAndLayout(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = LocalTheme.current.primary)
                .height(1.dp)
                .padding(horizontal = 16.dp)
        )

        if(title.isNotEmpty() || title.isNotBlank()){
            Text(
                text = title,
                style = customizedTextStyle(
                    fontSize = 14,
                    fontWeight = 600,
                    color = Color.White
                )
            )
        }


        content()
    }
}

@Preview
@Composable
private fun PreviewHeaderAndLayout() {
    HeaderAndLayout(
        modifier = Modifier,
        title = "Preview Title",
        content = {},
    )
}