package com.example.jetpack.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContextualFlowRowSample(
    modifier: Modifier = Modifier,
) {
    val MAXIMUM = 2
    var maxLines by remember { mutableIntStateOf(MAXIMUM) }
    ContextualFlowRow(
        modifier = modifier
            .animateContentSize(),
        itemCount = Language.entries.size,
        maxLines = maxLines,
        overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
            expandIndicator = {
                TextButton(
                    onClick = { maxLines += 1 },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.surfaceVariant,
                        containerColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(text = "${this@expandOrCollapseIndicator.totalItemCount - this@expandOrCollapseIndicator.shownItemCount}+ more")
                }
            },
            collapseIndicator = {
                TextButton(
                    onClick = { maxLines = MAXIMUM },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.errorContainer,
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(10.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                    Text(text = "Hide")
                }
            }
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { index ->
        TextButton(
            onClick = {},
            colors = ButtonDefaults.textButtonColors(
                contentColor = LocalTheme.current.secondary,
                containerColor = LocalTheme.current.secondary
            ),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = Language.entries[index].name,
                style = customizedTextStyle(color = Color.White)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewContextualFlowRowSample() {
    ContextualFlowRowSample()
}