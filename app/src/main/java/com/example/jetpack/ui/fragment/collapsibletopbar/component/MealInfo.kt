package com.example.jetpack.ui.fragment.collapsibletopbar.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealInfo(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "150",
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 500,
                    color = Color.White
                )
            )

            Text(
                text = stringResource(id = R.string.fake_title),
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 400,
                    color = Color.White
                ),
                maxLines = 1,
                modifier = Modifier
                    .wrapContentWidth()
                    .basicMarquee(Int.MAX_VALUE)
            )
        }
        VerticalDivider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxHeight(fraction = 0.25F)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "60.5",
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 500,
                    color = Color.White
                )
            )

            Text(
                text = stringResource(id = R.string.fake_title),
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 400,
                    color = Color.White
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .basicMarquee(Int.MAX_VALUE)
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxHeight(fraction = 0.25F)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "25",
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 500,
                    color = Color.White
                )
            )

            Text(
                text = stringResource(id = R.string.fake_title),
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 400,
                    color = Color.White
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .basicMarquee(Int.MAX_VALUE)
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxHeight(fraction = 0.25F)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "125",
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 500,
                    color = Color.White
                )
            )

            Text(
                text = stringResource(id = R.string.fake_title),
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 400,
                    color = Color.White
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .basicMarquee(Int.MAX_VALUE)
            )
        }
    }
}


@Preview
@Composable
fun PreviewMealInfo() {
    Column(modifier = Modifier.fillMaxWidth().background(color = Color.DarkGray)) {
        MealInfo(
            modifier = Modifier
        )
    }
}