package com.example.jetpack.ui.fragment.bluetooth.component

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

/**
 * @author Phong-Kaster
 *
 * <p>This class encapsulates the logic for requesting notification runtime permissions.
 * It manages the permission request process and handles the user's response within its own scope,
 * rather than relying on direct definitions within an Activity or Fragment.
 *
 * <p>This class can request multiple runtime permissions simultaneously,
 * instead of requiring separate requests for each permission.
 */
class BluetoothLifecycleObserver
@Inject
constructor(
    private val registry: ActivityResultRegistry,
    private val activity: Activity,
    private val callback: Callback
) : DefaultLifecycleObserver {
    lateinit var launcher: ActivityResultLauncher<Array<String>>

    companion object {
        fun getMandatoryPermissions(): Array<String> {
            val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12 or higher
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN
                )
            } else { // Android 11 or lower
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN
                )
            }

            return permissions
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(owner: LifecycleOwner) {
        launcher = createRuntimeLauncher(owner)
    }


    private fun createRuntimeLauncher(owner: LifecycleOwner): ActivityResultLauncher<Array<String>> {
        return registry.register("multiplePermissionLauncher", owner, ActivityResultContracts.RequestMultiplePermissions()) { map: Map<String, Boolean> ->

            // Android 12 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val enableBluetoothConnect = map[Manifest.permission.BLUETOOTH_CONNECT]
                val enableBluetoothScan = map[Manifest.permission.BLUETOOTH_SCAN]

                if(enableBluetoothConnect == true && enableBluetoothScan ==  true){
                    // TODO: do nothing because everything we need is OK
                } else {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH_CONNECT)){
                        // TODO: do nothing because Android system will request automatically
                    } else if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH_SCAN)){
                        // TODO: do nothing because Android system will request automatically
                    } else {
                        callback.openRationaleDialog()
                    }
                }

            } else { // Android 11 or lower
                val enableBluetooth = map[Manifest.permission.BLUETOOTH]
                val enableBluetoothAdmin = map[Manifest.permission.BLUETOOTH_ADMIN]

                if(enableBluetooth == true && enableBluetoothAdmin ==  true){
                    // TODO: do nothing because everything we need is OK
                } else {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
                        // TODO: do nothing because Android system will request automatically
                    } else if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH_ADMIN)){
                        // TODO: do nothing because Android system will request automatically
                    } else {
                        callback.openRationaleDialog()
                    }
                }
            }
        }
    }

    interface Callback{
        fun openRationaleDialog()
    }
}