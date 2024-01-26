package com.example.jetpack.ui.fragment.motionlayout.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.jetpack.R
import com.example.jetpack.ui.component.OutlineButton
import com.example.jetpack.ui.theme.PrimaryColor

/**
 * Motion Layout use JSON5 to create Motion Scene
 * @see [Motion Layout Button in Android Jetpack Compose](https://www.geeksforgeeks.org/motion-layout-button-in-android-jetpack-compose/)
 * @see [Compose MotionLayout JSON Syntax](https://github.com/androidx/constraintlayout/wiki/Compose-MotionLayout-JSON-Syntax)
 */
@OptIn(ExperimentalMotionApi::class)
@Composable
fun AnimationLayout1() {
    val context = LocalContext.current
    var enableAnimation by remember { mutableStateOf(false) }
    var targetValue by remember(enableAnimation) { mutableFloatStateOf(0F) }
    val progressAnimation by animateFloatAsState(targetValue = targetValue, animationSpec = tween(1000), label = "buttonAnimationProgress")
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene_animation_layout_1)
            .readBytes()
            .decodeToString()
    }

    LaunchedEffect(
        key1 = enableAnimation,
        block = {
            targetValue = if (enableAnimation) 1F else 0F
        }
    )

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progressAnimation,
        modifier = Modifier
            .fillMaxSize(),
        content = {

            OutlineButton(
                text = stringResource(id = R.string.fake_title),
                textColor = PrimaryColor,
                borderStroke = BorderStroke(width = 1.dp, color = PrimaryColor),
                onClick = { enableAnimation = !enableAnimation },
                modifier = Modifier.layoutId("button_1")
            )
            OutlineButton(
                text = stringResource(id = R.string.app_name),
                textColor = PrimaryColor,
                borderStroke = BorderStroke(width = 1.dp, color = PrimaryColor),
                onClick = { enableAnimation = !enableAnimation },
                modifier = Modifier.layoutId("button_2")
            )
        })
}

@Preview
@Composable
fun PreviewAnimationLayout1() {
    AnimationLayout1()
}