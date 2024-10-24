package com.example.jetpack.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.TimeConfiguration
import com.example.jetpack.ui.theme.ColorTextHelp
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlinx.coroutines.launch

@Composable
fun WheelTimePickerElement(
    timeConfiguration: TimeConfiguration = TimeConfiguration.Hour,
    initialValue: Int = 0,
    enableKeyboard: Boolean = false,

    onChangeValue: (Int) -> Unit = {},
    onChangeKeyboard: (Boolean) -> Unit = {},
) {
    var timeValue by remember { mutableIntStateOf(0) }
    val timeState = rememberPagerState(initialPage = timeConfiguration.initialPage + initialValue,
        pageCount = { timeConfiguration.maximumPage })


    /** For enable text field */
    //var openKeyboard by remember { mutableStateOf(false) }
    var timeQuery by remember(timeValue) { mutableStateOf("${timeValue - 1}") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()


    /** Whenever users scroll up/down then update the current value */
    LaunchedEffect(Unit) {
        snapshotFlow { timeState.settledPage }.collect { page ->
            val outcome = page % timeConfiguration.step
            onChangeValue(outcome)
        }
    }


    if (enableKeyboard) {
        val previousTime by remember(timeValue) { mutableIntStateOf(timeValue - 2) }
        val nextTime by remember(timeValue) { mutableIntStateOf(timeValue) }

        Column(
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(1 / 3f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(color = LocalTheme.current.background),
            ) {
                Text(
                    text = if (previousTime < 10) "0${previousTime}" else previousTime.toString(),
                    style = customizedTextStyle(
                        fontSize = 30, fontWeight = 300, color = ColorTextHelp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }

            BasicTextField(value = timeQuery,
                onValueChange = { timeQuery = it },
                textStyle = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight(500),
                    color = LocalTheme.current.textColor,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onChangeKeyboard(false)
                    coroutineScope.launch {
                        timeState.animateScrollToPage(
                            page = timeConfiguration.initialPage + timeQuery.toInt(),
                        )
                    }
                    keyboardController?.hide()
                }),
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = LocalTheme.current.primary,
                        shape = RoundedCornerShape(15.dp),
                    )
                    .background(color = LocalTheme.current.background)
                    .width(80.dp)
                    .padding(vertical = 22.5.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(color = LocalTheme.current.background),
            ) {
                Text(
                    text = if (nextTime < 10) "0${nextTime}" else nextTime.toString(),
                    style = customizedTextStyle(
                        fontSize = 30, fontWeight = 300, color = ColorTextHelp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
        }
    } else {
        VerticalPager(
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(1 / 3f),
            pageSize = PageSize.Fixed(80.dp),
            state = timeState,
            contentPadding = PaddingValues(vertical = 80.dp),
            pageContent = { page: Int ->
                timeValue = page % timeConfiguration.step
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape = RoundedCornerShape(15.dp))
                        .clickable {
                            onChangeKeyboard(true)
                        }
                        .background(
                            color = if (timeState.settledPage == page)
                                LocalTheme.current.background.copy(alpha = 0.5f)
                            else
                                LocalTheme.current.background
                        ),
                ) {
                    Text(
                        text = if (timeValue < 10) "0${timeValue}" else timeValue.toString(),
                        style = if (timeState.settledPage == page) customizedTextStyle(
                            fontSize = 32, fontWeight = 500, color = LocalTheme.current.textColor
                        )
                        else customizedTextStyle(
                            fontSize = 30, fontWeight = 300, color = ColorTextHelp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                }
            },
        )
    }
}

@Preview
@Composable
private fun PreviewScreenRecordingTimeElement() {
    WheelTimePickerElement(initialValue = 0, onChangeValue = { hour -> })
}