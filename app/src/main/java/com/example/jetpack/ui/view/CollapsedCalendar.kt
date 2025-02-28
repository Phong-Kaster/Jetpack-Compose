package com.example.jetpack.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.CalendarWeekday
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.AppUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.YearMonth

@Composable
fun CollapsedCalendar(
    modifier: Modifier = Modifier,
    initialMonth: YearMonth,
) {
    val TAG = "ExpandedCalendar"
    val maximumPage = 1000000

    var chosenWeekday by remember { mutableStateOf(CalendarWeekday()) }
    val pagerState = rememberPagerState(initialPage = maximumPage / 2, pageCount = { maximumPage })
    val coroutineScope = rememberCoroutineScope()

    
    fun getDaysOfWeek(baseDate: YearMonth): List<java.time.LocalDate> {
        val firstDayOfWeek = baseDate.atDay(1).dayOfWeek.value
        val startOfWeek = baseDate.atDay(1).minusDays(firstDayOfWeek.toLong() - 1)
        return List(7) { startOfWeek.plusDays(it.toLong()) }
    }

    fun YearMonth.plusWeeks(weeksToAdd: Long): YearMonth {
        return this.plusMonths((weeksToAdd * 7) / 30)
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest { page ->
                Log.d(TAG, "Navigated to week: $page")
            }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth()
    ) { page ->
        val weekOffset = page - (maximumPage / 2)
        val updatedMonth = initialMonth.plusWeeks(weekOffset.toLong())
        val daysInWeek = getDaysOfWeek(updatedMonth)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysInWeek.forEach { day ->
                val calendarWeekday = CalendarWeekday(localDate = day)
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(
                            color = if (chosenWeekday == calendarWeekday)
                                LocalTheme.current.primary
                            else
                                Color.Transparent
                        )
                        .clickable { chosenWeekday = calendarWeekday }
                        .padding(5.dp)
                        .size(40.dp),
                ) {
                    Text(
                        text = day.dayOfWeek.name.take(3),
                        style = if (calendarWeekday == chosenWeekday)
                            customizedTextStyle(color = LocalTheme.current.secondary)
                        else
                            customizedTextStyle(color = Color.White),
                    )
                    Text(
                        text = day.dayOfMonth.toString(),
                        style = if (calendarWeekday == chosenWeekday)
                            customizedTextStyle(color = LocalTheme.current.secondary)
                        else
                            customizedTextStyle(color = Color.White),
                    )
                }
            }
        }
    }


}

@Preview
@Composable
private fun PreviewCollapsedCalendar() {
    CollapsedCalendar(
        modifier = Modifier,
        initialMonth = YearMonth.of(2020, 3)
    )
}