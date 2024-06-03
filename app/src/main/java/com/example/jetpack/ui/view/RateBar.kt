package com.example.jetpack.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.data.enums.Star

@Composable
fun RateBar(
    modifier: Modifier = Modifier,
    chosenStar: Star,
    onChangeStar: (Star) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Star.entries.forEach {
            StartElement(
                selected = it.value <= chosenStar.value,
                enableDrawable = it.enableDrawable,
                disableDrawable = it.disableDrawable,
                modifier = Modifier.weight(1F),
                onClick = { onChangeStar(it) }
            )
        }
    }
}

@Composable
fun StartElement(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit = {},
    @DrawableRes enableDrawable: Int,
    @DrawableRes disableDrawable: Int,
) {
    Image(
        modifier = modifier
            .padding(5.dp)
            .clip(shape = CircleShape)
            .clickable { onClick() },
        painter = if (selected) painterResource(enableDrawable)
        else painterResource(id = disableDrawable),
        contentDescription = "icon",
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
fun PreviewRatingBar() {
    Column {
        RateBar(chosenStar = Star.ONE)
        RateBar(chosenStar = Star.TWO)
        RateBar(chosenStar = Star.THREE)
        RateBar(chosenStar = Star.FOUR)
        RateBar(chosenStar = Star.FIVE)
    }
}