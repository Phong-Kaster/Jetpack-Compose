package com.example.jetpack.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.modifier.outerShadow
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlinx.coroutines.delay

/**
 *The composable displays a UI section for "ImageRevealAnimation" in an app.
 *It visually demonstrates a "before" and "after" effect,
 *using an animated reveal transition to split and show two overlayed
 *images before & after, and animates a divider line sliding across them
 * @author Phong-Kaster
 */
@Composable
fun ImageBeforeAndAfterSwipeAnimation(modifier: Modifier = Modifier) {
    val dividerLineProgress = remember { Animatable(0f) }
    var boxWidthPx by remember { mutableIntStateOf(0) }
    var showDividerLine by remember { mutableStateOf(true) }

    /**
     * Animation Logic
     * Inside a LaunchedEffect(Unit), a forever loop:
     * 1. Animate the reveal from 0 fully "before" image to 1 fully "after" image" over 2 seconds.
     * 2. Wait 1 second.
     * 3. Animate back from 1 to 0 over 2 seconds.
     * 4. Hide divider line showDividerLine = false.
     * 5. Wait 1 second.
     * 6. Show divider line showDividerLine = true.
     *This creates an endless "wipe" effect, sliding back and forth between the two images
     **/
    LaunchedEffect(Unit) {
        while (true) {
            //slide divider line from left to right
            dividerLineProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
            )


            //slide divider line from right to left
            delay(1000)
            dividerLineProgress.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 4000, easing = FastOutLinearInEasing)
            )



            showDividerLine = false
            delay(1000)
            showDividerLine = true
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
    ) {
        // Title Text - Image Reveal Animation
        Text(
            text = stringResource(R.string.image_reveal_animation),
            style = customizedTextStyle(
                fontSize = 14,
                fontWeight = 600,
                color = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .onSizeChanged { boxWidthPx = it.width }
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // Before Image
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(R.drawable.ic_nazi_germany_flag),
                    contentDescription = "Before"
                )

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
            }

            // After Image Section
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        clip = true
                        shape = GenericShape { size, _ ->
                            val revealWidth = size.width * (1f - dividerLineProgress.value)
                            moveTo(0f, 0f)
                            lineTo(revealWidth, 0f)
                            lineTo(revealWidth, size.height)
                            lineTo(0f, size.height)
                            close()
                        }
                    }
            ) {
                // After Image
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(R.drawable.img_nazi_the_man_in_the_high_castle),
                    contentDescription = "After"
                )

                // Title After
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .outerShadow(
                            shadowRadius = 4.dp,
                            spreadRadius = 4.dp,
                            shadowColor = Color(0x99E99E75),
                            shape = CircleShape,
                        )
                        .align(Alignment.TopStart)
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

            if (boxWidthPx > 0 && showDividerLine) {
                val progress = dividerLineProgress.value
                val xPx = ((1f - progress) * boxWidthPx).toInt()

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .offset { IntOffset(x = xPx - 8.dp.roundToPx(), y = 0) }
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(
                                color = Color(0xffff5b00).copy(alpha = 0.1f)
                            )
                            .blur(10.dp)
                    )

                    Box(
                        modifier = Modifier
                            .width(2.5.dp)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF007AFF),
                                        Color(0xFF34C759),
                                    )
                                )
                            )
                    )

                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(
                                color = Color(0xffff5b00).copy(alpha = 0.1f)
                            )
                            .blur(10.dp)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewImageRevealAnimation() {
    ImageBeforeAndAfterSwipeAnimation()
}