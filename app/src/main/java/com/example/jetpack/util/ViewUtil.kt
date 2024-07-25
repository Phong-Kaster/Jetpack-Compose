package com.example.jetpack.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

object ViewUtil {
    @Composable
    fun PreviewContent(
        modifier: Modifier = Modifier,
        content: @Composable ( ColumnScope.() -> Unit )? = {},

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        ) {
            if (content == null) return@Column
            content()
        }
    }

    @Composable
    inline fun CenterColumn(
        modifier: Modifier = Modifier,
        itemSpacing: Dp = 0.dp,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(itemSpacing, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }

    @Composable
    inline fun CenterBox(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) { content() }
    }

    fun Offset.toIntOffset(): IntOffset = IntOffset(
        x = x.toInt(),
        y = y.toInt(),
    )

}
