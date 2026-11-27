package com.example.jetpack.ui.fragment.secondary.meshgradient.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.jetpack.R

/**
 * Ready-made colour sets the user can pick on the Mesh Gradient screen.
 *
 * Each entry is just a name plus a [MeshColors]. Add a new entry here and it shows up as a new
 * chip in the control panel, nothing else to change.
 *
 * Example:
 * ```kotlin
 * val colors: MeshColors = MeshPalette.Lagoon.colors
 * ```
 *
 * @param label the chip text
 * @param colors the five colours this palette paints with
 * @author Phong-Kaster
 */
enum class MeshPalette(
    @StringRes val label: Int,
    val colors: MeshColors,
) {
    Aurora(
        label = R.string.aurora,
        colors = MeshColors(
            topStart = Color(0xFF0B1E5B),
            topEnd = Color(0xFF1FA2A6),
            bottomStart = Color(0xFF5B2A86),
            bottomEnd = Color(0xFF0E5E6F),
            glow = Color(0xFF7CF5C8),
        ),
    ),
    Sunset(
        label = R.string.sunset,
        colors = MeshColors(
            topStart = Color(0xFFFF7A59),
            topEnd = Color(0xFFFFC371),
            bottomStart = Color(0xFF6A1B9A),
            bottomEnd = Color(0xFFD81B60),
            glow = Color(0xFFFFE082),
        ),
    ),
    Lagoon(
        label = R.string.lagoon,
        colors = MeshColors(
            topStart = Color(0xFF00B4DB),
            topEnd = Color(0xFF0083B0),
            bottomStart = Color(0xFF00C9A7),
            bottomEnd = Color(0xFF184E77),
            glow = Color(0xFFB5FFFC),
        ),
    ),
}
