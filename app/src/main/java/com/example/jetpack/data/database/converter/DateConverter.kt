package com.example.jetpack.data.database.converter

import androidx.room.TypeConverter
import java.util.Date

object DateConverter {
    @TypeConverter
    fun dateToEpochMillis(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun epochMillisToDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }
}