package com.example.jetpack.ui.fragment.secondary.meshgradient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.compose.runtime.collectAsState
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.fragment.secondary.meshgradient.component.MeshGradientBackdrop
import com.example.jetpack.ui.fragment.secondary.meshgradient.component.MeshGradientControlPanel
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshPalette
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows an animated "liquid" mesh-gradient backdrop built with Compose's `MeshGradientPainter`
 * (Compose UI 1.12+), plus a panel to play with its settings live.
 *
 * Idea from "Liquid Backdrops: Animated Mesh Gradients in Jetpack Compose" by Siva Vishnu.
 *
 * @author Phong-Kaster
 */
@AndroidEntryPoint
class MeshGradientFragment : CoreFragment() {

    private val viewModel: MeshGradientViewModel by viewModels()

    /**
     * Collects the screen state and wires the panel callbacks to the ViewModel.
     *
     * @author Phong-Kaster
     */
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        val uiState by viewModel.uiState.collectAsState()

        MeshGradientLayout(
            uiState = uiState,
            onBack = { safeNavigateUp() },
            onToggleExpanded = { viewModel.togglePanel() },
            onPaletteChange = { palette -> viewModel.setPalette(palette = palette) },
            onAnimateChange = { isAnimating -> viewModel.setAnimating(isAnimating = isAnimating) },
            onSmoothColorChange = { hasSmoothColor -> viewModel.setSmoothColor(hasSmoothColor = hasSmoothColor) },
            onShowVerticesChange = { showVertices -> viewModel.setShowVertices(showVertices = showVertices) },
            onWobbleChange = { wobble -> viewModel.setWobble(wobble = wobble) },
            onPeriodChange = { periodMillis -> viewModel.setPeriod(periodMillis = periodMillis) },
        )
    }
}

/**
 * Pure UI of the Mesh Gradient screen: top bar, the backdrop filling the rest, a title on top
 * and the control panel at the bottom.
 *
 * @author Phong-Kaster
 */
@Composable
private fun MeshGradientLayout(
    uiState: MeshGradientUiState,
    onBack: () -> Unit = {},
    onToggleExpanded: () -> Unit = {},
    onPaletteChange: (MeshPalette) -> Unit = {},
    onAnimateChange: (Boolean) -> Unit = {},
    onSmoothColorChange: (Boolean) -> Unit = {},
    onShowVerticesChange: (Boolean) -> Unit = {},
    onWobbleChange: (Float) -> Unit = {},
    onPeriodChange: (Int) -> Unit = {},
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.mesh_gradient),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack,
            )
        },
        content = {
            MeshGradientBackdrop(
                palette = uiState.palette,
                animate = uiState.isAnimating,
                periodMillis = uiState.periodMillis,
                wobble = uiState.wobble,
                hasSmoothColor = uiState.hasSmoothColor,
                showVertices = uiState.showVertices,
                modifier = Modifier.fillMaxSize(),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            Text(
                                text = stringResource(R.string.liquid_backdrop),
                                style = customizedTextStyle(fontSize = 28, fontWeight = 700, lineHeight = 36, color = Color.White),
                            )
                            Text(
                                text = stringResource(R.string.mesh_gradient_description),
                                style = customizedTextStyle(fontSize = 14, fontWeight = 400, lineHeight = 20, color = Color.White.copy(alpha = 0.85f)),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            MeshGradientControlPanel(
                                uiState = uiState,
                                onToggleExpanded = onToggleExpanded,
                                onPaletteChange = onPaletteChange,
                                onAnimateChange = onAnimateChange,
                                onSmoothColorChange = onSmoothColorChange,
                                onShowVerticesChange = onShowVerticesChange,
                                onWobbleChange = onWobbleChange,
                                onPeriodChange = onPeriodChange,
                            )
                        },
                    )
                },
            )
        },
    )
}

/**
 * Preview with default settings (animation is frozen in previews by the inspection clock).
 *
 * @author Phong-Kaster
 */
@Preview
@Composable
private fun PreviewMeshGradientLayout() {
    MeshGradientLayout(uiState = MeshGradientUiState(isAnimating = false))
}

/**
 * Preview with the settings card closed, so the whole backdrop shows.
 *
 * @author Phong-Kaster
 */
@Preview(name = "Settings collapsed")
@Composable
private fun PreviewMeshGradientLayoutCollapsed() {
    MeshGradientLayout(uiState = MeshGradientUiState(isAnimating = false, isPanelExpanded = false))
}

/**
 * Preview of the Sunset palette with the net drawn on top.
 *
 * @author Phong-Kaster
 */
@Preview(name = "Sunset with vertices")
@Composable
private fun PreviewMeshGradientLayoutSunset() {
    MeshGradientLayout(
        uiState = MeshGradientUiState(palette = MeshPalette.Sunset, isAnimating = false, showVertices = true),
    )
}
