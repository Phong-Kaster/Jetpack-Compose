package com.example.jetpack.domain.enums

import androidx.annotation.StringRes
import com.example.jetpack.R


enum class PhotoFolder(
    @StringRes val text: Int = R.string.all
) {
    ALL(text = R.string.all),
    DOWNLOAD(text = R.string.download),
    CAMERA(text = R.string.camera),
    SCREENSHOT(text = R.string.screenshot)
}