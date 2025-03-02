package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.state

import androidx.compose.foundation.ScrollState
import androidx.compose.ui.unit.Density
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.header.ToolbarState

/**
 * Class that manages scroll state and toolbar transitions
 */
data class ScreenScroller(
    val scrollState: ScrollState,
    val namePosition: Float
) {
    // Toolbar transition state based on the name position
    val toolbarTransitionState: ToolbarState
        get() = if (namePosition <= 0f || scrollState.value <= 0) {
            ToolbarState.HIDDEN
        } else {
            ToolbarState.SHOWN
        }

    /**
     * Get toolbar state based on current density
     */
    fun getToolbarState(density: Density): ToolbarState {
        return toolbarTransitionState
    }
}