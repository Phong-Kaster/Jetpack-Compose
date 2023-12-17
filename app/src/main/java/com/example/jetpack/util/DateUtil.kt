package com.example.jetpack.util

import android.annotation.SuppressLint
import com.example.jetpack.configuration.Language
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    @SuppressLint("SimpleDateFormat")
    fun Date.formatWithPattern(pattern: String, locale: Locale = Locale.getDefault()): String {
        val simpleDateFormat = SimpleDateFormat(pattern, locale)
        return simpleDateFormat.format(this@formatWithPattern)
    }
}