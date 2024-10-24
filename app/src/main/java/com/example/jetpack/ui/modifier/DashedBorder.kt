package com.example.jetpack.ui.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

/**
 * # [How to Create a Dashed Border in Jetpack Compose](https://medium.com/@kappdev/dashed-borders-in-jetpack-compose-a-comprehensive-guide-de990a944c4c)
 * Parameters:
 * @param brush The Brush used to paint the dashed border.
 *
 * @param shape The shape of the border.
 *
 * @param strokeWidth The width of the dashed border line.
 *
 * @param dashLength The length of each dash.
 *
 * @param gapLength The length of the gaps between dashes.
 *
 * @param cap The style of the dash ends.
 */
@Composable
fun Modifier.dashedBorder(
    brush: Brush,
    shape: Shape,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = this.drawWithContent {
    val outline = shape.createOutline(size, layoutDirection, density = this)
    val dashedStroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashLength.toPx(), gapLength.toPx())
        )
    )

    // Draw the content
    drawContent()

    // Draw the border
    drawOutline(
        outline = outline,
        style = dashedStroke,
        brush = brush
    )
}

@Composable
fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = dashedBorder(brush = SolidColor(color), shape, strokeWidth, dashLength, gapLength, cap)

@Preview
@Composable
private fun PreviewDashedBorderExample() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .dashedBorder(
                shape = RoundedCornerShape(15.dp),
                brush = Brush.linearGradient(
                    colors = listOf(
                        LocalTheme.current.primary,
                        LocalTheme.current.secondary
                    )
                ),
            )
            .background(color = LocalTheme.current.background, shape = RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = customizedTextStyle(
                fontWeight = 600,
                fontSize = 16,
                color = LocalTheme.current.primary
            ),
            modifier = Modifier
        )
    }
}