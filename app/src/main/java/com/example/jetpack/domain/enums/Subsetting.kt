package com.example.jetpack.domain.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpack.R

enum class Subsetting(
    @DrawableRes val icon: Int,
    @StringRes val text: Int,
) {
    TimedRecording(icon = R.drawable.ic_timed_recording, text = R.string.timed_recording),
}