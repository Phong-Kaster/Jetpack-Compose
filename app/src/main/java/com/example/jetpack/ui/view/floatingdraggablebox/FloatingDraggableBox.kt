package com.example.jetpack.ui.view.floatingdraggablebox

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

/**
 * A box that can be dragged around the screen.
 *
 * @param content The content of the box.
 * @see
 *
 * [Make the bubble float](https://engineering.theblueground.com/crafting-a-floating-bubble-in-android-with-jetpack-compose/)
 */
@Composable
fun FloatingDraggableBox(
    initialOffset: (FloatingDraggableState) -> IntOffset,
    content: @Composable BoxScope.(FloatingDraggableState) -> Unit,
) {
    val state = remember { mutableStateOf(FloatingDraggableState()) }

    // The outer Box defines the area in which we can drag our bubble and the inner Box wraps the bubble.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { state.updateContainerSize(size = it.size) },
    ) {
        // Basically, the inner Box is the one that will be dragged and as a result, the bubble will be dragged too
        Box(
            modifier = Modifier
                // offset modifier function which is used to specify the desired position of a composable by applying a translation to its original position
                .offset { state.value.offset }
                .onGloballyPositioned {
                    state.updateContentSize(
                        newSize = it.size,
                        initialOffset = initialOffset,
                    )
                }
                // pointerInput modifier function which helps us to process touch, drag, and gesture events within the region of the modified element
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        val calculatedX = state.value.offset.x + dragAmount.x.roundToInt()
                        val calculatedY = state.value.offset.y + dragAmount.y.roundToInt()

                        // The only thing that we miss right now, is to restrict dragging inside the container
                        val offset = IntOffset(
                            calculatedX.coerceIn(0, state.value.dragAreaSize.width),
                            calculatedY.coerceIn(0, state.value.dragAreaSize.height),
                        )

                        state.updateOffset(newOffset = offset)
                    }
                },
        ) { content(state.value) }
    }


    DisposableEffect(true) {
        // Reset position on hide
        onDispose {
            val offset = initialOffset(state.value)
            state.updateOffset(newOffset = offset)
        }
    }

}