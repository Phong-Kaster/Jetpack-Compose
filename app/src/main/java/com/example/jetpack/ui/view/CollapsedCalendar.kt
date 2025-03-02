package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.CalendarWeekday
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlinx.coroutines.launch
import java.time.YearMonth

/**
 * A collapsible calendar component that displays a week view and allows navigation between weeks.
 *
 * @param modifier Modifier to be applied to the calendar component
 * @param initialMonth The initial month to display in the calendar
 * @param onClick Callback function that is invoked when a day is selected, providing the selected [CalendarWeekday]
 *
 * Features:
 * - Horizontal paging to navigate between weeks
 * - Highlighting of the selected day
 * - Option to jump to today's date
 * - Displays both weekday names and day numbers
 *
 * The calendar maintains the state of the currently selected day and displays it at the bottom.
 * @author Phong-Kaster
 */
@Composable
fun CollapsedCalendar(
    modifier: Modifier = Modifier,
    initialMonth: YearMonth = YearMonth.now(),
    onClick: (CalendarWeekday) -> Unit = {},
) {
    val TAG = "CollapsedCalendar"
    val maximumPage = 1000000
    val initialPage = ((maximumPage * 0.5).toInt())

    var chosenWeekday by remember { mutableStateOf(CalendarWeekday()) }

    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { maximumPage })
    var currentPage by remember { mutableStateOf(0) }

    val startDayOfWeek by remember {
        mutableStateOf(
            initialMonth.atDay(1).minusDays(initialMonth.atDay(1).dayOfWeek.value.toLong() - 1)
        )
    }
    val endDayOfWeek by remember(startDayOfWeek) { derivedStateOf { startDayOfWeek.plusDays(6) } }

    // Calculate the current week number
    val currentWeekNumber by remember(startDayOfWeek, currentPage) {
        derivedStateOf {
            val offsetWeeks = currentPage - initialPage
            val currentWeekFirstDay = startDayOfWeek.plusWeeks(offsetWeeks.toLong())
            // Get week of year using WeekFields
            val weekFields = java.time.temporal.WeekFields.of(java.util.Locale.getDefault())
            currentWeekFirstDay.get(weekFields.weekOfYear())
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage = page
        }
    }

    val scope = rememberCoroutineScope()
    fun scrollToday() {
        scope.launch {
            // Calculate relative position for the current month
            chosenWeekday = CalendarWeekday(localDate = java.time.LocalDate.now())

            // Calculate the page offset needed to navigate to the current date
            val todayLocalDate = java.time.LocalDate.now()
            val weekDifference =
                startDayOfWeek.until(todayLocalDate, java.time.temporal.ChronoUnit.WEEKS).toInt()
            val targetPage = initialPage + weekDifference

            // Animate to the page containing today's date
            pagerState.animateScrollToPage(targetPage)
        }
    }

    LaunchedEffect(Unit) { scrollToday() }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Week $currentWeekNumber, ${
                chosenWeekday.localDate.format(
                    java.time.format.DateTimeFormatter.ofPattern(
                        "YYYY"
                    )
                )
            }",
            style = customizedTextStyle(
                fontSize = 14,
                fontWeight = 600,
                color = LocalTheme.current.textColor
            ),
            modifier = Modifier
        )



        Text(
            text = "From ${startDayOfWeek.format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd"))}" +
                    " to ${endDayOfWeek.format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd"))}",
            style = customizedTextStyle(
                fontSize = 14,
                fontWeight = 600,
                color = LocalTheme.current.textColor
            ),
            modifier = Modifier
        )

        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxWidth()
        ) { page ->
            // Calculate the offset in weeks between the current page and the initial page
            // This determines which week to display (going back or forward from the initial date)
            // For example, if we're on page 500001 and initial page was 500000, offsetWeeks would be 1
            // meaning we need to show the week that is 1 week ahead of our starting week

            // Create a list of 7 days that make up the current week being displayed
            // We start from the startDayOfWeek (first day of the week from our initial calculation)
            // Then add the week offset and the day index (0-6) to get the exact date for each day in the week
            val offsetWeeks = page - initialPage
            val daysInWeek = List(7) { index ->
                startDayOfWeek
                    .plusWeeks(offsetWeeks.toLong())
                    .plusDays(index.toLong())
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                daysInWeek.forEach { day ->
                    val calendarWeekday = CalendarWeekday(localDate = day)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(space = 5.dp, alignment = Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                chosenWeekday = calendarWeekday
                                onClick(chosenWeekday)
                            },
                    ) {
                        Text(
                            text = day.dayOfWeek.name
                                .lowercase()
                                .replaceFirstChar { it.uppercase() }
                                .take(3),
                            style = customizedTextStyle(color = Color.White),
                        )


                        Text(
                            text = if(day.dayOfMonth <= 10)
                                day.dayOfMonth.toString().padStart(2, '0')
                                else
                                day.dayOfMonth.toString(),
                            style = if (calendarWeekday == chosenWeekday)
                                customizedTextStyle(
                                    color = LocalTheme.current.secondary,
                                    fontSize = 16,
                                    fontWeight = 600,
                                )
                            else
                                customizedTextStyle(color = Color.White),
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(5.dp))
                                .background(
                                    color = if (chosenWeekday == calendarWeekday)
                                        LocalTheme.current.primary
                                    else
                                        Color.Transparent
                                )
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "${chosenWeekday.dayOfWeek}, ${chosenWeekday.gregorianDay}/${chosenWeekday.gregorianMonth}/${chosenWeekday.gregorianYear}",
                style = customizedTextStyle(
                    fontSize = 14,
                    fontWeight = 600,
                    color = LocalTheme.current.textColor,
                ),
                modifier = Modifier,
            )

            Text(
                text = stringResource(R.string.today),
                style = customizedTextStyle(
                    fontSize = 14,
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
private fun PreviewCollapsedCalendar() {
    CollapsedCalendar(
        modifier = Modifier,
        initialMonth = YearMonth.now(),
    )
}