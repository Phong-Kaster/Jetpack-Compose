package com.example.jetpack.ui.view.wordbyword.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import com.example.jetpack.ui.view.wordbyword.model.TextChunk

/****************************************
 * - Word By Word Animation State
 *
 * It extends Chunked Text Animation State
 * It will split the text into chunks that correspond to words
 */
class WordByWordAnimationState(
    textMeasurer: TextMeasurer,
    defaultTextStyle: TextStyle
) : ChunkedTextAnimationState(textMeasurer, defaultTextStyle) {

    /****************************************
     * - find chunks
     *
     */
    override fun findChunks(text: String, layoutResult: TextLayoutResult): List<TextChunk> {
        // This regex matches any sequence of non-whitespace characters → basically a "word".
        val wordRegex = "\\S+".toRegex()


        return wordRegex
            .findAll(text) // 1. Finds all word matches in the given string.
            .map { matchResult -> // 2. For each matchResult

                //2.1.The actual word (e.g. "Hello", "world").
                val word = matchResult.value

                //2.2.Get start position of the actual word
                val offset = layoutResult.getWordOffset(matchResult.range.start)

                //2.3.Final, create text chunk containing both the text of the word and its drawing offset.
                TextChunk(word, offset)
            }.toList()
    }

    /**************************************
     * - get word offset
     *
     * TextLayoutResult knows where each character of a laid-out string is positioned.
     * getBoundingBox(wordStart) → returns the bounding box of the character at wordStart
     * .topLeft → gives the top-left corner (an Offset) of that character
     *
     * This function finds the exact screen position of where the word begins
     */
    private fun TextLayoutResult.getWordOffset(wordStart: Int): Offset {
        return this.getBoundingBox(wordStart).topLeft
    }
}