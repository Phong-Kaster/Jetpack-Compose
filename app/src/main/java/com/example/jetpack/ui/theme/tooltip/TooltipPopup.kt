package com.bloodsugar.bloodpressure.bloodsugartracking.ui.component.tooltip

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlin.math.roundToInt

/**
 * tooltipContent - Content to display in tooltip.
 */
@Composable
fun TooltipPopup(
    modifier: Modifier = Modifier,
    tooltipComponent: @Composable (Modifier) -> Unit,
    tooltipContent: @Composable () -> Unit,
    showTooltip: Boolean = false,
) {
    var isTooltipShown by remember(showTooltip){ mutableStateOf(showTooltip) }
    var position by remember { mutableStateOf(TooltipPopupPosition()) }

    val view = LocalView.current.rootView

    if (isTooltipShown) {
        TooltipPopupFactory(
            onDismissRequest = { isTooltipShown = isTooltipShown.not() },
            position = position,
            backgroundColor = Color.White,
        ) {
            tooltipContent()
        }
    }

    tooltipComponent(
        modifier
            .noRippleClickable {
                isTooltipShown = isTooltipShown.not()
            }
            .onGloballyPositioned { coordinates ->
                position = TooltipPopupPosition.calculateTooltipPopupPosition(view, coordinates)
            }
    )
}

@Composable
fun TooltipPopupFactory(
    position: TooltipPopupPosition,
    backgroundShape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = Color(0xFF4A4D4B),
    arrowHeight: Dp = 4.dp,
    horizontalPadding: Dp = 16.dp,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var alignment = Alignment.TopCenter
    var offset = position.offset

    val horizontalPaddingInPx = with(LocalDensity.current) {
        horizontalPadding.toPx()
    }

    var arrowPositionX by remember { mutableFloatStateOf(position.centerPositionX) }

    with(LocalDensity.current) {
        val arrowPaddingPx = arrowHeight.toPx().roundToInt() * 4

        when (position.alignment) {
            TooltipAlignment.TopCenter -> {
                alignment = Alignment.TopCenter
                offset = offset.copy(
                    y = position.offset.y + arrowPaddingPx
                )
            }

            TooltipAlignment.BottomCenter -> {
                alignment = Alignment.BottomCenter
                offset = offset.copy(
                    y = position.offset.y - arrowPaddingPx
                )
            }
        }
    }

    val popupPositionProvider = remember(alignment, offset) {
        TooltipAlignmentOffsetPositionProvider(
            alignment = alignment,
            offset = offset,
            horizontalPaddingInPx = horizontalPaddingInPx,
            centerPositionX = position.centerPositionX,
        ) { position ->
            arrowPositionX = position
        }
    }

    Popup(
        popupPositionProvider = popupPositionProvider,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(dismissOnBackPress = false),
    ) {
        BubbleLayout(
            alignment = position.alignment,
            arrowHeight = arrowHeight,
            arrowColor = backgroundColor,
            arrowPositionX = arrowPositionX,
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .background(
                    color = backgroundColor,
                    shape = backgroundShape,
                ),
        ) {
            content()
        }
    }
}


@Composable
fun BubbleLayout(
    modifier: Modifier = Modifier,
    alignment: TooltipAlignment = TooltipAlignment.TopCenter,
    arrowHeight: Dp,
    arrowPositionX: Float,
    arrowColor: Color,
    content: @Composable () -> Unit
) {

    val arrowHeightPx = with(LocalDensity.current) {
        arrowHeight.toPx()
    }

    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()

                if (arrowPositionX <= 0f) return@drawWithContent

                val isTopCenter = alignment == TooltipAlignment.TopCenter

                val path = Path()

                /* Draw rectangle */
                if (isTopCenter) {
                    val position = Offset(arrowPositionX, 0F)// move the bottom of rectangle lower than content to make them like a single object
                    path.apply {
                        moveTo(x = position.x, y = position.y)
                        lineTo(x = position.x - arrowHeightPx, y = position.y)
                        lineTo(x = position.x, y = position.y - arrowHeightPx)
                        lineTo(x = position.x + arrowHeightPx, y = position.y)
                        lineTo(x = position.x, y = position.y)
                        close()
                    }
                } else {
                    val arrowY = drawContext.size.height
                    val position = Offset(arrowPositionX, arrowY)
                    path.apply {
                        moveTo(x = position.x, y = position.y)
                        lineTo(x = position.x + arrowHeightPx, y = position.y)
                        lineTo(x = position.x, y = position.y + arrowHeightPx)
                        lineTo(x = position.x - arrowHeightPx, y = position.y)
                        lineTo(x = position.x, y = position.y)
                        close()
                    }
                }

                drawPath(
                    path = path,
                    color = arrowColor,
                )
            }
    ) {
        content()
    }
}


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
