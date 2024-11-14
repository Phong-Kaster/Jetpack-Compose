package com.example.jetpack.ui.fragment.mediaplayer

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import com.example.jetpack.R
import com.example.jetpack.backgroundwork.MediaPlayerService
import com.example.jetpack.backgroundwork.MediaPlayerService2
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.fragment.mediaplayer.component.MusicController
import com.example.jetpack.ui.fragment.mediaplayer.component.SongElement
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.AppUtil.showToast
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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
    private lateinit var mediaPlayerService2: MediaPlayerService2
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
            val binder = service as MediaPlayerService2.LocalBinder
            mediaPlayerService2 = binder.getService()

            // get name of song
            /*songName = albums[song].getTitle(context = requireContext())


            mediaPlayerService.songName = songName
            mediaPlayerService.song = albums[song]*/


            /*mediaPlayerService.callback = callback
            mediaPlayerService.initializeMediaPlayer()


            mediaPlayerService.fireNotification(currentAction = Constant.ACTION_PAUSE)*/
            isConnected = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mediaPlayerService2.destroyService()
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
        /*val intent = Intent(requireContext(), MediaPlayerService::class.java)
        requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        requireContext().startService(intent)*/
    }


    /*************************************************
     * stopMediaPlayerService
     */
    private fun stopMediaPlayerService() {
        Log.d(TAG, "MediaPlayerService - stop")
        /*requireContext().unbindService(connection)*/
        isConnected = false
    }

    /*************************************************
     * skip next song
     */
    private fun goForward() {
        //requireContext().showToast(message = "Forward")
        //song = song.getForward(albums)
        //songName = albums[song].getTitle(context = requireContext())
        //mediaPlayerService.songName = songName
        //mediaPlayerService.song = albums[song]
        //mediaPlayerService.playSong()
        viewModel.goForward()
    }

    private fun goBackward() {
        //requireContext().showToast(message = "Backward")
        //song = song.getBackward(albums)
        //songName = albums[song].getTitle(context = requireContext())
        //mediaPlayerService.songName = songName
        //mediaPlayerService.song = albums[song]
        //mediaPlayerService.playSong()
        viewModel.goBackward()
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        var isPlaying by remember { mutableStateOf(false) }

        MediaPlayer2Layout(
            chosenSong = viewModel.chosenSong.collectAsState().value ?: Song(),
            songs = viewModel.songs.collectAsState().value,
            isPlaying = isPlaying,
            onBack = { safeNavigateUp() },
            onBackward = { goBackward() },
            onForward = { goForward() },
            onChangeSong = { index, song ->
                viewModel.updateChosenSong(
                    chosenIndex = index,
                    chosenSong = song
                )
            },
            onPlayPause = {
                if (isPlaying) {
                    isPlaying = false
                    requireContext().showToast(message = "Pause")
                    //mediaPlayerService.pause()
                } else {
                    isPlaying = true
                    requireContext().showToast(message = "Play")
                    //mediaPlayerService.resume()
                }
            },
        )
    }
}

@Composable
fun MediaPlayer2Layout(
    chosenSong: Song,
    songs: ImmutableList<Song>,
    isPlaying: Boolean,
    onBack: () -> Unit = {},
    onPlayPause: () -> Unit = {},
    onBackward: () -> Unit = {},
    onForward: () -> Unit = {},
    onChangeSong: (Int, Song) -> Unit = { _, _ -> },
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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (chosenSong.thumbnail == null) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(color = LocalTheme.current.secondary)
                                .size(150.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_music_note),
                                contentDescription = "Album",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(75.dp)

                            )
                        }
                    } else {
                        Image(
                            painter = rememberImagePainter(chosenSong.thumbnail),
                            contentDescription = "Album",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(color = LocalTheme.current.secondary)
                                .size(150.dp)
                        )
                    }
                    Text(
                        text = chosenSong.name,
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

                MusicController(
                    isPlaying = isPlaying,
                    onBackward = onBackward,
                    onForward = onForward,
                    onPlayPause = onPlayPause,
                    modifier = Modifier
                )


                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    itemsIndexed(
                        items = songs,
                        key = { index: Int, song: Song -> index },
                        itemContent = { index: Int, song: Song ->
                            SongElement(
                                song = song,
                                onClick = { onChangeSong(index, song) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
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
        chosenSong = Song.getFakeSong2(),
        songs = persistentListOf(
            Song.getFakeSong1(),
            Song.getFakeSong2()
        ),
        onBack = {},
        onForward = {},
        onBackward = {},
        onPlayPause = {},
    )
}