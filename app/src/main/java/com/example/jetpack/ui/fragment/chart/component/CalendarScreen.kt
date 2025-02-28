package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.CollapsedCalendar
import com.example.jetpack.ui.view.ExpandedCalendar
import java.time.YearMonth

@Composable
fun CalendarScreen() {
    CoreLayout(
        backgroundColor = LocalTheme.current.background,
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item(
                    key = "CollapsedCalendar",
                    content = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "1. CollapsedCalendar",
                                style = customizedTextStyle(
                                    fontSize = 14,
                                    fontWeight = 600,
                                    color = Color.White
                                )
                            )

                            CollapsedCalendar(
                                modifier = Modifier.fillMaxWidth(),
                                initialMonth = YearMonth.of(2020, 3)
                            )
                        }
                    }
                )

                item(
                    key = "ExpandedCalendar",
                    content = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "2. ExpandedCalendar",
                                style = customizedTextStyle(
                                    fontSize = 14,
                                    fontWeight = 600,
                                    color = Color.White
                                )
                            )
                            ExpandedCalendar(
                                modifier = Modifier.fillMaxWidth(),
                                initialMonth = YearMonth.of(2020, 3)
                            )
                        }
                    }
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