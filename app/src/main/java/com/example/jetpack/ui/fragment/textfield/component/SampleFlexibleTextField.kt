package com.example.jetpack.ui.fragment.textfield.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.FlexibleTextField

@Composable
fun SampleFlexibleTextField (
    modifier: Modifier = Modifier
) {
    var value by remember {
        mutableStateOf("Buffett was born in Omaha, Nebraska. The")
    }

    Column(modifier = modifier) {
        // Visual Transformation
        Text(
            text = "Flexible Text Field",
            style = customizedTextStyle(
                fontSize = 18,
                fontWeight = 700,
                color = Color.White
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )

        FlexibleTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun PreviewSampleFlexibleTextField(){
    SampleFlexibleTextField()
}