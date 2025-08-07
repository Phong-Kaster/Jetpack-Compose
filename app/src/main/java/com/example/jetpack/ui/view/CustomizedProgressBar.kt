package com.example.jetpack.ui.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.animationInfiniteFloat5
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun CustomizedProgressBar(
    modifier: Modifier = Modifier,
) {
    val corner = remember { 15 }

    val pathMeasure by remember { mutableStateOf(PathMeasure()) }

    // progress from 0f to 1f
    var progress by remember { mutableFloatStateOf(0f) }
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        label = "progressAnimation",
        animationSpec = animationInfiniteFloat5
    )
    val pathWithProgress by remember { mutableStateOf(Path()) }

    LaunchedEffect(Unit){ progress = 1f }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = LocalTheme.current.background)
            .height(50.dp)
            .drawBehind {
//                rotate(degrees = 0f) {
//                    scale(scaleX = 1f, scaleY = 1f) {
                val path = Path()

                path.moveTo(x = size.width * 0.5f, y = 0f)

                // draw top-end corner
                path.lineTo(x = size.width - corner.dp.toPx(), y = 0f)
                path.arcTo(
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = 90f,
                    rect = Rect(
                        offset = Offset(
                            x = size.width - corner.dp.toPx(),
                            y = 0f
                        ),
                        size = Size(
                            width = corner.dp.toPx(),
                            height = corner.dp.toPx()
                        )
                    ),
                    forceMoveTo = false
                )

                // draw bottom-end corner
                path.lineTo(
                    x = size.width,
                    y = size.height - corner.dp.toPx()
                )
                path.arcTo(
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    rect = Rect(
                        offset = Offset(
                            x = size.width - corner.dp.toPx(),
                            y = size.height - corner.dp.toPx()
                        ),
                        size = Size(
                            width = corner.dp.toPx(),
                            height = corner.dp.toPx()
                        )
                    ),
                    forceMoveTo = false,
                )

                // draw bottom-start corner
                path.lineTo(
                    x = size.width * 0.5f,
                    y = size.height
                )
                path.lineTo(
                    x = corner.dp.toPx(),
                    y = size.height
                )
                path.arcTo(
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    rect = Rect(
                        offset = Offset(
                            x = 0f,
                            y = size.height - corner.dp.toPx()
                        ),
                        size = Size(
                            width = corner.dp.toPx(),
                            height = corner.dp.toPx()
                        )
                    ),
                    forceMoveTo = false
                )

                // draw top-start corner
                path.lineTo(
                    x = 0f,
                    y = corner.dp.toPx()
                )
                path.arcTo(
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false,
                    rect = Rect(
                        offset = Offset(
                            x = 0f,
                            y = 0f
                        ),
                        size = Size(
                            width = corner.dp.toPx(),
                            height = corner.dp.toPx()
                        )
                    )
                )
                path.lineTo(
                    x = size.width * 0.5f,
                    y = 0f
                )
                pathMeasure.setPath(path = path, forceClosed = false)

                pathWithProgress.reset()
                pathMeasure.setPath(path, forceClosed = false)
                pathMeasure.getSegment(
                    startDistance = 0F,
                    stopDistance = pathMeasure.length * progressAnimation,
                    destination = pathWithProgress,
                    startWithMoveTo = true
                )



                drawPath(
                    path = path,
                    style = Stroke(width = 5.dp.toPx()),
                    color = Color.White
                )

                drawPath(
                    path = pathWithProgress,
                    color = Color.Green,
                    style = Stroke(width = 5.dp.toPx())
                )

                /*-- end of draw path*/
            }
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = customizedTextStyle(
                fontWeight = 400, fontSize = 18,
                color = LocalTheme.current.textColor
            ),
        )
    }


}

@Preview
@Composable
private fun PreviewCustomizedProgressBar() {
    CustomizedProgressBar()
}