package com.example.jetpack.ui.fragment.secondary.meshgradient.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The five "paint pots" a mesh gradient mixes from.
 *
 * Think of the screen as a sheet of paper with four corners. Each corner gets one paint pot
 * ([topStart], [topEnd], [bottomStart], [bottomEnd]). The middle of the paper gets a fifth,
 * brighter pot ([glow]) so the centre shines a little.
 *
 * Example:
 * ```kotlin
 * val sunset = MeshColors(
 *     topStart = Color(0xFFFF7A59),
 *     topEnd = Color(0xFFFFC371),
 *     bottomStart = Color(0xFF6A1B9A),
 *     bottomEnd = Color(0xFFD81B60),
 *     glow = Color(0xFFFFE082),
 * )
 * ```
 *
 * @param topStart colour of the top-left corner
 * @param topEnd colour of the top-right corner
 * @param bottomStart colour of the bottom-left corner
 * @param bottomEnd colour of the bottom-right corner
 * @param glow colour mixed into the four inner points
 * @author Phong-Kaster
 */
@Immutable
data class MeshColors(
    val topStart: Color,
    val topEnd: Color,
    val bottomStart: Color,
    val bottomEnd: Color,
    val glow: Color,
)
