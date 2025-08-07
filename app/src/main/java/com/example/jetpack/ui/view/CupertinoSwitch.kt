package com.example.jetpack.ui.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CupertinoSwitch(
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    width: Dp = 51.dp,
    height: Dp = 31.dp,
    thumbSize: Dp = 27.dp,
) {
    var checkedValue by remember(checked) { mutableStateOf(checked) }
    val trackOnColor = Color(0xFF34C759)
    val trackOffColor = Color(0xFFDFDFDF)
    val thumbColor = Color.White
    val trackColor by animateColorAsState(
        targetValue = if (checkedValue) trackOnColor else trackOffColor,
        animationSpec = TweenSpec(durationMillis = 200), label = "trackColor"
    )
    val thumbOffset by animateDpAsState(
        targetValue = if (checkedValue) (width - thumbSize - 2.dp) else 2.dp,
        animationSpec = TweenSpec(durationMillis = 200), label = "thumbOffset"
    )
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .size(
                width = width,
                height = height
            )
            .background(
                color = trackColor,
                shape = RoundedCornerShape(percent = 50)
            )
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    checkedValue = !checkedValue
                    onCheckedChange(checkedValue)
                }),
    ) {
        Box(
            modifier = Modifier
                .padding(start = thumbOffset)
                .size(thumbSize)
                .shadow(
                    elevation = 1.dp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = Color.Black.copy(alpha = 0.13f)
                )
                .background(
                    color = thumbColor,
                    shape = CircleShape
                )
        )
    }
}

@Preview
@Composable
private fun PreviewCupertinoSwitch() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(color = Color.DarkGray)
            .padding(5.dp)
    ) {
        CupertinoSwitch(
            checked = false,
            onCheckedChange = { },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.width(16.dp))

        CupertinoSwitch(
            checked = true,
            onCheckedChange = { },
            modifier = Modifier
        )
    }

}