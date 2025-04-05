package com.newton.commonUi.ui

import java.time.*
import java.time.format.*
import java.util.*

fun Long.toFormattedDate(): String {
    val instant = Instant.ofEpochMilli(this)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return "${localDateTime.dayOfMonth} ${
    localDateTime.month.toString().lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }, ${localDateTime.year}"
}

fun String.toFormatedDate(): String {
    val instant = Instant.parse(this)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return "${localDateTime.dayOfMonth} ${
    localDateTime.month.toString().lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }, ${localDateTime.year}"
}

// fun Long.toLocaltime():String{
//    val instant = Instant.ofEpochMilli(this)
//    return instant.toString()
// }

fun Long.toLocaltime(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .format(DateTimeFormatter.ISO_INSTANT)
}

fun String.toLocalDateTime(): LocalDateTime {
    val instant = Instant.parse(this)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

fun String.toLocalDate(): LocalDate {
    val instant = Instant.parse(this)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate()
}

//
// fun Long.intoMidnight(): Long {
//    val instant = Instant.ofEpochMilli(this)
//    val timeZone = TimeZone.currentSystemDefault()
//    val localDateTime = instant.toLocalDateTime(timeZone)
//    val midnightLocalDateTime = LocalDateTime(
//        year = localDateTime.year,
//        monthNumber = localDateTime.monthNumber,
//        dayOfMonth = localDateTime.dayOfMonth,
//        hour = 0,
//        minute = 0,
//        second = 0,
//        nanosecond = 0
//    )
//
//    val midnightTime = midnightLocalDateTime.toInstant(timeZone)
//
//    return midnightTime.toEpochMilliseconds()
// }
//
// fun getEndOfMonthUnixTime(): Long {
//    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
//    val nextMonth = currentDate.plus(1, DateTimeUnit.MONTH)
//    val firstDayOfNextMonth = LocalDate(nextMonth.year, nextMonth.month, 1)
//    val lastDayOfCurrentMonth = firstDayOfNextMonth.minus(1, DateTimeUnit.DAY)
//    val endOfMonthDateTime = lastDayOfCurrentMonth.atTime(23, 59, 59)
//    val unixTime =
//        endOfMonthDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
//    return unixTime
// }
//
// fun startDateAndEndDateOfMonth(
//    month: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).monthNumber,
//    currentYear: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
// ): Pair<Long, Long> {
//
//    val startDate = LocalDate(currentYear, month, 1)
//        .atStartOfDayIn(TimeZone.currentSystemDefault())
//        .toEpochMilliseconds()
//
//    val daysInMonth = LocalDate(currentYear, month, 1)
//        .plus(1, DateTimeUnit.MONTH)
//        .minus(1, DateTimeUnit.DAY)
//        .atTime(23, 59, 59, 999_999_999)
//        .toInstant(TimeZone.currentSystemDefault())
//        .toEpochMilliseconds()
//
//    return Pair(startDate, daysInMonth)
// }
