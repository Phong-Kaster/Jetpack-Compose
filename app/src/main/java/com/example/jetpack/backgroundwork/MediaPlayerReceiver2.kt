package com.example.jetpack.backgroundwork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.jetpack.configuration.Constant

class MediaPlayerReceiver2 : BroadcastReceiver() {
    private val TAG = this.javaClass.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive")

        if (intent == null) {
            Log.d(TAG, "onReceive - intent is null")
            return
        }


        val actionToMediaPlayerService = Intent(context, MediaPlayerService::class.java)
        when (intent.action) {
            Constant.ACTION_PLAY -> actionToMediaPlayerService.action = Constant.ACTION_PLAY
            Constant.ACTION_PAUSE -> actionToMediaPlayerService.action = Constant.ACTION_PAUSE
            Constant.ACTION_STOP -> actionToMediaPlayerService.action = Constant.ACTION_STOP
            Constant.ACTION_NEXT -> actionToMediaPlayerService.action = Constant.ACTION_NEXT
            Constant.ACTION_PREVIOUS -> actionToMediaPlayerService.action = Constant.ACTION_PREVIOUS
        }

        try {
            context?.startService(actionToMediaPlayerService)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "onReceive - error = ${e.message}")
        }
    }
}