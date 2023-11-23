package com.example.jetpack.repository

import com.example.jetpack.datastore.SettingDatastore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository
@Inject
constructor(
    private val settingDatastore: SettingDatastore,
) {
    fun enableIntro(): Boolean { return settingDatastore.enableIntro }
}