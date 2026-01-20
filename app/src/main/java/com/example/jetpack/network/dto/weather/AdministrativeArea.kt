package com.example.jetpack.network.dto.weather

import com.google.gson.annotations.SerializedName

/**
 * # DTO is Data transfer object
 */
data class AdministrativeArea(
    @SerializedName("ID") val ID: String? = null,
    @SerializedName("LocalizedName") val LocalizedName: String? = null,
    @SerializedName("EnglishName") val EnglishName: String? = null,
    @SerializedName("Level") val Level: Int? = null,
    @SerializedName("LocalizedType") val LocalizedType: String? = null,
    @SerializedName("EnglishType") val EnglishType: String? = null,
    @SerializedName("CountryID") val CountryID: String? = null
) {
    companion object {
        fun getFakeAdministrativeArea(): AdministrativeArea {
            return AdministrativeArea(
                ID = "BB",
                LocalizedName = "Brandenburg",
                EnglishName = "Brandenburg",
                Level = 1,
                LocalizedType = "State",
                EnglishType = "State",
                CountryID = "DE"
            )
        }
    }
}