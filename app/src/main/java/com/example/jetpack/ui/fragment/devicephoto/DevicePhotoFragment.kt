package com.example.jetpack.ui.fragment.devicephoto

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.domain.enums.PhotoFolder
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.component.LifecycleComposable
import com.example.jetpack.ui.fragment.devicephoto.component.GalleryFolders
import com.example.jetpack.ui.fragment.devicephoto.component.GalleryGridView
import com.example.jetpack.ui.fragment.devicephoto.component.RequestGalleryPermission
import com.example.jetpack.util.NavigationUtil.safePopBackstack
import com.example.jetpack.util.PermissionUtil.isPermissionCameraGranted
import com.example.jetpack.util.PermissionUtil.isPermissionStorageGranted
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class DevicePhotoFragment : CoreFragment() {
    private val viewModel: DevicePhotoViewModel by viewModels()

    private var photoUri: String? = null

    val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            Log.d(TAG, "capture photo success with uri is $photoUri")
        } else {
            null
        }
    }

    fun captureImage(callback: (String?) -> Unit = {}) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takeImageIntent ->
            // Ensure that there's a camera activity to handle the intent
            takeImageIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? = try {
                    File.createTempFile(
                        "IMG_", ".jpg",
                        requireContext().getExternalFilesDir(null)
                    )
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri? = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        it
                    )
                    photoUri = photoURI.toString()
                    takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    cameraLauncher.launch(takeImageIntent)
                }
            }
        }
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()

        val photoFolder = viewModel.uiState.collectAsState().value.photoFolder

        // enable request permission
        var triggerRequestPermission by remember { mutableStateOf(0) }

        // Life cycle owner
        LifecycleComposable(
            onDestroy = { },
            onInitialized = { },
            onCreated = { },
            onStarted = { },
            onResumed = {
                // Fetch photos from device when the screen is resumed
                viewModel.refetchPhotoFromFolder(photoFolder = photoFolder)

                println("$TAG --------------------------------------------------")
                println("$TAG - camera granted is ${isPermissionCameraGranted(context = requireContext())}")
                println("$TAG - storage granted is ${isPermissionStorageGranted(context = requireContext())}")
                triggerRequestPermission += 1
            }
        )


        DevicePhotoLayout(
            uiState = viewModel.uiState.collectAsState().value,
            onBack = { safePopBackstack() },
            onOpenCamera = {
                // no camera permission, open bottom sheet
                if (!isPermissionCameraGranted(context = requireContext())) {
                    triggerRequestPermission += 1
                    return@DevicePhotoLayout
                }

                // camera permission, open camera
                captureImage(callback = { photoUri ->
                    Log.d(TAG, "ComposeView - photoUri is $photoUri")
                })
            },
            onSelectPhoto = { uri: String -> },
            onSelectedPhotoFolder = { photoFolder ->
                viewModel.refetchPhotoFromFolder(photoFolder)
                viewModel.changeFolderSelected(photoFolder)
            },
        )

        RequestGalleryPermission(
            enable = triggerRequestPermission,
            onPhotosPermissionGranted = { viewModel.refetchPhotoFromFolder(photoFolder) }
        )
    }
}

@Composable
private fun DevicePhotoLayout(
    uiState: PhotoUiState,
    onBack: () -> Unit = {},
    onSelectedPhotoFolder: (PhotoFolder) -> Unit = {},
    onOpenCamera: () -> Unit = {},
    onSelectPhoto: (String) -> Unit = {},
) {
    CoreLayout(
        topBar = {
            CoreTopBar2(
                onLeftClick = onBack,
                title = stringResource(id = R.string.library),

                )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {

                // Photo Library Tab
                GalleryFolders(
                    listFolderOfPhoto = PhotoFolder.entries,
                    folderCurrent = uiState.photoFolder,
                    onSelectedPhotoFolder = onSelectedPhotoFolder,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                // Gallery Grid View
                GalleryGridView(
                    devicePhotos = uiState.devicePhotos,
                    onOpenCamera = onOpenCamera,
                    onSelectPhoto = onSelectPhoto,
                    modifier = Modifier,
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDevicePhotoLayout() {
    DevicePhotoLayout(
        uiState = PhotoUiState(
            devicePhotos = listOf("a", "b", "c", "d", "e")
        )
    )
}