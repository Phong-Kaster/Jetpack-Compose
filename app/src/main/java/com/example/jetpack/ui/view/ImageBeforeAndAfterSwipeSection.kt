package com.example.jetpack.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.modifier.outerShadow
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlin.math.roundToInt

@Composable
fun ImageBeforeAndAfterSwipeSection(
    modifier: Modifier = Modifier,
) {

    var boxWidthPx by remember { mutableIntStateOf(0) }
    // 'offsetX' is the width in px revealed from the right (0 = only after shown, max = before covers all)
    var offsetX: Float by remember { mutableFloatStateOf(0.5f) }

    HeaderAndLayout(
        title = "Image Before and After Swipe Section",
        modifier = modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background),
        content = {
            Box(modifier = Modifier.fillMaxWidth()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .onSizeChanged { boxWidthPx = it.width }
                        .fillMaxWidth()
                        .height(200.dp)
                        .pointerInput(boxWidthPx) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                // Drag to the LEFT increases before, drag to the RIGHT decreases before
                                val newOffset = (offsetX - dragAmount)
                                    .coerceIn(0f, boxWidthPx.toFloat())
                                offsetX = newOffset
                            }
                        }
                ) {
                    // Blurred image (always fills background)
                    Image(
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(R.drawable.img_nazi_the_man_in_the_high_castle),
                        contentDescription = "blurred background",
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(10.dp),
                    )

                    // Clear image - revealed from the right side, covering right side of divider
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                clip = true
                                shape = GenericShape { size, _ ->
                                    val revealStart = size.width - offsetX
                                    moveTo(revealStart, 0f)
                                    lineTo(size.width, 0f)
                                    lineTo(size.width, size.height)
                                    lineTo(revealStart, size.height)
                                    close()
                                }
                            }
                    ) {
                        Image(
                            contentScale = ContentScale.FillWidth,
                            painter = painterResource(R.drawable.img_nazi_the_man_in_the_high_castle),
                            contentDescription = "clear image",
                            modifier = Modifier.fillMaxSize(),
                        )

                    }

                    if (boxWidthPx > 0) {
                        // Divider position: moves from right (0) to left (max)
                        val xPx = (boxWidthPx - offsetX).roundToInt()
                        // Load the handle icon as a Painter, works with VectorDrawable
                        val painter = painterResource(id = R.drawable.ic_swipe_before_and_after)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxHeight()
                                .offset { IntOffset(x = xPx, y = 0) }
                        ) {
                            // Draw the vertical divider line and the handle icon centered
                            androidx.compose.foundation.Canvas(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(2.dp),
                                onDraw = {
                                    // Draw sharp vertical divider
                                    drawLine(
                                        color = Color.White,
                                        start = Offset(size.width / 2, 0f),
                                        end = Offset(size.width / 2, size.height),
                                        strokeWidth = size.width // 2.dp
                                    )
                                    // Circle background for handle
                                    val iconSize = 30.dp.toPx() // Circle size for handle background
                                    val painterSize = 20.dp.toPx()
                                    val centerY = size.height / 2
                                    val centerX = size.width / 2
                                    drawCircle(
                                        color = Color.White,
                                        radius = iconSize / 2f,
                                        center = Offset(centerX, centerY)
                                    )

                                    // Draw the VectorDrawable directly using painter
                                    translate(
                                        left = centerX - painterSize / 2f,
                                        top = centerY - painterSize / 2f
                                    ) {
                                        with(painter) {
                                            draw(size = Size(painterSize, painterSize))
                                        }
                                    }
                                }
                            )
                        }
                    }
                }


                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopStart)
                        .background(Color(0x99000000), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    text = stringResource(R.string.before),
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 400, color = Color.White,
                    )
                )

                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .outerShadow(
                            shadowRadius = 4.dp,
                            spreadRadius = 4.dp,
                            shadowColor = Color(0x99E99E75),
                            shape = CircleShape,
                        )
                        .align(Alignment.TopEnd)
                        .background(
                            color = Color(0xffE99E75),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 13.dp, vertical = 2.dp),
                    text = stringResource(R.string.after),
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 600,
                        color = Color.White
                    ),
                )
            }

        }
    )
}

@Preview
@Composable
private fun PreviewImageBeforeAndAfterSwipeSection() {
    ImageBeforeAndAfterSwipeSection()
}