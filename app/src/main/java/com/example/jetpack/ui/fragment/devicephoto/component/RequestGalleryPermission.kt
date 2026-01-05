package com.example.jetpack.ui.fragment.devicephoto.component

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.jetpack.ui.component.LifecycleComposable
import com.example.jetpack.util.AppUtil.openAppSetting
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

/**
 * A composable used to request CAMERA + STORAGE permissions before opening the camera.
 *
 * Key behaviors:
 * - When [enable] changes, permissions are re-checked immediately.
 * - If permissions are missing → a bottom sheet appears.
 * - If permission is permanently denied (DeniedAlwaysException) → the sheet allows redirecting to app settings.
 * - If user grants permission → bottom sheet closes and [onPhotosPermissionGranted] is triggered.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestGalleryPermission(
    enable: Int,
    onPhotosPermissionGranted: () -> Unit = {}
) {
    val TAG = "RequestGalleryPermission"
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Accompanist permission states
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    // storage permission
    var storageDeniedCount by remember { mutableIntStateOf(0) }
    val storagePermissionState =
        rememberPermissionState(
            permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            }
        )


    // Bottom sheet visibility flag
    var showBottomSheet by remember { mutableStateOf(false) }

    // Flags to indicate whether user must open app settings
    var allowOpenCameraSettings by remember { mutableStateOf(false) }
    var allowOpenStorageSettings by remember { mutableStateOf(false) }

    /**
     * Re-check permissions whenever [enable] changes.
     */
    LaunchedEffect(enable) {
        val cameraGranted = cameraPermissionState.status.isGranted
        val storageGranted = storagePermissionState.status.isGranted

        showBottomSheet = !cameraGranted || !storageGranted

        // Reset flags every new cycle
        allowOpenCameraSettings = false
        allowOpenStorageSettings = false
    }

    /**
     * Request CAMERA permission.
     */
    fun requestCamera() {
        scope.launch {
            cameraPermissionState.launchPermissionRequest()

            when {
                cameraPermissionState.status.isGranted -> {
                    onPhotosPermissionGranted()
                    showBottomSheet = false
                }

                !cameraPermissionState.status.shouldShowRationale -> {
                    // Permanently denied
                    allowOpenCameraSettings = true
                    showBottomSheet = true
                }

                else -> {
                    // Temporarily denied
                    //showBottomSheet = true
                    //openAppSetting(context)
                }
            }
        }
    }

    LifecycleComposable(
        onResumed = {

        }
    )

    /**
     * Request STORAGE permission.
     */
    var hasTryRequestStorage by remember { mutableStateOf(false) }
    fun requestStorage() {
        scope.launch {


            val isGranted = storagePermissionState.status.isGranted
            val shouldShowRationale = storagePermissionState.status.shouldShowRationale

            if(!hasTryRequestStorage) {
                storagePermissionState.launchPermissionRequest()
                hasTryRequestStorage = true
                return@launch
            }



            Log.d(TAG, "requestStorage - ------")
            Log.d(TAG, "requestStorage - called")
            Log.d(TAG, "requestStorage - isGranted is $isGranted")
            Log.d(TAG, "requestStorage - shouldShowRationale is $shouldShowRationale")

            if (isGranted) {
                Toast.makeText(context, "Storage is enable!", Toast.LENGTH_SHORT).show()
                showBottomSheet = false
            } else {
                if (shouldShowRationale) {
                    Log.d(TAG, "requestStorage - should show rationale")
                    storagePermissionState.launchPermissionRequest()
                } else {
                    Log.d(TAG, "requestStorage - open app setting")
                    openAppSetting(context = context)
                }
            }
        }
    }


    /**
     * Permission Bottom Sheet UI.
     */
    GalleryPermissionBottomSheet(
        enable = showBottomSheet,
        isCameraGranted = cameraPermissionState.status.isGranted,
        isStorageGranted = storagePermissionState.status.isGranted,
        onDismiss = { showBottomSheet = false },

        onCameraGranted = {
            if (allowOpenCameraSettings) openAppSetting(context)
            else requestCamera()
        },

        onStorageGranted = {
            if (allowOpenStorageSettings) openAppSetting(context)
            else requestStorage()
        }
    )
}
