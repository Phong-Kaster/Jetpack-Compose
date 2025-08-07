package com.example.jetpack.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtil {
    /**
     * return true if notification has enabled
     * return false if notification has disabled
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    fun isNotificationEnabled(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks if multiple permissions are granted.
     *
     * @param context The application context.
     * @param permissions A vararg of permissions to check (e.g., Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS).
     * @return True if all specified permissions are granted, false otherwise.
     *
     * Usage example:
     * ```kotlin
     * if (PermissionUtil.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)) {
     *     // Both Camera and Record Audio permissions are granted
     * } else {
     *     // At least one permission is not granted, request them
     * }
     * ```
     */
    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks if a specific permission is granted.
     *
     * @param context The application context.
     * @param permission The permission to check (e.g., Manifest.permission.CAMERA).
     * @return True if the permission is granted, false otherwise.
     *
     * Usage example:
     * ```kotlin
     * if (PermissionUtil.isPermissionGranted(this, Manifest.permission.READ_CONTACTS)) {
     *     // Permission is granted, proceed with the operation
     * } else {
     *     // Permission is not granted, request it
     * }
     * ```
     */
    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}