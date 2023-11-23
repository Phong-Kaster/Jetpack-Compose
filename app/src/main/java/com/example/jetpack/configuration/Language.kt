package com.example.jetpack.configuration

import androidx.annotation.DrawableRes
import com.example.jetpack.R
import java.util.Locale

enum class Language
    (
    val code: String,
    val displayName: String,
    @DrawableRes
    val drawableId: Int,
) {
    English(code = "en", displayName = "English", drawableId = R.drawable.ic_language_english),
    Hindi(code = "hi", displayName = "Hindi", drawableId = R.drawable.ic_language_hindi),
    Japanese(code = "ja", displayName = "Japanese", drawableId = R.drawable.ic_language_japanese),
    Korean(code = "ko", displayName = "Korean", drawableId = R.drawable.ic_language_korean),
    French(code = "fr", displayName = "French", drawableId = R.drawable.ic_language_french),
    German(code = "de", displayName = "German", drawableId = R.drawable.ic_language_german),
    Portuguese(code = "pt", displayName = "Portuguese", drawableId = R.drawable.ic_language_portuguese),
    Spanish(code = "es", displayName = "Spanish", drawableId = R.drawable.ic_language_spanish);

    companion object {
        fun default(): Language = English

        fun getByCode(code: String?): Language {
            if (code == null) return default()
            return entries.firstOrNull { it.code == code } ?: English
        }

        fun getSortedList(): List<Language> {
            val list = entries.toMutableList()
            val defaultCode = Locale.getDefault().language
            val indexOfDefault = list.indexOfFirst { defaultCode == it.code }
            if (indexOfDefault > 0) {
                val item = list.removeAt(indexOfDefault)
                list.add(0, item)
            }
            return list
        }
    }
}