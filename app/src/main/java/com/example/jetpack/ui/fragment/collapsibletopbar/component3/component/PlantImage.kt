package com.example.jetpack.ui.fragment.collapsibletopbar.component3.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.jetpack.R

@Composable
fun PlantImage(
    imageHeight: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(imageHeight)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nazi_germany_flag),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}