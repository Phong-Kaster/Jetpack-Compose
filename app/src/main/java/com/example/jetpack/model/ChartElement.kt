package com.example.jetpack.model

import javax.annotation.concurrent.Immutable

@Immutable
data class ChartElement
constructor(
    val name: String,
    val value: Float
){
    companion object{
        fun getFakeData(): List<ChartElement>{
            return listOf(
                ChartElement(name = "A", value = 0F),
                ChartElement(name = "B", value = 25F),
                ChartElement(name = "C", value = 50F),
                ChartElement(name = "D", value = 75F),
                ChartElement(name = "E", value = 100F),
                ChartElement(name = "F", value = 12.4F),
                ChartElement(name = "G", value = 43.5F),
                ChartElement(name = "H", value = 34F),
                ChartElement(name = "I", value = 69.5F),
                ChartElement(name = "K", value = 12.56F),
                ChartElement(name = "L", value = 90.5F),
                ChartElement(name = "M", value = 100F),
            )
        }
    }
}