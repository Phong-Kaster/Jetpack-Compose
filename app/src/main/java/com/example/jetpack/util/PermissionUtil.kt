package com.example.jetpack.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

object PermissionUtil {
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    fun isNotiEnabled(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }
}