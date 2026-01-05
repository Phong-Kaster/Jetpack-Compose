package com.example.jetpack.ui.fragment.devicephoto

import androidx.compose.runtime.Stable
import com.example.jetpack.domain.enums.PhotoFolder

@Stable
data class PhotoUiState(
    /** The currently selected folder/filter when viewing device photos. */
    val photoFolder: PhotoFolder = PhotoFolder.ALL,

    /** List of device photo paths to display in the user’s gallery picker. */
    val devicePhotos: List<String> = emptyList(),
)