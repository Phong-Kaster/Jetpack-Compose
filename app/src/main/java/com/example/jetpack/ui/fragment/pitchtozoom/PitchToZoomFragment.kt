package com.example.jetpack.ui.fragment.pitchtozoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PitchToZoomFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        PitchToZoomLayout(
            onBack = { safeNavigateUp() }
        )
    }
}

@Composable
fun PitchToZoomLayout(
    onBack: () -> Unit = {},
) {
    // Mutable state variables to hold scale and offset values
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val minScale = 1f
    val maxScale = 4f

    // Remember the initial offset
    var initialOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    // Coefficient for slowing down movement
    val slowMovement = 0.5f

    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.pitch_to_zoom),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        bottomBar = {
            Text(
                text = "The Napoleonic Wars greatly expanded the French empire until Napoleon Bonaparte’s military prowess faltered and he was exiled.",
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 600,
                    color = LocalTheme.current.textColor
                ),
                modifier = Modifier.padding(16.dp)
            )
        },
        backgroundColor = LocalTheme.current.background,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = LocalTheme.current.background
                    )
                    .navigationBarsPadding()
            ) {
                // Box composable containing the image
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                // Update scale with the zoom
                                val newScale = scale * zoom
                                scale = newScale.coerceIn(minScale, maxScale)

                                // Calculate new offsets based on zoom and pan
                                val centerX = size.width / 2
                                val centerY = size.height / 2
                                val offsetXChange = (centerX - offsetX) * (newScale / scale - 1)
                                val offsetYChange = (centerY - offsetY) * (newScale / scale - 1)

                                // Calculate min and max offsets
                                val maxOffsetX = (size.width / 2) * (scale - 1)
                                val minOffsetX = -maxOffsetX
                                val maxOffsetY = (size.height / 2) * (scale - 1)
                                val minOffsetY = -maxOffsetY

                                // Update offsets while ensuring they stay within bounds
                                if (scale * zoom <= maxScale) {
                                    offsetX =
                                        (offsetX + pan.x * scale * slowMovement + offsetXChange)
                                            .coerceIn(minOffsetX, maxOffsetX)
                                    offsetY =
                                        (offsetY + pan.y * scale * slowMovement + offsetYChange)
                                            .coerceIn(minOffsetY, maxOffsetY)
                                }

                                // Store initial offset on pan
                                if (pan != Offset(0f, 0f) && initialOffset == Offset(0f, 0f)) {
                                    initialOffset = Offset(offsetX, offsetY)
                                }
                            }
                        }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    // Reset scale and offset on double tap
                                    if (scale != 1f) {
                                        scale = 1f
                                        offsetX = initialOffset.x
                                        offsetY = initialOffset.y
                                    } else {
                                        scale = 2f
                                    }
                                }
                            )
                        }
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offsetX
                            translationY = offsetY
                        }
                ) {
                    // Image to be displayed with pinch-to-zoom functionality
                    Image(
                        painter = painterResource(id = R.drawable.img_napoleon_bonaparte),
                        contentDescription = "Napoleon",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )
                }


            }
        }
    )
}

@Preview
@Composable
private fun PreviewPitchToZoom() {
    PitchToZoomLayout(onBack = {})
}