package com.example.jetpack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.jetpack.JetpackComposeApplication
import com.example.jetpack.configuration.Constant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingDatastore
@Inject
constructor(app: JetpackComposeApplication) {
    // At the top level of your kotlin file:
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = Constant.SETTING_DATASTORE)
    private val datastore = app.datastore


    // keys of datastore
    private val languageKey = stringPreferencesKey("languageKey")
    private val enableIntroKey = booleanPreferencesKey("enableIntroKey")


    // Enable Intro
    var enableIntro: Boolean
        get() = runBlocking { datastore.data.first()[enableIntroKey] ?: false }
        set(value) = runBlocking { datastore.edit { pref -> pref[enableIntroKey] = value } }
}