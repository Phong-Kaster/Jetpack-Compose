package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.ColorAirQualityGood
import com.example.jetpack.ui.theme.ColorAirQualityModerate
import com.example.jetpack.ui.theme.ColorAirQualitySatisfactory

@Composable
fun HighlightIndexColor(
    modifier: Modifier = Modifier,
    colorLeft: Color = ColorAirQualityGood,
    colorCenter: Color = ColorAirQualitySatisfactory,
    colorRight: Color = ColorAirQualityModerate,
    titleLeft: String? = null,
    titleCenter: String? = null,
    titleRight: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
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
                        color = colorLeft,
                        shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                    )
            ) {
                Text(text = "0 - 40", color = Color.White)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .background(
                        color = colorCenter,
                    )
            ) {
                Text(text = "40 - 60", color = Color.White)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .background(
                        color = colorRight,
                        shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                    )
            ) {
                Text(text = "60 - 100", color = Color.White)
            }
        }


        if(titleLeft != null || titleCenter != null || titleRight != null){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = titleLeft ?: "",
                    color = Color.White,
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = titleCenter ?: "",
                    color = Color.White,
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = titleRight ?: "",
                    color = Color.White,
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewHighlightIndexColor() {
    Column {
        HighlightIndexColor()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Divider()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        HighlightIndexColor(
            titleLeft = "Good",
            titleCenter = "Satisfactory",
            titleRight = "Moderate"
        )
    }
}