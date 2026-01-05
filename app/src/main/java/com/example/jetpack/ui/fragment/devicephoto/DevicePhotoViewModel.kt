package com.example.jetpack.ui.fragment.devicephoto

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.JetpackApplication
import com.example.jetpack.domain.enums.PhotoFolder
import com.example.jetpack.util.PermissionUtil.isPermissionCameraGranted
import com.example.jetpack.util.PermissionUtil.isPermissionStorageGranted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject
import kotlin.collections.isEmpty

@HiltViewModel
class DevicePhotoViewModel
@Inject
constructor(
    val applicationContext: JetpackApplication,
) : ViewModel() {
    private val TAG = this.javaClass.simpleName

    private var _uiState = MutableStateFlow(PhotoUiState())
    val uiState = _uiState.asStateFlow()

    fun refetchPhotoFromFolder(photoFolder: PhotoFolder = PhotoFolder.ALL) {
        viewModelScope.launch {
            try {
                // check have permission
                val accessStorage = isPermissionStorageGranted(context = applicationContext)


                // if not have permission, do nothing
                if (!accessStorage) {
                    return@launch
                }

                val listOfPhotos = when (photoFolder) {
                    PhotoFolder.ALL -> getPhotosFromDevice()
                    PhotoFolder.DOWNLOAD -> getPhotosFromFolderDownload()
                    PhotoFolder.CAMERA, PhotoFolder.SCREENSHOT -> getPhotosFromFolder(photoFolder = photoFolder)
                }


                _uiState.value = _uiState.value.copy(devicePhotos = listOfPhotos)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    suspend fun getPhotosFromDevice(): List<String> {
        try {
            val photos = mutableListOf<String>()

            // Select a row from the direction
            val projection = arrayOf(MediaStore.Images.Media._ID)

            // sort image by date
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            // Query the direction
            val query = applicationContext.contentResolver.query( // query to Media Store
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // uri of gallery
                projection,
                null,
                null,
                sortOrder
            ) // give a cursor data like table

            // take data from cursor
            query?.use { cursor ->
                // get column index
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                // loop through all rows
                while (cursor.moveToNext()) {
                    //
                    val id = cursor.getLong(idColumn)
                    //
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    photos.add(contentUri.toString())
                }
            }

            return photos

        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    suspend fun getPhotosFromFolderDownload(): List<String> {
        try {
            val parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val directory = File(parent, "")
            val imageFiles = mutableListOf<File>()

            val listFiles = directory.listFiles()
            if (listFiles == null || listFiles.isEmpty()) {
                return emptyList()
            }

            for (file in listFiles) {
                if (file.isDirectory) {
                    file.listFiles()?.forEach { childFile ->
                        if (childFile.isFile && childFile.name.endsWith(".jpg")) {
                            imageFiles.add(childFile)
                        }
                    }
                } else if (file.isFile && file.name.endsWith(".jpg")) {
                    imageFiles.add(file)
                }
            }

            val devicePhotos = mutableListOf<String>()
            for (file in imageFiles) {
                val uri = Uri.fromFile(file)
                devicePhotos.add(uri.toString())
            }
            return devicePhotos
        } catch (ex: Exception) {
            ex.printStackTrace()
            return emptyList()
        }
    }

    suspend fun getPhotosFromFolder(photoFolder: PhotoFolder): List<String> {
        try {

            val photos = mutableListOf<String>()

            val projection = arrayOf(
                MediaStore.Images.Media._ID
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            // selection
            val selection = when (photoFolder) {
                PhotoFolder.ALL -> null
                else -> "${MediaStore.Images.Media.RELATIVE_PATH} like ?"
            }

            // selectionArgs
            val selectionArgs = when (photoFolder) {
                PhotoFolder.CAMERA -> arrayOf("%DCIM/Camera%")
                PhotoFolder.SCREENSHOT -> arrayOf("%Screenshots%")
                else -> null
            }

            // query
            val query = applicationContext.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )
                    photos.add(uri.toString())
                }
            }

            return photos
        } catch (ex: Exception) {
            ex.printStackTrace()
            return emptyList()
        }
    }



    fun changeFolderSelected(photoFolder: PhotoFolder) {
        _uiState.value = _uiState.value.copy(photoFolder = photoFolder)
    }
}