package com.example.jetpack.data.repository

import com.example.jetpack.network.adapter.AccuWeatherFactory
import com.example.jetpack.network.dto.weather.LocationAuto
import com.example.jetpack.network.service.AccuWeatherService
import com.example.jetpack.network.service.DummyService
import com.example.jetpack.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DummyRepository

@Inject
constructor(
    private val dummyService: DummyService
) {
    private val TAG = this.javaClass.simpleName
    suspend fun getCarts() {
        LogUtil.logcat(tag = TAG, message = "getCarts called")
        val response = withContext(Dispatchers.IO) {
            dummyService.getDummyCarts()
        }
        LogUtil.logcat(tag = TAG, message = "getCarts called response is ${response}")
        LogUtil.logcat(tag = TAG, message = "getCarts response is ${response.body()}")
        //val formattedResponse = AccuWeatherFactory().invoke(response) as List<LocationAuto>

       // return formattedResponse
    }
}