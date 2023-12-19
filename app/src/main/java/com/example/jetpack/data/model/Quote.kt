package com.example.jetpack.data.model

import com.example.jetpack.data.enums.Category
import java.time.LocalDate
import java.util.Date
import javax.annotation.concurrent.Immutable

@Immutable
data class Quote
constructor(
    val uid: Long = 0,
    val content: String? = "",
    val category: List<Category> = listOf(),
    val createAt: Date = Date(),
    val createAtEpochDay: Long = LocalDate.now().toEpochDay()
)
{
}