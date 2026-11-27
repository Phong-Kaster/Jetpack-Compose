package com.example.jetpack.network.dto.dummy

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * One shopping cart from `GET https://dummyjson.com/carts`.
 *
 * `@Keep` + `@SerializedName` keep the class and field names intact in release builds so Gson
 * can still fill it from JSON (see [CartsResponse]).
 *
 * Example:
 * ```kotlin
 * val cart = CartDto(id = 1, products = emptyList(), total = 0.0, discountedTotal = 0.0, userId = 7, totalProducts = 0, totalQuantity = 0)
 * ```
 *
 * @param id cart id
 * @param products the products in the cart
 * @param total price before discount
 * @param discountedTotal price after discount
 * @param userId owner of the cart
 * @param totalProducts number of different products
 * @param totalQuantity number of items, counting duplicates
 * @author Phong-Kaster
 */
@Keep
data class CartDto(
    @SerializedName("id") val id: Int,
    @SerializedName("products") val products: List<ProductDto>,
    @SerializedName("total") val total: Double,
    @SerializedName("discountedTotal") val discountedTotal: Double,
    @SerializedName("userId") val userId: Int,
    @SerializedName("totalProducts") val totalProducts: Int,
    @SerializedName("totalQuantity") val totalQuantity: Int
)
