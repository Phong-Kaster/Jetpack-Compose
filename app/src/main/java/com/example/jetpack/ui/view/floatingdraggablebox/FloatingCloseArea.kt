package com.example.jetpack.ui.view.floatingdraggablebox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FloatingCloseArea(
    enable: Boolean = true,
    isOverCloseArea: Boolean = true,
    closeCircleSizeDp: Dp = 30.dp,
    modifier: Modifier = Modifier,
){
    if(!enable) return

    Box(
        modifier = modifier
            .size(closeCircleSizeDp)
            .background(
                color = if (isOverCloseArea) Color.Red else Color.Gray.copy(alpha = 0.7f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close Bubble",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewFloatingCloseArea(){
    FloatingCloseArea()
}