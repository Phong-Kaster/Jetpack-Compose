package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.Subsetting
import com.example.jetpack.ui.component.SquareElement
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.view.ContextualFlowRowSample
import com.example.jetpack.ui.view.SubsettingElement

@Composable
fun ComponentScreen(
    onOpenAlertDialog: () -> Unit = {},
    onOpenDottedTextDialog: () -> Unit = {},
    onOpenDialog: () -> Unit = {},
    onOpenWheelTimePicker: () -> Unit = {},
) {
    val state = rememberLazyGridState()
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 32.dp)
            .fillMaxSize()
    ) {
        items(
            items = Language.entries.take(2),
            key = { item: Language -> item.name },
            itemContent = { language: Language ->
                SquareElement(
                    language = language,
                    onClick = {
                        when (language) {
                            Language.English -> onOpenAlertDialog()
                            Language.German -> onOpenDottedTextDialog()
                            else -> onOpenDialog()
                        }
                    })
            }
        )

        item(
            key = "WheelTimePicker",
            span = { GridItemSpan(2) },
            content = {
                SubsettingElement(
                    subsetting = Subsetting.TimedRecording,
                    onClick = onOpenWheelTimePicker,
                    modifier = Modifier
                        .fillMaxWidth()
                        .borderWithAnimatedGradient(
                            colorBackground = LocalTheme.current.background,
                            width = 3.dp,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(
                            color = LocalTheme.current.background,
                            shape = RoundedCornerShape(25.dp)
                        )
                )
            }
        )

        item(
            key = "ContextualFlowRow",
            span = { GridItemSpan(2) },
            content = {
                ContextualFlowRowSample(
                    modifier = Modifier
                        .fillMaxWidth()
                        .borderWithAnimatedGradient(
                            colorBackground = LocalTheme.current.background,
                            width = 3.dp,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(
                            color = LocalTheme.current.background,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(vertical = 10.dp)
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewComponentScreen() {
    ComponentScreen()
}