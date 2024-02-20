package com.example.jetpack.lifecycleobserver

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

/**
 * @author Phong-Kaster
 *
 * This class encapsulates the logic for requesting notification runtime permissions.
 * It manages the permission request process and handles the user's response within its own scope,
 * rather than relying on direct definitions within an Activity or Fragment.
 *
 * This class can request multiple runtime permissions simultaneously, instead of requiring separate requests for each permission.
 */
class MultiplePermissionsLifecycleObserver
@Inject
constructor(
    private val registry: ActivityResultRegistry,
    private val activity: Activity,
    private val callback: Callback
) : DefaultLifecycleObserver {
    lateinit var launcher: ActivityResultLauncher<Array<String>>

    companion object {
        val mandatoryPermissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.RECORD_AUDIO
        )
    }


    override fun onCreate(owner: LifecycleOwner) {
        launcher = createRuntimeLauncher(owner)
    }

    private fun createRuntimeLauncher(owner: LifecycleOwner): ActivityResultLauncher<Array<String>> {
        return registry.register("multiplePermissionLauncher", owner, ActivityResultContracts.RequestMultiplePermissions()) { map: Map <String, Boolean> ->
            val enableRecordAudio = map[android.Manifest.permission.RECORD_AUDIO]
            val enableAccessCoarseLocation = map[android.Manifest.permission.ACCESS_COARSE_LOCATION]
            val enableAccessFineLocation = map[android.Manifest.permission.ACCESS_FINE_LOCATION]


            if (enableRecordAudio == true && enableAccessCoarseLocation == true && enableAccessFineLocation == true) {
                // TODO: do nothing because everything we need is OK
            } else {
                if (shouldShowRequestPermissionRationale(activity, android.Manifest.permission.RECORD_AUDIO)) {
                    // TODO: do nothing because Android system will request automatically
                } else if (shouldShowRequestPermissionRationale(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // TODO: do nothing because Android system will request automatically
                } else if (shouldShowRequestPermissionRationale(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // TODO: do nothing because Android system will request automatically
                } else {
                    callback.openRationaleDialog()
                }
            }
        }
    }

    interface Callback {
        fun openRationaleDialog()
    }
}