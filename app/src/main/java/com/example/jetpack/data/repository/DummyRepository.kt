package com.example.jetpack.data.repository

import com.example.jetpack.domain.model.Status
import com.example.jetpack.network.dto.dummy.CartsResponse
import com.example.jetpack.network.dto.weather.LocationAuto
import com.example.jetpack.network.service.DummyService
import com.example.jetpack.network.util.safeApiCallFlow
import com.example.jetpack.util.LogUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DummyRepository
@Inject
constructor(
    private val dummyService: DummyService
) {
    private val TAG = this.javaClass.simpleName
    fun getCarts(): Flow<Status<CartsResponse>> {
        return flow {
            emit(Status.Loading())
            val response = safeApiCallFlow { dummyService.getDummyCarts() }.first()

            if (response is Status.Failure) {
                LogUtil.logcat(tag = TAG, message = "request failed")
                emit(Status.Failure(message = response.message ?: "Unknown error"))
                return@flow
            }

            val data = response.data
            if (data == null) {
                LogUtil.logcat(tag = TAG, message = "data is ${data}, stop!")
                emit(Status.Failure(message = "data is ${data}, stop!"))
                return@flow
            }

            val carts = data.carts
            LogUtil.logcat(tag = TAG, message = "carts is $carts")
            emit(Status.Success(data = data))
        }
    }
}