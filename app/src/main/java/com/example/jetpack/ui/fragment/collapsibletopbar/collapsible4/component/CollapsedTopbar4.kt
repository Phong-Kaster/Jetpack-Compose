package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible4.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun CollapsedTopbar4(
    isCollapsed: Boolean,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val colorTitle: Color by animateColorAsState(
        label = "colorTitle",
        targetValue = if (isCollapsed)
            LocalTheme.current.textColor
        else
            Color.Transparent,
    )

    val colorIcon: Color by animateColorAsState(
        label = "colorTitle",
        targetValue = if (isCollapsed)
            LocalTheme.current.primary
        else
            LocalTheme.current.background,
    )


    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors =
                    listOf(
                        LocalTheme.current.secondary,
                        LocalTheme.current.secondary,
                        LocalTheme.current.primary,
                    )
                )
            )
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            tint = LocalTheme.current.primary,
            contentDescription = "Icon",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = LocalTheme.current.background)
                .padding(3.dp)
                .clickable { onBack() }
                .size(20.dp)
        )


        Text(
            text = stringResource(R.string.app_name),
            style = customizedTextStyle(
                fontSize = 22,
                fontWeight = 800,
                color = colorTitle
            )
        )
    }


}

@Preview
@Composable
private fun PreviewCollapsibleTopbarCollapsed() {
    CollapsedTopbar4(
        isCollapsed = true,
        onBack = {},
        modifier = Modifier.fillMaxWidth()
    )
}


@Preview
@Composable
private fun PreviewCollapsibleTopbar() {
    CollapsedTopbar4(
        isCollapsed = false,
        onBack = {},
        modifier = Modifier.fillMaxWidth()
    )
}