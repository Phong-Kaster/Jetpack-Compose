package com.example.jetpack.repository

import com.example.jetpack.configuration.Language
import com.example.jetpack.datastore.SettingDatastore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository
@Inject
constructor(
    private val settingDatastore: SettingDatastore,
) {
    fun enableIntro(): Boolean {
        return settingDatastore.enableIntro
    }

    fun getLanguage(): Language {
        return settingDatastore.language
    }

    fun setLanguage(language: Language) {
        settingDatastore.language = language
    }

    fun getLanguageFlow() = settingDatastore.languageFlow
}