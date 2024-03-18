package com.example.jetpack.data.model

import javax.annotation.concurrent.Immutable
import kotlin.math.max
import kotlin.math.min

/**
 * <p>Indicates that the object's state will never change after being created.
 * This is a strong promise, meaning any attempt to modify the object's properties will result in a compile error.
 * When used with composable functions, Compose knows the object won't change, so it can skip recomposing the composable if its other parameters haven't changed.
 * Examples of immutable types include primitives like Int, String, and data classes without mutable properties.
 * </p>
 */
@Immutable
data class ChartElement(
    val name: String,
    val valueMin: Float,
    val valueMax: Float = 100F,
) {
    companion object {
        fun getFakeElements(): List<ChartElement> {
            return listOf(
                ChartElement(name = "A", valueMin = 35F, valueMax = 287F),
                ChartElement(name = "B", valueMin = 25F, valueMax = 199F),
                ChartElement(name = "C", valueMin = 50F, valueMax = 31F),
                ChartElement(name = "D", valueMin = 75F, valueMax = 12F),
                ChartElement(name = "E", valueMin = 100F, valueMax = 49F),
                ChartElement(name = "F", valueMin = 12.4F, valueMax = 53F),
                ChartElement(name = "G", valueMin = 43.5F, valueMax = 46F),
                ChartElement(name = "H", valueMin = 34F, valueMax = 90F),
                ChartElement(name = "I", valueMin = 69.5F, valueMax = 123F),
                ChartElement(name = "K", valueMin = 12.56F, valueMax = 244F),
                ChartElement(name = "L", valueMin = 90.5F, valueMax = 285F),
                ChartElement(name = "M", valueMin = 300F, valueMax = 300F),
                ChartElement(name = "N", valueMin = 0F, valueMax = 300F),
                ChartElement(name = "O", valueMin = 0F, valueMax = 315F),
                ChartElement(name = "P", valueMin = 130F, valueMax = 255F),
                ChartElement(name = "Q", valueMin = 125F, valueMax = 250F),
                ChartElement(name = "R", valueMin = -5F, valueMax = 0F),
                ChartElement(name = "S", valueMin = 0F, valueMax = 0F),
                ChartElement(name = "T", valueMin = 0F, valueMax = 0F),
                ChartElement(name = "U", valueMin = 0F, valueMax = 0F),
                ChartElement(name = "V", valueMin = 0F, valueMax = 15F),
                ChartElement(name = "W", valueMin = 0F, valueMax = 25F),
                ChartElement(name = "X", valueMin = 0F, valueMax = 35F),
                ChartElement(name = "Y", valueMin = 0F, valueMax = 45F),
                ChartElement(name = "Z", valueMin = 0F, valueMax = 55F),
            )
        }

        fun getFakeElement(): ChartElement {
            val value = (0..100).random().toFloat()
            return ChartElement(name = "A", valueMin = value)
        }

        fun ChartElement.getMaxValue(): Float {
            return max(valueMax, valueMin)
        }

        fun ChartElement.getMinValue(): Float {
            return min(valueMax, valueMin)
        }



    }
}