package com.example.jetpack.ui.fragment.mediaplayer.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.fragment.mediaplayer.Song
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun SongElement(
    enabled: Boolean,
    song: Song,
    onClick: ()->Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
    ) {
        AnimatedVisibility(
            visible = song.thumbnail == null,
            content = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = LocalTheme.current.secondary)
                        .size(50.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_music_note),
                        contentDescription = "Album",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(25.dp)

                    )
                }
            }
        )


        AnimatedVisibility(
            visible = song.thumbnail != null,
            content = {
                Image(
                    painter = rememberImagePainter(song.thumbnail),
                    contentDescription = "Album",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = LocalTheme.current.secondary)
                        .size(50.dp)
                )
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .wrapContentWidth()
                .weight(1f)
                .height(IntrinsicSize.Min)
        ) {
            Text(
                text = song.name,
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 600,
                    color = if(enabled) LocalTheme.current.primary else LocalTheme.current.textColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(Int.MAX_VALUE),
            )

            Text(
                style = customizedTextStyle(
                    fontSize = 14,
                    fontWeight = 400,
                    color = if (enabled) LocalTheme.current.primary else LocalTheme.current.textColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(Int.MAX_VALUE),
                text = if (song.artist.contains("<unknown>"))
                    stringResource(R.string.unknown)
                else
                    song.artist,
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier,
            content = {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewSongElement() {
    SongElement(
        enabled = false,
        song = Song.getFakeSong2()
    )
}