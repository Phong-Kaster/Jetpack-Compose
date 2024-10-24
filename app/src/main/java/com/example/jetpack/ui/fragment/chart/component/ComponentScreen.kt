package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.Subsetting
import com.example.jetpack.ui.component.SquareElement
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.animationInfiniteFloatSuperLong
import com.example.jetpack.ui.view.AnimatedProgressBar
import com.example.jetpack.ui.view.ContextualFlowRowSample
import com.example.jetpack.ui.view.SubsettingElement

@Composable
fun ComponentScreen(
    onOpenAlertDialog: () -> Unit = {},
    onOpenDottedTextDialog: () -> Unit = {},
    onOpenDialog: () -> Unit = {},
    onOpenWheelTimePicker: () -> Unit = {},
) {

    val state = rememberLazyGridState()

    val infiniteTransition = rememberInfiniteTransition(label = "inifiniteTransition")
    val animatedProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = animationInfiniteFloatSuperLong,
        label = "animatedProgress"
    )

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        items(
            items = Language.entries.take(2),
            key = { item: Language -> item.name },
            itemContent = { language: Language ->
                SquareElement(
                    language = language,
                    onClick = {
                        when (language) {
                            Language.English -> onOpenAlertDialog()
                            Language.German -> onOpenDottedTextDialog()
                            else -> onOpenDialog()
                        }
                    })
            }
        )

        item(
            key = "WheelTimePicker",
            span = { GridItemSpan(2) },
            content = {
                SubsettingElement(
                    subsetting = Subsetting.TimedRecording,
                    onClick = onOpenWheelTimePicker,
                    modifier = Modifier
                        .fillMaxWidth()
                        .borderWithAnimatedGradient(
                            colorBackground = LocalTheme.current.background,
                            width = 3.dp,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(
                            color = LocalTheme.current.background,
                            shape = RoundedCornerShape(25.dp)
                        )
                )
            }
        )

        item(
            key = "ContextualFlowRow",
            span = { GridItemSpan(2) },
            content = {
                ContextualFlowRowSample(
                    modifier = Modifier
                        .fillMaxWidth()
                        .borderWithAnimatedGradient(
                            colorBackground = LocalTheme.current.background,
                            width = 3.dp,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(
                            color = LocalTheme.current.background,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(vertical = 10.dp)
                )
            }
        )

        item(
            key = "AnimatedProgressBar",
            span = { GridItemSpan(2) },
            content = {
                AnimatedProgressBar(
                    progress = animatedProgress.value,
                    colors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta),
                    glowRadius = 20.dp,
                    strokeWidth = 5.dp,
                    gradientAnimationSpeed = 5000,
                    trackBrush = SolidColor(Color.DarkGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .borderWithAnimatedGradient(
                            colorBackground = LocalTheme.current.background,
                            width = 3.dp,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(
                            color = LocalTheme.current.background,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 10.dp)

                )
            })
    }
}

@Preview
@Composable
private fun PreviewComponentScreen() {
    ComponentScreen()
}