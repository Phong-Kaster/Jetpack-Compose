package com.example.jetpack.ui.fragment.motionlayout.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.example.jetpack.R
import com.example.jetpack.ui.theme.PrimaryColor

/**
 * Motion Layout use JSON5 to create Motion Scene
 * @see [Motion Layout Button in Android Jetpack Compose](https://www.geeksforgeeks.org/motion-layout-button-in-android-jetpack-compose/)
 * @see [Compose MotionLayout JSON Syntax](https://github.com/androidx/constraintlayout/wiki/Compose-MotionLayout-JSON-Syntax)
 */
@OptIn(ExperimentalMotionApi::class)
@Composable
fun AnimationLayout3() {
    val context = LocalContext.current
    var enableAnimation by remember { mutableStateOf(false) }
    var targetValue by remember(enableAnimation) { mutableFloatStateOf(0F) }
    val progressAnimation by animateFloatAsState(targetValue = targetValue, animationSpec = tween(1000), label = "buttonAnimationProgress")
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene_animation_layout_3)
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
            Box(modifier = Modifier.fillMaxSize()
                .background(color = Color.DarkGray)
                .layoutId("box"))

            Icon(
                imageVector = Icons.Default.Settings,
                tint = PrimaryColor,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable { }
                    .size(24.dp)
                    .layoutId("settingReference")
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_nazi_swastika),
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable { }
                    .size(24.dp)
                    .layoutId("searchReference")
            )
        })
}

@Preview
@Composable
fun PreviewAnimationLayout3() {
    AnimationLayout3()
}