package com.example.jetpack.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetpack.data.database.converter.CategoryConverter
import com.example.jetpack.data.database.converter.DateConverter
import com.example.jetpack.data.database.dao.QuoteDao
import com.example.jetpack.data.database.entity.QuoteEntity

@Database(
    entities = [QuoteEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class, CategoryConverter::class)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}