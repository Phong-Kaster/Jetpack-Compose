package com.example.jetpack.ui.view.floatingdraggablebox

import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

/**
 * State of the [FloatingDraggableBox].
 *
 * @param itemSize Size of the content inside the [FloatingDraggableBox].
 * @param containerSize Size of the container in which the [FloatingDraggableBox] is placed.
 * @param position Current offset of the content inside the [FloatingDraggableBox].
 *
 * [containerSize] and [position] are used to calculate the drag area size. They are useful for calculating the area in
 * which is permitted to drag out box
 */
data class FloatingDraggableState(
    var itemSize: IntSize = IntSize(width = 0, height = 0),
    var containerSize: IntSize = IntSize(width = 0, height = 0),
    var position: IntOffset = IntOffset(x = 0, y = 0),
) {

    val dragAreaSize: IntSize
        get() = IntSize(
            width = containerSize.width - itemSize.width,
            height = containerSize.height - itemSize.height,
        )
}

// ------------------------------------------------------------
// ✅ Extension Helpers for MutableState<FloatingDraggableState>
// ------------------------------------------------------------

/**
 * Updates the container size of the draggable item.
 */
fun MutableState<FloatingDraggableState>.updateContainerSize(size: IntSize) {
    value = value.copy(containerSize = size)
}

/**
 * Updates the content size and sets the initial offset if it's rendered for the first time.
 */
fun MutableState<FloatingDraggableState>.updateContentSize(
    newSize: IntSize,
    initialOffset: (FloatingDraggableState) -> IntOffset,
) {
    val current = value
    val isFirstRender = newSize.isNotEmpty() && current.itemSize.isEmpty()

    val newOffset = if (isFirstRender) {
        val stateWithSize = current.copy(itemSize = newSize)
        initialOffset(stateWithSize)
    } else {
        current.position
    }

    value = current.copy(itemSize = newSize, position = newOffset)
}

/**
 * Updates the offset (position) of the draggable item.
 */
fun MutableState<FloatingDraggableState>.updateOffset(newOffset: IntOffset) {
    value = value.copy(position = newOffset)
}

// ------------------------------------------------------------
// ✅ Internal Utility Extensions
// ------------------------------------------------------------

private fun IntSize.isEmpty(): Boolean = this == IntSize.Zero
private fun IntSize.isNotEmpty(): Boolean = !isEmpty()