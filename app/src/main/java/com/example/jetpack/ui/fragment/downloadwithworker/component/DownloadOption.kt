package com.example.jetpack.ui.fragment.downloadwithworker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun DownloadOption(
    title: String = "PDF Example",
    onClick: ()->Unit = {},
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = LocalTheme.current.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = LocalTheme.current.background,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = customizedTextStyle(
                fontWeight = 600,
                fontSize = 16,
                color = LocalTheme.current.textColor
            ),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(Int.MAX_VALUE)
        )

        Text(
            style = customizedTextStyle(
                fontWeight = 400,
                fontSize = 14,
                color = LocalTheme.current.textColor
            ),
            text = stringResource(R.string.tap_to_set_up),
        )
    }
}

@Preview
@Composable
private fun PreviewDownloadOption() {
    DownloadOption()
}