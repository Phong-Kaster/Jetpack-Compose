package com.example.jetpack.backgroundwork

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.jetpack.MainActivity
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.ui.fragment.mediaplayer.Song
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@AndroidEntryPoint
class MediaPlayerService2 : Service() {

    private val TAG = this.javaClass.simpleName

    /* For media player */
    private var player: MediaPlayer? = null
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    /*state of MediaPlayer*/
    private var _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    /*name of the song will be played*/
    var song: Song? = null

    /*callback is the way to skip next/previous*/
    var callback: Callback? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        createNotificationChannel()
    }

    @Override
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        Log.d(TAG, "onStartCommand - action = ${intent?.action}")
        createNotificationChannel()
        when (intent?.action) {
            Constant.ACTION_PAUSE -> pause()
            Constant.ACTION_STOP -> stop()
            Constant.ACTION_PLAY -> {
                if (isPlaying.value) {
                    pause()
                } else {
                    resume()
                }
            }

            Constant.ACTION_NEXT -> callback?.next()
            Constant.ACTION_PREVIOUS -> callback?.previous()
        }
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        destroyService()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private val localBinder: IBinder = LocalBinder()

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): MediaPlayerService2 = this@MediaPlayerService2
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind")
        return localBinder
    }


    /**
     * ------------------------------ ONLY FOR MEDIA PLAYER ------------------------------
     */
    private val callbackPrepared = MediaPlayer.OnPreparedListener { mediaPlayer -> }
    fun initializeMediaPlayer() {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()

        if (player != null) return

        player = MediaPlayer.create(this, song?.uri)
        player?.setAudioAttributes(audioAttributes)
        player?.setOnPreparedListener(callbackPrepared)
    }

    fun resume() {
        Log.d(TAG, "resume")
        if (player == null) {
            playSong()
        } else {
            fireNotification(currentAction = Constant.ACTION_PLAY)
            player?.start()
            _isPlaying.value = true
        }
    }

    fun pause() {
        Log.d(TAG, "pause")
        fireNotification(currentAction = Constant.ACTION_PAUSE)
        player?.pause()
        _isPlaying.value = false
    }

    private fun stop() {
        Log.d(TAG, "stop")
        if (isPlaying.value) {
            pause()
        }
        destroyService()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        notificationManager.cancel(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID)
    }

    /**
     * this playSong function runs for start media player for the first time or
     * users click stop notification then return app & click play song immediately
     * */
    fun playSong() {
        Log.d(TAG, "playSong")
        fireNotification(currentAction = Constant.ACTION_PLAY)

        _isPlaying.value = false
        player?.release()
        player = null

        initializeMediaPlayer()
        player?.start()
        _isPlaying.value = true
    }

    fun destroyService() {
        Log.d(TAG, "destroyService")
        _isPlaying.value = false
        player?.stop()
        player?.release()
        player = null
        notificationManager.cancel(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID)
    }

    /**
     * ------------------------------ ONLY FOR NOTIFICATION ------------------------------
     */
    private fun createNotificationChannel() {
        val name = Constant.FOREGROUND_SERVICE_CHANNEL_NAME
        val descriptionText = Constant.FOREGROUND_SERVICE_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(Constant.FOREGROUND_SERVICE_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * create notification with customized layout with 2 form: big & small
     */
    private fun createNotification(currentAction: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)


        /** Define actions for media player */
        val play = Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_PLAY }
        val pause = Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_PAUSE }
        val stop = Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_STOP }
        val previous = Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_PREVIOUS }
        val next = Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_NEXT }


        val playPendingIntent = PendingIntent.getBroadcast(this, 0, play, PendingIntent.FLAG_IMMUTABLE)
        val pausePendingIntent = PendingIntent.getBroadcast(this, 0, pause, PendingIntent.FLAG_IMMUTABLE)
        val stopPendingIntent = PendingIntent.getBroadcast(this, 0, stop, PendingIntent.FLAG_IMMUTABLE)
        val previousPendingIntent = PendingIntent.getBroadcast(this, 0, previous, PendingIntent.FLAG_IMMUTABLE)
        val nextPendingIntent = PendingIntent.getBroadcast(this, 0, next, PendingIntent.FLAG_IMMUTABLE)


        /** Inflate the small custom notification layout*/
        val notificationSmall = RemoteViews(packageName, R.layout.layout_notification_music_controller_small)
        val notificationBig = RemoteViews(packageName, R.layout.layout_notification_music_controller_big)


        /** Show button play/pause based on current action*/
        if (currentAction == Constant.ACTION_PLAY) {
            notificationSmall.setViewVisibility(R.id.button_play, View.GONE)
            notificationBig.setViewVisibility(R.id.button_play, View.GONE)

            notificationSmall.setViewVisibility(R.id.button_pause, View.VISIBLE)
            notificationBig.setViewVisibility(R.id.button_pause, View.VISIBLE)
        } else {
            notificationSmall.setViewVisibility(R.id.button_play, View.VISIBLE)
            notificationBig.setViewVisibility(R.id.button_play, View.VISIBLE)

            notificationSmall.setViewVisibility(R.id.button_pause, View.GONE)
            notificationBig.setViewVisibility(R.id.button_pause, View.GONE)
        }


        /** Inflate the small notification layout*/
        notificationSmall.setOnClickPendingIntent(R.id.button_play, playPendingIntent)
        notificationSmall.setOnClickPendingIntent(R.id.button_pause, pausePendingIntent)
        notificationSmall.setOnClickPendingIntent(R.id.button_next, nextPendingIntent)
        notificationSmall.setOnClickPendingIntent(R.id.button_previous, previousPendingIntent)


        /** Inflate the big notification layout*/
        notificationBig.setTextViewText(R.id.title, song?.name)
        notificationBig.setOnClickPendingIntent(R.id.button_play, playPendingIntent)
        notificationBig.setOnClickPendingIntent(R.id.button_pause, pausePendingIntent)
        notificationBig.setOnClickPendingIntent(R.id.button_stop, stopPendingIntent)
        notificationBig.setOnClickPendingIntent(R.id.button_next, nextPendingIntent)
        notificationBig.setOnClickPendingIntent(R.id.button_previous, previousPendingIntent)


        val builder = NotificationCompat.Builder(this, Constant.FOREGROUND_SERVICE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_iron_cross_wehtmatch)
            .setContentIntent(pendingIntent)
            .setContentTitle(getString(R.string.app_name))
            .setCustomContentView(notificationSmall)
            .setCustomBigContentView(notificationBig)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSilent(false)
            .setAutoCancel(false)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())

        return builder.build()
    }


    fun fireNotification(currentAction: String) {
        val notification = createNotification(currentAction = currentAction)
        notificationManager.notify(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID, notification)
    }


    interface Callback {
        fun next()
        fun previous()
    }
}