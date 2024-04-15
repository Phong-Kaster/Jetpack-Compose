package com.example.jetpack.util

import android.annotation.SuppressLint
import java.text.DecimalFormat
import kotlin.math.PI

object NumberUtil {

    fun generateRandomFloat(): Float {
        val min = 1F
        val max = 100F
        val random: Double = min + Math.random() * (max - min)
        return random.toFloat()
    }

    /**
     * convert from 0.25F out of 1F => 25% of 100%
     */
    fun Float.toPercentage(): String {
        val f = DecimalFormat("##.00")
        return f.format(this * 100)
    }


    /**
     * convert from degree to radian
     * for instance, 90 degree to PI / 2 radian
     */
    fun Float.toRadian(): Double {
        return this * (PI / 180F)
    }

    /**
     * convert from radian to degree
     */
    fun Double.toDegree(): Double {
        return this * (180F/ PI)
    }

    private const val MIN_VALUE = 1
    private const val MAX_VALUE = 3999
    private val RomanNumber_M = arrayOf("", "M", "MM", "MMM")
    private val RomanNumber_C = arrayOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
    private val RomanNumber_X = arrayOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
    private val RomanNumber_I = arrayOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")

    @SuppressLint("DefaultLocale")
    fun Int.toRomanNumber(): String {
        require(!(this < MIN_VALUE || this > MAX_VALUE)) {
            java.lang.String.format(
                "The number must be in the range [%d, %d]",
                MIN_VALUE,
                MAX_VALUE
            )
        }
        return StringBuilder()
            .append(RomanNumber_M[this / 1000])
            .append(RomanNumber_C[this % 1000 / 100])
            .append(RomanNumber_X[this % 100 / 10])
            .append(RomanNumber_I[this % 10])
            .toString()
    }

    /**
     * 70.35032 -> 70.35
     * */
    fun Float.withTwoDigits(): String {
        return "%.2f".format(this)
    }


    /**
     * 70.35032 -> 70.3
     * */
    fun Float.withOneDigit(): String {
        return "%.1f".format(this)
    }
}