package com.example.jetpack.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

/**
 * Progress Bar
 *
 * @param percentage from 0% to 100%
 * @param trackColor color of current progress bar
 * @param modifier Modifier of composable
 */
@Composable
fun ProgressBar(
    percentage: Float = 20f,
    trackColor: Color = Color(0xFF004BDC),
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = customizedTextStyle(
                        fontSize = 12,
                        fontWeight = 400,
                        color = LocalTheme.current.textColor
                    ).toSpanStyle()
                ) {
                    append(text = "Progress")
                }
                append(text = "\t\t")
                withStyle(
                    style = customizedTextStyle(
                        fontSize = 12,
                        fontWeight = 700,
                        color = LocalTheme.current.textColor
                    ).toSpanStyle()
                ) {
                    append(text = "${percentage}%")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Canvas(
            modifier = Modifier.fillMaxWidth(),
            onDraw = {
                val spacing = this.size.width

                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = 10f,
                    cap = StrokeCap.Round,
                )

                drawLine(
                    color = trackColor,
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = spacing * (percentage / 100f), y = size.height),
                    strokeWidth = 10f,
                    cap = StrokeCap.Round,
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewProgressBar() {
    ProgressBar(
        percentage = 55.59f,
        modifier = Modifier.fillMaxWidth()
            .background(
                color = Color.DarkGray
            )
            .padding(16.dp)
    )
}