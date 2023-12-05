package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.ChartColor1
import com.example.jetpack.ui.theme.ChartColor2
import com.example.jetpack.ui.theme.ChartColor3
import com.example.jetpack.ui.theme.ChartColor4
import com.example.jetpack.ui.theme.ChartColor5
import com.example.jetpack.ui.theme.ChartColor6
import com.example.jetpack.ui.theme.body14
import com.example.jetpack.util.ViewUtil

@Composable
fun ColorBar(
    color1: Color = ChartColor4,
    color2: Color = ChartColor5,
    color3: Color = ChartColor6,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .background(
                    color = color1,
                    shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                )
        ) {
            Text(text = "0 - 40", style = body14, color = Color.White)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .background(
                    color = color2,
                )
        ) {
            Text(text = "40 - 60", style = body14, color = Color.White)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .background(
                    color = color3,
                    shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                )
        ) {
            Text(text = "60 - 100", style = body14, color = Color.White)
        }
    }
}

@Preview
@Composable
fun PreviewColorBar() {
    ViewUtil.PreviewContent {
        ColorBar(
            color1 = ChartColor1,
            color2 = ChartColor2,
            color3 = ChartColor3
        )
    }
}