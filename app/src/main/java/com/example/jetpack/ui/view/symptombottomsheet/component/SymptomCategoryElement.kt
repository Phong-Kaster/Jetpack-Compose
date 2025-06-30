package com.example.jetpack.ui.view.symptombottomsheet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.lunacycle.ui.theme.PrimaryColor
import com.apero.lunacycle.ui.theme.customizedTextStyle

@Composable
fun SymptomCategoryElement(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    enable: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(color = if (enable) PrimaryColor else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = customizedTextStyle(
                fontSize = 14, fontWeight = 500,
            ),
            color = if (enable) Color.White else Color(0xFF111111),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 13.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewSymptomCategoryElement() {
    Column(modifier = Modifier.background(color = Color.White)) {
        SymptomCategoryElement(
            modifier = Modifier,
            text = "Headache",
            onClick = {},
            enable = true
        )

        SymptomCategoryElement(
            modifier = Modifier,
            text = "Headache",
            onClick = {},
            enable = false
        )
    }
}