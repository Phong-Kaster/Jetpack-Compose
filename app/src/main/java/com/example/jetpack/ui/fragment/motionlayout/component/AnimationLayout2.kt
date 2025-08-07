package com.example.jetpack.ui.fragment.motionlayout.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.OnSwipe
import androidx.constraintlayout.compose.SwipeDirection
import androidx.constraintlayout.compose.SwipeMode
import androidx.constraintlayout.compose.SwipeSide
import androidx.constraintlayout.compose.SwipeTouchUp
import androidx.constraintlayout.compose.Transition
import androidx.constraintlayout.compose.layoutId
import com.example.jetpack.R
import com.example.jetpack.ui.theme.PrimaryColor

/**
 * @see [Introduction to MotionLayout in Compose](https://github.com/androidx/constraintlayout/wiki/Introduction-to-MotionLayout-in-Compose)
 * @see [MotionLayout in Compose](https://github.com/androidx/constraintlayout/wiki/Introduction-to-MotionLayout-in-Compose)
 */
@OptIn(ExperimentalMotionApi::class)
@Composable
fun AnimationLayout2() {
    val startConstraintSet = ConstraintSet {}
    val endConstraintSet = ConstraintSet {}
    val cSet2 = ConstraintSet { }
    val transition = Transition(from = "start", to = "end") { }

    MotionLayout(
       motionScene = MotionScene {
            val settingReference = createRefFor("settingReference")
            val searchReference = createRefFor("searchReference")
            val boxReference = createRefFor("boxReference")


            defaultTransition(
                from = constraintSet { // this: ConstraintSetScope
                    constrain(settingReference) { // this: ConstrainScope
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                    }

                    constrain(searchReference){
                        end.linkTo(parent.end, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                    }
                },
                to = constraintSet { // this: ConstraintSetScope
                    constrain(settingReference) { // this: ConstrainScope
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                    }

                    constrain(searchReference){
                        start.linkTo(settingReference.end, 10.dp)
                        top.linkTo(parent.top, 16.dp)
                    }
                },
                transitionContent = { // this: TransitionScope
                    keyAttributes(searchReference){
                        // Rotate 45 degrees left at 25% of the progress
                        frame(frame = 25, keyFrameContent =  {
                            translationY = 15.dp
                        })

                        // Rotate 45 degrees right at 75% of the progress
                        frame(frame = 75, keyFrameContent =  {
                            translationY = 45.dp
                        })
                    }

                    onSwipe = OnSwipe(
                        anchor = boxReference,
                        side = SwipeSide.End,
                        direction = SwipeDirection.Down,
                        mode = SwipeMode.Spring,
                        onTouchUp = SwipeTouchUp.AutoComplete
                    )

                    onSwipe = OnSwipe(
                        anchor = boxReference,
                        side = SwipeSide.End,
                        direction = SwipeDirection.Up,
                        mode = SwipeMode.Spring,
                        onTouchUp = SwipeTouchUp.AutoComplete
                    )

            })
        },
        progress = 0f,
        Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .layoutId("boxReference"))

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
    }
}

@Preview
@Composable
fun PreviewAnimationLayout2() {
    AnimationLayout2()
}