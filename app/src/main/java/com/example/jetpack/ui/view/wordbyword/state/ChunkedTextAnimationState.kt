package com.example.jetpack.ui.view.wordbyword.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import com.example.jetpack.ui.view.wordbyword.model.TextChunk
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/****************************************
 * - Chunked Text Animation State - https://gist.github.com/mkhotych/798c49244192790b83c4e9597603b872
 *
 * This ChunkedTextAnimationState is an abstract class
 * that defines how to animate text by chunks (instead of showing it all at once).
 * @param textMeasurer used to measure how the text will layout(width, height, line breaks)
 * @param defaultTextStyle Base style applied to the text if no override is passed
 */
abstract class ChunkedTextAnimationState(
    private val textMeasurer: TextMeasurer,
    val defaultTextStyle: TextStyle
) {

    /**
     * @param boxSize The measured bounding box of the rendered text.
     */
    var boxSize by mutableStateOf<IntSize>(IntSize.Zero)
        private set

    /**
     * @param textStyle The style currently applied to text. Defaults to defaultTextStyle
     */
    var textStyle by mutableStateOf<TextStyle>(defaultTextStyle)
        private set

    /**
     * @param chunks Holds all chunks of the text (calculated in loadText).
     */
    private var chunks = emptyList<TextChunk>()

    /**
     * @param chunksToDisplay Keeps track of which chunks are currently being displayed on screen.
     */
    private val _chunksToDisplay = mutableStateListOf<TextChunk>()
    val chunksToDisplay: List<TextChunk> = _chunksToDisplay

    /**
     * @param showTextJob A coroutine job that animates adding chunks one by one.
     */
    private var showTextJob: Job? = null

    /****************************************
     * - showText
     * Creates the typewriter-like animation effect
     */
    suspend fun showText(delayBetweenChunksMillis: Long) {
        coroutineScope {
            // Cancels any old animation
            dismissText()

            //Launches a coroutine job:
            //+ Iterates through all chunks.
            //+ Adds each chunk to _chunksToDisplay.
            //+ Waits delayBetweenChunksMillis before adding the next one.
            showTextJob = launch {
                for (chunk in chunks) {
                    _chunksToDisplay.add(chunk)
                    delay(delayBetweenChunksMillis)
                }
            }
        }
    }

    /****************************************
     * - loadText
     */
    fun loadText(
        text: String,
        style: TextStyle = this.defaultTextStyle,
        constraints: Constraints = Constraints()
    ) {
        // Clears previous state
        clearState()

        // Uses textMeasurer to measure the text layout (size, wrapping).
        val layoutResult = textMeasurer.measure(text, style, constraints = constraints)

        // Updates textStyle and boxSize.
        textStyle = style
        boxSize = layoutResult.size

        // Calls findChunks (abstract → must be implemented by subclass) to decide how to split the text into chunks.
        chunks = findChunks(text, layoutResult)
    }

    protected abstract fun findChunks(text: String, layoutResult: TextLayoutResult): List<TextChunk>

    /****************************************
     * - clearState
     * Basically resets to the initial state.
     */
    fun clearState() {
        dismissText()
        boxSize = IntSize.Zero
        chunks = emptyList()
    }

    /****************************************
     * - dismissText
     * Stops animation and removes visible text.
     */
    fun dismissText() {
        // Cancels the current animation job if running.
        showTextJob?.cancel()
        showTextJob = null

        // Clears _chunksToDisplay
        _chunksToDisplay.clear()
    }
}