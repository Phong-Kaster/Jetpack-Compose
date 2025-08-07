package com.example.jetpack.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.example.jetpack.configuration.Constant.DOWNLOAD_FILE_WORKER_CHANNEL_DESCRIPTION
import com.example.jetpack.configuration.Constant.DOWNLOAD_FILE_WORKER_CHANNEL_ID
import com.example.jetpack.configuration.Constant.DOWNLOAD_FILE_WORKER_CHANNEL_NAME
import com.example.jetpack.configuration.Constant.DOWNLOAD_FILE_WORKER_NOTIFICATION_ID
import com.example.jetpack.util.AppUtil.showToast
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileOutputStream
import java.net.URL


/**
 * # [Step by Step Guide to Download Files With WorkManager](https://proandroiddev.com/step-by-step-guide-to-download-files-with-workmanager-b0231b03efd1)
 */
@HiltWorker
class DownloadFileWorker
@AssistedInject
constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val TAG = this.javaClass.simpleName

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun doWork(): Result {

        val fileUrl = inputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URL) ?: ""
        val fileName = inputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_NAME) ?: ""
        val fileType = inputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_TYPE) ?: ""

        Log.d(TAG, "doWork - fileName = $fileName")
        Log.d(TAG, "doWork - fileUrl = $fileUrl")
        Log.d(TAG, "doWork - fileType = $fileType")

        /* If one of 3 fields is empty then stop*/
        if (fileName.isEmpty() || fileType.isEmpty() || fileUrl.isEmpty())
            return Result.failure()

        /*Create notification channel for Android 8 & higher*/
        createNotificationChannel(context = context)

        /*show notification with progress bar*/
        popupNotification(context = context, fileName = fileName)

        var uri: Uri? = null
        try {
            uri = getSavedFileUri(
                fileName = fileName,
                fileType = fileType,
                fileUrl = fileUrl,
                context = context
            )
        } catch (ex: Exception) {
            Log.d(TAG, "error = ${ex.message}")
            ex.printStackTrace()
        }

        cancelNotification()
        return if (uri == null)
            Result.failure()
        else
            Result.success(workDataOf(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URI to uri.toString()))

    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            DOWNLOAD_FILE_WORKER_CHANNEL_ID,
            DOWNLOAD_FILE_WORKER_CHANNEL_NAME,
            importance
        ).apply {
            description = DOWNLOAD_FILE_WORKER_CHANNEL_DESCRIPTION
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun popupNotification(context: Context, fileName: String) {

        val enableNotification =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
        if (enableNotification != PackageManager.PERMISSION_GRANTED) {
            return
        }


        val builder = NotificationCompat.Builder(context, DOWNLOAD_FILE_WORKER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_nazi_wehtmatch)
            .setContentTitle("Downloading $fileName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setProgress(100, 50, true)

        //4. Show notification with notificationId which is a unique int for each notification that you must define
        val notificationManager = NotificationManagerCompat.from(context)
        try {
            notificationManager.cancel(DOWNLOAD_FILE_WORKER_NOTIFICATION_ID)
            notificationManager.notify(DOWNLOAD_FILE_WORKER_NOTIFICATION_ID, builder.build())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun cancelNotification() {
        NotificationManagerCompat
            .from(context)
            .cancel(DOWNLOAD_FILE_WORKER_NOTIFICATION_ID)
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
            Constant.PDF.first -> Constant.PDF.second
            Constant.PNG.first -> Constant.PNG.second
            Constant.MP4.first -> Constant.MP4.second
            else -> ""
        }

        Log.d(TAG, "getSavedFileUri - mimeType = $mimeType")

        /* If the MIME type is empty (i.e., the file type was not recognized), it returns null */
        if (mimeType.isEmpty()) return null

        /* Android 10 (API Level 29) and Higher */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/Demo")
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