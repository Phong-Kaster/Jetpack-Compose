package com.example.jetpack.ui.fragment.secondary.meshgradient.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * The math behind the mesh: where every point sits and which colour it carries.
 *
 * Picture a fishing net laid over the screen. The net has [ROWS] x [COLUMNS] squares ("patches"),
 * so it has `(ROWS + 1) x (COLUMNS + 1)` knots ("vertices"). Positions are "normalized": `(0, 0)`
 * is the top-left corner of the screen and `(1, 1)` is the bottom-right corner.
 *
 * Only the inner knots wiggle. The knots on the border stay pinned so the edges of the screen
 * never show an empty gap.
 *
 * Example:
 * ```kotlin
 * val where = MeshLattice.position(row = 1, column = 2, time = 0.25f, wobble = 0.07f)
 * val paint = MeshLattice.color(row = 1, column = 2, colors = MeshPalette.Aurora.colors, time = 0.25f)
 * ```
 *
 * @author Phong-Kaster
 */
object MeshLattice {

    /** Number of patches from top to bottom. */
    const val ROWS = 3

    /** Number of patches from left to right. */
    const val COLUMNS = 3

    /**
     * Biggest safe wobble. The gap between two knots is `1 / 3 ≈ 0.33`. Two neighbouring inner
     * knots moving 0.12 towards each other still keep a 0.09 gap, so no patch folds over another.
     */
    const val MAX_WOBBLE = 0.12f

    /** How strongly the [MeshColors.glow] colour is mixed into the inner knots (0 = none, 1 = all). */
    private const val GLOW_WEIGHT = 0.25f

    private const val TWO_PI = (2 * PI).toFloat()

    /**
     * Tells if a knot is inside the net (true) or on its border (false).
     *
     * @author Phong-Kaster
     */
    fun isInterior(row: Int, column: Int): Boolean {
        return row in 1 until ROWS && column in 1 until COLUMNS
    }

    /**
     * Where a knot sits when nothing moves: an even grid.
     *
     * @author Phong-Kaster
     */
    fun restingPosition(row: Int, column: Int): Offset {
        return Offset(x = column / COLUMNS.toFloat(), y = row / ROWS.toFloat())
    }

    /**
     * Where a knot sits at a given moment of the loop.
     *
     * Each inner knot draws a small "figure 8": x follows one sine wave per loop, y follows two
     * cosine waves per loop. Whole numbers of waves per loop mean the end of the loop lands exactly
     * on its start, so restarting the loop never jumps. Every knot gets its own head start
     * ("phase") so they do not all move together like soldiers.
     *
     * @param time where we are in the loop, from 0 (start) to 1 (end)
     * @param wobble how far a knot may travel away from its resting place
     * @author Phong-Kaster
     */
    fun position(row: Int, column: Int, time: Float, wobble: Float): Offset {
        val resting = restingPosition(row = row, column = column)
        if (!isInterior(row = row, column = column)) return resting

        val phase = (row * (COLUMNS + 1) + column) * 0.23f
        val dx = wobble * sin(TWO_PI * (time + phase))
        val dy = wobble * cos(TWO_PI * (2f * time + phase))
        return resting + Offset(x = dx, y = dy)
    }

    /**
     * Which colour a knot carries at a given moment of the loop.
     *
     * Border knots mix the four corner colours by how close they are to each corner (like mixing
     * paint on a palette) and never change.
     *
     * Inner knots are the "liquid": each one slowly swings between two different palette colours,
     * one full swing per loop, with its own head start. Moving knots that also change colour make
     * the blobs look like they flow into each other. A bit of [MeshColors.glow] keeps the centre
     * bright.
     *
     * @param time where we are in the loop, from 0 (start) to 1 (end)
     * @author Phong-Kaster
     */
    fun color(row: Int, column: Int, colors: MeshColors, time: Float): Color {
        if (!isInterior(row = row, column = column)) return cornerMix(row = row, column = column, colors = colors)

        val ring = listOf(colors.topStart, colors.topEnd, colors.bottomEnd, colors.bottomStart, colors.glow)
        val index = (row - 1) * (COLUMNS - 1) + (column - 1)
        val phase = index * 0.25f
        val swing = 0.5f + 0.5f * sin(TWO_PI * (time + phase))
        val flowing = lerp(start = ring[index % ring.size], stop = ring[(index + 2) % ring.size], fraction = swing)
        return lerp(start = flowing, stop = colors.glow, fraction = GLOW_WEIGHT)
    }

    /**
     * Mixes the four corner colours by how close the knot is to each corner.
     *
     * @author Phong-Kaster
     */
    private fun cornerMix(row: Int, column: Int, colors: MeshColors): Color {
        val across = column / COLUMNS.toFloat()
        val down = row / ROWS.toFloat()
        val top = lerp(start = colors.topStart, stop = colors.topEnd, fraction = across)
        val bottom = lerp(start = colors.bottomStart, stop = colors.bottomEnd, fraction = across)
        return lerp(start = top, stop = bottom, fraction = down)
    }
}
