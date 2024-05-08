package com.example.jetpack.data.repository

import com.example.jetpack.data.database.QuoteDatabase
import com.example.jetpack.data.database.entity.QuoteEntity
import com.example.jetpack.data.database.mapper.QuoteMapper.toEntity
import com.example.jetpack.data.database.mapper.QuoteMapper.toModel
import com.example.jetpack.data.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository
@Inject
constructor(
    private val database: QuoteDatabase
) {
    private val dao = database.quoteDao()

    fun delete(quote: Quote) {
        try {
            val entity = quote.toEntity()
            dao.delete(entity)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    suspend fun insertOrUpdate(quote: Quote): Long {
        return try {
            val entity = quote.toEntity()
            dao.insertOrUpdate(entity)
        } catch (ex: Exception) {
            ex.printStackTrace()
            0
        }
    }

    fun findAllFlow(): Flow<List<Quote>> {
        return dao.findAllFlow().map { list ->
            list.map { entity ->
                entity.toModel()
            }
        }
    }

    fun findAll(): List<Quote> {
        return dao.findAll().map { quoteEntity: QuoteEntity ->
            quoteEntity.toModel()
        }
    }
}