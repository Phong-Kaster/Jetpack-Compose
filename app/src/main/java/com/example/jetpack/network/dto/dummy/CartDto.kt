package com.example.jetpack.network.dto.dummy

data class CartDto(
    val id: Int,
    val products: List<ProductDto>,
    val total: Double,
    val discountedTotal: Double,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)
