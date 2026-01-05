package com.example.jetpack.ui.fragment.devicephoto.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import com.example.jetpack.util.ScreenDimension.figmaHeightScale
import com.example.jetpack.util.ScreenDimension.figmaWidthScale


@Composable
fun PhotoTabElement(
    title: String = stringResource(R.string.app_name),
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(100.dp),
                color = if (selected) Color(0xFF2977B5) else Color(0xFF2B2E35),
            )
            .clip(shape = RoundedCornerShape(100.dp))
            .clickable { onClick() }
            .background(
                shape = RoundedCornerShape(100.dp),
                color = if (selected) Color(0xFF183955) else Color(0xFF0C0D11),
            )
    ) {
        Text(
            text = title,
            style = customizedTextStyle(
                fontSize = (12 * figmaWidthScale()).toInt(),
                fontWeight = 400,
                color = Color.White,
            ),
            modifier = Modifier.wrapContentSize().padding(
                vertical = 8.dp * figmaHeightScale(),
                horizontal = 12.dp * figmaWidthScale()
            )
        )
    }
}

@Preview
@Composable
private fun PreviewPhotoCategoryElement() {
    Row(modifier = Modifier.fillMaxWidth()) {
        PhotoTabElement(selected = false)
        Spacer(modifier = Modifier.width(8.dp))
        PhotoTabElement(selected = true)
    }
}