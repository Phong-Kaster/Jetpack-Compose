package com.example.jetpack.backgroundwork


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.media3.ui.PlayerNotificationManager
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
    private var _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        createNotificationChannel()
    }

    @Override
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        Log.d(TAG, "onStartCommand - action = ${intent?.action}")
        Log.d(TAG, "onStartCommand - isPlaying.value = ${isPlaying.value}")
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
                stop()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                notificationManager.cancel(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID)
            }
        }
        fireNotification()
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
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
        Log.d(TAG, "onBind: ")
        return localBinder
    }

    fun destroyService() {
        player?.stop()
        player?.release()
        _isPlaying.value = false
        notificationManager.cancel(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID)
    }

    /**
     * ------------------------------ ONLY FOR MEDIA PLAYER ------------------------------
     */
    private val callbackPrepared = MediaPlayer.OnPreparedListener { mediaPlayer -> }
    fun initializeMediaPlayer(song: Int) {
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
        fireNotification()
        player?.start()
        _isPlaying.value = true
    }

    fun pause() {
        player?.pause()
        _isPlaying.value = false
    }

    fun stop() {
        player?.stop()
        _isPlaying.value = false
    }

    fun playSong(song: Int) {
        fireNotification()
        _isPlaying.value = false
        player?.release()
        player = null
        initializeMediaPlayer(song)
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

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val playIntent =
            Intent(this, MediaPlayerService::class.java).apply { action = Constant.ACTION_PLAY }
        val playPendingIntent =
            PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)

        /*val pauseIntent = Intent(this, MediaPlayerService::class.java).apply { action = Constant.ACTION_PAUSE }
        val pausePendingIntent = PendingIntent.getService( this, 1, pauseIntent, PendingIntent.FLAG_IMMUTABLE )*/

        val stopIntent =
            Intent(this, MediaPlayerService::class.java).apply { action = Constant.ACTION_STOP }
        val stopPendingIntent =
            PendingIntent.getService(this, 2, stopIntent, PendingIntent.FLAG_IMMUTABLE)

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

        // Inflate the custom notification layout
        val notificationLayout =
            RemoteViews(packageName, R.layout.layout_notification_music_controller)
        notificationLayout.setOnClickPendingIntent(R.id.notification_play_pause, playPendingIntent)
        notificationLayout.setOnClickPendingIntent(R.id.notification_stop, stopPendingIntent)

        val builder = NotificationCompat.Builder(this, Constant.FOREGROUND_SERVICE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_iron_cross_wehtmatch)
            .setContentIntent(pendingIntent)
            .setContentTitle(getString(R.string.app_name))
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSilent(false)
            .setAutoCancel(false)
            .setOngoing(true)
            .setOnlyAlertOnce(true)


        return builder.build()
    }

    private fun fireNotification() {
        val notification = createNotification()
        notificationManager.notify(Constant.FOREGROUND_SERVICE_NOTIFICATION_ID, notification)
    }
}