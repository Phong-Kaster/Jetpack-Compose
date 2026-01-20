package com.example.jetpack.data.repository

import com.example.jetpack.network.dto.weather.LocationAuto

/**
 * # [Advance Retrofit](https://medium.com/android-alchemy/advance-retrofit-handling-authentication-logging-errors-and-more-2b1c7b7cb26f)
 */
interface WeatherRepository {
    suspend fun searchAutocomplete(keyword: String): List<LocationAuto>
    suspend fun searchLocationByKey()
    suspend fun getCurrentForecastFlow()
}