package com.example.jetpack.network.dto.weather

import com.google.gson.annotations.SerializedName

/**
 * # DTO is Data transfer object
 */
data class Region (
    @SerializedName("ID"            ) val ID            : String? = null,
    @SerializedName("LocalizedName" ) val LocalizedName : String? = null,
    @SerializedName("EnglishName"   ) val EnglishName   : String? = null
)