package com.example.jetpack.repository


import com.example.jetpack.network.adapter.AccuWeatherFactory
import com.example.jetpack.network.dto.LocationAuto
import com.example.jetpack.network.service.AccuWeatherService
import com.example.jetpack.util.AppUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class WeatherRepositoryImplement
@Inject
constructor(
    private val accuWeatherService: AccuWeatherService
) : WeatherRepository {
    override suspend fun searchAutocomplete(keyword: String): List<LocationAuto> {
        val response = withContext(Dispatchers.IO) {
            accuWeatherService.searchAutocomplete(keyword = keyword)
        }

        val formattedResponse = AccuWeatherFactory().invoke(response) as List<LocationAuto>

       return formattedResponse
    }

    override suspend fun searchLocationByKey() {

    }

    override suspend fun getCurrentForecastFlow() {

    }
}

fun <T> networkCallback(
    onResponse: (Call<T>, Response<T>) -> Unit,
    onFail: (Call<T>, Throwable) -> Unit
): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(
            call: Call<T>,
            response: Response<T>
        ) {
            onResponse(call, response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFail(call, t)
        }
    }
}