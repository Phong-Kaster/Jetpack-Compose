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
import com.example.jetpack.configuration.Constant
import com.example.jetpack.configuration.Constant.NOTIFICATION_AFTER_10_MINUTE_ID
import com.example.jetpack.configuration.Constant.NOTIFICATION_CHANNEL_ID
import com.example.jetpack.domain.enums.NotificationMessage
import com.example.jetpack.notification.NotificationManager.sendNotificationAfter5Minute

/**
 * Send a notification after 10 minute only after users just install app
 */
class NotificationAfter5MinuteReceiver: BroadcastReceiver() {
    private val TAG = "NotificationAfter5MinuteReceiver"
    
    override fun onReceive(context: Context, intent: Intent) {
        println("$TAG - onReceive -------------------------------")
        /*0. Start repeating notification if the device was shut down and then reboot*/
        if ((intent.action == "android.intent.action.BOOT_COMPLETED")) {
            sendNotificationAfter5Minute(context)
        }

//        logEvent("display_notification")

        /*1.Action show notification*/
        popupNotification(context, intent)
    }

    private fun popupNotification(context: Context, intent: Intent) {

        val message = NotificationMessage.FIRST_START
        
        //1. Create an explicit intent for an Activity in your app
        val destinationIntent = Intent(context, MainActivity::class.java)
//        destinationIntent.putExtra(Constant.CLICK_ON_NOTIFICATION, "click")
        destinationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // if there are more than 2 buttons on customized layout then requestCode must not the same between pending intents
        val pendingIntent = PendingIntent.getActivity(
            context,
            Constant.REQUEST_CODE_2,
            destinationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        //2. set customized view
        val layoutSmall = RemoteViews(context.packageName, R.layout.layout_notification_small)
        layoutSmall.setImageViewResource(R.id.notificationAvatar, message.photo)
        layoutSmall.setTextViewText(R.id.notificationTitle, context.getString(message.title))
        layoutSmall.setTextViewText(R.id.notificationContent, context.getString(message.subtitle))
        layoutSmall.setOnClickPendingIntent(R.id.layout_notification_small, pendingIntent)

        val layoutBig = RemoteViews(context.packageName, R.layout.layout_notification_big)
        layoutBig.setImageViewResource(R.id.notificationAvatar, message.photo)
        layoutBig.setTextViewText(R.id.notificationTitle, context.getString(message.title))
        layoutBig.setTextViewText(R.id.notificationContent, context.getString(message.subtitle))
        layoutBig.setTextViewText(R.id.notificationButton, context.getString(message.textButton))
        layoutBig.setOnClickPendingIntent(R.id.notificationButton, pendingIntent)

        //3. define notification builder
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_logo)
                .setContentTitle(context.getString(message.subtitle))
                .setContentText(context.getString(message.title))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(context.getString(message.subtitle))
                )
                .setCustomContentView(layoutSmall)
                .setCustomBigContentView(layoutBig)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)


        //4. Show notification with notificationId which is a unique int for each notification that you must define
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        try {
            notificationManager.cancel(NOTIFICATION_AFTER_10_MINUTE_ID)
            notificationManager.notify(NOTIFICATION_AFTER_10_MINUTE_ID, builder.build())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}