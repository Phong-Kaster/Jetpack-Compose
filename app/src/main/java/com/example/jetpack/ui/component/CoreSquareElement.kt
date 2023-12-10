package com.example.jetpack.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun SquareElement(
    language: Language,
    onClick: (Language) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            /*.onGloballyPositioned {
                if (highlight.highlightTag.icon == R.drawable.ic_air_quality) {
                    IntroOffset.airQuality = try {
                        highlight.title!!.toInt()
                    } catch (_: Exception) {
                        0
                    }
                    IntroOffset.offsetHighLightDetail =
                        it.positionInRoot().y.toInt() - getStatusBarHeight()
                }
            }*/
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.Black.copy(alpha = 0.1F))
            .clickable { onClick(language) }
            .aspectRatio(1F)
            .border(
                width = 0.2.dp,
                color = Color.White.copy(alpha = 0.5F),
                shape = RoundedCornerShape(20.dp)
            )
    ) {

        Image(
            painter = painterResource(id = language.drawable),
            contentDescription = stringResource(id = R.string.icon),
            modifier = Modifier.size(25.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = language.text),
            style = customizedTextStyle(fontWeight = 400, fontSize = 18)
        )

        Text(
            text = language.name,
            style = customizedTextStyle(fontWeight = 400, fontSize = 28)
        )


        Text(
            text = language.code,
            style = customizedTextStyle(fontWeight = 300, fontSize = 16),
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.End) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward_circle),
                contentDescription = stringResource(id = R.string.icon),
                tint = Color.White,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun PreviewSquareElement() {
    ViewUtil.PreviewContent {
        SquareElement(language = Language.German)
    }
}