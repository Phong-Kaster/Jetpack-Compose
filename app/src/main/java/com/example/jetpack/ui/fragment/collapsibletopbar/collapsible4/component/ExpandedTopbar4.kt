package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible4.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.AnimatedProgressBar

@Composable
fun ExpandedTopbar4(
    modifier: Modifier = Modifier
) {

    val progress = 0.5f

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                )
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors =
                    listOf(
                        LocalTheme.current.secondary,
                        LocalTheme.current.secondary,
                        LocalTheme.current.primary,
                    )
                )
            )
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        /*Icon(
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            tint = LocalTheme.current.primary,
            contentDescription = "Icon",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = LocalTheme.current.background)
                .padding(3.dp)
                .clickable { onBack() }
                .size(20.dp)
        )*/

        Text(
            text = stringResource(R.string.app_name),
            style = customizedTextStyle(
                fontSize = 22,
                fontWeight = 800,
                color = LocalTheme.current.textColor
            )
        )

        Text(
            text = stringResource(R.string.fake_content),
            style = customizedTextStyle(
                fontSize = 14,
                fontWeight = 400,
                color = LocalTheme.current.textColor
            )
        )


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
                    append(text = "${progress * 100}%")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        AnimatedProgressBar(
            progress = progress,
            colors = Constant.LIST_OF_COLOUR,
            strokeWidth = 5.dp,
            strokeCap = StrokeCap.Round,
            gradientAnimationSpeed = 10000,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        )
    }
}


@Preview
@Composable
private fun PreviewExpandedTopbar() {
    ExpandedTopbar4(
        modifier = Modifier
    )
}