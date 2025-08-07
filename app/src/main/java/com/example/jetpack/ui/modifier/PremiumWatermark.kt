package com.example.jetpack.ui.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.toIntSize
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme

/**
 * [Premium watermark](https://medium.com/@kappdev/how-to-create-a-reusable-premium-watermark-in-jetpack-compose-83d79e9ce7c3)
 *
 * that draws a scrim, border, and a mark on top of the content, typically used to indicate premium or exclusive content.
 */
@Composable
fun Modifier.premiumWatermark(
    shape: Shape = RectangleShape,
    scrimColor: Color = Color.Yellow.copy(0.5f),
    borderColor: Color = Color.Yellow,
    borderWidth: Dp = 1.dp,
    markColor: Color = Color.Yellow,
    markPainter: Painter = painterResource(R.drawable.ic_crown),
    markSize: DpSize = DpSize.Unspecified,
    markAlignment: Alignment = Alignment.TopEnd,
    markPadding: PaddingValues = PaddingValues()
): Modifier {
    return this.drawWithContent {
        drawContent()


        //1. Drawing Scrim
        val outline = shape.createOutline(size, layoutDirection, this)

        drawOutline(
            outline = outline,
            color = scrimColor
        )

        //2. Drawing Border
        drawOutline(
            outline = outline,
            style = Stroke(borderWidth.toPx()),
            color = borderColor
        )

        //3. Drawing Mark
        //3.1 Calculate the mark size in pixels
        val markSizePx = if (markSize.isUnspecified) {
            // Use default value based on layout min dimension
            Size(size.minDimension * 0.25f, size.minDimension * 0.25f)
        } else markSize.toSize()


        //3.2 Calculate padding values
        val leftPadding = markPadding.calculateLeftPadding(layoutDirection).toPx()
        val rightPadding = markPadding.calculateRightPadding(layoutDirection).toPx()
        val topPadding = markPadding.calculateTopPadding().toPx()
        val bottomPadding = markPadding.calculateBottomPadding().toPx()


        //3.3 Calculate size of the area with padding applied
        val sizeWithPadding = Size(
            width = size.width - (leftPadding + rightPadding),
            height = size.height - (topPadding + bottomPadding)
        )


        //3.4 Calculate the mark position within the sizeWithPadding area, considering alignment
        val markPosition = markAlignment.align(
            size = markSizePx.toIntSize(),
            space = sizeWithPadding.toIntSize(),
            layoutDirection = layoutDirection
        )


        //3.5 Translate the canvas to the position of drawing
        translate(
            left = leftPadding + markPosition.x,
            top = topPadding + markPosition.y
        ) {
            // Draw the mark painter
            with(markPainter) {
                draw(
                    size = markSizePx,
                    colorFilter = ColorFilter.tint(markColor)
                )
            }
        }
    }
}

/**
 * another overload has an additional `isVisible` parameter that controls the effect visibility
 *
 * @author Phong-Kaster
 * */
@Composable
fun Modifier.premiumWatermark(
    isVisible: Boolean = true,
    shape: Shape = RectangleShape,
    scrimColor: Color = Color.Yellow.copy(0.5f),
    borderColor: Color = Color.Yellow,
    borderWidth: Dp = 1.dp,
    markColor: Color = Color.Yellow,
    markPainter: Painter = painterResource(R.drawable.ic_crown),
    markSize: DpSize = DpSize.Unspecified,
    markAlignment: Alignment = Alignment.TopEnd,
    markPadding: PaddingValues = PaddingValues()
): Modifier {
    return if (isVisible) this.premiumWatermark(
        shape = shape,
        scrimColor = scrimColor,
        borderColor = borderColor,
        borderWidth = borderWidth,
        markColor = markColor,
        markPainter = markPainter,
        markSize = markSize,
        markAlignment = markAlignment,
        markPadding = markPadding
    ) else this
}


@Composable
fun PremiumWatermarkExample(
    modifier: Modifier = Modifier,
) {
    var checked by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)

    ) {
        Text(
            text = "Premium Watermark",
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = true,
                onCheckedChange = {},
                modifier = Modifier
                    .premiumWatermark(
                        shape = RoundedCornerShape(2.dp),
                        markAlignment = Alignment.TopStart,
                        markPadding = PaddingValues(2.dp),
                        markSize = DpSize(8.dp, 8.dp)
                    )
                    .size(20.dp)
            )

            Switch(
                checked = checked,
                onCheckedChange = { boolean -> checked = boolean },
                modifier = Modifier
                    .premiumWatermark(
                        shape = CircleShape,
                        markAlignment = Alignment.CenterStart,
                        markPadding = PaddingValues(start = 10.dp),
                        markSize = DpSize(12.dp, 12.dp)
                    )
                    .height(32.dp)
            )

            FloatingActionButton(
                onClick = { },
                containerColor = Color.Gray,
                modifier = Modifier
                    .premiumWatermark(
                        shape = FloatingActionButtonDefaults.shape,
                        markPadding = PaddingValues(5.dp),
                        markSize = DpSize(10.dp, 10.dp),
                        markAlignment = Alignment.TopEnd,
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }

    }

}

@Preview
@Composable
private fun PreviewPremiumWatermark() {
    PremiumWatermarkExample()
}