package com.example.jetpack.notification

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.jetpack.MainActivity
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant.NOTIFICATION_ID
import com.example.jetpack.configuration.Constant.NOTIFICATION_CHANNEL_ID
import com.example.jetpack.notification.NotificationManager.sendNotification
import com.example.jetpack.util.AppUtil


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        /*0. Start repeating notification if the device was shut down and then reboot*/
        if ((intent.action == "android.intent.action.BOOT_COMPLETED")) {
            sendNotification(context)
        }

        /*1.Action show notification*/
        popupNotification(context, intent)

        /*2.Action prepare for next notification*/
        sendNotification(context)
    }
    private fun popupNotification(context: Context, intent: Intent) {
        //1. Create an explicit intent for an Activity in your app
        val destinationIntent = Intent(context, MainActivity::class.java)
        destinationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 1896, destinationIntent, PendingIntent.FLAG_IMMUTABLE)


        //2. set customized view
        val layoutSmall = RemoteViews(context.packageName, R.layout.layout_notification_small)
        layoutSmall.setTextViewText(R.id.notificationTitle, context.getString(R.string.app_name))
        layoutSmall.setTextViewText(R.id.notificationContent, context.getString(R.string.fake_message))
        layoutSmall.setOnClickPendingIntent(R.id.layout_notification_small, pendingIntent)

        val layoutBig = RemoteViews(context.packageName, R.layout.layout_notification_big)
        layoutBig.setTextViewText(R.id.notificationTitle, context.getString(R.string.app_name))
        layoutBig.setTextViewText(R.id.notificationContent, context.getString(R.string.fake_message))
        layoutBig.setTextViewText(R.id.notificationButton, context.getString(R.string.fake_title))
        layoutBig.setOnClickPendingIntent(R.id.notificationButton, pendingIntent)

        //3. define notification builder
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_nazi_symbol)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.fake_title))
            .setStyle(NotificationCompat.BigTextStyle().bigText(context.getString(R.string.fake_content)))
            .setCustomContentView(layoutSmall)
            .setCustomBigContentView(layoutBig)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)


        //4. Show notification with notificationId which is a unique int for each notification that you must define
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        try {
            notificationManager.cancel(NOTIFICATION_ID)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
