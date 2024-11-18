package com.example.jetpack.ui.fragment.collapsibletopbar.collapsibletopbar3.header

// Toolbar state related classes and functions to achieve the CollapsingToolbarLayout animation
enum class ToolbarState {
    HIDDEN,
    SHOWN,

    ;

    companion object {
        val ToolbarState.isShown
            get() = this == SHOWN
    }
}

