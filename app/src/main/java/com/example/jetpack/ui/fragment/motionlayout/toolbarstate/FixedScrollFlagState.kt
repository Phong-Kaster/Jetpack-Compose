package com.example.jetpack.ui.fragment.motionlayout.toolbarstate

abstract class FixedScrollFlagState(heightRange: IntRange) : ScrollFlagState(heightRange) {

    final override val offset: Float = 0f

}