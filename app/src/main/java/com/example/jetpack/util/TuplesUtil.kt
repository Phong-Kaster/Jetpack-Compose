package com.example.jetpack.util

import java.io.Serializable

/**
 * This class simulates the original - Tuple.kt in Kotlin*/
class TuplesUtil {
    /**
     * Represents a fourth of values
     *
     * There is no meaning attached to values in this class, it can be used for any purpose.
     * Triple exhibits value semantics, i.e. two triples are equal if all three components are equal.
     * An example of decomposing it into values:
     *
     * @param A type of the first value.
     * @param B type of the second value.
     * @param C type of the third value.
     * @param D type of the fourth value.
     * @property first First value.
     * @property second Second value.
     * @property third Third value.
     * @property fourth Fourth value
     */
    public data class Fourth<out A, out B, out C, out D>(
        public val first: A,
        public val second: B,
        public val third: C,
        public val fourth: D,
    ) : Serializable {

        /**
         * Returns string representation of the [Fourth] including its [first], [second], [third] & [fourth] values.
         */
        public override fun toString(): String = "($first, $second, $third, $fourth)"
    }

    /**
     * Converts this triple into a list.
     */
    public fun <T> Fourth<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)
}