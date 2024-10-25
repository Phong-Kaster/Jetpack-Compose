package com.example.jetpack.ui.fragment.home.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.HomeShortcut
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun HomeShortcutItem(
    shortcut: HomeShortcut,
    onClick: (HomeShortcut) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable { onClick(shortcut) }
            .border(
                width = 1.dp,
                color = LocalTheme.current.textColor.copy(alpha = 0.5F),
                shape = RoundedCornerShape(15.dp)
            )
            /*.background(color = Color.Black.copy(alpha = 0.1F))*/
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Icon(
            painter = painterResource(id = shortcut.drawable),
            contentDescription = stringResource(id = R.string.icon),
            modifier = Modifier.size(25.dp),
            tint = LocalTheme.current.textColor
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(0.9F)){
            Text(
                text = stringResource(id = shortcut.text),
                color = LocalTheme.current.textColor,
                style = customizedTextStyle(fontWeight = 400, fontSize = 18),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE)
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            tint = LocalTheme.current.textColor,
            contentDescription = null
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