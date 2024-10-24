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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.Background2
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
            .clip(shape = RoundedCornerShape(20.dp))
            .borderWithAnimatedGradient(
                colorBackground = LocalTheme.current.background,
                width = 3.dp,
                shape = RoundedCornerShape(25.dp),
                colors = listOf(Color(0xFF004BDC), Color(0xFF004BDC), Color(0xFF9EFFFF), Color(0xFF9EFFFF), Color(0xFF9EFFFF), Color(0xFF9EFFFF), Color(0xFF004BDC), Color(0xFF004BDC)),
            )
            .background(color = LocalTheme.current.background, shape = RoundedCornerShape(25.dp))
            .clickable { onClick(language) }
            .aspectRatio(1F)
    ) {

        Image(
            painter = painterResource(id = language.drawable),
            contentDescription = stringResource(id = R.string.icon),
            modifier = Modifier.size(25.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = language.text),
            style = customizedTextStyle(fontWeight = 400, fontSize = 18),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = language.name,
            style = customizedTextStyle(fontWeight = 400, fontSize = 28),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


        Text(
            text = language.code,
            style = customizedTextStyle(fontWeight = 300, fontSize = 16),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.End
        ) {
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