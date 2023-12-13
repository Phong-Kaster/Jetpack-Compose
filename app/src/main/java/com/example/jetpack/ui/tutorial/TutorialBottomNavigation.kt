package com.example.jetpack.ui.tutorial

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetpack.ui.theme.ChartColor4
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.ViewUtil

@Composable
fun TutorialBottomNavigation() {
    val tutorial = LocalTutorialBottomNavigationState.current

    AppUtil.logcat(message= "${tutorial.addButtonSize.topLeft}")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = 0.6F))
            .drawBehind {
                clipPath(
                    Path().apply {
                        addOval(tutorial.addButtonSize.inflate(6.dp.toPx()))
                    },
                    clipOp = ClipOp.Difference
                ) {
                    drawRect(color = Color.White.copy(0.6f))
                }
            }
    ) {
        val (button, text) = createRefs()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                /*.offset(
                    x = tutorial.addButtonSize.topLeft.x.dp,
                    y = tutorial.addButtonSize.topLeft.y.dp
                )*/
                .clip(shape = CircleShape)
                .size(48.dp)
                .background(color = ChartColor4)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                }

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewTutorialBottomNavigation() {
    ViewUtil.PreviewContent {
        TutorialBottomNavigation()
    }
}