package com.example.jetpack.network.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


/**
 * # DTO is Data transfer object
 * @author Phong-Apero
 * this class is bases on AccuWeather API - Autocomplete Search
 * @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
 * */
@Keep
data class LocationAuto(
    @SerializedName("Version"            ) val Version            : Int?                = null,
    @SerializedName("Key"                ) val Key                : String?             = null,
    @SerializedName("Type"               ) val Type               : String?             = null,
    @SerializedName("Rank"               ) val Rank               : Int?                = null,
    @SerializedName("LocalizedName"      ) val LocalizedName      : String?             = null,
    @SerializedName("Country"            ) val Country            : Country?            = Country(),
    @SerializedName("AdministrativeArea" ) val AdministrativeArea : AdministrativeArea? = AdministrativeArea()
){
    companion object{
        fun getFakeLocationAPI(): List<LocationAuto> {

            val country = Country.getFakeCountry()
            val administrativeArea = AdministrativeArea.getFakeAdministrativeArea()

            return listOf(
                LocationAuto(Version = 1, Key = "1024259", Type ="City", Rank = 63, LocalizedName = "Glienicke/Nordbahn", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "995487", Type ="City", Rank = 75, LocalizedName = "Schildow", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "173535", Type ="City", Rank = 83, LocalizedName = "Lindenberg", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "173570", Type ="City", Rank = 83, LocalizedName = "Mühlenbeck", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "2599961", Type ="City", Rank = 85, LocalizedName = "Birkholz", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "2599879", Type ="City", Rank = 85, LocalizedName = "Gartenstadt Großziethen", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "167900", Type ="City", Rank = 85, LocalizedName = "Großziethen", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "3557587", Type ="City", Rank = 85, LocalizedName = "Klarahöh", Country = country, AdministrativeArea = administrativeArea),
                LocationAuto(Version = 1, Key = "1013761", Type ="City", Rank = 85, LocalizedName = "Kleinziethen", Country = country, AdministrativeArea = administrativeArea),
            )
        }
    }
}