package com.example.jetpack.ui.fragment.devicephoto.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.example.jetpack.R
import com.example.jetpack.util.ScreenDimension
import com.example.jetpack.util.ScreenDimension.figmaHeightScale
import com.example.jetpack.util.ScreenDimension.figmaWidthScale

@Composable
fun GalleryGridView(
    modifier: Modifier = Modifier,
    devicePhotos: List<String> = emptyList(),
    onOpenCamera: () -> Unit = {},
    onSelectPhoto: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp * figmaHeightScale()),
        horizontalArrangement = Arrangement.spacedBy(4.dp * figmaWidthScale()),
        content = {
            // Camera
            item(
                span = { GridItemSpan(1) },
                content = {
                    Box(
                        modifier = Modifier
                            .background(color = Color(0xFF353535))
                            .width(125.dp * ScreenDimension.figmaWidthScale())
                            .clickable { onOpenCamera() }
                            .wrapContentWidth()
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_camera),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(43.dp * figmaWidthScale())
                        )
                    }
                }
            )

            // Image from device
            itemsIndexed(
                items = devicePhotos,
                itemContent = { index, uri ->
                    val context = LocalContext.current
                    val imageRequest = remember(uri) {
                        ImageRequest.Builder(context)
                            .data(uri)
                            .crossfade(true)
                            .precision(Precision.INEXACT)
                            .build()
                    }

                    Box(
                        modifier = Modifier
                            .background(color = Color(0xFF353535))
                            .width(125.dp * figmaWidthScale())
                            .clickable { onSelectPhoto(uri) }
                            .wrapContentWidth()
                            .aspectRatio(1f)
                    ) {
                        if (LocalInspectionMode.current) {
                            Image(
                                painter = painterResource(R.drawable.img_showcase_7),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .matchParentSize()
                            )
                        } else {
                            AsyncImage(
                                model = imageRequest,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                onError = { },
                                modifier = Modifier
                                    .matchParentSize()
                            )
                        }
                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun PreviewGalleryDeviceList() {
    GalleryGridView(
        devicePhotos = listOf(
            "a",
            "a",
            "a",
            "a",
            "a",
        ),
        modifier = Modifier,
        onOpenCamera = {},
    )
}