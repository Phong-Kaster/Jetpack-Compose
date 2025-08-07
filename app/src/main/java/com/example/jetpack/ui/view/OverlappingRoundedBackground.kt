package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OverlappingRoundedBackground(modifier: Modifier = Modifier) {

    val fraction = 0.8f
    val verticalBias = 0.1f
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(358 / 232f)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(fraction * 0.80f)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 50.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 50.dp
                    )
                )
                .background(Color.Blue)
                .aspectRatio(2f)
                .align(BiasAlignment(horizontalBias = 1f, verticalBias = verticalBias + 0.4f))
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 50.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 50.dp
                    )
                )
                .background(Color.Black)
                .aspectRatio(2f)
                .align(BiasAlignment(horizontalBias = 1f, verticalBias = verticalBias))
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 50.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 50.dp
                    )
                )
                .background(Color.Red)
                .aspectRatio(2f)
        )


    }
}

@Preview
@Composable
private fun PreviewOverlappingRoundedBackground() {
    OverlappingRoundedBackground(
        modifier = Modifier.fillMaxWidth()
    )
}