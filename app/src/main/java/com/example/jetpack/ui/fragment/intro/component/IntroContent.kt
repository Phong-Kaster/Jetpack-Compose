package com.example.jetpack.ui.fragment.intro.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.TextColor1
import com.example.jetpack.ui.theme.body16
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.CenterColumn

@Composable
fun IntroContent(
    @StringRes titleId: Int = R.string.fake_title,
    @StringRes contentId: Int = R.string.fake_message,
    @DrawableRes imageId: Int = R.drawable.ic_launcher_foreground,
) {
    CenterColumn(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(titleId),
            style = customizedTextStyle(30, 700),
            color = LocalTheme.current.textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(contentId),
            style = body16,
            color = LocalTheme.current.textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Icon(
            painter = painterResource(id = imageId),
            contentDescription = null,
            tint =  LocalTheme.current.textColor,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .weight(1f),
        )
    }
}

@Preview
@Composable
fun PreviewIntroContent() {
    ViewUtil.PreviewContent {
        IntroContent()
    }
}