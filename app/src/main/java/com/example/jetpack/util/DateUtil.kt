package com.example.jetpack.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object DateUtil {
    /**
     * HOUR FORMAT - https://www.digitalocean.com/community/tutorials/java-simpledateformat-java-date-format
     * K -> Hour in am/pm for 12 hour format (0-11)
     * H -> Hour in the day (0-23)
     * h -> Hour in am/pm for 12 hour format (1-12)
     * */
    const val PATTERN_HH_mm = "HH:mm" // 09:15
    const val PATTERN_HH_mm_ss = "HH:mm:ss" // 09:15:45
    const val PATTERN_hh_mm_ss = "hh:mm:ss" // 09:15:45
    const val PATTERN_hh_mm_aa = "hh:mm aa" // 09:15 AM
    const val PATTERN_hh_mm_ss_aa = "KK:mm:ss aa" // 09:15:50 AM

    // DAY FORMAT
    const val PATTERN_EEE_MMM_dd = "EEE, MMM dd" // Mon, December 01
    const val PATTERN_EEEE = "EEEE" // Monday
    const val PATTERN_EEE = "EEE" // Mon
    const val PATTERN_YYYY = "YYYY" // 2024
    const val PATTERN_dd_MMM = "dd MMM" // 14 DEC
    const val PATTERN_MMM = "MMM" // 14 DEC
    const val PATTERN_dd = "dd" // 14


    @SuppressLint("SimpleDateFormat")
    fun Date.formatWithPattern(pattern: String, locale: Locale = Locale.getDefault()): String {
        val simpleDateFormat = SimpleDateFormat(pattern, locale)
        return simpleDateFormat.format(this@formatWithPattern)
    }

    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceDaysBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {
        val difference: Long = fromDate.time - toDate.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return days
    }

    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceHoursBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {
        val difference: Long = fromDate.time - toDate.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return hours
    }

    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceMinutesBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {
        val difference: Long = fromDate.time - toDate.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        return minutes
    }

    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceSecondsBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {
        val difference: Long = fromDate.time - toDate.time
        val seconds = difference / 1000
        return seconds
    }

    fun calculatePercent(sunrise: Date, sunset: Date): Float {
        val calendarSunrise = Calendar.getInstance()
        calendarSunrise.time = sunrise

        val calendarSunset = Calendar.getInstance()
        calendarSunset.time = sunset

        val calendarNow = Calendar.getInstance()
        calendarNow.time = Date()

        val distance: Long = abs(calendarSunrise.timeInMillis - calendarSunset.timeInMillis)
        val elapsedDistance: Long = abs(calendarNow.timeInMillis - calendarSunrise.timeInMillis)
        val percent = (elapsedDistance.toDouble() / distance)


//        Log.d("TAG", "calculatePercent --------------------")
//        Log.d("TAG", "calculatePercent - sunrise = $sunrise")
//        Log.d("TAG", "calculatePercent - sunset =  $sunset")
//        Log.d("TAG", "calculatePercent - distance = $distance")
//        Log.d("TAG", "calculatePercent - elapsedDistance = $elapsedDistance")
//        Log.d("TAG", "calculatePercent - percent = $percent ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance / distance)} ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance - distance)} ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance * distance)} ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance + distance)} ")
        return if(percent > 1) 1f else percent.toFloat()
    }

    /**
     * convert from seconds to hours
     * for example: 18960 seconds = 5 hour 15 minute 30 second
     */
    fun Int.toHour(): Int {
        val outcome = this / (60 * 60)
        return outcome
    }

    /**
     * convert from seconds to hours
     * for example: 18960 seconds = 5 hour 15 minute 30 second
     */
    fun Int.toMinute(): Int {
        val leftSecond = this - this.toHour() * 60 * 60
        val outcome = leftSecond / 60
        return outcome
    }

    /**
     * convert from seconds to hours
     * for example: 18960 seconds = 5 hour 15 minute 30 second
     */
    fun Int.toSecond(): Int {
        return this - this.toHour() * 60 * 60 - this.toMinute() * 60
    }
}