package com.example.jetpack.ui.fragment.mediaplayer

import android.graphics.Bitmap
import android.net.Uri
import javax.annotation.concurrent.Immutable

@Immutable
data class Song(
    val name: String = "",
    val artist: String = "",
    val duration: Int = 0,
    val thumbnail: Bitmap? = null,
    val size: Int = 0,
    val uri: Uri? = null,
) {
    companion object {
        fun getFakeSong1(): Song {
            return Song(
                name = "We R Who We R.mp3",
                artist = "Kesha",
                duration = 100,
                thumbnail = null,
                size = 100,
                uri = null
            )
        }

        fun getFakeSong2(): Song {
            return Song(
                name = "Napoleon",
                artist = "Bonaparte",
                duration = 100,
                thumbnail = null,
                size = 100,
                uri = null
            )
        }
    }
}