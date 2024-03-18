package com.example.jetpack.ui.fragment.motionlayout.toolbarstate.scrollflag

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import com.example.jetpack.ui.fragment.motionlayout.toolbarstate.FixedScrollFlagState

class ExitUntilCollapsedState(
    heightRange: IntRange,
    scrollOffset: Float = 0f
) : FixedScrollFlagState(heightRange) {

    /**
     * If the top of the list has been reached, _scrollOffset will
     * be updated with a value between 0 and rangeDifference.
     * Otherwise, _scrollOffset will remain the same. Also, _consumed will be updated accordingly.
     */
    override var _scrollOffset: Float by mutableStateOf(
        value = scrollOffset.coerceIn(0f, rangeDifference.toFloat()),
        policy = structuralEqualityPolicy()
    )

    override var scrollOffset: Float
        get() = _scrollOffset
        set(value) {
            if (scrollTopLimitReached) {
                val oldOffset = _scrollOffset
                _scrollOffset = value.coerceIn(0f, rangeDifference.toFloat())
                _consumed = oldOffset - _scrollOffset
            } else {
                _consumed = 0f
            }
        }

    companion object {
        val Saver = run {

            val minHeightKey = "MinHeight"
            val maxHeightKey = "MaxHeight"
            val scrollOffsetKey = "ScrollOffset"

            mapSaver(
                save = {
                    mapOf(
                        minHeightKey to it.minHeight,
                        maxHeightKey to it.maxHeight,
                        scrollOffsetKey to it.scrollOffset
                    )
                },
                restore = {
                    ExitUntilCollapsedState(
                        heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
                        scrollOffset = it[scrollOffsetKey] as Float,
                    )
                }
            )
        }
    }
}