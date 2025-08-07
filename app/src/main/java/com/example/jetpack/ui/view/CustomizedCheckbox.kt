package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomizedCheckbox(
    checked: Boolean = false,
    onChangeChecked: (Boolean) -> Unit = {},
    checkedCheckmarkColor: Color = Color.White,
    checkedBoxColor: Color = Color.Blue,
    checkedBorderColor: Color = Color.Transparent,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp)
) {
    var isChecked by remember(checked) { mutableStateOf(checked) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape = shape)
            .clickable {
                isChecked = !isChecked
                onChangeChecked(isChecked)
            }
            .border(
                width = 1.dp,
                color = checkedBorderColor,
                shape = shape
            )
            .background(color = if (isChecked) checkedBoxColor else Color.Transparent)
            .padding(5.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            tint = checkedCheckmarkColor,
            contentDescription = "Icon",
            modifier = Modifier.size(24.dp)
        )
    }

}

@Preview
@Composable
private fun PreviewCustomizedCheckbox() {
    CustomizedCheckbox(
        checked = true,
        checkedCheckmarkColor = Color.Blue,
        checkedBoxColor = Color.Yellow,
        onChangeChecked = { isChecked -> }
    )
}