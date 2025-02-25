package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.ExpandedCalendar

@Composable
fun CalendarScreen() {
    CoreLayout(
        backgroundColor = LocalTheme.current.background,
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                ExpandedCalendar(modifier = Modifier.fillMaxWidth())
            }
        }
    )
}

@Preview
@Composable
private fun PreviewCalendarScreen() {
    CalendarScreen()
}