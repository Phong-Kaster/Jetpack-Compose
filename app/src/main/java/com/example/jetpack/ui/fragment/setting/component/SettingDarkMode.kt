package com.example.jetpack.ui.fragment.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.ShadowColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.modifier.outerShadow
import com.example.jetpack.ui.view.AnimatedThemeSwitcher

@Composable
fun SettingDarkMode(
    enableDarkMode: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .outerShadow(4.dp, 1.dp, ShadowColor, RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .borderWithAnimatedGradient(
                width = 2.dp,
                shape = RoundedCornerShape(15.dp),
            )
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable {
                onClick()
            }
            .heightIn(64.dp)
            .background(color = LocalTheme.current.background)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = if (enableDarkMode)
                stringResource(id = R.string.dark_mode)
            else
                stringResource(id = R.string.light_mode),
            style = customizedTextStyle(
                fontSize = 16,
                fontWeight = 600,
                color = LocalTheme.current.textColor
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        AnimatedThemeSwitcher(
            enableDarkMode = enableDarkMode,
            color = LocalTheme.current.textColor,
            modifier = Modifier
                .size(35.dp),
        )
    }
}

@Preview
@Composable
private fun PreviewSettingDarkMode() {
    SettingDarkMode(
        enableDarkMode = true,
        onClick = {},
        modifier = Modifier,
    )
}