package com.example.jetpack.ui.fragment.secondary.meshgradient.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.fragment.secondary.meshgradient.MeshGradientUiState
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshLattice
import com.example.jetpack.ui.fragment.secondary.meshgradient.model.MeshPalette
import com.example.jetpack.ui.theme.customizedTextStyle
import java.util.Locale

/** How long the panel takes to open or close. */
private const val TOGGLE_MILLIS = 220

/** Shortest and longest loop the speed slider allows, in milliseconds. */
private const val MIN_PERIOD_MILLIS = 4_000f
private const val MAX_PERIOD_MILLIS = 30_000f

/**
 * The see-through card at the bottom of the Mesh Gradient screen: palette chips, three switches
 * and two sliders that change the backdrop live.
 *
 * Its header ("Settings" + arrow) opens and closes the card, so the user can hide the controls
 * and look at the whole backdrop.
 *
 * Example:
 * ```kotlin
 * MeshGradientControlPanel(
 *     uiState = MeshGradientUiState(),
 *     onPaletteChange = { palette -> viewModel.setPalette(palette) },
 * )
 * ```
 *
 * @param uiState current settings to show; [MeshGradientUiState.isPanelExpanded] opens the card
 * @param onToggleExpanded user tapped the header to open or close the card
 * @param onPaletteChange user tapped a palette chip
 * @param onAnimateChange user flipped the "Animate" switch
 * @param onSmoothColorChange user flipped the "Smooth colors" switch
 * @param onShowVerticesChange user flipped the "Show vertices" switch
 * @param onWobbleChange user dragged the wobble slider
 * @param onPeriodChange user dragged the loop-length slider (milliseconds)
 * @author Phong-Kaster
 */
@Composable
fun MeshGradientControlPanel(
    uiState: MeshGradientUiState,
    modifier: Modifier = Modifier,
    onToggleExpanded: () -> Unit = {},
    onPaletteChange: (MeshPalette) -> Unit = {},
    onAnimateChange: (Boolean) -> Unit = {},
    onSmoothColorChange: (Boolean) -> Unit = {},
    onShowVerticesChange: (Boolean) -> Unit = {},
    onWobbleChange: (Float) -> Unit = {},
    onPeriodChange: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, shape = RoundedCornerShape(24.dp), color = Color.White.copy(alpha = 0.25f))
            .background(color = Color.Black.copy(alpha = 0.35f), shape = RoundedCornerShape(24.dp)),
        content = {
            PanelHeader(
                expanded = uiState.isPanelExpanded,
                onClick = onToggleExpanded,
            )

            AnimatedVisibility(
                visible = uiState.isPanelExpanded,
                enter = expandVertically(animationSpec = tween(TOGGLE_MILLIS)) + fadeIn(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(TOGGLE_MILLIS)) + fadeOut(animationSpec = tween(200)),
                content = {
                    PanelBody(
                        uiState = uiState,
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
}

/**
 * The always-visible top row: "Settings" on the left, an arrow on the right that points up when
 * the card is closed (tap to open) and down when it is open (tap to close).
 *
 * @author Phong-Kaster
 */
@Composable
private fun PanelHeader(
    expanded: Boolean,
    onClick: () -> Unit,
) {
    val arrowRotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f, animationSpec = tween(TOGGLE_MILLIS), label = "arrowRotation")
    val clickLabel = if (expanded) stringResource(R.string.collapse) else stringResource(R.string.expand)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(
                onClickLabel = clickLabel,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = stringResource(R.string.settings),
                style = customizedTextStyle(fontSize = 16, fontWeight = 600, lineHeight = 22, color = Color.White),
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .basicMarquee(Int.MAX_VALUE),
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.rotate(arrowRotation),
            )
        },
    )
}

/**
 * The part that hides when the card is closed: palette chips, switches and sliders.
 *
 * @author Phong-Kaster
 */
