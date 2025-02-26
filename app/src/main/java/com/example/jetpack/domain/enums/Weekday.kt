package com.example.jetpack.domain.enums

import androidx.annotation.StringRes
import com.example.jetpack.R

/**
 * Represents a day of the week.
 *
 * @property fullName The string resource ID for the full name of the day.
 * @property shortName The string resource ID for the short name (abbreviation) of the day.
 * @property dayOfWeek The numerical representation of the day of the week, with 1 being Sunday and 7 being Saturday. Follow by Calendar.DAY_OF_WEEK
 */
enum class Weekday(
    @StringRes val fullName: Int,
    @StringRes val shortName: Int,
    val dayOfWeek: Int,
) {
    SUNDAY(fullName = R.string.sunday, shortName = R.string.sun, dayOfWeek = 1),
    MONDAY(fullName = R.string.monday, shortName = R.string.mon, dayOfWeek = 2),
    TUESDAY(fullName = R.string.tuesday, shortName = R.string.tue, dayOfWeek = 3),
    WEDNESDAY(fullName = R.string.wednesday, shortName = R.string.wed, dayOfWeek = 4),
    THURSDAY(fullName = R.string.thursday, shortName = R.string.thu, dayOfWeek = 5),
    FRIDAY(fullName = R.string.friday, shortName = R.string.fri, dayOfWeek = 6),
    SATURDAY(fullName = R.string.saturday, shortName = R.string.sat, dayOfWeek = 7),
}