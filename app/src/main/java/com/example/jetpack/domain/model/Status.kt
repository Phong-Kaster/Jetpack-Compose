package com.example.jetpack.domain.model

sealed class Status<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Status<T>(data)
    class Failure<T>(message: String, data: T? = null) : Status<T>(data, message)
    class Loading<T> : Status<T>()
}