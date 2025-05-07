package com.example.jetpack.core.injection

import com.example.jetpack.data.repository.SettingRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EntryPointRepository {
    fun settingRepository(): SettingRepository
}