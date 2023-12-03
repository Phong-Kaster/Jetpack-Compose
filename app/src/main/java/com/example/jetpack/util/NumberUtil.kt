package com.example.jetpack.util

import java.text.DecimalFormat

object NumberUtil {

    fun generateRandomFloat(): Float{
        val min = 1F
        val max = 100F
        val random: Double = min + Math.random() * (max - min)
        return random.toFloat()
    }

    /**
     * convert from 0.25F out of 1F => 25% of 100%
     */
    fun Float.toPercentage(): String{
        val f = DecimalFormat("##.00")
        return f.format(this*100)
    }
}