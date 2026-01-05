package com.example.jetpack.ui.fragment.devicephoto.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.domain.enums.PhotoFolder
import com.example.jetpack.util.ScreenDimension

@Composable
fun GalleryFolders(
    listFolderOfPhoto: List<PhotoFolder>,
    folderCurrent: PhotoFolder = PhotoFolder.ALL,
    onSelectedPhotoFolder: (PhotoFolder) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp * ScreenDimension.figmaWidthScale()),
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(
            items = listFolderOfPhoto,
            key = { index, item -> "$index ${item.text}" },
        ) { index, folder ->
            PhotoTabElement(
                title = stringResource(folder.text),
                selected = folderCurrent == folder,
                onClick = { onSelectedPhotoFolder(folder) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPhotoLibraryTab(){
    GalleryFolders(
        listFolderOfPhoto = PhotoFolder.entries,
        folderCurrent = PhotoFolder.ALL,
        onSelectedPhotoFolder = {},
    )
}