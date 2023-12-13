package com.example.jetpack.ui.tutorial

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Rect


val LocalTutorialBottomNavigationState =
    staticCompositionLocalOf { TutorialBottomNavigationState(hasDone = true) }

@Stable
class TutorialBottomNavigationState
constructor(
    hasDone: Boolean
) {
    var hasTutorialDone by mutableStateOf(hasDone)
    var addButtonSize by mutableStateOf(Rect.Zero)
    var onClickAddButton by mutableStateOf({})
}