package com.example.jetpack.ui.view.wordbyword.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import com.example.jetpack.ui.view.wordbyword.model.TextChunk

/****************************************
 * - Line By Line Animation State
 *
 * It extends Chunked Text Animation State
 * It will split the text into lines
 *
 * LineByLineAnimationState extends the abstract ChunkedTextAnimationState, but instead of
 * splitting the text into words (like WordByWordAnimationState did), it splits the text into lines.
 * So each line of text becomes a TextChunk that can later be animated in sequence.
 */
class LineByLineAnimationState(
    textMeasurer: TextMeasurer,
    defaultTextStyle: TextStyle
): ChunkedTextAnimationState(textMeasurer, defaultTextStyle) {

    override fun findChunks(text: String, layoutResult: TextLayoutResult): List<TextChunk> {
        //Define mutable list to store outcome after finishing for-loop
        val outcome = mutableListOf<TextChunk>()

        /*lineCount gives the total number of lines in the laid-out text
         *Example text = "Hello world\nHow are you?"
         *if the text is wrapped into 2 lines, this returns 2
         */
        val lineCount = layoutResult.lineCount

        for (line in 0 until lineCount) {
            /*These give the character indices where a line starts and ends in the original text string
            Line 0 with start=0 & end=11 → "Hello world"
            Line 1 with start=12 & end=23 → "How are you?"
            */
            val start = layoutResult.getLineStart(line)
            val end = layoutResult.getLineEnd(line)


            /*extracts the autual string content for the line.
            Start at 0 & end at 11 -> lineText = "Hello world"*/
            val lineText = text.substring(start, end)

            // offset represents for the position where this line should be drawn
            val offset = Offset(
                x = layoutResult.getLineLeft(line),
                y = layoutResult.getLineTop(line)
            )

            // Final, add the line to outcome
            val textChunk = TextChunk(lineText, offset)
            outcome.add(textChunk)
        }

        // Final, return outcome
        return outcome
    }
}