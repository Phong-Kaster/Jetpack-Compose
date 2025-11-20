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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

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


    // For smooth animation when snapping to edges
    val animatedOffset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    val scope = rememberCoroutineScope()

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
                .offset { state.value.position }
                .onGloballyPositioned {
                    state.updateContentSize(
                        newSize = it.size,
                        initialOffset = initialOffset,
                    )
                }
                // pointerInput modifier function which helps us to process touch, drag, and gesture events within the region of the modified element
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            // sync animation value to current offset before dragging
                            scope.launch {
                                animatedOffset.snapTo(
                                    Offset(
                                        state.value.position.x.toFloat(),
                                        state.value.position.y.toFloat()
                                    )
                                )
                            }
                        },
                        onDrag = { _, dragAmount ->
                            val calculatedX = state.value.position.x + dragAmount.x.roundToInt()
                            val calculatedY = state.value.position.y + dragAmount.y.roundToInt()

                            // The only thing that we miss right now, is to restrict dragging inside the container
                            val offset = IntOffset(
                                calculatedX.coerceIn(0, state.value.dragAreaSize.width),
                                calculatedY.coerceIn(0, state.value.dragAreaSize.height),
                            )

                            state.updateOffset(newOffset = offset)
                        },
                        onDragEnd = {
                            val containerWidth = state.value.containerSize.width
                            val middleX = containerWidth / 2f
                            val currentOffset = state.value.position
                            val targetX =
                                if (currentOffset.x < middleX) 0f else state.value.dragAreaSize.width.toFloat()
                            val targetY = currentOffset.y.toFloat()

                            scope.launch {
                                animatedOffset.snapTo(
                                    Offset(
                                        currentOffset.x.toFloat(),
                                        currentOffset.y.toFloat()
                                    )
                                )

                                animatedOffset.animateTo(
                                    targetValue = Offset(targetX, targetY),
                                    animationSpec = tween(durationMillis = 400)
                                ) {
                                    // ✅ Update state continuously per frame
                                    state.updateOffset(
                                        IntOffset(
                                            value.x.roundToInt(),
                                            value.y.roundToInt()
                                        )
                                    )
                                }
                            }
                        }
                    )
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