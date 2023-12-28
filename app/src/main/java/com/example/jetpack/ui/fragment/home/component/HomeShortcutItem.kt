package com.example.jetpack.ui.fragment.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import com.example.jetpack.R
import com.example.jetpack.data.enums.HomeShortcut
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun HomeShortcutItem(
    shortcut: HomeShortcut,
    onClick: (HomeShortcut) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable { onClick(shortcut) }
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.5F),
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = Color.Black.copy(alpha = 0.1F))
            .padding(horizontal = 16.dp, vertical = 16.dp)

            .aspectRatio(1F)

    ) {

        Icon(
            painter = painterResource(id = shortcut.drawable),
            contentDescription = stringResource(id = R.string.icon),
            modifier = Modifier.size(25.dp),
            tint = PrimaryColor
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = shortcut.text),
            color = PrimaryColor,
            style = customizedTextStyle(fontWeight = 400, fontSize = 18),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun PreviewHomeShortcutItem() {
    ViewUtil.PreviewContent {
        HomeShortcutItem(shortcut = HomeShortcut.Tutorial)
    }
}