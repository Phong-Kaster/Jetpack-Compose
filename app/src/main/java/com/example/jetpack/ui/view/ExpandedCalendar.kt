package com.example.jetpack.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.CalendarWeekday
import com.example.jetpack.domain.enums.Weekday
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.AppUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

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
    val TAG = "ExpandedCalendar"
    val maximumPage = 12
    var chosenCalendarWeekday by remember { mutableStateOf(CalendarWeekday()) }
    val pagerState = rememberPagerState(
        initialPage = (maximumPage * 0.5f).toInt(),
        pageCount = { maximumPage }
    )

    val currentMonthOffset by remember(pagerState.currentPage) {
        derivedStateOf {
            pagerState.currentPage - (maximumPage * 0.5f).toInt()
        }
    }

    val today = YearMonth.now()
    val todayOffset = today.monthValue - initialMonth.monthValue + (today.year - initialMonth.year) * 12
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        snapshotFlow { pagerState.settledPage }.collectLatest { settledPage ->
            AppUtil.logcat(tag = TAG, message = "settledPage = $settledPage", enableDivider = true)
        }
    }


    LaunchedEffect(Unit){
        AppUtil.logcat(tag = TAG, message = "initialMonth = $initialMonth", enableDivider = true)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "${chosenCalendarWeekday.dayOfWeek}, ${chosenCalendarWeekday.gregorianDay}/${chosenCalendarWeekday.gregorianMonth}/${chosenCalendarWeekday.gregorianYear}",
            style = customizedTextStyle(
                fontSize = 16,
                fontWeight = 600,
                color = LocalTheme.current.textColor,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LocalTheme.current.background)
                .wrapContentHeight(),
            verticalAlignment = Alignment.Top
        ) { page ->
            val currentMonth = initialMonth.plusMonths(page.toLong())
            val daysInMonth = currentMonth.lengthOfMonth()
            val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7
            val daysToPrepend = if (firstDayOfWeek == 0) 0 else firstDayOfWeek

            AppUtil.logcat(tag = TAG, message = "currentMonth ${currentMonth.monthValue} has $daysInMonth days")
            AppUtil.logcat(tag = TAG, message = "currentMonthOffset = $currentMonthOffset")
            AppUtil.logcat(tag = TAG, message = "firstDayOfWeek = $firstDayOfWeek")
            AppUtil.logcat(tag = TAG, message = "daysToPrepend = $daysToPrepend")

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = modifier.background(color = LocalTheme.current.background),
                verticalArrangement = Arrangement.Top
            ) {
                itemsIndexed(
                    items = Weekday.entries,
                    key = { index, weekday -> index },
                    itemContent = { index, weekday ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(weekday.shortName),
                                style = customizedTextStyle(
                                    color = LocalTheme.current.textColor
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                )

                items(
                    count = daysInMonth + daysToPrepend,
                    itemContent = { index ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(
                                    color = if (index >= daysToPrepend) {
                                        val dayOfMonth = index - daysToPrepend + 1
                                        val calendarWeekday = CalendarWeekday(
                                            localDate = currentMonth.atDay(dayOfMonth)
                                        )

                                        if (chosenCalendarWeekday == calendarWeekday) {
                                            LocalTheme.current.primary
                                        } else {
                                            Color.Transparent
                                        }
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .clickable {
                                    val calendarWeekday = if (index >= daysToPrepend) {
                                        val dayOfMonth = index - daysToPrepend + 1
                                        CalendarWeekday(localDate = currentMonth.atDay(dayOfMonth))
                                    } else
                                        null
                                    chosenCalendarWeekday = calendarWeekday ?: CalendarWeekday()
                                    calendarWeekday?.let { Log.d("Date Clicked", it.toString()) }
                                }
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (index >= daysToPrepend) {
                                val dayOfMonth = index - daysToPrepend + 1
                                val calendarWeekday = CalendarWeekday(localDate = currentMonth.atDay(dayOfMonth))
                                Text(
                                    text = dayOfMonth.toString(),
                                    style = customizedTextStyle(),
                                    textAlign = TextAlign.Center,
                                    color = if (calendarWeekday == chosenCalendarWeekday) LocalTheme.current.secondary else Color.White
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}