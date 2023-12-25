package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName

/**
 * # DTO is Data transfer object
 */
data class Country (
    @SerializedName("ID"            ) val ID            : String? = null,
    @SerializedName("LocalizedName" ) val LocalizedName : String? = null,
    @SerializedName("EnglishName"   ) val EnglishName   : String? = null
){
    companion object{
        fun getFakeCountry(): Country{
            return Country(ID = "DE", LocalizedName = "Deutschland", EnglishName = "Germany")
        }
    }
}