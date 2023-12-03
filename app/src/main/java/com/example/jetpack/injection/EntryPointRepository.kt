package com.example.jetpack.injection

import com.example.jetpack.repository.SettingRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EntryPointRepository {
    fun settingRepository(): SettingRepository
}