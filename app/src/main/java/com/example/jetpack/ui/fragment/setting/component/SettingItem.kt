package com.example.jetpack.ui.fragment.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.ShadowColor
import com.example.jetpack.ui.theme.WeakColor
import com.example.jetpack.ui.theme.body14
import com.example.jetpack.ui.theme.body15
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.theme.outerShadow
import com.example.jetpack.util.ViewUtil

@Composable
fun SettingItem(
    icon: Int = R.drawable.ic_bottom_article,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .outerShadow(4.dp, 1.dp, ShadowColor, RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .borderWithAnimatedGradient(
                width = 2.dp,
                shape = RoundedCornerShape(15.dp),
            )
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .heightIn(64.dp)
            .background(color = LocalTheme.current.background)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = LocalTheme.current.textColor,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 600
                ),
                color = LocalTheme.current.textColor
            )
            if (subtitle != null) {
                Text(text = subtitle, style = body14, color = LocalTheme.current.dim)
            }
        }
        Spacer(modifier = Modifier.weight(0.9F))
        Icon(
            Icons.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = LocalTheme.current.textColor,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Preview
@Composable
fun PreviewSettingItem() {
    ViewUtil.PreviewContent {
        SettingItem(
            title = stringResource(id = R.string.fake_title),
            subtitle = stringResource(id = R.string.fake_title),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(18.dp))
        SettingItem(
            title = stringResource(id = R.string.fake_title),
            subtitle = null,
            onClick = {}
        )
    }
}