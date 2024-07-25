package com.example.jetpack.data.database.mapper

import com.example.jetpack.data.database.entity.QuoteEntity
import com.example.jetpack.data.database.mapper.QuoteMapper.toModel
import com.example.jetpack.domain.model.Quote

object QuoteMapper {
    fun QuoteEntity.toModel(): Quote {
        return Quote(
            uid = this.uid,
            content = this.content,
            value = this.value,
            category = this.category,
            createAt = this.createAt,
            createAtEpochDay = this.createAtEpochDay
        )
    }

    fun Quote.toEntity(): QuoteEntity{
        return QuoteEntity(
            uid = this.uid ?: 0,
            content = this.content,
            value = this.value,
            category = this.category,
            createAt = this.createAt,
            createAtEpochDay = this.createAtEpochDay
        )
    }
}