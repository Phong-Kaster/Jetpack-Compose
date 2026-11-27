package com.example.jetpack.network.dto.dummy

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * The answer of `GET https://dummyjson.com/carts`: a list of shopping carts.
 *
 * `@Keep` + `@SerializedName` stop R8 from renaming or removing this class and its fields in
 * release builds. Without them Gson cannot rebuild the object from JSON and the app crashes
 * with a `ClassCastException` as soon as Home loads.
 *
 * Example:
 * ```kotlin
 * val response: Response<CartsResponse> = dummyService.getDummyCarts()
 * val firstCart: CartDto? = response.body()?.carts?.firstOrNull()
 * ```
 *
 * @param carts every cart in the response
 * @author Phong-Kaster
 */
@Keep
data class CartsResponse(
    @SerializedName("carts") val carts: List<CartDto>
)
