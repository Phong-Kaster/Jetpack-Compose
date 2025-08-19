package com.example.jetpack.ui.view.wordbyword.model

import androidx.compose.ui.geometry.Offset

/**
 * Text Check hold a single piece of text (whether a word or a line) and its corresponding offset.
 * @param textMeasurer A [TextMeasurer] instance used to measure text layout without rendering.
 * @param defaultTextStyle The default [TextStyle] to use for text measurement and display.
 */
data class TextChunk(
    val text: String,
    val offset: Offset,
)
