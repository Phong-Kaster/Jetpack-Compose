package com.example.jetpack.network.adapter

import android.util.Log
import retrofit2.Response
import javax.inject.Inject

class AccuWeatherFactory
@Inject
constructor() {
    val tag = this.javaClass.name
    operator fun <T> invoke(response: Response<T>): T? {
        Log.w(tag, "-----------------")
        Log.w(tag, "message: ${response.message()}")
        Log.w(tag, "code: ${response.code()}")
        Log.w(tag, "raw: ${response.raw()}")

        if (response.isSuccessful) {
            return response.body()
        } else {
            when (response.code()) {
                401 -> throw Exception("UnauthorizedException")
                403 -> throw Exception("ServerNotFoundException")
                404 -> throw Exception("ForbiddenException")
                else -> throw Exception()
            }
        }
    }
}