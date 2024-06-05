package com.example.jetpack.ui.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.animationInfiniteFloat

@Composable
fun ShimmerItem(
    loading: Boolean,
    content: @Composable () -> Unit = {},
) {
    if (loading) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    width = 0.5.dp,
                    color = Color.White.copy(alpha = 0.5F),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .size(25.dp)
                .shimmerEffect()) {}

            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .height(25.dp)
                .width(100.dp)
                .shimmerEffect()) {}
            Spacer(modifier = Modifier.weight(0.9F))
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .size(14.dp)
                .shimmerEffect()) {}
        }
    } else {
        content()
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition(label = "transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = animationInfiniteFloat,
        label = "startOffsetX"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(x = startOffsetX, y = 0F),
            end = Offset(x = startOffsetX + size.width.toFloat(), y = size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Preview
@Composable
private fun PreviewShimmerEffect() {
    Column {
        ShimmerItem(loading = true)
        Divider()
        ShimmerItem(loading = false)
    }
}