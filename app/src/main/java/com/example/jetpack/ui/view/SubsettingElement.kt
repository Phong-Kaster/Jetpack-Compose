package com.example.jetpack.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.Subsetting
import com.example.jetpack.ui.theme.ColorTextSecondary
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun SubsettingElement(
    subsetting: Subsetting,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(color = LocalTheme.current.background)
            .padding(16.dp)

    ) {
        Image(
            painter = painterResource(id = subsetting.icon),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )

        Text(
            text = stringResource(id = subsetting.text),
            style = customizedTextStyle(fontSize = 16, color = LocalTheme.current.textColor, fontWeight = 400),
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "Icon",
                tint = LocalTheme.current.textColor,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSubsettingElement() {
    SubsettingElement(subsetting = Subsetting.TimedRecording)
}