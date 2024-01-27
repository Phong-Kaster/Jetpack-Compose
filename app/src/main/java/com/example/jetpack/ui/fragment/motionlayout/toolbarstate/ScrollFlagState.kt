package com.example.jetpack.ui.fragment.motionlayout.toolbarstate

abstract class ScrollFlagState(heightRange: IntRange) : ToolbarState {

    protected val minHeight = heightRange.first
    protected val maxHeight = heightRange.last
    protected val rangeDifference = maxHeight - minHeight /*is the range that scroll does not make toolbar changes*/
    protected var _consumed: Float = 0f

    protected abstract var _scrollOffset: Float

    init {
        require(0 <= heightRange.first && heightRange.first <= heightRange.last) {
            "The lowest height value must be >= 0 and the highest height value must be >= the lowest value."
        }
    }

    /**
     * @param height takes a value between minHeight and maxHeight.
     * When scrollOffset is ≥ rangeDifference, the toolbar is collapsed.
     * When scrollOffset is 0, the toolbar is expanded.
     */
    final override val height: Float
        get() = (maxHeight - scrollOffset).coerceIn(minHeight.toFloat(), maxHeight.toFloat())


    /**
     * @param progress takes a value between 0 and 1,
     * which correspond to the toolbar collapsed and expanded states respectively.
     */
    final override val progress: Float
        get() = 1 - (maxHeight - height) / rangeDifference


    /**
     * @param consumed corresponds to the length that the toolbar
     * takes from the total scrolling length provided by the NestedScrollConnection object*/
    final override val consumed: Float
        get() = _consumed

    /**
     * @param scrollTopLimitReached works as a flag that lets us know if the first item of the list is completely visible.
     */
    final override var scrollTopLimitReached: Boolean = true
}