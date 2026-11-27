package com.example.jetpack.ui.fragment.secondary.meshgradient

import androidx.lifecycle.ViewModel
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshPalette
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Keeps the Mesh Gradient screen settings, so they survive a screen rotation.
 *
 * Each function changes one setting and publishes a new [MeshGradientUiState].
 *
 * Example:
 * ```kotlin
 * viewModel.setPalette(palette = MeshPalette.Lagoon)
 * ```
 *
 * @author Phong-Kaster
 */
@HiltViewModel
class MeshGradientViewModel
@Inject
constructor() : ViewModel() {

    private val TAG = "MeshGradientViewModel"

    private val _uiState = MutableStateFlow(MeshGradientUiState())
    val uiState: StateFlow<MeshGradientUiState> = _uiState.asStateFlow()

    /**
     * Opens the settings card when it is closed, and closes it when it is open.
     *
     * @author Phong-Kaster
     */
    fun togglePanel() {
        _uiState.value = _uiState.value.copy(isPanelExpanded = !_uiState.value.isPanelExpanded)
    }

    /**
     * Picks the palette the backdrop paints with.
     *
     * @author Phong-Kaster
     */
    fun setPalette(palette: MeshPalette) {
        _uiState.value = _uiState.value.copy(palette = palette)
    }

    /**
     * Starts (true) or freezes (false) the drifting motion.
     *
     * @author Phong-Kaster
     */
    fun setAnimating(isAnimating: Boolean) {
        _uiState.value = _uiState.value.copy(isAnimating = isAnimating)
    }

    /**
     * Switches between smooth (Catmull-Rom) and cheaper (bilinear) colour blending.
     *
     * @author Phong-Kaster
     */
    fun setSmoothColor(hasSmoothColor: Boolean) {
        _uiState.value = _uiState.value.copy(hasSmoothColor = hasSmoothColor)
    }

    /**
     * Shows or hides the net drawn on top of the gradient.
     *
     * @author Phong-Kaster
     */
    fun setShowVertices(showVertices: Boolean) {
        _uiState.value = _uiState.value.copy(showVertices = showVertices)
    }

    /**
     * Sets how far the inner knots may travel.
     *
     * @author Phong-Kaster
     */
    fun setWobble(wobble: Float) {
        _uiState.value = _uiState.value.copy(wobble = wobble)
    }

    /**
     * Sets how long one loop of the motion lasts, in milliseconds.
     *
     * @author Phong-Kaster
     */
    fun setPeriod(periodMillis: Int) {
        _uiState.value = _uiState.value.copy(periodMillis = periodMillis)
    }
}
