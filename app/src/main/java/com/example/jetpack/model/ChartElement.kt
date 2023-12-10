package com.example.jetpack.model

import javax.annotation.concurrent.Immutable

@Immutable
data class ChartElement
constructor(
    val name: String,
    val value: Float
)