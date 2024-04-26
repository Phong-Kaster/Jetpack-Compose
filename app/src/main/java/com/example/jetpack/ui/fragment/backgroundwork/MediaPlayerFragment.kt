package com.example.jetpack.ui.fragment.backgroundwork

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.backgroundwork.MediaPlayerService
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.fragment.backgroundwork.MediaPlayer.getBackward
import com.example.jetpack.ui.fragment.backgroundwork.MediaPlayer.getForward
import com.example.jetpack.ui.fragment.backgroundwork.MediaPlayer.getTitle
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * This screen creates a basic media player with MediaPlayer.
 * It combines a foreground service and a bound service
 *
 * [Create a Music Player on Android: Song Playback] - https://code.tutsplus.com/create-a-music-player-on-android-song-playback--mobile-22778t
 * [Creating a music player app in Kotlin with MediaPlayer and ExoPlayer] - https://reintech.io/blog/creating-music-player-app-kotlin-mediaplayer-exoplayer
 */
@AndroidEntryPoint
class MediaPlayerFragment : CoreFragment() {

    val TAG = "MediaPlayerService"

    private lateinit var mediaPlayerService: MediaPlayerService
    private var isConnected: Boolean = false


    private var songName: String by mutableStateOf("")
    private var song: Int by mutableIntStateOf(0)
    private val albums = listOf(
        R.raw.sundial_dreams,
        R.raw.the_enchanted_garden,
        R.raw.through_the_arbor
    )

    /** Defines callbacks for service binding, passed to bindService().  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as MediaPlayerService.LocalBinder
            mediaPlayerService = binder.getService()
            mediaPlayerService.initializeMediaPlayer(song = albums[song])


            // get name of song
            songName = albums[song].getTitle(context = requireContext())
            isConnected = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mediaPlayerService.destroyService()
            isConnected = false
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

    private fun startMediaPlayerService() {
        Log.d(TAG, "MediaPlayerService - start")
        val intent = Intent(requireContext(), MediaPlayerService::class.java)
        requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        requireContext().startService(intent)
    }

    private fun stopMediaPlayerService() {
        Log.d(TAG, "MediaPlayerService - stop")
        requireContext().unbindService(connection)
        isConnected = false
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

        ForegroundServiceLayout(
            isPlaying = isPlaying,
            songName = songName,
            onBack = { safeNavigateUp() },
            onPlayPause = {
                if (isPlaying) {
                    mediaPlayerService.pause()
                } else {
                    mediaPlayerService.resume()
                }
            },
            onBackward = {
                song = song.getBackward(albums)
                songName = albums[song].getTitle(context = requireContext())
                mediaPlayerService.playSong(song = albums[song])
            },
            onForward = {
                song = song.getForward(albums)
                songName = albums[song].getTitle(context = requireContext())
                mediaPlayerService.playSong(song = albums[song])
            }
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ForegroundServiceLayout(
    isPlaying: Boolean,
    songName: String,
    onBack: () -> Unit = {},
    onPlayPause: () -> Unit = {},
    onBackward: () -> Unit = {},
    onForward: () -> Unit = {},
) {
    CoreLayout(
        backgroundColor = Background,
        topBar = {
            CoreTopBar2(
                title = stringResource(id = R.string.foreground_service),
                titleArrangement = Arrangement.Start,
                onLeftClick = onBack,
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = songName,
                    color = PrimaryColor,
                    modifier = Modifier
                        .basicMarquee(Int.MAX_VALUE)
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )

                // BACKWARD, PLAY PAUSE & FORWARD
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onBackward,
                        content = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                tint = PrimaryColor,
                                contentDescription = stringResource(id = R.string.icon)
                            )
                        },
                        modifier = Modifier
                    )

                    IconButton(
                        onClick = onPlayPause,
                        content = {
                            Icon(
                                painter =
                                if (isPlaying) painterResource(id = R.drawable.ic_pause)
                                else painterResource(id = R.drawable.ic_play),
                                tint = PrimaryColor,
                                contentDescription = stringResource(id = R.string.icon)
                            )
                        },
                        modifier = Modifier
                    )

                    IconButton(
                        onClick = onForward,
                        content = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                tint = PrimaryColor,
                                contentDescription = stringResource(id = R.string.icon)
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
private fun PreviewForegroundServiceLayout() {
    ForegroundServiceLayout(
        isPlaying = false,
        songName = stringResource(id = R.string.fake_title)
    )
}