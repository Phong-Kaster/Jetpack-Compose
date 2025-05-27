package com.example.jetpack.util

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.jetpack.R
import com.example.jetpack.domain.enums.AudioFormat
import com.example.jetpack.domain.enums.VideoFormat
import java.io.File
import kotlin.math.roundToInt

object FileUtil {

    val TAG = this.javaClass.simpleName

    /**
     * get True Name From Video
     * For example: my_record.mp3 then my_record is name of this audio file.
     * this function will return MY_RECORD AS RESULT
     */
    fun getNameWithoutExtensionFromVideo(name: String): String {
        var title = ""

        when {
            name.contains(VideoFormat.MP4.name.lowercase(), ignoreCase = true) -> title =
                name.split(".${VideoFormat.MP4.name.lowercase()}")[0]

            name.contains(VideoFormat.AVI.name.lowercase(), ignoreCase = true) -> title =
                name.split(".${VideoFormat.AVI.name.lowercase()}")[0]

            name.contains(VideoFormat.MKV.name.lowercase(), ignoreCase = true) -> title =
                name.split(".${VideoFormat.MKV.name.lowercase()}")[0]

            name.contains(VideoFormat.MOV.name.lowercase(), ignoreCase = true) -> title =
                name.split(".${VideoFormat.MOV.name.lowercase()}")[0]
        }

        return title
    }

    /**
     * get True Name From Audio
     * For example: my_record.mp3 then my_record is name of this audio file.
     * this function will return MY_RECORD AS RESULT
     */
    fun String.getNameWithoutExtensionFromAudio(): String {
        var title = ""



        when {
            this.contains(AudioFormat.AAC.name.lowercase(), ignoreCase = true) -> title =
                this.split(".${AudioFormat.AAC.name.lowercase()}")[0]

            this.contains(AudioFormat.M4A.name.lowercase(), ignoreCase = true) -> title =
                this.split(".${AudioFormat.M4A.name.lowercase()}")[0]

            this.contains(AudioFormat.MP3.name.lowercase(), ignoreCase = true) -> title =
                this.split(".${AudioFormat.MP3.name.lowercase()}")[0]

            this.contains(AudioFormat.WAV.name.lowercase(), ignoreCase = true) -> title =
                this.split(".${AudioFormat.WAV.name.lowercase()}")[0]

            this.contains(AudioFormat.WMA.name.lowercase(), ignoreCase = true) -> title =
                this.split(".${AudioFormat.WMA.name.lowercase()}")[0]

            this.contains(AudioFormat.FLAC.name.lowercase(), ignoreCase = true) -> title =
                this.split(".${AudioFormat.FLAC.name.lowercase()}")[0]
        }

        return title
    }

    /**
     * get name of an audio file.
     * For example: my_record.mp3 then my_record is name of this audio file.
     * this function will return MP3 AS RESULT
     */
    fun String.getExtensionFromVideo(): String {
        var extension = ""

        when {
            this.contains(VideoFormat.MP4.name, ignoreCase = true) -> extension =
                VideoFormat.MP4.name.lowercase()

            this.contains(VideoFormat.AVI.name, ignoreCase = true) -> extension =
                VideoFormat.AVI.name.lowercase()

            this.contains(VideoFormat.MKV.name, ignoreCase = true) -> extension =
                VideoFormat.MKV.name.lowercase()

            this.contains(VideoFormat.MOV.name, ignoreCase = true) -> extension =
                VideoFormat.MOV.name.lowercase()
        }
        return ".${extension}"// .m4a
    }

    fun isNameUnacceptable(name: String, extension: String): Int {
        if (name.isEmpty()) {
            return R.string.filename_can_not_be_empty
        }

        if (name.isBlank()) {
            return R.string.filename_can_not_be_blank
        }

        if (name.length > 31) {
            return R.string.filename_can_not_have_the_following_character
        }

        val invalidCharacter = listOf(
            "#", "%", "&", "{", "}", "/",
            "<", ">", "*", "?", "$",
            "!", "'", "\"", "\\", ":",
            "@", "+", "`", "|", "=", ".",
            ".${VideoFormat.MP4.name.lowercase()}",
            ".${VideoFormat.AVI.name.lowercase()}",
            ".${VideoFormat.MKV.name.lowercase()}",
            ".${VideoFormat.MOV.name.lowercase()}"
        )

        for (index in invalidCharacter.indices) {
            val character = invalidCharacter[index]
            if (name.contains(character)) {
                return R.string.filename_can_not_have_the_following_character
            }
        }

        return 0
    }

