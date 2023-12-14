package com.example.jetpack.ui.fragment.tutorial.component

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Rect


val LocalTutorial =
    staticCompositionLocalOf { TutorialState(enableTutorial = true) }

@Stable
class TutorialState
constructor(
    enableTutorial: Boolean,
    val onDone: () -> Unit = {}
) {
    var enable by mutableStateOf(enableTutorial)
    var addButtonSize by mutableStateOf(Rect.Zero)
    var titleRect by mutableStateOf(Rect.Zero)
    var photoRect by mutableStateOf(Rect.Zero)

    var currentTutorial by mutableIntStateOf(0)
    val TOTAL_TUTORIALS = 3
}