package com.example.jetpack.ui.fragment.instagramcarousel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * # [Create Instagram-like Long Press and Draggable Carousel Indicators](https://proandroiddev.com/create-instagram-like-long-press-and-draggable-carousel-indicators-in-jetpack-compose-ce16fa75bc1e)
 * # [Source code](https://github.com/pushpalroy/JetDraggableIndicators)
 * */
@Composable
fun DraggableIndicator(
    modifier: Modifier = Modifier,
    state: PagerState,
    itemCount: Int,
    onPageChange: (Int) -> Unit,
) {
    /** For knowing users clicks long-press and drag*/
    val accumulatedDragAmount = remember { mutableFloatStateOf(0f) }
    var enableDrag by remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current


    /** For specifying distance that users scroll to switch next page*/
    val density = LocalDensity.current
    val threshold = remember {
        with(density) {
            ((80.dp / (itemCount.coerceAtLeast(1))) + 10.dp).toPx()
        }
    }

    /** Automatically switch to current page of horizontal pager*/
    val currentPage = state.currentPage
    val coroutineScope = rememberCoroutineScope()
    val lazyRowState = rememberLazyListState()
    LaunchedEffect(currentPage) {
        coroutineScope.launch {
            lazyRowState.animateScrollToItem(index = currentPage)
        }
    }


    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(50),
                color = if (enableDrag)
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                else
                    Color.Transparent,
                ),
    ) {
        // Indicators will be added here
        LazyRow(
            state = lazyRowState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 120.dp)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            enableDrag = true
                            accumulatedDragAmount.floatValue = 0f
                        },
                        onDrag = { change, dragAmount ->
                            if (!enableDrag) return@detectDragGesturesAfterLongPress

                            change.consume()
                            accumulatedDragAmount.floatValue += dragAmount.x

                            if (abs(accumulatedDragAmount.floatValue) < threshold) return@detectDragGesturesAfterLongPress

                            val nextPage = if (accumulatedDragAmount.floatValue < 0)
                                state.currentPage + 1
                            else
                                state.currentPage - 1
                            val correctedNextPage = nextPage.coerceIn(0, itemCount - 1)

                            if (correctedNextPage != state.currentPage) {
                                haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onPageChange(correctedNextPage)
                            }
                            accumulatedDragAmount.floatValue = 0f
                        },
                        onDragEnd = {
                            enableDrag = false
                            accumulatedDragAmount.floatValue = 0f
                        }
                    )
                },
        ) {
            items(itemCount) { index ->
                val scaleFactor =
                    calculateScaleFactor(index = index, currentPage = state.currentPage)
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                        .drawBehind {
                            drawCircle(
                                color = if (index == state.currentPage)
                                    Color(0xFF03A9F4)
                                else
                                    Color.Gray
                            )
                        }
                )
            }
        }
    }
}


/**
 * calculateScaleFactor
 * @param index The index of the current page.
 *
 * @param currentPage The index of the currently selected page.
 *
 * 1f - This represents the base scale factor, implying that in the default state (when the indicator is the current page),
 * it should not be scaled down at all (i.e., it retains its original size).
 *
 * 0.1f * abs(i - currentPage) - This portion of the formula calculates the difference in position between the current indicator
 * (i) and the currently selected page (currentPage). The absolute value (abs) ensures that the distance is always a positive number,
 * regardless of whether the current indicator is before or after the current page. This distance is then multiplied by 0.1f, determining how much the scale factor decreases as the distance from the current page increases. This means, that for each step away from the current page, the indicator scales down by 10%
 *
 * .coerceAtMost(0.6f) - This method caps the maximum scale-down effect to 60% (0.6f). Without this cap,
 * indicators far away from the current page could become too small or even disappear.
 * By limiting the scale factor reduction to a maximum of 60%, we ensure that all indicators remain visible and maintain a minimum scale, contributing to a balanced and harmonious visual effect across the carousel.
 */
private fun calculateScaleFactor(index: Int, currentPage: Int): Float {
    return 1f - (0.1f * abs(index - currentPage)).coerceAtMost(0.6f)
}

@Preview
@Composable
private fun PreviewDraggableIndicator() {

    val state = rememberPagerState(
        initialPage = 5,
        pageCount = { 100 },
    )

    DraggableIndicator(
        state = state,
        itemCount = state.pageCount,
        onPageChange = {

        },
        modifier = Modifier
            .background(color = Color.DarkGray)
    )
}