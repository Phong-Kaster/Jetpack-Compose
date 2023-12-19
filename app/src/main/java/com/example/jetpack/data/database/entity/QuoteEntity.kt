package com.example.jetpack.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetpack.data.enums.Category
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "table_quote")
class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "content") val content: String? = null,
    @ColumnInfo(name = "category") val category: List<Category> = listOf(),
    @ColumnInfo(name = "createAt") val createAt: Date = Date(),
    @ColumnInfo(name = "createAtEpochDay") val createAtEpochDay: Long = LocalDate.now().toEpochDay()
)
{

}