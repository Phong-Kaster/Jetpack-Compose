package com.example.jetpack.util

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
}