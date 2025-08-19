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

        for (i in text.indices) { // iterate through each character index.
            //1. convert each character into a String (since TextChunk expects a string).
            val char = text[i].toString()

            //2. gives the Offset (x, y) where that character should be drawn
            val offset = layoutResult.getBoundingBox(i).topLeft

            //3. Finally, collect them all into a List<TextChunk>.
            chunks.add(TextChunk(char, offset))
        }

        // Example with "Ask me later" -> chucks [A, s, k,  , m, e,  , l, a, t, e, r]

        return chunks
    }
}
