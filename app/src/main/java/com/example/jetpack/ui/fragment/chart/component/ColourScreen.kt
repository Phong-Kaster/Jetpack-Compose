package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.view.ColorSquare

@Composable
fun ColourScreen() {

    val state = rememberLazyGridState()


    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item(
            key = "redBackgroundAndWhiteText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare()
            }
        )

        item(
            key = "blueBackgroundAndWhiteText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "0063EC",
                    textTitle = "FFFFFF",
                    backgroundColor = Color(0xFF0063EC),
                    textColor = Color.White
                )
            }
        )

        item(
            key = "yellowBackgroundAndBlueText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "FFD154",
                    textTitle = "002795",
                    backgroundColor = Color(0xFFFFD154),
                    textColor = Color(0xFF002795)
                )
            }
        )

        item(
            key = "violetBackgroundAndYellowText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "6D24CF",
                    textTitle = "FFE06F",
                    backgroundColor = Color(0xFF6D24CF),
                    textColor = Color(0xFFFFE06F)
                )
            }
        )

        item(
            key = "greenBackgroundAndBlueText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "68D69D",
                    textTitle = "401D83",
                    backgroundColor = Color(0xFF68D69D),
                    textColor = Color(0xFF401D83)
                )
            }
        )

        item(
            key = "orangeBackgroundAndYellowText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "FF9240",
                    textTitle = "FFFFA9",
                    backgroundColor = Color(0xFFFF9240),
                    textColor = Color(0xFFFFFFA9)
                )
            }
        )

        item(
            key = "grayBackgroundAndRedText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "D9D9D9",
                    textTitle = "C20000",
                    backgroundColor = Color(0xFFD9D9D9),
                    textColor = Color(0xFFC20000)
                )
            }
        )

        item(
            key = "pinkBackgroundAndWhiteText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "FF83A9",
                    textTitle = "FFFFFF",
                    backgroundColor = Color(0xFFFF83A9),
                    textColor = Color.White
                )
            }
        )

        item(
            key = "darkGrayBackgroundAndYellowText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "3D3D3D",
                    textTitle = "FFD154",
                    backgroundColor = Color(0xFF3D3D3D),
                    textColor = Color(0xFFFFD154)
                )
            }
        )


        item(
            key = "purpleBackgroundAndBlueText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "D4B5FF",
                    textTitle = "002795",
                    backgroundColor = Color(0xFFD4B5FF),
                    textColor = Color(0xFF002795)
                )
            }
        )


        item(
            key = "pastelBlueBackgroundAndWhiteText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "96D6FF",
                    textTitle = "FFFFFF",
                    backgroundColor = Color(0xFF96D6FF),
                    textColor = Color.White,
                )
            }
        )

        item(
            key = "pastelPinkBackgroundAndYellowText",
            span = { GridItemSpan(1) },
            content = {
                ColorSquare(
                    backgroundTitle = "FFC0B9",
                    textTitle = "FFFFC5",
                    backgroundColor = Color(0xFFFFC0B9),
                    textColor = Color(0xFFFFFFC5),
                )
            }
        )
    }
}