package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName
/**
 * # DTO is Data transfer object
 */
data class Imperial (
    @SerializedName("Value"    ) val Value    : Int?    = null,
    @SerializedName("Unit"     ) val Unit     : String? = null,
    @SerializedName("UnitType" ) val UnitType : Int?    = null
)