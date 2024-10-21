package com.example.jetpack.ui.view.elsatimepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TimeCircleElement(
    items: List<Timestamp>,
    itemSize: Dp,
    size: Dp,
    itemPadding: Dp = 30.dp,
    onClick: (Timestamp) -> Unit,
    content: @Composable (Timestamp) -> Unit,
    modifier: Modifier = Modifier,
) {
    Layout(
        content = {   // Draw each item with appropriate information
            items.forEachIndexed { index, timestamp ->
                Box(
                    modifier = Modifier.size(itemSize),
                    content = { content(timestamp) }
                )
            }
        },
        modifier = modifier,
        measurePolicy = { measurables: List<Measurable>, constraints: Constraints ->

            val paddingInPx = itemPadding.toPx()
            val sizeInPx = size.toPx().toInt()


            //val placeables = measurables.map { measurable -> measurable.measure(constraints) }
            val placeables = measurables[0]


            // We need to remove the itemSize because items will be positioned not in a circle but at the edge
            val availableSpace = sizeInPx - itemSize.toPx()

            val radius = (availableSpace / 2.0).roundToInt()

            //  Calculate the step between each item
            val angleStep = (360 / items.size.toDouble()) * Math.PI / 180.0

            layout(
                width = sizeInPx,
                height = sizeInPx
            ) {

            }
        }
    )
}

private fun getCoordinates(angle: Double, radius: Double, paddings: Float): Offset {
    val radiusWithPaddings = radius - paddings
    val x = radiusWithPaddings * sin(angle)
    val y = radiusWithPaddings * cos(angle)
    // Adding radius is necessary to shift the origin from the center of the circle
    return Offset(
        x = x.toFloat() + radius.toFloat(),
        y = y.toFloat() + radius.toFloat(),
    )
}


@Preview
@Composable
private fun PreviewTimeCircleElement() {

}