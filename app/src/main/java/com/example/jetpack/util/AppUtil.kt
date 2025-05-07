package com.example.jetpack.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.util.UUID


object AppUtil {

    fun generateUUID(): String = UUID.randomUUID().toString()

    fun Context.showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    fun openWebsite(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(uri)
        context.startActivity(intent)
    }
}