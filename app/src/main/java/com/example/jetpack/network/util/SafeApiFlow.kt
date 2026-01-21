package com.example.jetpack.network.util

import com.bumptech.glide.load.engine.Resource
import com.example.jetpack.domain.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException


suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Status<T> {
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Status.Success(body)
            }
        }
        // This handles non-2xx responses (e.g., 404, 500)
        return Status.Failure("Error ${response.code()}: ${response.message()}")
    } catch (e: Exception) {
        // This handles exceptions like no internet connection
        return Status.Failure(e.message ?: "An unexpected error occurred")
    }
}

fun <T : Any> safeApiCallFlow(
    apiCall: suspend () -> Response<T>
): Flow<Status<T>> = flow {

    try {
        val response = apiCall()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(Status.Success(data = body))
                return@flow
            }
        }

        emit(
            value = Status.Failure(
                message = "Http error ${response.code()}: ${response.message()}",
                data = response.body()
            )
        )

    } catch (e: Exception) {

        // 🚨 REQUIRED: do NOT catch cancellation
        if (e is CancellationException) throw e


        val failure = when (e) {
            is SocketTimeoutException ->
                Status.Failure(message = "Request timeout")

            is IOException -> Status.Failure(message = "No internet connection",)

            else -> Status.Failure(message = "Unexpected error",)
        }

        emit(failure)
    }

}.flowOn(Dispatchers.IO)