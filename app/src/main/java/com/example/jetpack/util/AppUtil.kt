package com.example.jetpack.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.util.UUID
import androidx.core.net.toUri


object AppUtil {

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
        } catch (ex: Exception){
            ex.printStackTrace()
        }

    }
}