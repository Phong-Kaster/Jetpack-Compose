package com.example.jetpack.worker

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.notification.NotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * # [Step by Step Guide to Download Files With WorkManager](https://proandroiddev.com/step-by-step-guide-to-download-files-with-workmanager-b0231b03efd1)
 */
@HiltWorker
class DownloadFileWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        val fileUrl = inputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URL) ?: ""
        val fileName = inputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_NAME) ?: ""
        val fileType = inputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_TYPE) ?: ""

        /* If one of 3 fields is empty then stop*/
        if (fileName.isEmpty() || fileType.isEmpty() || fileUrl.isEmpty())
            return Result.failure()

        /*Create notification channel for Android 8 & higher*/
        NotificationManager.createNotificationChannel(context = context)

        /*show notification with progress bar*/
        popupNotification(context = context)

        val uri = getSavedFileUri(
            fileName = fileName,
            fileType = fileType,
            fileUrl = fileUrl,
            context = context
        )

        NotificationManagerCompat.from(context).cancel(Constant.DOWNLOAD_FILE_WORKER_NOTIFICATION_ID)
        return if (uri == null)
            Result.failure()
         else
            Result.success(workDataOf(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URI to uri.toString()))
    }

    private fun popupNotification(context: Context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            return

        val builder = NotificationCompat.Builder(context, Constant.DOWNLOAD_FILE_WORKER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_iron_cross_wehtmatch)
            .setContentTitle("Downloading your file...")
            .setOngoing(true)
            .setProgress(0, 0, true)

        NotificationManagerCompat.from(context)
            .notify(Constant.DOWNLOAD_FILE_WORKER_NOTIFICATION_ID, builder.build())
    }

    /**
     * The getSavedFileUri function downloads a file from a given URL and saves it to the device's storage, returning the URI of the saved file. It handles different scenarios based on the Android version
     *
     * @param fileName The name to give the saved file.
     * @param fileType The type of the file (e.g., "PDF", "PNG", "MP4")
     * @param fileUrl The URL from which to download the file
     * @param context The application context
     * */
    private fun getSavedFileUri(
        fileName: String,
        fileType: String,
        fileUrl: String,
        context: Context
    ): Uri? {
        /*It checks the fileType and sets the corresponding MIME type. If the file type is not recognized, it returns null*/
        val mimeType = when (fileType) {
            "PDF" -> "application/pdf"
            "PNG" -> "image/png"
            "MP4" -> "video/mp4"
            else -> ""
        }

        /* If the MIME type is empty (i.e., the file type was not recognized), it returns null */
        if (mimeType.isEmpty()) return null

        /* Android 10 (API Level 29) and Higher */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/DownloaderDemo")
            }
            val resolver = context.contentResolver

            /* It uses the ContentResolver to insert a new file into the downloads folder with the specified name and MIME type. */
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)


            /* If the file is created successfully, it returns the URI. Otherwise, it returns null */
            return if (uri == null) {
                null
            } else {
                /* It downloads the file from the given URL and writes it to the newly created file */
                URL(fileUrl).openStream().use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                    }
                }
                uri
            }

        } else { /* Android 9 (API Level 28) and Older */
            /* It creates a new file in the downloads directory with the specified name */
            val target = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )

            /* It downloads the file from the given URL and writes it to the newly created file */
            URL(fileUrl).openStream().use { input ->
                FileOutputStream(target).use { output ->
                    input.copyTo(output)
                }
            }


            /* It returns the URI of the saved file */
            return target.toUri()
        }
    }
}