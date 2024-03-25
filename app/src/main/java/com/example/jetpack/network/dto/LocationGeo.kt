package com.example.jetpack.network.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.UUID


/**
 * # DTO is Data transfer object
 * @author Phong-Apero
 * this class is bases on AccuWeather API - Geoposition Search
 * @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
 * */
@Keep
class LocationGeo(
    @SerializedName("Version") val Version: Int? = null,
    @SerializedName("Key") val Key: String? = UUID.randomUUID().toString(),
    @SerializedName("Type") val Type: String? = null,
    @SerializedName("LocalizedName") val LocalizedName: String? = null,
    @SerializedName("EnglishName") val EnglishName: String? = null,
    @SerializedName("Region") val Region: Region? = Region(),
    @SerializedName("Country") val Country: Country? = Country(),
    @SerializedName("AdministrativeArea") val AdministrativeArea: AdministrativeArea? = AdministrativeArea(),
    @SerializedName("TimeZone") val TimeZone: TimeZone? = TimeZone(),
    @SerializedName("GeoPosition") val GeoPosition: GeoPosition? = GeoPosition(),
    @SerializedName("IsAlias") val IsAlias: Boolean? = null,
    @SerializedName("ParentCity") val ParentCity: ParentCity? = ParentCity(),
): Serializable


