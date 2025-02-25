package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Preview
@Composable
private fun PreviewExpandableHorizontalCalendar() {
    ExpandedCalendar(
        modifier = Modifier.fillMaxWidth(),
    )
}


@Composable
fun ExpandedCalendar(
    modifier: Modifier = Modifier,
    initialMonth: YearMonth = YearMonth.now(),
) {
    val pagerState = rememberPagerState(
        initialPage = (Int.MAX_VALUE * 0.5f).toInt(),
        pageCount = { Int.MAX_VALUE }
    )
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())

    val currentMonthOffset = pagerState.currentPage - (Int.MAX_VALUE / 2)

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top
    ) { page ->
        val currentMonth = initialMonth.plusMonths((currentMonthOffset + page).toLong())
        val daysInMonth = currentMonth.lengthOfMonth()
        val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7 // Adjust so Monday is 0
        val daysToPrepend = if (firstDayOfWeek == 0) 0 else firstDayOfWeek

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = modifier.background(color = LocalTheme.current.background),
            verticalArrangement = Arrangement.Top
        ) {
            // Display weekday names starting from Monday
            val weekdays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            items(weekdays.size) { index ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = weekdays[index],
                        style = customizedTextStyle(
                            color = LocalTheme.current.textColor
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(daysInMonth + daysToPrepend) { index ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (index >= daysToPrepend) {
                        val dayOfMonth = index - daysToPrepend + 1
                        Text(
                            text = dayOfMonth.toString(),
                            style = customizedTextStyle(
                                color = LocalTheme.current.textColor
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}