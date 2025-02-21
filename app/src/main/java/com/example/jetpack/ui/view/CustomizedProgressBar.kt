package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun CustomizedProgressBar(
    modifier: Modifier = Modifier,
) {
    val corner = remember { 13.dp }

    val pathMeasure by remember { mutableStateOf(PathMeasure()) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = LocalTheme.current.background)
            .aspectRatio(4 / 3f)
            .drawBehind {
                val path = androidx.compose.ui.graphics.Path()


                drawRoundRect(
                    color = Color.Red,
                    size = Size(
                        width = size.width * 0.5f,
                        height = size.height * 0.5f,
                    ),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
                )

                drawPath(
                    path = path,
                    color = Color.Yellow
                )
            }
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = customizedTextStyle(
                fontWeight = 400, fontSize = 18,
                color = LocalTheme.current.textColor
            ),
        )
    }


}

@Preview
@Composable
private fun PreviewCustomizedProgressBar() {
    CustomizedProgressBar()
}