package com.example.jetpack.ui.view.wordbyword

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.ui.view.wordbyword.state.CharByCharAnimationState
import com.example.jetpack.ui.view.wordbyword.state.LineByLineAnimationState
import com.example.jetpack.ui.view.wordbyword.state.WordByWordAnimationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SimpleChuckedTextAnimation(modifier: Modifier = Modifier) {
    val textMeasurer = rememberTextMeasurer()
    val screenWidthPx =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }

    val textStyle = LocalTextStyle.current.copy(
        fontSize = 20.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight(600),
        color = Color.White,
    )

    val wordByWordState = remember { WordByWordAnimationState(textMeasurer, textStyle) }
    val lineByLineState = remember { LineByLineAnimationState(textMeasurer, textStyle) }
    val charByCharState = remember { CharByCharAnimationState(textMeasurer, textStyle) }

    val dummyText = "Talk with Elyx..."

    LaunchedEffect(Unit) {
        launch {
            wordByWordState.loadText(
                text = dummyText,
                constraints = Constraints(maxWidth = screenWidthPx)
            )
            delay(1000)
            wordByWordState.showText(200)
        }
        launch {
            lineByLineState.loadText(
                text = dummyText,
                constraints = Constraints(maxWidth = screenWidthPx)
            )
            delay(1000)
            lineByLineState.showText(200)
        }

        launch {
            charByCharState.loadText(
                text = dummyText,
                constraints = Constraints(maxWidth = screenWidthPx)
            )
            delay(1000)
            charByCharState.showText(200)
        }
    }

    WordByWordAnimation(
        state = charByCharState,
        chunkAnimation = {
            fadeIn(
                animationSpec = tween(durationMillis = 0, easing = LinearEasing),
            )
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PreviewSimpleChuckedTextAnimation() {
    SimpleChuckedTextAnimation()
}