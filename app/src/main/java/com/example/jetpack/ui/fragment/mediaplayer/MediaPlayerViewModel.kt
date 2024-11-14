package com.example.jetpack.ui.fragment.mediaplayer

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.JetpackApplication
import com.example.jetpack.util.FileUtil.getNameWithoutExtensionFromAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * # Collections of Media Files
 * - Images
 *      Stored in: DCIM/ and Pictures/ directories
 *
 *      Added to: MediaStore.Images table
 *
 * - Videos
 *      Stored in: DCIM/, Movies/, and Pictures/ directories
 *
 *      Added to: MediaStore.Video table
 *
 * - Audio Files
 *      Stored in:
 *
 *              Alarms
 *
 *              Audiobooks
 *
 *              Music
 *
 *              Notifications
 *
 *              Podcasts
 *
 *              Ringtones
 *
 *      Added to: MediaStore.Audio table
 *
 * - Downloaded Files
 *      Stored in: Download/ directory
 *
 *      Added to: MediaStore.Downloads table (for devices running Android 10 and higher)
 *
 * # Special Cases
 * Recordings directory: Audio files (e.g., voice recordings) stored in Recordings/ directory. Not available on Android 11 and lower.
 *
 * # MediaStore.Files Collection
 * This collection's contents depend on whether your app uses scoped storage (Android 10 and higher).
 *
 * - Scoped Storage Enabled
 *
 *      Shows only media files that your app has created.
 *
 * - Scoped Storage Disabled or Unavailable
 *
 *      Shows all types of media files.
 *
 * # Access to Media Files from Other Apps
 *
 *      If you need to view media files created by other apps, use READ_EXTERNAL_STORAGE permission.
 *
 *      It's recommended to use MediaStore APIs for opening files not created by your app.
 */
@HiltViewModel
class MediaPlayerViewModel
@Inject
constructor(
    private val applicationContext: JetpackApplication
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private var index by mutableIntStateOf(0)
    private val listOfSong = mutableListOf<Song>()

    /**
     * song that users choose now
     */
    private var _song = MutableStateFlow<Song?>(Song())
    val chosenSong = _song.asStateFlow()

    /**
     * All songs in device
     * */
    private var _songs = MutableStateFlow<ImmutableList<Song>>(persistentListOf())
    val songs = _songs.asStateFlow()


    init {
        collectSongInStorage()
    }

    /**
     * ------------------------------ ONLY FOR MEDIA PLAYER FRAGMENT 2 ------------------------------
     */
    fun collectSongInStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            listOfSong.clear()
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
                MediaStore.Audio.Media.ARTIST,
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
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)

                while (cursor.moveToNext()) {
                    // Get values of columns for a given Audio.
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val artist = cursor.getString(artistColumn)
                    val duration = cursor.getInt(durationColumn)
                    val size = cursor.getInt(sizeColumn)

                    val uri: Uri =
                        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

                    // Load thumbnail of a specific media item.
                    val thumbnail: Bitmap? = loadThumbnail(context = applicationContext, uri = uri)


                    // Stores column values and the contentUri in a local object
                    // that represents the media file.

                    listOfSong += Song(
                        name = name.getNameWithoutExtensionFromAudio(),
                        artist = artist,
                        duration = duration,
                        size = size,
                        uri = uri,
                        thumbnail = thumbnail
                    )
                }
            }

            Log.d(TAG, "collectSongInStorage - listOfSong = ${listOfSong.size}")
            Log.d(TAG, "collectSongInStorage - song = ${_song.value}")

            if (listOfSong.isEmpty()) return@launch
            _songs.value = listOfSong.distinct().toImmutableList()
            _song.value = listOfSong[index]
        }
    }

    /**********************************
     * Load album art of a song
     */
    private fun loadThumbnail(context: Context, uri: Uri): Bitmap? {
        // Check if the file exists val
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        if (cursor == null || !cursor.moveToFirst()) {
            cursor?.close()
            return null
        }
        cursor.close()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                context.contentResolver.loadThumbnail(uri, Size(640, 480), null)
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
        } else {
            try {
                MediaStore.Images.Thumbnails.getThumbnail(
                    context.contentResolver,
                    ContentUris.parseId(uri),
                    MediaStore.Images.Thumbnails.MINI_KIND,
                    null
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
        }
    }

    fun updateChosenSong(chosenIndex:Int, chosenSong: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            _song.value = chosenSong
            index = chosenIndex
        }
    }
}