package com.example.jetpack.ui.view.wordbyword.state

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import com.example.jetpack.ui.view.wordbyword.model.TextChunk

class CharByCharAnimationState(
    textMeasurer: TextMeasurer,
    defaultTextStyle: TextStyle
) : ChunkedTextAnimationState(textMeasurer, defaultTextStyle) {

    override fun findChunks(text: String, layoutResult: TextLayoutResult): List<TextChunk> {
        val chunks = mutableListOf<TextChunk>()

        for (i in text.indices) {
            val char = text[i].toString()
            val offset = layoutResult.getBoundingBox(i).topLeft
            chunks.add(TextChunk(char, offset))
        }

        return chunks
    }
}
