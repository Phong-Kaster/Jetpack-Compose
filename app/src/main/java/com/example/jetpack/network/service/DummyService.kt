package com.example.jetpack.network.service

import com.example.jetpack.network.dto.dummy.CartsResponse
import retrofit2.Response
import retrofit2.http.GET

interface DummyService {
    @GET("carts")
    suspend fun getDummyCarts(): Response<CartsResponse>
}