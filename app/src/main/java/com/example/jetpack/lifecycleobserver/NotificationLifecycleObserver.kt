package com.example.jetpack.lifecycleobserver

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.jetpack.R
import com.example.jetpack.notification.LockscreenManager
import com.example.jetpack.notification.NotificationManager
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.LogUtil
import com.example.jetpack.util.PermissionUtil
import javax.inject.Inject

/**
 * This class encapsulates the logic for requesting notification runtime permissions.
 * It manages the permission request process and handles the user's response within its own scope,
 * rather than relying on direct definitions within an Activity or Fragment.
 *
 * @see [Receive an activity result in a separate class] https://developer.android.com/training/basics/intents/result#separate
 */
class NotificationLifecycleObserver
@Inject
constructor(
    private val registry: ActivityResultRegistry,
    private val activity: Activity
) : DefaultLifecycleObserver {

    lateinit var systemLauncher: ActivityResultLauncher<String>
    private lateinit var settingLauncher: ActivityResultLauncher<Intent>


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        systemLauncher = createSystemLauncher(owner)
        settingLauncher = createSettingLauncher(owner)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createSystemLauncher(owner: LifecycleOwner): ActivityResultLauncher<String> {
        return registry.register("PermissionLauncher", owner, ActivityResultContracts.RequestPermission()) { isAccessed ->
            if (isAccessed) {
                LockscreenManager.sendNotification(context = activity)
                NotificationManager.sendNotification(context = activity)
            } else {
                if (shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS)) {
                    openRationaleDialog()
                } else {
                    openSettingDialog()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createSettingLauncher(owner: LifecycleOwner): ActivityResultLauncher<Intent> {
        return registry.register("SettingPermissionLauncher", owner, ActivityResultContracts.StartActivityForResult()){
            val enabled = PermissionUtil.isNotificationEnabled(context = activity)
            if (enabled) {
                NotificationManager.sendNotification(context = activity)
                LockscreenManager.sendNotification(context = activity)
            } else {
                LogUtil.logcat("Manifest.permission.POST_NOTIFICATIONS is rejected !")
            }
        }
    }


    /**
     * request POST NOTIFICATION by using Android Runtime System part 2
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openRationaleDialog() {
        //1. define listener for button positive
        val listener =
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                systemLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }


        //2. show dialog
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(activity.getString(R.string.fake_title))
        builder.setMessage(activity.getString(R.string.fake_message))
        builder.setPositiveButton(R.string.ok, listener)
        builder.show()
    }

    /**
     * guide users open app setting to enable POST NOTIFICATION
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openSettingDialog() {
        //1. define listener for button positive
        val positiveListener =
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                //1.1 dismiss intro dialog
                dialog.dismiss()


                //1.2 open app setting
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.setData(uri)

                settingLauncher.launch(intent)
            }


        //2. show dialog
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(activity.getString(R.string.fake_title))
        builder.setMessage(activity.getString(R.string.fake_message))
        builder.setPositiveButton(R.string.ok, positiveListener)
        builder.setNegativeButton(R.string.cancel, null)
        builder.show()
    }
}