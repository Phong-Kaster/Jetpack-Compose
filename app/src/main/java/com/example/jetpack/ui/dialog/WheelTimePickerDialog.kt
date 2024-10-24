package com.example.jetpack.ui.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.TimeConfiguration
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.theme.ColorTextPrimary
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.DateUtil.toHour
import com.example.jetpack.util.DateUtil.toMinute
import com.example.jetpack.util.DateUtil.toSecond


@Composable
fun WheelTimePickerDialog(
    enable: Boolean,
    screenRecordingTime: Int,
    onDismissRequest: () -> Unit = {},
    onConfirm: (Int, Int, Int) -> Unit = { _, _, _ -> },
) {
    CoreDialog(
        enable = enable,
        onDismissRequest = onDismissRequest,
        content = {
            WheelTimePickerLayout(
                screenRecordingTime = screenRecordingTime,
                onDismissRequest = onDismissRequest,
                onConfirm = onConfirm
            )
        }
    )
}


@Composable
fun WheelTimePickerLayout(
    screenRecordingTime: Int,
    onDismissRequest: () -> Unit = {},
    onConfirm: (Int, Int, Int) -> Unit = { _, _, _ -> },
) {
    var isCustomized by remember { mutableStateOf(false) }

    var hour by remember { mutableIntStateOf(0) }
    var minute by remember { mutableIntStateOf(0) }
    var second by remember { mutableIntStateOf(0) }


    LaunchedEffect(key1 = screenRecordingTime) {
        isCustomized = screenRecordingTime != Constant.DEFAULT_SCREEN_RECORDING_TIME
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .imePadding()
            .requiredWidth(350.dp)
            .background(color = LocalTheme.current.background, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .clip(shape = CircleShape)
                .clickable { onDismissRequest() }
                .align(BiasAlignment(1f, 0f))) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = LocalTheme.current.textColor,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.timed_recording),
                style = TextStyle(
                    color = LocalTheme.current.textColor, fontSize = 16.sp, fontWeight = FontWeight(500)
                ),
                textAlign = TextAlign.Center
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 0.5.dp, color = Color(0xFFF1F1F1), shape = RoundedCornerShape(15.dp)
                )
                .padding(15.dp)
        ) {
            // OPTION NONE
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .background(color = LocalTheme.current.background)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        isCustomized = false
                    }
                    .padding(horizontal = 5.dp, vertical = 5.dp)
            ) {
                Image(
                    painter =
                    if (isCustomized)
                        painterResource(id = R.drawable.ic_circle_unchecked)
                    else
                        painterResource(id = R.drawable.ic_circle_checked),
                    contentDescription = "Icon",
                    modifier = Modifier.size(15.dp)
                )

                Text(
                    text = stringResource(id = R.string.none),
                    style = customizedTextStyle(
                        fontSize = 16,
                        fontWeight = 400,
                        color = LocalTheme.current.textColor
                    )
                )
            }


            // OPTION CUSTOMIZE
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .background(color = LocalTheme.current.background)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        isCustomized = true
                    }
                    .padding(horizontal = 5.dp, vertical = 5.dp)
            ) {
                Image(
                    painter =
                    if (isCustomized)
                        painterResource(id = R.drawable.ic_circle_checked)
                    else
                        painterResource(id = R.drawable.ic_circle_unchecked),
                    contentDescription = "Icon",
                    modifier = Modifier.size(15.dp)
                )

                Text(
                    text = stringResource(R.string.customize),
                    style = customizedTextStyle(
                        fontSize = 16,
                        fontWeight = 400,
                        color = LocalTheme.current.textColor
                    )
                )
            }


            AnimatedVisibility(
                visible = isCustomized,
                content = {
                    WheelTimePicker(
                        initialHour = screenRecordingTime.toHour(),
                        initialMinute = screenRecordingTime.toMinute(),
                        initialSecond = screenRecordingTime.toSecond(),
                        onChangeHour = { hour = it },
                        onChangeMinute = { minute = it },
                        onChangeSecond = { second = it },
                    )
                }
            )
        }

        Button(
            onClick = {
                onDismissRequest()
                onConfirm(hour, minute, second)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LocalTheme.current.secondary,
                contentColor = Color.White,
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            content = {
                Text(
                    text = stringResource(id = R.string.save),
                    style = customizedTextStyle(
                        fontSize = 18,
                        fontWeight = 500,
                        color = LocalTheme.current.textColor,
                    )
                )
            }
        )
    }
}


@Composable
fun WheelTimePicker(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    initialSecond: Int = 0,
    onChangeHour: (Int) -> Unit = {},
    onChangeMinute: (Int) -> Unit = {},
    onChangeSecond: (Int) -> Unit = {},
) {
    var enableKeyboardHour by remember { mutableStateOf(false) }
    var enableKeyboardMinute by remember { mutableStateOf(false) }
    var enableKeyboardSecond by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
        ) {
            Text(
                text = stringResource(R.string.hour),
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 400,
                    color = LocalTheme.current.textColor
                )
            )
            Text(
                text = stringResource(R.string.minute),
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 400,
                    color = LocalTheme.current.textColor
                )
            )
            Text(
                text = stringResource(R.string.second),
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 400,
                    color = LocalTheme.current.textColor
                )
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFF1F1F1),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {

            WheelTimePickerElement(
                timeConfiguration = TimeConfiguration.Hour,
                initialValue = initialHour,
                onChangeValue = onChangeHour,
                enableKeyboard = enableKeyboardHour,
                onChangeKeyboard = {
                    enableKeyboardHour = it
                    enableKeyboardMinute = false
                    enableKeyboardMinute = false
                },
            )

            Text(
                text = ":",
                style = customizedTextStyle(
                    fontSize = 40,
                    fontWeight = 400
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 5.dp)
            )

            WheelTimePickerElement(
                timeConfiguration = TimeConfiguration.Minute,
                initialValue = initialMinute,
                onChangeValue = onChangeMinute,
                enableKeyboard = enableKeyboardMinute,
                onChangeKeyboard = {
                    enableKeyboardHour = false
                    enableKeyboardMinute = it
                    enableKeyboardSecond = false
                },
            )

            Text(
                text = ":",
                style = customizedTextStyle(
                    fontSize = 40,
                    fontWeight = 400
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 5.dp)
            )

            WheelTimePickerElement(
                timeConfiguration = TimeConfiguration.Second,
                initialValue = initialSecond,
                onChangeValue = onChangeSecond,
                enableKeyboard = enableKeyboardSecond,
                onChangeKeyboard = {
                    enableKeyboardHour = false
                    enableKeyboardMinute = false
                    enableKeyboardSecond = it
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewScreenRecordingTimeLayout() {
    WheelTimePickerLayout(
        screenRecordingTime = 9999,
        onDismissRequest = {},
        onConfirm = { hour, minute, second -> },
    )
}