package com.example.jetpack.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.jetpack.JetpackApplication
import com.example.jetpack.configuration.Constant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TutorialDatastore
@Inject
constructor(application: JetpackApplication) {
    // At the top level of your kotlin file:
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = Constant.TUTORIAL_DATASTORE)
    private val datastore = application.datastore


    // keys of datastore
    private val enableTutorialKey = booleanPreferencesKey("enableTutorialKey")

    // enable tutorial
    var enableTutorial: Boolean
        get() = runBlocking { datastore.data.first()[enableTutorialKey] ?: true }
        set(value) = runBlocking { datastore.edit { pref -> pref[enableTutorialKey] = value } }
}