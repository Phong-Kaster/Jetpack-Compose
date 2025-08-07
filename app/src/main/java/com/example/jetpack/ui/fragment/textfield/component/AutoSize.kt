package com.example.jetpack.ui.fragment.textfield.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun AutoSize() {
    var text by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Visual Transformation
        Text(
            text = "Auto Size",
            style = customizedTextStyle(
                fontSize = 18,
                fontWeight = 700,
                color = Color.White
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )

        BasicText(
            text = text,
            maxLines = 1,
            style = customizedTextStyle(
                fontSize = 14,
                color = Color.White,
            ),
            autoSize = TextAutoSize.StepBased(
                minFontSize = 11.sp,
                maxFontSize = 14.sp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = LocalTheme.current.secondary)
        )

        Text(
            text = "Enter some text:",
            style = customizedTextStyle(
                fontSize = 14,
                color = Color.White
            )
        )

        BasicTextField(
            value = text,
            onValueChange = { text = it },
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "Enter text here",
                        style = customizedTextStyle()
                    )
                }
                innerTextField()
            },

            textStyle = customizedTextStyle(
                fontSize = 14,
                color = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = LocalTheme.current.primary,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(vertical = 16.dp, horizontal = 16.dp),
        )


    }
}

@Preview
@Composable
private fun PreviewAutoSize() {
    AutoSize()
}