package com.example.jetpack.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.jetpack.R

object SharingUtil {

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