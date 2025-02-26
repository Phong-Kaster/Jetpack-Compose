package com.example.jetpack.domain.enums

import androidx.compose.runtime.Stable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Stable
data class CalendarWeekday(
    val localDate: LocalDate = LocalDate.now(),
){
    // For Gregorian Calendar
    val dayOfWeek: String = localDate.format(DateTimeFormatter.ofPattern("E")) // get the day by formatting the date WED, THU
    val isToday: Boolean = LocalDate.now().equals(localDate)
    val gregorianDay: Int = localDate.dayOfMonth
    val gregorianMonth: Int = localDate.monthValue
    val gregorianYear: Int = localDate.year
}