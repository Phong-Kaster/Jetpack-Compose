package com.example.jetpack.ui.view


import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.util.AppUtil
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


/**
 * # [How to Implement a Custom Swipe to Reveal in Jetpack Compose](https://www.youtube.com/watch?v=-L_d-0Emmwc)
 */
@Composable
fun SwipeToReveal(
    isRevealed: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit = {},
    onExpand: () -> Unit = {},
    onCollapse: () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    /*card width is the width behind of content*/
    var cardWidth by remember { mutableFloatStateOf(0f) }

    /*make content stick to the left or the right when swipe more natural*/
    val offset = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    /*LaunchedEffect(
        key1 = isRevealed,
        key2 = cardWidth,
        block = {
            if (isRevealed) {
                offset.animateTo(cardWidth)
            } else {
                offset.animateTo(0f)
            }
        }
    )*/

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .onSizeChanged {
                    cardWidth = it.width.toFloat()
                }
                .align(Alignment.CenterEnd)
        ) {
            actions()
        }

        Surface(
            shape = RoundedCornerShape(15.dp),
            color = LocalTheme.current.background,
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        x = offset.value.roundToInt(),
                        y = 0
                    )
                } // Move content with the same distance that users drag
                .pointerInput(
                    key1 = cardWidth,
                    block = {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, dragAmount ->
                                scope.launch {
                                    /**
                                     * if we want to swipe right to reveal.
                                     * Change (-cardWidth) to (cardWidth) to swipe right to reveal.
                                     * */
                                    AppUtil.logcat(message = "dragAmount = $dragAmount")
                                    val newOffset = (offset.value + dragAmount).coerceIn(
                                        minimumValue = -cardWidth,
                                        maximumValue = 0f
                                    )
                                    offset.snapTo(newOffset) // switch immediately without animation
                                    //offset.animateTo(newOffset) // switch with animation
                                }
                            },
                            onDragEnd = {
                                val halfOfCardWidth = -cardWidth * 0.5f
                                if (offset.value < halfOfCardWidth) {
                                    scope.launch {
                                        offset.animateTo(0 - cardWidth)
                                        onExpand()
                                    }
                                } else {
                                    scope.launch {
                                        offset.animateTo(0f)
                                        onCollapse()
                                    }
                                }
                            }
                        )
                    }),
            content = { content() }
        )

    }
}

@Preview
@Composable
private fun PreviewSwipeToReveal() {
    SwipeToReveal(

    )
}