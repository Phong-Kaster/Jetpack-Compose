package com.example.jetpack.network.dto.weather

import com.google.gson.annotations.SerializedName

/**
 * # DTO is Data transfer object
 */
data class TimeZone (
    @SerializedName("Code"             ) val Code             : String?  = null,
    @SerializedName("Name"             ) val Name             : String?  = null,
    @SerializedName("GmtOffset"        ) val GmtOffset        : Float?     = null,
    @SerializedName("IsDaylightSaving" ) val IsDaylightSaving : Boolean? = null,
    @SerializedName("NextOffsetChange" ) val NextOffsetChange : String?  = null
)