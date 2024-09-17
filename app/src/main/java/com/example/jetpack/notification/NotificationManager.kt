package com.example.jetpack.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant.NOTIFICATION_CHANNEL_ID
import com.example.jetpack.util.AppUtil
import java.util.Calendar

/**
 * @author Phong-Apero
 * @since 07-09-2023
 * this class is responsible for creating & show notification
 */
object NotificationManager {
    /**
     * Note: Create the NotificationChannel, but only on API 26+ because the NotificationChannel
     * class is new and not in the support library
     */
    fun createNotificationChannel(context: Context) {
        //1. define variable
        val name: CharSequence = context.getString(R.string.app_name)
        val description = context.getString(R.string.fake_message)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = description


        //2. Register the channel with the system; you can't change the importance or other notification behaviors after this
        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }

    fun sendNotification(context: Context) {
        //1. define current time
        val now = Calendar.getInstance()
        val currentHour = now[Calendar.HOUR_OF_DAY]
        val currentMinute = now[Calendar.MINUTE]
        val month = now[Calendar.MONTH] + 1
        val date = now[Calendar.DATE]
        val year = now[Calendar.YEAR]


        //2. find time of next notification
        AppUtil.logcat(message = "now is $date/$month/$year $currentHour:$currentMinute")


        // 3. fire notification at specific time
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmTime = Calendar.getInstance()
        alarmTime.timeInMillis = System.currentTimeMillis()
        alarmTime[Calendar.HOUR_OF_DAY] = 15
        alarmTime[Calendar.MINUTE] = 0
        alarmTime[Calendar.SECOND] = 10
        if (now.after(alarmTime)) {
            alarmTime.add(Calendar.DATE, 1)
        }


        //Final. set up notification at specific time
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.timeInMillis, pendingIntent)
    }
}