package com.example.jetpack.data.repository

import com.example.jetpack.network.dto.LocationAuto

interface WeatherRepository {
    suspend fun searchAutocomplete(keyword: String): List<LocationAuto>
    suspend fun searchLocationByKey()
    suspend fun getCurrentForecastFlow()
}