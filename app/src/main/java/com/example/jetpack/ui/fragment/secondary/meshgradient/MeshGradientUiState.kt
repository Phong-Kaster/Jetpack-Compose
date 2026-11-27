package com.example.jetpack.ui.fragment.secondary.meshgradient

import androidx.compose.runtime.Immutable
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshPalette

/**
 * Everything the Mesh Gradient screen needs to draw itself.
 *
 * Example:
 * ```kotlin
 * val frozenSunset = MeshGradientUiState(palette = MeshPalette.Sunset, isAnimating = false)
 * ```
 *
 * @param palette the five colours the backdrop paints with
 * @param isAnimating true lets the inner knots drift; false freezes the picture
 * @param hasSmoothColor true = Catmull-Rom colour blending, false = cheaper bilinear blending
 * @param showVertices true draws the net and its knots on top of the gradient
 * @param wobble how far the inner knots travel (0 to `MeshLattice.MAX_WOBBLE`)
 * @param periodMillis how long one loop of the motion lasts
 * @param isPanelExpanded true shows the whole settings card; false shows only its header
 * @author Phong-Kaster
 */
@Immutable
data class MeshGradientUiState(
    val palette: MeshPalette = MeshPalette.Aurora,
    val isAnimating: Boolean = true,
    val hasSmoothColor: Boolean = true,
    val showVertices: Boolean = false,
    val wobble: Float = 0.10f,
    val periodMillis: Int = 10_000,
    val isPanelExpanded: Boolean = true,
)
