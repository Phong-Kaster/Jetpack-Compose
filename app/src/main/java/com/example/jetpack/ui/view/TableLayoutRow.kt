package com.example.jetpack.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun HighlightRowForTable(
    enableBottomRoundedCorner: Boolean = false,
    leftContent: String,
    @StringRes centerContent: Int,
    @DrawableRes emoji: Int
) {
    Column {
        Divider(thickness = 0.5.dp)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(
                    color = Color.White.copy(alpha = 0.05F),
                    shape =
                    if (enableBottomRoundedCorner)
                        RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                    else RoundedCornerShape(0.dp)
                )
        ) {
            Text(
                text = leftContent,
                color = Color.White,
                style = customizedTextStyle(fontWeight = 300, fontSize = 16),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .width(0.5.dp)
                    .background(color = Color.White)
                    .fillMaxHeight()
            )
            Text(
                text = stringResource(id = centerContent),
                color = Color.White,
                style = customizedTextStyle(fontWeight = 300, fontSize = 16),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .width(0.5.dp)
                    .background(color = Color.White)
                    .fillMaxHeight()
            )
            Image(
                painter = painterResource(id = emoji),
                contentDescription = null,
                modifier = Modifier
                    .weight(1F)
                    .size(25.dp)
            )
        }

        if (enableBottomRoundedCorner)
        else Divider(thickness = 0.5.dp)
    }

}

@Preview
@Composable
fun PreviewHighlightRowForTable() {

    Column {
        HighlightRowForTable(
            leftContent = "<50",
            centerContent = R.string.fake_title,
            emoji = R.drawable.ic_language_german
        )
        HighlightRowForTable(
            leftContent = "<50",
            centerContent = R.string.fake_title,
            emoji = R.drawable.ic_language_english
        )
        HighlightRowForTable(
            leftContent = "<50",
            centerContent = R.string.fake_title,
            emoji = R.drawable.ic_language_japanese,
            enableBottomRoundedCorner = true
        )
    }
}