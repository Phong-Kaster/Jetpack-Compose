package com.example.jetpack.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.content.ContextCompat.startActivity
import java.util.UUID
import androidx.core.net.toUri


object AppUtil {

    /**
     * 826 là chiều cao của màn hình theo FIGMA, đổi lại số này nếu thiet kế figma được vẽ trên màn hình khác
     * For example
     *  Text( text = "Hello World",
     *      fontSize = 16.sp * screenHeightScale
     *)
     * */
    val figmaScaleHeight: Float
        @Composable get() = LocalConfiguration.current.screenHeightDp / 844f

    /**
     * 392 là chiều rộng của màn hình theo FIGMA, đổi lại số này nếu thiet kế figma được vẽ trên màn hình khác
     * For example
     *  Text( text = "Hello World",
     *      fontSize = 16.sp * screenWidthScale
     *)
     * */
    val figmaScaleWidth: Float
        @Composable get() = LocalConfiguration.current.screenWidthDp / 390f

    fun generateUUID(): String = UUID.randomUUID().toString()

    fun Context.showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    fun openWebsite(context: Context, url: String) {
        try {
            val uri = url.toUri()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            context.startActivity(intent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    /*************************************************
     * openSettingPermission
     */
    fun openAppSetting(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}