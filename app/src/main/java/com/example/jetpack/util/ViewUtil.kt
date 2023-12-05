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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ViewUtil {
    @Composable
    fun PreviewContent(
        content: @Composable ColumnScope.() -> Unit = {}
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)) {
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
}
