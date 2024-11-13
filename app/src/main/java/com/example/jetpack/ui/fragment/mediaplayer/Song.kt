package com.example.jetpack.ui.fragment.mediaplayer

import android.graphics.Bitmap
import android.net.Uri
import javax.annotation.concurrent.Immutable

@Immutable
data class Song(
    val name: String = "",
    val duration:Int = 0,
    val thumbnail: Bitmap? = null,
    val size: Int = 0,
    val uri: Uri? = null,
)