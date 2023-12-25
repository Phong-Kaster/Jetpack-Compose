package com.example.jetpack.network.service

import com.example.jetpack.network.dto.LocationGeo
import com.example.jetpack.network.dto.LocationAuto
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeatherService {
    /**
     * https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
     */
    @GET("/locations/v1/cities/geoposition/search")
    fun searchGeoposition( @Query("q") lnglat: String ): Response<LocationGeo>


    /**
     * https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/autocomplete
     */
    @GET("/locations/v1/cities/autocomplete")
    suspend fun searchAutocomplete( @Query("q") keyword: String ): Response<List<LocationAuto>>


    /**
     * https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/%7BlocationKey%7D
     */
    @GET("/locations/v1/{locationKey}")
    fun searchLocationByKey( @Path("locationKey") locationKey: String ): Deferred<Response<LocationGeo>>

   /* @GET("/currentconditions/v1/{locationKey}")
    fun getCurrentLocation(
        @Path("locationKey") locationKey: String
    ): Deferred<Response<List<CurrentForecastResponse>>>

    @GET("/forecasts/v1/hourly/72hour/{locationKey}")
    fun get12HoursForecast(
        @Path("locationKey") locationKey: String
    ): Deferred<Response<List<HourlyResponse>>>

    @GET("/forecasts/v1/daily/10day/{locationKey}")
    fun get10DaysForecast(
        @Path("locationKey") locationKey: String
    ): Deferred<Response<DailyBaseResponse>>

    @GET("/imagery/v1/maps/radsat/1024x1024/{locationKey}")
    fun getMaps(
        @Path("locationKey") locationKey: String
    ): Deferred<Response<MapResponse>>

    @POST("https://api.reliefweb.int/v1/reports?appname=WeatherLive")
    fun getListDisaster(@Body text: JsonObject = JsonObject()): Deferred<Response<BaseListResponse<DisasterListResponse>>>

    @GET("https://api.reliefweb.int/v1/reports/{id}")
    fun getListDisaster(@Path("id") id: String): Deferred<Response<BaseListResponse<DisasterDetailResponse>>>*/
}