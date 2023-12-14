package com.example.jetpack.ui.fragment.tutorial.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.ColorUVIndexLow
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.toIntOffset

@Composable
fun TutorialStep3() {
    /*define animation*/
    val tutorial = LocalTutorial.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.1F))
            .drawBehind {
                clipPath(
                    Path().apply {
                        addRect(tutorial.photoRect.inflate(20.dp.toPx()))
                    },
                    clipOp = ClipOp.Difference
                ) {
                    drawRect(color = Color.Black.copy(0.7f))
                }
            }
            .clickable {
                tutorial.currentTutorial = 3
                tutorial.onDone()
            }
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .offset {
                    tutorial.photoRect.topLeft.toIntOffset() - IntOffset(
                        x = (tutorial.photoRect.width).toInt(),
                        y = 0
                    )
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_tutorial_arrow_3),
                contentDescription = null,
                tint = ColorUVIndexLow
            )

            Text(
                text = stringResource(id = R.string.fake_title),
                style = customizedTextStyle(),
                color = ColorUVIndexLow,
                textAlign = TextAlign.Center,
            )

        }
    }
}

@Preview
@Composable
fun PreviewTutorialStep3() {
    ViewUtil.PreviewContent {
        TutorialStep3()
    }
}