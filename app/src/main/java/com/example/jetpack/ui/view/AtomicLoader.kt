package com.example.jetpack.ui.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.util.ViewUtil

/**
 * # [Create an Atomic Loader in Jetpack Compose](https://medium.com/@kappdev/how-to-create-an-atomic-loader-in-jetpack-compose-cbf0a74aa5fa)
 */
@Composable
fun AtomicLoader(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
        ),
        label = "rotation"
    )

    Box(modifier = modifier) {

        RotatingCircle(
            rotationX = 35f,
            rotationY = -45f,
            rotationZ = -90f + rotation.value,
            borderColor = Color.Red,
            borderWidth = 3.dp,
            modifier = Modifier.matchParentSize()
        )

        RotatingCircle(
            rotationX = 50f,
            rotationY = 10f,
            rotationZ = rotation.value,
            borderColor = Color.Cyan,
            borderWidth = 3.dp,
            modifier = Modifier.matchParentSize()
        )

        RotatingCircle(
            rotationX = 35f,
            rotationY = 55f,
            rotationZ = 90f + rotation.value,
            borderColor = LocalTheme.current.textColor,
            borderWidth = 3.dp,
            modifier = Modifier.matchParentSize()
        )
    }
}

/**
 * A composable function that draws a rotating circle with a border effect.
 *
 * @param modifier Modifier to be applied to the circle's drawing area.
 * @param rotationX Rotation angle around the X-axis in degrees.
 * @param rotationY Rotation angle around the Y-axis in degrees.
 * @param rotationZ Rotation angle around the Z-axis in degrees.
 * @param borderColor Color of the circle's border.
 * @param borderWidth Width of the circle's border.
 */
@Composable
fun RotatingCircle(
    rotationX: Float,
    rotationY: Float,
    rotationZ: Float,
    borderColor: Color,
    borderWidth: Dp,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .graphicsLayer {
                this.rotationX = rotationX
                this.rotationY = rotationY
                this.rotationZ = rotationZ
                cameraDistance = 12f * density
            }) {
        val mainCircle = Path().apply {
            addOval(oval = Rect(size.center, size.minDimension * 0.5f))
        }

        val clipCenter = Offset(
            x = size.width * 0.5f - borderWidth.toPx(), y = size.height * 0.5f
        )

        val clipCircle = Path().apply {
            addOval(oval = Rect(clipCenter, size.minDimension * 0.5f))
        }

        val path = Path().apply {
            op(mainCircle, clipCircle, PathOperation.Difference)
        }

        drawPath(path = path, color = borderColor)
    }
}

@Preview
@Composable
private fun PreviewAtomicLoader() {
    ViewUtil.PreviewContent(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            listOf(Color(0xFF3C4B57), Color(0xFF1C262B))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                AtomicLoader(
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    )
}