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
import com.example.jetpack.R
import com.example.jetpack.database.enums.Star

@Composable
fun RatingBar(
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
        StartElement(
            selected = Star.ONE.value <= chosenStar.value,
            star = Star.ONE,
            enableDrawable = R.drawable.ic_star_enable,
            disableDrawable = R.drawable.ic_star_disable,
            modifier = Modifier.weight(1F),
            onClick = { onChangeStar(it) }
        )

        StartElement(
            selected = Star.TWO.value <= chosenStar.value,
            star = Star.TWO,
            enableDrawable = R.drawable.ic_star_enable,
            disableDrawable = R.drawable.ic_star_disable,
            modifier = Modifier.weight(1F),
            onClick = { onChangeStar(it) }
        )

        StartElement(
            selected = Star.THREE.value <= chosenStar.value,
            star = Star.THREE,
            enableDrawable = R.drawable.ic_star_enable,
            disableDrawable = R.drawable.ic_star_disable,
            modifier = Modifier.weight(1F),
            onClick = { onChangeStar(it) }
        )

        StartElement(
            selected = Star.FOUR.value <= chosenStar.value,
            star = Star.FOUR,
            enableDrawable = R.drawable.ic_star_enable,
            disableDrawable = R.drawable.ic_star_disable,
            modifier = Modifier.weight(1F),
            onClick = { onChangeStar(it) }
        )

        StartElement(
            selected = Star.FIVE.value <= chosenStar.value,
            star = Star.FIVE,
            enableDrawable = R.drawable.ic_star_special_enable,
            disableDrawable = R.drawable.ic_star_special_disable,
            modifier = Modifier.weight(1F),
            onClick = { onChangeStar(it) }
        )
    }
}

@Composable
fun StartElement(
    modifier: Modifier = Modifier,
    selected: Boolean,
    star: Star,
    onClick: (Star) -> Unit = {},
    @DrawableRes enableDrawable: Int,
    @DrawableRes disableDrawable: Int,
) {
    Image(
        modifier = modifier
            .padding(5.dp)
            .clip(shape = CircleShape)
            .clickable { onClick(star) },
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
        RatingBar(chosenStar = Star.ONE)
        RatingBar(chosenStar = Star.TWO)
        RatingBar(chosenStar = Star.THREE)
        RatingBar(chosenStar = Star.FOUR)
        RatingBar(chosenStar = Star.FIVE)
    }
}