    /**
     * From byte to Kilobyte
     */
    fun Int.toKilobyte(): Float {
        return this * 0.001f
    }

    /**
     * From Byte to Megabyte
     */
    fun Int.toMegabyte(): Float {
        return this * 0.000001f
    }

    fun Float.roundTo(n: Int): String {
        return if (this == 0f) "0" else "%.${n}f".format(this)
    }

    fun Long.toMegabyte(): Float {
        return this * 0.000001f
    }


    /**
     * From millisecond of video to total watch time
     * For example: 12381283 milliseconds is equal to 3 hours, 26 minutes, and 21 seconds.
     */
    fun Long.toTotalWatchTime(): String {
        val totalSeconds = (this * 0.001).roundToInt()

        val totalMinutes = totalSeconds / 60

        val hours = totalMinutes / 60
        val minutes = totalMinutes - hours * 60
        val seconds = totalSeconds - hours * 60 * 60 - minutes * 60

//        Log.d("TAG", "toSecond - totalSeconds = $totalSeconds")
//        Log.d("TAG", "toSecond - totalMinutes = $totalMinutes")
//        Log.d("TAG", "toSecond - hours = $hours")
//        Log.d("TAG", "toSecond - minutes = $minutes")
//        Log.d("TAG", "toSecond - seconds = $seconds")

        val readableHours = if (hours > 9) hours.toString() else "0$hours"
        val readableMinutes = if (minutes > 9) minutes.toString() else "0$minutes"
        val readableSeconds = if (seconds > 9) seconds.toString() else "0$seconds"

        return if (hours > 0)
            "${readableHours}:${readableMinutes}:${readableSeconds}"
        else
            "${readableMinutes}:${readableSeconds}"
    }

    /*fun shareVideo(context: Context, video: Video) {
        if (video.contentUri.path.isNullOrEmpty()) {
            Toast.makeText(context, "Video URI is null", Toast.LENGTH_SHORT).show()
            return
        }


        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, video.contentUri)
            type = "video/mp4"
        }

        val chooserIntent = Intent.createChooser(shareIntent, "")
        context.startActivity(chooserIntent)
    }*/

    fun createVideoFolder() {
        val internalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        val folderPath = File(internalStorageDir, "PHONGKASTER")

        Log.d(TAG, "createVideoFolder - folderPath absolutePath = ${folderPath.absolutePath}")
        Log.d(TAG, "createVideoFolder - folderPath path = ${folderPath.path}")

        try {
            if (folderPath.exists()) {
                Log.d(TAG, "createVideoFolder - folder path exist")
            } else {
                folderPath.mkdirs()
                Log.d(TAG, "createVideoFolder - folder created successfully")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun File.getVideoMediaStoreUri(context: Context): Uri {
        val mediaId = getVideoMediaStoreId(absolutePath, context)
        return ContentUris.withAppendedId(MediaStore.Video.Media.getContentUri("external"), mediaId)
    }

    fun getVideoMediaStoreId(videoPath: String, context: Context): Long {
        var id: Long = 0
        val cr: ContentResolver = context.contentResolver
        val uri: Uri = MediaStore.Files.getContentUri("external")
        val selection: String = MediaStore.Video.Media.DATA
        val selectionArgs = arrayOf(videoPath)
        val projection = arrayOf<String>(MediaStore.Video.Media._ID)
        val sortOrder: String = MediaStore.Video.Media.TITLE.toString() + " ASC"
        val cursor: Cursor? = cr.query(uri, projection, "$selection=?", selectionArgs, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val idIndex: Int = cursor.getColumnIndex(MediaStore.Video.Media._ID)
                id = cursor.getString(idIndex).toLong()
            }
        }
        return id
    }

    fun File.getMediaDuration(context: Context): Long {
        return try {
            if (!exists()) return 0L
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, Uri.parse(absolutePath))
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            retriever.release()
            duration?.toLongOrNull() ?: 0
        } catch (e: Exception) {
            0L
        }
    }
}