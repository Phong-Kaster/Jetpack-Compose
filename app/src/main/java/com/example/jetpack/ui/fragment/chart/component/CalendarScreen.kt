package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.view.ExpandedCalendar
import java.time.YearMonth

@Composable
fun CalendarScreen() {
    CoreLayout(
        backgroundColor = LocalTheme.current.background,
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                ExpandedCalendar(
                    modifier = Modifier.fillMaxWidth(),
                    initialMonth = YearMonth.of(2020, 3)
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewCalendarScreen() {
    CalendarScreen()
}