@Composable
private fun PanelBody(
    uiState: MeshGradientUiState,
    onPaletteChange: (MeshPalette) -> Unit,
    onAnimateChange: (Boolean) -> Unit,
    onSmoothColorChange: (Boolean) -> Unit,
    onShowVerticesChange: (Boolean) -> Unit,
    onWobbleChange: (Float) -> Unit,
    onPeriodChange: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    MeshPalette.entries.forEach(
                        action = { palette ->
                            PaletteChip(
                                palette = palette,
                                selected = palette == uiState.palette,
                                onClick = { onPaletteChange(palette) },
                            )
                        },
                    )
                },
            )

            SwitchRow(
                label = stringResource(R.string.animate),
                checked = uiState.isAnimating,
                onCheckedChange = onAnimateChange,
            )

            SwitchRow(
                label = stringResource(R.string.smooth_colors),
                checked = uiState.hasSmoothColor,
                onCheckedChange = onSmoothColorChange,
            )

            SwitchRow(
                label = stringResource(R.string.show_vertices),
                checked = uiState.showVertices,
                onCheckedChange = onShowVerticesChange,
            )

            SliderRow(
                label = stringResource(R.string.wobble),
                valueText = String.format(Locale.US, "%.2f", uiState.wobble),
                value = uiState.wobble,
                valueRange = 0f..MeshLattice.MAX_WOBBLE,
                onValueChange = onWobbleChange,
            )

            SliderRow(
                label = stringResource(R.string.loop_length),
                valueText = stringResource(R.string.x_seconds, uiState.periodMillis / 1000f),
                value = uiState.periodMillis.toFloat(),
                valueRange = MIN_PERIOD_MILLIS..MAX_PERIOD_MILLIS,
                onValueChange = { millis -> onPeriodChange(millis.toInt()) },
            )
        },
    )
}

/**
 * One rounded palette chip; the selected one is filled white.
 *
 * @author Phong-Kaster
 */
@Composable
private fun PaletteChip(
    palette: MeshPalette,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val background = if (selected) Color.White else Color.White.copy(alpha = 0.12f)
    val textColor = if (selected) Color.Black else Color.White

    Text(
        text = stringResource(palette.label),
        style = customizedTextStyle(fontSize = 13, fontWeight = 600, lineHeight = 18, color = textColor),
        maxLines = 1,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color = background)
            .clickable(
                onClickLabel = stringResource(palette.label),
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = onClick,
            )
            .padding(horizontal = 14.dp, vertical = 6.dp),
    )
}

/**
 * A label on the left and a switch on the right.
 *
 * @author Phong-Kaster
 */
@Composable
private fun SwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = label,
                style = customizedTextStyle(fontSize = 14, fontWeight = 500, lineHeight = 20, color = Color.White),
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .basicMarquee(Int.MAX_VALUE),
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Black,
                    checkedTrackColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.White.copy(alpha = 0.2f),
                    uncheckedBorderColor = Color.White.copy(alpha = 0.5f),
                ),
            )
        },
    )
}

/**
 * A label with its current value on one line and a slider under it.
 *
 * @author Phong-Kaster
 */
@Composable
private fun SliderRow(
    label: String,
    valueText: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text(
                        text = label,
                        style = customizedTextStyle(fontSize = 14, fontWeight = 500, lineHeight = 20, color = Color.White),
                        maxLines = 1,
                        modifier = Modifier
                            .weight(1f)
                            .basicMarquee(Int.MAX_VALUE),
                    )
                    Text(
                        text = valueText,
                        style = customizedTextStyle(fontSize = 14, fontWeight = 600, lineHeight = 20, color = Color.White),
                        maxLines = 1,
                    )
                },
            )
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = valueRange,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.White.copy(alpha = 0.25f),
                ),
            )
        },
    )
}

/**
 * Preview of the open panel.
 *
 * @author Phong-Kaster
 */
@Preview(name = "Expanded")
@Composable
private fun PreviewMeshGradientControlPanelExpanded() {
    MeshGradientControlPanel(
        uiState = MeshGradientUiState(isPanelExpanded = true),
        modifier = Modifier.background(color = Color(0xFF0E5E6F)),
    )
}

/**
 * Preview of the closed panel (header only).
 *
 * @author Phong-Kaster
 */
@Preview(name = "Collapsed")
@Composable
private fun PreviewMeshGradientControlPanelCollapsed() {
    MeshGradientControlPanel(
        uiState = MeshGradientUiState(isPanelExpanded = false),
        modifier = Modifier.background(color = Color(0xFF0E5E6F)),
    )
}
