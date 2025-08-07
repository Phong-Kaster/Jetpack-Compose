package com.example.jetpack.ui.fragment.mediaplayer

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri

object MediaPlayerUtil {
    /**
     * return title and artirst of song
     * for example, Sundial Dream - Kelvin Dream
     */
    fun Int.getTitle(context: Context): String {
        val mediaPath = Uri.parse("android.resource://" + context.packageName + "/" + this)
        val metadata = MediaMetadataRetriever()
        metadata.setDataSource(context, mediaPath)

        val title = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val artist = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)

        return "$title - $artist"
    }

    fun Int.getBackward(albums: List<Int>): Int {
        return if (this > 0) {
            this - 1
        } else {
            albums.size - 1
        }
    }

    fun Int.getForward(albums: List<Int>): Int {
        return if (this < albums.size - 1) {
            this + 1
        } else {
            0
        }
    }
}