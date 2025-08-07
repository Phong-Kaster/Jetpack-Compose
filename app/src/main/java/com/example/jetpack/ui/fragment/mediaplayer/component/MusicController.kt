package com.example.jetpack.ui.fragment.mediaplayer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme

@Composable
fun MusicController(
    isPlaying: Boolean,
    onBackward: () -> Unit = {},
    onPlayPause: () -> Unit = {},
    onForward: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // BACKWARD, PLAY PAUSE & FORWARD
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
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