package com.example.jetpack.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.compose.rememberNavController
import com.example.jetpack.R
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.LocalNavController
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlin.math.roundToInt

@Composable
fun CoreExpandableFloatingButton(
    extended: Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current ?: rememberNavController()
    var expand by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = expand,
            enter = slideInVertically() + fadeIn() + expandVertically(expandFrom = Alignment.Top),
            exit = slideOutVertically() + fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom),
            content = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Menu.entries.forEach {
                        FloatingActionButton(
                            containerColor = PrimaryColor,
                            onClick = { navController.navigate(it.destinationId) },
                            modifier = Modifier.padding(16.dp).height(48.dp).widthIn(min = 48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = it.drawableId),
                                contentDescription = stringResource(id = R.string.icon),
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        )

        FloatingActionButton(
            onClick = { expand = !expand },
            modifier = modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .height(48.dp)
                .widthIn(min = 48.dp),
            containerColor = Color.Red
        ) {
            CoreExpandableFloatingButtonContent(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_nazi_symbol),
                        contentDescription = null,
                        tint = OppositePrimaryColor,
                        modifier = Modifier
                            .size(30.dp)
                            .drawBehind {
                                drawCircle(
                                    color = Color.White,
                                    radius = size.width * 0.55F
                                )
                            })
                },
                text = {
                    if (extended) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = customizedTextStyle(
                                fontSize = 14,
                                fontWeight = 700,
                                color = Color.White
                            )
                        )
                    }
                },
                extended = extended
            )
        }
    }
}


@Composable
fun CoreExpandableFloatingButtonContent(
    icon: @Composable () -> Unit = {},
    text: @Composable () -> Unit = {},
    extended: Boolean = false
) {
    val currentState = if (extended) ExpandableFabStates.Extended else ExpandableFabStates.Collapsed
    val transition = updateTransition(currentState, "fab_transition")

    val textOpacity by transition.animateFloat(
        transitionSpec = {
            if (targetState == ExpandableFabStates.Collapsed) {
                tween(
                    easing = LinearEasing,
                    durationMillis = (transitionDuration / 12f * 5).roundToInt() // 5 / 12 frames
                )
            } else {
                tween(
                    easing = LinearEasing,
                    delayMillis = (transitionDuration / 3f).roundToInt(), // 4 / 12 frames
                    durationMillis = (transitionDuration / 12f * 5).roundToInt() // 5 / 12 frames
                )
            }
        },
        label = "fab_text_opacity"
    ) { state ->
        if (state == ExpandableFabStates.Collapsed) {
            0f
        } else {
            1f
        }
    }
    val fabWidthFactor by transition.animateFloat(
        transitionSpec = {
            if (targetState == ExpandableFabStates.Collapsed) {
                tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = transitionDuration
                )
            } else {
                tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = transitionDuration
                )
            }
        },
        label = "fab_width_factor"
    ) { state ->
        if (state == ExpandableFabStates.Collapsed) {
            0f
        } else {
            1f
        }
    }
    // Deferring reads using lambdas instead of Floats here can improve performance,
    // preventing recompositions.
    IconAndTextRow(
        icon = icon,
        text = text,
        opacityProgress = { textOpacity },
        widthProgress = { fabWidthFactor },
    )
}

@Composable
private fun IconAndTextRow(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    opacityProgress: () -> Float, // Lambdas instead of Floats, to defer read
    widthProgress: () -> Float,
) {
    Layout(
        modifier = Modifier,
        content = {
            icon()
            Box(modifier = Modifier.graphicsLayer { alpha = opacityProgress() }) {
                text()
            }
        }
    ) { measurables, constraints ->

        val iconPlaceable = measurables[0].measure(constraints)
        val textPlaceable = measurables[1].measure(constraints)

        val height = constraints.maxHeight

        // FAB has an aspect ratio of 1 so the initial width is the height
        val initialWidth = height.toFloat()

        // Use it to get the padding
        val iconPadding = (initialWidth - iconPlaceable.width) / 2f

        // The full width will be : padding + icon + padding + text + padding
        val expandedWidth = iconPlaceable.width + textPlaceable.width + iconPadding * 3

        // Apply the animation factor to go from initialWidth to fullWidth
        val width = lerp(initialWidth, expandedWidth, widthProgress())

        layout(width.roundToInt(), height) {
            iconPlaceable.place(
                iconPadding.roundToInt(),
                constraints.maxHeight / 2 - iconPlaceable.height / 2
            )
            textPlaceable.place(
                (iconPlaceable.width + iconPadding * 2).roundToInt(),
                constraints.maxHeight / 2 - textPlaceable.height / 2
            )
        }
    }
}

private enum class ExpandableFabStates { Collapsed, Extended }

private const val transitionDuration = 200