package com.example.jetpack.injection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpack.data.database.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val quoteDatabase = "QuoteDatabase"

    @Provides
    @Singleton
    fun provideQuoteDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(context = context, klass = QuoteDatabase::class.java, name = quoteDatabase)
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
    }
}