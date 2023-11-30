package com.example.jetpack.ui.theme

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/** Draw shadow both inside and outside of the component */
fun Modifier.shadow(
    shadowRadius: Dp,
    spreadRadius: Dp,
    shadowColor: Color,
    shape: Shape,
): Modifier = drawWithContent {
    drawContent()
    drawIntoCanvas {
        // Create a shadow paint
        val paint = Paint().apply {
            asFrameworkPaint().also { fp ->
                fp.maskFilter = BlurMaskFilter(shadowRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
                fp.color = shadowColor.toArgb()
                fp.style = android.graphics.Paint.Style.STROKE
                fp.strokeWidth = spreadRadius.toPx()
            }
        }

        // Create a path from shape
        val path = Path().apply {
            addOutline(shape.createOutline(size, LayoutDirection.Ltr, Density(density)))
        }

        // Draw the shadow around the bounds
        // This shadow is clipped to be only visible outside the bounds
        it.drawPath(path, paint)
    }
}

/** Draw shadow both outside of the component */
fun Modifier.outerShadow(
    shadowRadius: Dp,
    spreadRadius: Dp,
    shadowColor: Color,
    shape: Shape,
): Modifier = drawWithContent {
    drawContent()
    drawIntoCanvas {
        // Create a shadow paint
        val paint = Paint().apply {
            asFrameworkPaint().also { fp ->
                fp.maskFilter = BlurMaskFilter(shadowRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
                fp.color = shadowColor.toArgb()
                fp.style = android.graphics.Paint.Style.STROKE
                fp.strokeWidth = spreadRadius.toPx()
            }
        }

        // Create a path from shape
        val path = Path().apply {
            addOutline(shape.createOutline(size, LayoutDirection.Ltr, Density(density)))
        }

        // Clip to prevent drawing inside the bounds
        it.clipPath(path, ClipOp.Difference)

        // Draw the shadow around the bounds
        // This shadow is clipped to be only visible outside the bounds
        it.drawPath(path, paint)
    }
}

/** Draw shadow both inside of the component */
fun Modifier.innerShadow(
    shadowRadius: Dp,
    shadowColor: Color,
    shape: Shape,
): Modifier = drawWithContent {
    drawContent()
    drawIntoCanvas {
        // Create a shadow paint
        val paint = Paint().apply {
            asFrameworkPaint().also { fp ->
                fp.maskFilter = BlurMaskFilter(10.dp.toPx(), BlurMaskFilter.Blur.NORMAL)
                fp.color = shadowColor.toArgb()
                fp.style = android.graphics.Paint.Style.STROKE
                fp.strokeWidth = shadowRadius.toPx()
            }
        }

        // Create a path from shape
        val path = Path().apply {
            addOutline(shape.createOutline(size, LayoutDirection.Ltr, Density(density)))
        }

        // Clip to prevent drawing inside the bounds
        it.clipPath(path, ClipOp.Intersect)

        // Draw the shadow around the bounds
        // This shadow is clipped to be only visible outside the bounds
        it.drawPath(path, paint)
    }
}