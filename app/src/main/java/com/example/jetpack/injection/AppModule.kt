package com.example.jetpack.injection

import android.content.Context
import com.example.jetpack.JetpackComposeApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext context: Context): JetpackComposeApplication {
        return context as JetpackComposeApplication
    }
}