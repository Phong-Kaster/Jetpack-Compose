package com.example.jetpack.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetpack.domain.enums.Category
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "table_quote")
class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "content") val content: String? = null,
    @ColumnInfo(name = "value") val value: Float = 0F,
    @ColumnInfo(name = "category") val category: Category? = null,
    @ColumnInfo(name = "createAt") val createAt: Date = Date(),
    @ColumnInfo(name = "createAtEpochDay") val createAtEpochDay: Long = LocalDate.now().toEpochDay()
)
{

}