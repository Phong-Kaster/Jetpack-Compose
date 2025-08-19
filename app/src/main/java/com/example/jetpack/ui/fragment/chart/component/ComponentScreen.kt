package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.domain.enums.Subsetting
import com.example.jetpack.ui.component.SquareElement
import com.example.jetpack.ui.modifier.PremiumWatermarkExample
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.animationInfiniteFloatSuperLong
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.AnimatedProgressBar
import com.example.jetpack.ui.view.ContextualFlowRowSample
import com.example.jetpack.ui.view.CupertinoSwitch
import com.example.jetpack.ui.view.CustomizedCheckbox
import com.example.jetpack.ui.view.CustomizedProgressBar
import com.example.jetpack.ui.view.GlowingButton
import com.example.jetpack.ui.view.GradientProgressIndicator
import com.example.jetpack.ui.view.SubsettingElement
import com.example.jetpack.ui.view.SwipeToReveal
import com.example.jetpack.ui.view.wordbyword.SimpleChuckedTextAnimation
import com.example.jetpack.util.AppUtil.showToast

@Composable
fun ComponentScreen(
    onOpenAlertDialog: () -> Unit = {},
    onOpenDottedTextDialog: () -> Unit = {},
    onOpenDialog: () -> Unit = {},
    onOpenWheelTimePicker: () -> Unit = {},
    onOpenCountdownSnackbar: () -> Unit = {},
) {

    val context = LocalContext.current
    val state = rememberLazyGridState()

    val infiniteTransition = rememberInfiniteTransition(label = "inifiniteTransition")
    val animatedProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = animationInfiniteFloatSuperLong,
        label = "animatedProgress"
    )

    var expanded by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item(
            key = "animateContentSize",
            span = { GridItemSpan(2) },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize() // 👈 magic happens here
                        .clickable { expanded = !expanded }
                ) {
                    Text(
                        text = "Animate Content Size", style = customizedTextStyle(
                            fontSize = 20,
                            color = Color.White,
                        )
                    )

                    if (expanded) {
                        Text(
                            text = "Here's the full description of Animate Content Size...",
                            style = customizedTextStyle(
                                fontSize = 16,
                                color = Color.White,
                            )
                        )
                    }
                }
            }
        )

        item(
            key = "swipeToReveal",
            span = { GridItemSpan(2) },
            content = {
                SwipeToReveal(
                    actions = {
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ),
                            onClick = { context.showToast("Delete") },
                            content = {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier
                                )
                            }
                        )
                    },
                    modifier = Modifier.background(color = LocalTheme.current.background),
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
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
                                .padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_heer),
                                contentDescription = stringResource(id = R.string.icon),
                                modifier = Modifier.size(25.dp),
                                tint = LocalTheme.current.textColor
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(0.9F)) {
                                Text(
                                    text = "Swipe left to reveal",
                                    color = LocalTheme.current.textColor,
                                    style = customizedTextStyle(fontWeight = 400, fontSize = 18),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE)
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                tint = LocalTheme.current.textColor,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        )

        item(
            key = "CountdownSnackbar",
            span = { GridItemSpan(2) },
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .borderWithAnimatedGradient(
                            colorBackground = LocalTheme.current.background,
                            width = 3.dp,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .clip(shape = RoundedCornerShape(25.dp))
                        .clickable { onOpenCountdownSnackbar() }
                        .background(
                            color = LocalTheme.current.background,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_iron_cross_bundeswehr),
                        contentDescription = stringResource(id = R.string.icon),
                        modifier = Modifier.size(25.dp),
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(R.string.countdown_snackbar),
                        color = LocalTheme.current.textColor,
                        style = customizedTextStyle(fontWeight = 400, fontSize = 18),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE)
                    )
                }
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
            key = "CustomizedCheckbox",
            span = { GridItemSpan(2) },
            content = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
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
                ) {
                    CustomizedCheckbox(shape = RoundedCornerShape(10.dp))
                    CustomizedCheckbox(shape = RoundedCornerShape(10.dp))
                    CustomizedCheckbox(shape = RoundedCornerShape(10.dp))
                    CustomizedCheckbox(shape = RoundedCornerShape(10.dp))
                    CustomizedCheckbox(shape = RoundedCornerShape(10.dp))
                    CustomizedCheckbox(shape = RoundedCornerShape(10.dp))
                    CustomizedCheckbox(shape = RoundedCornerShape(10.dp))
                }
            }
        )


        item(
            key = "AnimatedProgressBar",
            span = { GridItemSpan(2) },
            content = {
                AnimatedProgressBar(
                    progress = animatedProgress.value,
                    colors = listOf(
                        Color.Red,
                        Color.Yellow,
                        Color.Green,
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta
                    ),
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
            }
        )

        item(
            key = "GradientProgressIndicator",
            span = { GridItemSpan(2) },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Gradient Progress Indicator",
                        style = customizedTextStyle(
                            fontSize = 14,
                            fontWeight = 600,
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    GradientProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(
                                color = LocalTheme.current.background,
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }
            }
        )

        item(
            key = "PathInCanvas",
            span = { GridItemSpan(2) },
            content = {
                CustomizedProgressBar()
            }
        )

        item(
            key = "premiumWatermark",
            span = { GridItemSpan(2) },
            content = {
                PremiumWatermarkExample(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = LocalTheme.current.primary,
                            shape = RoundedCornerShape(20.dp),
                        )
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = Color.DarkGray)
                        .padding(5.dp)

                )
            }
        )

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
            key = "glowingButton",
            span = { GridItemSpan(2) },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Glowing Button",
                        style = customizedTextStyle(
                            fontSize = 14,
                            fontWeight = 600,
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    GlowingButton(
                        onClick = {
                            context.showToast("Glowing Button Clicked")
                        },
                    )
                }
            }
        )

        item(
            key = "cupertinoSwitch",
            span = { GridItemSpan(1) },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Cupertino Switch",
                        style = customizedTextStyle(
                            fontSize = 14,
                            fontWeight = 600,
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CupertinoSwitch(
                            checked = true,
                            onCheckedChange = { boolean -> },
                            modifier = Modifier
                                .wrapContentSize()
                        )

                        CupertinoSwitch(
                            checked = false,
                            onCheckedChange = { boolean -> },
                            modifier = Modifier
                                .wrapContentSize()
                        )
                    }

                }
            }
        )

        item(
            key = "wordByWordAnimation",
            span = { GridItemSpan(2) },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Word By Word Animation",
                        style = customizedTextStyle(
                            fontSize = 14,
                            fontWeight = 600,
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    SimpleChuckedTextAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .borderWithAnimatedGradient(
                                colorBackground = LocalTheme.current.background,
                                width = 3.dp,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color.Transparent)
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun PreviewComponentScreen() {
    ComponentScreen()
}