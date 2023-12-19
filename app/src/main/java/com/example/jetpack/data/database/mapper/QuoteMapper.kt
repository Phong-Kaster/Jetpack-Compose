package com.example.jetpack.data.database.mapper

import com.example.jetpack.data.database.entity.QuoteEntity
import com.example.jetpack.data.model.Quote

object QuoteMapper {
    fun QuoteEntity.toModel(): Quote {
        return Quote(
            uid = this.uid,
            content = this.content,
            category = this.category,
            createAt = this.createAt,
            createAtEpochDay = this.createAtEpochDay
        )
    }

    fun Quote.toEntity(): QuoteEntity{
        return QuoteEntity(
            uid = this.uid,
            content = this.content,
            category = this.category,
            createAt = this.createAt,
            createAtEpochDay = this.createAtEpochDay
        )
    }
}