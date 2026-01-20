package com.example.jetpack.network.util

import com.example.jetpack.domain.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


fun <T> safeApiCallFlow(
    apiCall: suspend () -> T
): Flow<Status<T>> = flow {
    try {
        val result = apiCall()
        emit(Status.Success(result))
    } catch (e: SocketTimeoutException) {
        emit(Status.Failure(message = "Request timeout"))
    } catch (e: HttpException) {
        when (e.code()) {
            in 400..499 -> emit(Status.Failure(message = "Client error (${e.code()})"))
            in 500..599 -> emit(Status.Failure(message = "Server error (${e.code()})"))
            else -> emit(Status.Failure(message = "Error is ${e.message}"))
        }
    } catch (e: IOException) {
        emit(Status.Failure(message = "Check internet connection"))
    } catch (e: Exception) {
        emit(Status.Failure(message = "Error ${e.message}"))
    }
}.flowOn(Dispatchers.IO)

