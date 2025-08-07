package com.example.jetpack.domain.model

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
    var name: String,
    var valueMin: Float,
    var valueMax: Float = 100F,
) {

    class Builder() {
        private var name: String = ""
        private var valueMin: Float = 0f
        private var valueMax: Float = 100F

        fun setName(name: String): Builder {
            this.name = name
            return this
        }

        fun setValueMin(valueMin: Float): Builder {
            this.valueMin = valueMin
            return this
        }

        fun setValueMax(valueMax: Float): Builder {
            this.valueMax = valueMax
            return this
        }

        fun create(): ChartElement {
            return ChartElement(name, valueMin, valueMax)
        }
    }


    companion object {
        fun getFakeElements(take: Int? = null): List<ChartElement> {
            val input =  listOf(
                ChartElement(name = "A", valueMin = 35F, valueMax = 18F),
                ChartElement(name = "B", valueMin = 25F, valueMax = 50F),
                ChartElement(name = "C", valueMin = 50F, valueMax = 31F),
                ChartElement(name = "D", valueMin = 75F, valueMax = 12F),
                ChartElement(name = "E", valueMin = 100F, valueMax = 49F),
                ChartElement(name = "F", valueMin = 12.4F, valueMax = 53F),
                ChartElement(name = "G", valueMin = 43.5F, valueMax = 46F),
                ChartElement(name = "H", valueMin = 34F, valueMax = 90F),
                ChartElement(name = "I", valueMin = 69.5F, valueMax = 123F),
                ChartElement(name = "K", valueMin = 12.56F, valueMax = 150F),
                ChartElement(name = "L", valueMin = 90.5F, valueMax = 100F),
                ChartElement(name = "M", valueMin = 300F, valueMax = 160F),
                ChartElement(name = "N", valueMin = 25F, valueMax = 170F),
                ChartElement(name = "O", valueMin = 10F, valueMax = 180F),
                ChartElement(name = "P", valueMin = 130F, valueMax = 160F),
                ChartElement(name = "Q", valueMin = 125F, valueMax = 150F),
                ChartElement(name = "R", valueMin = -5F, valueMax = 60F),
                ChartElement(name = "S", valueMin = 45F, valueMax = 50F),
                ChartElement(name = "T", valueMin = 32F, valueMax = 40F),
                ChartElement(name = "U", valueMin = 97F, valueMax = 100F),
                ChartElement(name = "V", valueMin = 55F, valueMax = 15F),
                ChartElement(name = "W", valueMin = 80F, valueMax = 25F),
                ChartElement(name = "X", valueMin = 96F, valueMax = 35F),
                ChartElement(name = "Y", valueMin = 88F, valueMax = 45F),
                ChartElement(name = "Z", valueMin = 68F, valueMax = 55F),
            )

            val outcome = if(take == null){
                input
            } else {
                input.take(take)
            }

            return outcome
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