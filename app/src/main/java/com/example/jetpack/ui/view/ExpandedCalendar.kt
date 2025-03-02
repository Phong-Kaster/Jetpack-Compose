package com.example.jetpack.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.CalendarWeekday
import com.example.jetpack.domain.enums.Weekday
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.AppUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.YearMonth


@Composable
fun ExpandedCalendar(
    modifier: Modifier = Modifier,
    initialMonth: YearMonth,
) {
    val TAG = "ExpandedCalendar"
    val maximumPage = 1000000


    val targetPage = remember {
        derivedStateOf {
            // Calculate relative position for the current month
            val currentYearMonth = YearMonth.now()

            val monthsDifference =
                (currentYearMonth.year - initialMonth.year) * 12 + (currentYearMonth.monthValue - initialMonth.monthValue)
            monthsDifference.coerceIn(0, maximumPage - 1) // 02/2025
        }
    }

    var chosenCalendarWeekday by remember { mutableStateOf(CalendarWeekday()) }
    val pagerState = rememberPagerState(
        initialPage = targetPage.value,
        pageCount = { maximumPage }
    )
    var currentPage by remember { mutableIntStateOf(0) }
    val currentMonth by remember(currentPage) {
        derivedStateOf {
            initialMonth.plusMonths(currentPage.toLong())
        }
    }


    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.settledPage }.collectLatest { settledPage ->
            AppUtil.logcat(tag = TAG, message = "settledPage = $settledPage", enableDivider = true)
            currentPage = settledPage
        }
    }

    val scope = rememberCoroutineScope()
    fun scrollToday() {
        scope.launch {
            // Calculate relative position for the current month
            val currentYearMonth = YearMonth.now()

            val monthsDifference =
                (currentYearMonth.year - initialMonth.year) * 12 + (currentYearMonth.monthValue - initialMonth.monthValue)
            val target = monthsDifference.coerceIn(0, maximumPage - 1) // 02/2025
            chosenCalendarWeekday = CalendarWeekday()
            pagerState.animateScrollToPage(target)
        }
    }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
    ) {
        Text(
            text = "${
                currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }
            } ${currentMonth.year}",
            style = customizedTextStyle(
                fontSize = 16,
                fontWeight = 600,
                color = LocalTheme.current.textColor,
            ),
            modifier = Modifier,
        )

        HorizontalPager(
            verticalAlignment = Alignment.Top,
            beyondViewportPageCount = 2,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LocalTheme.current.background)
                .requiredHeight(300.dp)
        ) { page ->
            AppUtil.logcat(tag = TAG, message = "created page = ${page}")
            val month = initialMonth.plusMonths(page.toLong())
            val daysInMonth = month.lengthOfMonth()
            val firstDayOfWeek = (month.atDay(1).dayOfWeek.value + 6) % 7 // Shifted by 1 to start on Monday
            val daysToPrepend = firstDayOfWeek

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
                                            localDate = month.atDay(dayOfMonth)
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
                                        CalendarWeekday(localDate = month.atDay(dayOfMonth))
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
                                val calendarWeekday =
                                    CalendarWeekday(localDate = month.atDay(dayOfMonth))
                                Text(
                                    text = dayOfMonth.toString(),
                                    style =
                                    if (calendarWeekday == chosenCalendarWeekday)
                                        customizedTextStyle(
                                            fontSize = 16
                                        )
                                    else
                                        customizedTextStyle(),
                                    textAlign = TextAlign.Center,
                                    color = if (calendarWeekday == chosenCalendarWeekday) LocalTheme.current.secondary else Color.White
                                )
                            }
                        }
                    }
                )
            }
        }

//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(shape = RoundedCornerShape(10.dp))
//                .background(color = LocalTheme.current.primary)
//                .height(1.dp)
//                .padding(horizontal = 16.dp)
//        )
//
//
        Spacer(
            modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "${chosenCalendarWeekday.dayOfWeek}, ${chosenCalendarWeekday.gregorianDay}/${chosenCalendarWeekday.gregorianMonth}/${chosenCalendarWeekday.gregorianYear}",
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 600,
                    color = LocalTheme.current.textColor,
                ),
                modifier = Modifier,
            )

            Text(
                text = stringResource(R.string.today),
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 600,
                    color = LocalTheme.current.primary,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable { scrollToday() }
            )
        }

    }
}

@Preview
@Composable
private fun PreviewExpandableHorizontalCalendar() {
    ExpandedCalendar(
        modifier = Modifier.fillMaxWidth(),
        initialMonth = YearMonth.of(2024, 12)
    )
}
