package com.example.jetpack.ui.fragment.permission.lifecycleobserver

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.jetpack.notification.LockscreenManager
import com.example.jetpack.notification.NotificationManager
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.PermissionUtil
import java.util.concurrent.Callable
import javax.inject.Inject

/**
 * This class encapsulates the logic for requesting notification runtime permissions.
 * It manages the permission request process and handles the user's response within its own scope,
 * rather than relying on direct definitions within an Activity or Fragment.
 *
 * This class is similar to the Notification Runtime Launcher, but instead of using the standard Android alert dialog,
 * it displays a custom Jetpack Compose rationale dialog when requesting notification permissions.
 * To achieve this, it uses an interface to control relevant boolean variables.
 *
 * @see [Receive an activity result in a separate class] https://developer.android.com/training/basics/intents/result#separate
 */
class NotificationLifecycleObserver2
@Inject
constructor(
    private val registry: ActivityResultRegistry,
    private val activity: Activity,
    private val callback: Callback
) : DefaultLifecycleObserver {
    lateinit var launcher: ActivityResultLauncher<String>
    lateinit var settingLauncher: ActivityResultLauncher<Intent>

    private val tag = "NotificationRuntimeLauncher2"
    override fun onCreate(owner: LifecycleOwner) {
        launcher = createRuntimeLauncher(owner)
        settingLauncher = createSystemLauncher(owner)
    }

    /**
     * To request notification runtime permission*/
    private fun createRuntimeLauncher(owner: LifecycleOwner): ActivityResultLauncher<String> {
        return registry.register("notificationLauncher", owner, ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // TODO: do nothing
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.POST_NOTIFICATIONS)
                ) {
                    // TODO: should Show Request Permission Rationale so that we do not need to do any more
                } else {
                    // TODO: show jetpack compose dialog
                    callback.openRationaleDialog()
                }
            }
        }
    }

    /**
     * To open app setting*/
    private fun createSystemLauncher(owner: LifecycleOwner): ActivityResultLauncher<Intent> {
        return registry.register("SettingAppLauncher", owner, ActivityResultContracts.StartActivityForResult()){

        }
    }

    interface Callback {
        fun openRationaleDialog()
    }
}