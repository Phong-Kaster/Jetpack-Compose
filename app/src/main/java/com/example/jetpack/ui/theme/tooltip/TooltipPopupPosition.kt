package com.bloodsugar.bloodpressure.bloodsugartracking.ui.component.tooltip

import android.view.View
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.unit.IntOffset

data class TooltipPopupPosition(
    val offset: IntOffset = IntOffset(0, 0),
    val alignment: TooltipAlignment = TooltipAlignment.TopCenter,
    val centerPositionX: Float = 0f,
){
    companion object {
        fun calculateTooltipPopupPosition(
            view: View,
            coordinates: LayoutCoordinates?,
        ): TooltipPopupPosition {
            coordinates ?: return TooltipPopupPosition()

            val visibleWindowBounds = android.graphics.Rect()
            view.getWindowVisibleDisplayFrame(visibleWindowBounds)

            val boundsInWindow = coordinates.boundsInWindow()

            val heightAbove = boundsInWindow.top - visibleWindowBounds.top
            val heightBelow = visibleWindowBounds.bottom - visibleWindowBounds.top - boundsInWindow.bottom

            val centerPositionX = boundsInWindow.right - (boundsInWindow.right - boundsInWindow.left) / 2

            val offsetX = centerPositionX - visibleWindowBounds.centerX()

            return if (heightAbove < heightBelow) {
                val offset = IntOffset(
                    y = coordinates.size.height,
                    x = offsetX.toInt()
                )
                TooltipPopupPosition(
                    offset = offset,
                    alignment = TooltipAlignment.TopCenter,
                    centerPositionX = centerPositionX,
                )
            } else {
                TooltipPopupPosition(
                    offset = IntOffset(
                        y = -coordinates.size.height,
                        x = offsetX.toInt()
                    ),
                    alignment = TooltipAlignment.BottomCenter,
                    centerPositionX = centerPositionX,
                )
            }
        }
    }
}