package com.example.jetpack.ui.fragment.mediaplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import com.example.jetpack.R
import com.example.jetpack.backgroundwork.MediaPlayerService
import com.example.jetpack.configuration.Constant
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * This screen creates a basic media player with MediaPlayer.
 * It combines a foreground service and a bound service
 *
 * [Create a Music Player on Android: Song Playback](https://code.tutsplus.com/create-a-music-player-on-android-song-playback--mobile-22778t)
 *
 * [Creating a music player app in Kotlin with MediaPlayer and ExoPlayer](https://reintech.io/blog/creating-music-player-app-kotlin-mediaplayer-exoplayer)
 */
@AndroidEntryPoint
class MediaPlayerFragment2 : CoreFragment() {
    private lateinit var mediaPlayerService: MediaPlayerService
    private var isConnected: Boolean = false
    private val viewModel: MediaPlayerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.collectSongInStorage()
    }

    /*************************************************
     * Defines callbacks for service binding, passed to bindService().
     */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as MediaPlayerService.LocalBinder
            mediaPlayerService = binder.getService()

            // get name of song
            //songName = albums[song].getTitle(context = requireContext())


            //mediaPlayerService.songName = songName
            //mediaPlayerService.song = albums[song]


            mediaPlayerService.callback = callback
            //mediaPlayerService.initializeMediaPlayer()


            mediaPlayerService.fireNotification(currentAction = Constant.ACTION_PAUSE)
            isConnected = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mediaPlayerService.destroyService()
            isConnected = false
        }
    }

    /**
     * ------------------------------ CALLBACK OF MEDIA PLAYER SERVICE ------------------------------
     */
    private val callback = object : MediaPlayerService.Callback {
        override fun next() {
            goForward()
        }

        override fun previous() {
            goBackward()
        }
    }

    override fun onStart() {
        super.onStart()
        startMediaPlayerService()
    }

    override fun onStop() {
        super.onStop()
        stopMediaPlayerService()
    }

    /*************************************************
     * startMediaPlayerService
     */
    private fun startMediaPlayerService() {
        Log.d(TAG, "MediaPlayerService - start")
        val intent = Intent(requireContext(), MediaPlayerService::class.java)
        requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        requireContext().startService(intent)
    }


    /*************************************************
     * stopMediaPlayerService
     */
    private fun stopMediaPlayerService() {
        Log.d(TAG, "MediaPlayerService - stop")
        requireContext().unbindService(connection)
        isConnected = false
    }

    /*************************************************
     * skip next song
     */
    private fun goForward() {
        //song = song.getForward(albums)
        //songName = albums[song].getTitle(context = requireContext())
        //mediaPlayerService.songName = songName
        //mediaPlayerService.song = albums[song]
        mediaPlayerService.playSong()
    }

    private fun goBackward() {
        //song = song.getBackward(albums)
        //songName = albums[song].getTitle(context = requireContext())
        //mediaPlayerService.songName = songName
        //mediaPlayerService.song = albums[song]
        mediaPlayerService.playSong()
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        val isPlaying =
            if (isConnected) {
                mediaPlayerService.isPlaying.collectAsState().value
            } else {
                false
            }

        MediaPlayer2Layout(
            song = viewModel.song.collectAsState().value ?: Song(),
            isPlaying = isPlaying,
            onBack = { safeNavigateUp() },
            onPlayPause = {
                if (isPlaying) {
                    mediaPlayerService.pause()
                } else {
                    mediaPlayerService.resume()
                }
            },
            onBackward = { goBackward() },
            onForward = { goForward() }
        )
    }
}

@Composable
fun MediaPlayer2Layout(
    song: Song,
    isPlaying: Boolean,
    onBack: () -> Unit = {},
    onPlayPause: () -> Unit = {},
    onBackward: () -> Unit = {},
    onForward: () -> Unit = {},
) {
    CoreLayout(
        backgroundColor = Background,
        topBar = {
            CoreTopBar2(
                title = stringResource(id = R.string.media_player),
                titleArrangement = Arrangement.Start,
                onLeftClick = onBack,
            )
        },
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .align(BiasAlignment(horizontalBias = 0f, verticalBias = -0.5f))
                ) {
                    Image(
                        painter = if (song.thumbnail == null)
                            painterResource(R.drawable.img_napoleon_bonaparte)
                        else
                            rememberImagePainter(song.thumbnail),
                        contentDescription = "Album",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = song.name,
                        color = LocalTheme.current.textColor,
                        style = customizedTextStyle(
                            fontWeight = 600,
                            fontSize = 16,
                        ),
                        modifier = Modifier
                            .basicMarquee(Int.MAX_VALUE)
                            .padding(16.dp)
                    )
                }


                // BACKWARD, PLAY PAUSE & FORWARD
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(BiasAlignment(horizontalBias = 0f, verticalBias = 1f))
                        .padding(16.dp)
                ) {
                    IconButton(
                        onClick = onBackward,
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.ic_music_skip_previous),
                                tint = LocalTheme.current.textColor,
                                contentDescription = stringResource(id = R.string.icon),
                                modifier = Modifier
                                    .size(80.dp)
                            )
                        },
                        modifier = Modifier
                    )

                    IconButton(
                        onClick = onPlayPause,
                        content = {
                            Icon(
                                painter =
                                if (isPlaying) painterResource(id = R.drawable.ic_music_pause)
                                else painterResource(id = R.drawable.ic_music_play),
                                tint = LocalTheme.current.textColor,
                                contentDescription = stringResource(id = R.string.icon),
                                modifier = Modifier
                                    .size(48.dp)
                            )
                        },
                        modifier = Modifier

                    )

                    IconButton(
                        onClick = onForward,
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.ic_music_skip_next),
                                tint = LocalTheme.current.textColor,
                                contentDescription = stringResource(id = R.string.icon),
                                modifier = Modifier
                                    .size(80.dp)
                            )
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewMediaPlayer2Layout() {
    MediaPlayer2Layout(
        isPlaying = true,
        song = Song(
            name = "Napoleon",
            duration = 100,
            thumbnail = null,
            size = 100,
            uri = null
        ),
        onBack = {},
        onForward = {},
        onBackward = {},
        onPlayPause = {},
    )
}