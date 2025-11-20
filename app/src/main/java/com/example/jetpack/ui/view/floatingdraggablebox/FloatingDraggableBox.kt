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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.jetpack.util.CanvasUtil.distanceTo
import com.example.jetpack.util.CanvasUtil.dpToPx
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


    var isDragging by remember { mutableStateOf(false) }
    var isOverCloseArea by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) } // to control whether bubble is shown


    // For smooth animation when snapping to edges
    val animatedOffset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    val scope = rememberCoroutineScope()


    // Define close area size and position
    val closeCircleSizeDp = 50.dp
    val closeCircleSizePx = with(LocalDensity.current) { closeCircleSizeDp.roundToPx() }

    // Close circle is centered horizontally, near the bottom with some margin
    val closeCircleCenter = Offset(
        x = state.value.containerSize.width / 2f,
        y = state.value.containerSize.height - closeCircleSizePx / 2f - 80.dp.dpToPx() // 32dp margin from bottom
    )



    DisposableEffect(true) {
        // Reset position on hide
        onDispose {
            val offset = initialOffset(state.value)
            state.updateOffset(newOffset = offset)
        }
    }


    // The outer Box defines the area in which we can drag our bubble and the inner Box wraps the bubble.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { state.updateContainerSize(size = it.size) },
    ) {
        if (!isVisible) return@Box

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
                            isDragging = true


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

                            // Restrict inside drag area
                            val offset = IntOffset(
                                calculatedX.coerceIn(0, state.value.dragAreaSize.width),
                                calculatedY.coerceIn(0, state.value.dragAreaSize.height),
                            )

                            // Calculate bubble center position (for overlap test)
                            val bubbleCenter = Offset(
                                x = offset.x + state.value.itemSize.width / 2f,
                                y = offset.y + state.value.itemSize.height / 2f
                            )

                            // Distance between bubble center and close circle center
                            val distance = bubbleCenter.distanceTo(closeCircleCenter)

                            // Update overlap boolean
                            isOverCloseArea = distance < closeCircleSizePx / 2

                            // Update position
                            state.updateOffset(newOffset = offset)
                        },
                        onDragEnd = {
                            isDragging = false
                            if (isOverCloseArea) {
                                // User dropped bubble on close area, hide bubble immediately
                                isVisible = false
                            } else {
                                // Normal snap behavior
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
                        }
                    )
                },
        ) { content(state.value) }


        // Show the close circle
        FloatingCloseArea(
            enable = isDragging,
            isOverCloseArea = isOverCloseArea,
            closeCircleSizeDp = closeCircleSizeDp,
            modifier = Modifier
                .size(closeCircleSizeDp)
                .offset {
                    IntOffset(
                        (closeCircleCenter.x - closeCircleSizePx / 2).roundToInt(),
                        (closeCircleCenter.y - closeCircleSizePx / 2).roundToInt()
                    )
                }
                .background(
                    color = if (isOverCloseArea) Color.Red else Color.Gray.copy(alpha = 0.7f),
                    shape = CircleShape
                )
        )
    }
}