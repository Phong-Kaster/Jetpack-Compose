package com.example.jetpack.ui.fragment.secondary.motionlayout.toolbarstate

import androidx.compose.runtime.Stable

/**
 * @param height takes a value between minHeight and maxHeight. When scrollOffset is ≥ rangeDifference, the toolbar is collapsed. When scrollOffset is 0, the toolbar is expanded.
 * @param scrollTopLimitReached works as a flag that lets us know if the first item of the list is completely visible.
 */
@Stable
interface ToolbarState {
    val offset: Float
    val height: Float
    val progress: Float
    val consumed: Float
    var scrollTopLimitReached: Boolean
    var scrollOffset: Float
}