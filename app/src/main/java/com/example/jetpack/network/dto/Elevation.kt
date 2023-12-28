package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName
/**
 * # DTO is Data transfer object
 */
data class Elevation (
    @SerializedName("Metric"   ) val Metric   : Metric?   = Metric(),
    @SerializedName("Imperial" ) val Imperial : Imperial? = Imperial()
)