package com.example.jetpack.repository

import com.example.jetpack.network.dto.LocationAuto

interface WeatherRepository {
    suspend fun searchAutocomplete(keyword: String): List<LocationAuto>
    suspend fun searchLocationByKey()
    suspend fun getCurrentForecastFlow()
}