package com.example.jetpack.network.dto.dummy

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * One product line inside a [CartDto].
 *
 * `@Keep` + `@SerializedName` keep the class and field names intact in release builds so Gson
 * can still fill it from JSON (see [CartsResponse]).
 *
 * Example:
 * ```kotlin
 * val lineTotal: Double = product.price * product.quantity
 * ```
 *
 * @param id product id
 * @param title product name
 * @param price price of one item
 * @param quantity how many items are in the cart
 * @param total price × quantity, before discount
 * @param discountPercentage discount in percent
 * @param discountedTotal total after discount
 * @param thumbnail image URL
 * @author Phong-Kaster
 */
@Keep
data class ProductDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("total") val total: Double,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    @SerializedName("discountedTotal") val discountedTotal: Double,
    @SerializedName("thumbnail") val thumbnail: String
)
