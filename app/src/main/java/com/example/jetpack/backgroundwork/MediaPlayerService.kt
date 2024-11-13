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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@AndroidEntryPoint
class MediaPlayerService : Service() {

    private val TAG = this.javaClass.simpleName

    /* For media player */
    private var player: MediaPlayer? = null
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    /*state of MediaPlayer*/
    private var _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    /*name of the song is playing*/
    var songName: String = ""
    var song: Int = 0

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
            Constant.ACTION_PLAY -> {
                if (isPlaying.value) {
                    pause()
                } else {
                    resume()
                }
            }

            Constant.ACTION_PAUSE -> pause()
            Constant.ACTION_STOP -> {
                if (isPlaying.value) {
                    pause()
                }
                destroyService()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                notificationManager.cancel(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID)
            }
        }
        //fireNotification(currentAction = Constant.ACTION_PAUSE)
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
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind")
        return localBinder
    }

    fun destroyService() {
        Log.d(TAG, "destroyService")
        player?.stop()
        player?.release()
        player = null
        _isPlaying.value = false
        notificationManager.cancel(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID)
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

        if (player == null) {
            player = MediaPlayer.create(this, song)
            player?.setAudioAttributes(audioAttributes)
            player?.setOnPreparedListener(callbackPrepared)
        }
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

    fun stop() {
        try {
            player?.stop()
            _isPlaying.value = false
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

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

    private fun createNotification(currentAction: String): Notification {
        Log.d(TAG, "createNotification - current Action = $currentAction")
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)


        val play =
            Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_PLAY }
        val pause =
            Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_PAUSE }
        val stop =
            Intent(this, MediaPlayerReceiver::class.java).apply { action = Constant.ACTION_STOP }

        val playPendingIntent =
            PendingIntent.getBroadcast(this, 0, play, PendingIntent.FLAG_IMMUTABLE)
        val pausePendingIntent =
            PendingIntent.getBroadcast(this, 0, pause, PendingIntent.FLAG_IMMUTABLE)
        val stopPendingIntent =
            PendingIntent.getBroadcast(this, 0, stop, PendingIntent.FLAG_IMMUTABLE)

        /*val builder = NotificationCompat.Builder(this, Constant.FOREGROUND_SERVICE_CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Playing music")
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setColorized(true)
            //.setColor(ContextCompat.getColor(this, R.color.primary))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_iron_cross_wehtmatch)
            .addAction(R.drawable.ic_play, "Play", playPendingIntent)
            .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)
            .addAction(R.drawable.ic_close, "Stop", stopPendingIntent)
            .setSilent(false)
            .setAutoCancel(false)
            .setOngoing(true)
            .setOnlyAlertOnce(true)*/


        // Inflate the small custom notification layout
        val notificationLayoutSmall =
            RemoteViews(packageName, R.layout.layout_notification_music_controller_small)
        val notificationLayoutBig =
            RemoteViews(packageName, R.layout.layout_notification_music_controller_big)


        /*Show button play/pause based on current action*/
        if (currentAction == Constant.ACTION_PLAY) {
            notificationLayoutSmall.setViewVisibility(R.id.button_play, View.GONE)
            notificationLayoutBig.setViewVisibility(R.id.button_play, View.GONE)

            notificationLayoutSmall.setViewVisibility(R.id.button_pause, View.VISIBLE)
            notificationLayoutBig.setViewVisibility(R.id.button_pause, View.VISIBLE)
        } else {
            notificationLayoutSmall.setViewVisibility(R.id.button_play, View.VISIBLE)
            notificationLayoutBig.setViewVisibility(R.id.button_play, View.VISIBLE)

            notificationLayoutSmall.setViewVisibility(R.id.button_pause, View.GONE)
            notificationLayoutBig.setViewVisibility(R.id.button_pause, View.GONE)
        }


        // Inflate the small notification layout
        notificationLayoutSmall.setOnClickPendingIntent(R.id.button_play, playPendingIntent)
        notificationLayoutSmall.setOnClickPendingIntent(R.id.button_pause, pausePendingIntent)
        notificationLayoutSmall.setOnClickPendingIntent(R.id.button_stop, stopPendingIntent)


        // Inflate the big notification layout
        notificationLayoutBig.setTextViewText(R.id.title, songName)
        notificationLayoutBig.setOnClickPendingIntent(R.id.button_play, playPendingIntent)
        notificationLayoutBig.setOnClickPendingIntent(R.id.button_pause, pausePendingIntent)
        notificationLayoutBig.setOnClickPendingIntent(R.id.button_stop, stopPendingIntent)


        val builder = NotificationCompat.Builder(this, Constant.FOREGROUND_SERVICE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_iron_cross_wehtmatch)
            .setContentIntent(pendingIntent)
            .setContentTitle(getString(R.string.app_name))
            .setCustomContentView(notificationLayoutSmall)
            .setCustomBigContentView(notificationLayoutBig)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSilent(false)
            .setAutoCancel(false)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
            )
        return builder.build()
    }


    fun fireNotification(currentAction: String) {
        val notification = createNotification(currentAction = currentAction)
        notificationManager.notify(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID, notification)
    }
}