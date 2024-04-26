package com.example.jetpack.data.repository

import com.example.jetpack.configuration.Language
import com.example.jetpack.configuration.Logo
import com.example.jetpack.datastore.SettingDatastore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository
@Inject
constructor(
    private val settingDatastore: SettingDatastore,
) {
    // ENABLE INTRO
    fun enableIntro(): Boolean {
        return settingDatastore.enableIntro
    }

    fun setEnableIntro(boolean: Boolean) {
        settingDatastore.enableIntro = boolean
    }

    // ENABLE LANGUAGE INTRO
    fun enableLanguageIntro(): Boolean {
        return settingDatastore.enableLanguageIntro
    }

    fun setEnableLanguageIntro(boolean: Boolean) {
        settingDatastore.enableLanguageIntro = boolean
    }


    // LANGUAGE
    fun getLanguage(): Language {
        return settingDatastore.language
    }

    fun setLanguage(language: Language) {
        settingDatastore.language = language
    }

    fun getLanguageFlow() = settingDatastore.languageFlow

    // LOGO
    fun getLogo(): Logo {
        return settingDatastore.logo
    }

    fun setLogo(logo: Logo){
        settingDatastore.logo = logo
    }
}