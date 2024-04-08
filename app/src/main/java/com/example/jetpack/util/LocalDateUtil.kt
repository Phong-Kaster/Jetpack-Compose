package com.example.jetpack.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


object LocalDateUtil {

    fun currentEpochDay(): Long = LocalDate.now().toEpochDay()


    fun LocalDateTime.elapsedMinutes(): Int {
        return hour * 60 + minute
    }

    /**
     * return the number of seconds have been elapsed.
     * for instance, now, 15h30 then elapsed seconds is
     * 15(hours) * 60 * 60 + 30(minutes) * 60 + seconds
     * */
    fun LocalDateTime.elapsedSeconds(): Int {
        return this.hour * 60 * 60 + minute * 60 + second
    }

    /**
     * convert from local date to date
     */
    fun LocalDate.toDate(): Date {
        return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    /**
     * convert from Epoch day to date string
     * for example: LocalDate.now().toEpochDay().toDateString() -> 04/04/2024
     */
    fun Long.toDateString(pattern: String = "dd/MM/yyyy"): String {
        val localDate = LocalDate.ofEpochDay(this)
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val formattedString = localDate.format(formatter)

        return formattedString
    }

}