package com.example.jetpack.ui.fragment.tutorial.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetpack.R
import com.example.jetpack.ui.theme.ColorUVIndexLow
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.animationInfiniteColor
import com.example.jetpack.ui.theme.animationInfiniteFloat
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.toIntOffset


@Composable
fun TutorialStep1() {
    /*define animation*/
    val tutorial = LocalTutorial.current
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(initialValue = ColorUVIndexLow, targetValue = PrimaryColor, animationSpec = animationInfiniteColor, label = "animatedColor")
    val scale by infiniteTransition.animateFloat(initialValue = 1f, targetValue = 1.3f, animationSpec = animationInfiniteFloat, label = "scale")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.1F))
            .drawBehind {
                clipPath(
                    Path().apply {
                        addOval(tutorial.addButtonSize.inflate(6.dp.toPx()))
                    },
                    clipOp = ClipOp.Difference
                ) {
                    drawRect(color = Color.Black.copy(0.6f))
                }
            }
    ) {
        val (button, text) = createRefs()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset { tutorial.addButtonSize.topLeft.toIntOffset() }
                .clip(shape = CircleShape)
                .size(48.dp)
                .background(color = Color.Red)
                .clickable { tutorial.currentTutorial = 1 }
                .constrainAs(button) {}
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .offset {
                    tutorial.addButtonSize.topLeft
                        .toIntOffset()
                        .copy(x = 0)
                }
                .constrainAs(text) {
                    bottom.linkTo(button.top, margin = 20.dp)
                    centerHorizontallyTo(parent)

                }
        ) {
            Text(
                text = stringResource(R.string.tap_this_add_button),
                color = animatedColor,
                style = customizedTextStyle(fontSize = 14, fontWeight = 600),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_finger_down),
                contentDescription = stringResource(id = R.string.icon),
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin.Center
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewTutorialStep1() {
    ViewUtil.PreviewContent {
        TutorialStep1()
    }
}