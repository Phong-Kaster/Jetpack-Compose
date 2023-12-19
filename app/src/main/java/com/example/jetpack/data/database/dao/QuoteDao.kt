package com.example.jetpack.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpack.data.database.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: QuoteEntity): Long

    @Delete
    fun delete(record: QuoteEntity)

    @Query("SELECT * FROM table_quote ORDER BY table_quote.createAt DESC ")
    fun findAll(): List<QuoteEntity>

    @Query("SELECT * FROM table_quote ORDER BY table_quote.createAt DESC ")
    fun findAllFlow(): Flow<List<QuoteEntity>>


    @Query("SELECT * FROM table_quote WHERE :beginDay <= table_quote.createAt AND table_quote.createAt <= :finishDay ORDER BY table_quote.createAt DESC ")
    fun findFromDateToDate(beginDay: Long, finishDay: Long): List<QuoteEntity>

    @Query("SELECT * FROM table_quote WHERE table_quote.uid = :uid")
    fun findWithID(uid: Long): QuoteEntity
}