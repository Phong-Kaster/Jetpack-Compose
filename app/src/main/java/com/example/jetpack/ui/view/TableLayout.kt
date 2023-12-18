package com.example.jetpack.ui.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.TextColor5
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun TableLayout() {
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(
                    color = PrimaryColor,
                    shape = RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
        ) {
            Text(
                text = stringResource(id = R.string.fake_title),
                color = TextColor5,
                style = customizedTextStyle(fontWeight = 600, fontSize = 16),
                modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
                .padding(vertical = 16.dp), textAlign = TextAlign.Center)
            Spacer(
                modifier = Modifier
                .width(0.5.dp)
                .background(color = Color.White)
                .fillMaxHeight())
            Text(
                text = stringResource(id = R.string.fake_title),
                color = TextColor5,
                style = customizedTextStyle(fontWeight = 600, fontSize = 16),
                modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
                .padding(vertical = 16.dp),
                textAlign = TextAlign.Center)
            Spacer(
                modifier = Modifier
                .width(0.5.dp)
                .background(color = Color.White)
                .fillMaxHeight())
            Text(
                text = stringResource(id = R.string.fake_title),
                color = TextColor5,
                style = customizedTextStyle(fontWeight = 600, fontSize = 16),
                modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
                .padding(vertical = 16.dp), textAlign = TextAlign.Center)
        }

        HighlightRowForTable(leftContent = "<50°F", centerContent = R.string.fake_title, enableBottomRoundedCorner = false, emoji = R.drawable.ic_language_english)
        HighlightRowForTable(leftContent = "50 - 60°F", centerContent = R.string.fake_title, enableBottomRoundedCorner = false, emoji = R.drawable.ic_language_german)
        HighlightRowForTable(leftContent = "60 - 65°F", centerContent = R.string.fake_title, enableBottomRoundedCorner = false, emoji = R.drawable.ic_language_french)
        HighlightRowForTable(leftContent = "65 - 70°F", centerContent = R.string.fake_title, enableBottomRoundedCorner = false, emoji = R.drawable.ic_language_japanese)
        HighlightRowForTable(leftContent = "70°F+", centerContent = R.string.fake_title, enableBottomRoundedCorner = true, emoji = R.drawable.ic_language_vietnamese)

    }
}

@Preview
@Composable
fun PreviewTableLayout() {
    TableLayout()
}