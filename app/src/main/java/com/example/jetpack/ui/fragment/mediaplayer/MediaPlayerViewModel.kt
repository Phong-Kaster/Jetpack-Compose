package com.example.jetpack.ui.fragment.mediaplayer

import android.annotation.SuppressLint
import android.content.ContentUris
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.JetpackApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaPlayerViewModel
@Inject
constructor(
    private val applicationContext: JetpackApplication
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private var _song = MutableStateFlow<Song?>(Song())
    val song = _song.asStateFlow()

    private var index = mutableIntStateOf(0)
    private val listOfSong = mutableListOf<Song>()

    init {
        collectSongInStorage()
    }

    @SuppressLint("NewApi")
    fun collectSongInStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Audio.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL
                    )
                } else {
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE
            )

            // Show only Audios that are at least 5 minutes in duration.
            val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
            val selectionArgs = arrayOf("0")

            // Display Audios in alphabetical order based on their display name.
            val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

            val query = applicationContext.contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
            query?.use { cursor ->
                // Cache column indices.
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)

                while (cursor.moveToNext()) {
                    // Get values of columns for a given Audio.
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val size = cursor.getInt(sizeColumn)

                    val uri: Uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    // Load thumbnail of a specific media item.
                    val thumbnail: Bitmap =
                        applicationContext.contentResolver.loadThumbnail(uri, Size(640, 480), null)

                    // Stores column values and the contentUri in a local object
                    // that represents the media file.
                    listOfSong += Song(
                        name = name,
                        duration = duration,
                        size = size,
                        uri = uri,
                        thumbnail = thumbnail
                    )
                }
            }

            _song.value = listOfSong[index.intValue]

            Log.d(TAG, "collectSongInStorage - listOfSong = $listOfSong")
            Log.d(TAG, "collectSongInStorage - listOfSong = ${listOfSong.size}")
            Log.d(TAG, "collectSongInStorage - song = ${_song.value}")
        }
    }
}