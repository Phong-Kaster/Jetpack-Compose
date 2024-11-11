package com.example.jetpack.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.jetpack.R
import java.util.UUID


object AppUtil {
    fun logcat(message: String, tag: String = "Jetpack Compose", enableDivider: Boolean = false) {
        if(enableDivider){
            Log.d(tag, "----------------------------")
        }
        Log.d(tag, "-> message = $message")

    }

    fun generateUUID(): String = UUID.randomUUID().toString()

    fun openWebsite(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(uri)
        context.startActivity(intent)
    }

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun hideNavigationBar(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            isAppearanceLightNavigationBars = false
            hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    fun composeEmail(context: Context, star: Int, feedback: String): Intent {
        val subject = context.getString(R.string.blood_sugar_feedback)
        val body =
            "• Application Name: ${context.getString(R.string.app_name)}" + "\n\n• Rate star: $star star / 5 star" + "\n\n• Customer feedback: ${feedback.trim()}"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("n18dccn147@student.ptithcm.edu.vn"))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)

        return intent
    }

    fun copyToClipboard(context: Context, text: String){
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }

    fun shareApplication(context: Context){
        try {
            var shareMessage = context.getString(R.string.let_me_recommend_you_this_application)
            //shareMessage = (shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID) + "\n\n"

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)


            val chooserIntent = Intent.createChooser(shareIntent, "")

            context.startActivity(chooserIntent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}