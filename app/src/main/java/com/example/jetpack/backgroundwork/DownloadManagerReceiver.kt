package com.example.jetpack.backgroundwork

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import javax.inject.Singleton

@Singleton
class DownloadManagerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id != -1L) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notification = NotificationCompat.Builder(context, "download_channel")
                    .setContentTitle("Download Completed")
                    .setContentText("Your file has been downloaded.")
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)

                    .build()
                notificationManager.notify(id.toInt(), notification)
            }
        }
    }
}