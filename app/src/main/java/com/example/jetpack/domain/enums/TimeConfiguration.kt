package com.example.jetpack.domain.enums

import com.example.jetpack.configuration.Constant

enum class TimeConfiguration(
    val initialPage: Int,
    val maximumPage: Int,
    val step: Int
){
    Hour(initialPage = Constant.INITIAL_PAGE_HOUR, maximumPage = Constant.MAXIMUM_PAGE, step = 24),
    Minute(initialPage = Constant.INITIAL_PAGE_MINUTE, maximumPage = Constant.MAXIMUM_PAGE, step = 60),
    Second(initialPage = Constant.INITIAL_PAGE_SECOND, maximumPage = Constant.MAXIMUM_PAGE, step = 60),
}