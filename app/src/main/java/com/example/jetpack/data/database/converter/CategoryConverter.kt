package com.example.jetpack.data.database.converter

import androidx.room.TypeConverter
import com.example.jetpack.domain.enums.Category
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

object CategoryConverter {

    /** convert from Tag to String*/
    @TypeConverter
    fun categoryToString(tags: List<Category>): String {
        val list = tags.map { tag: Category ->
            tag.name
        }

        val json = Gson().toJson(list)
        return json
    }


    /** convert from String to Tag*/
    @TypeConverter
    fun stringToCategory(json: String): List<Category> {
        val listType = object : TypeToken<List<String>>() {}.type

        val tags = Gson().fromJson<List<String>>(json, listType)

        val listOfBloodPressureTag = try {
            tags.mapNotNull {
                Category.valueOf(it) ?: null
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            listOf()
        }

        return listOfBloodPressureTag
    }
}