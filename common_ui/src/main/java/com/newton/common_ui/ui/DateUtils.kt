package com.newton.common_ui.ui

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

import java.time.LocalDateTime as JavaLocalDateTime
import java.time.LocalDate as JavaLocalDate

fun getCurrentUnixTime(): Long {
    return Clock.System.now().toEpochMilliseconds()
}

fun getCurrentFormatedDate(): String {
    val instant = Clock.System.now()
    val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.date} ${localDateTime.month} ${localDateTime.year}"
}

fun Long.toFormattedDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.dayOfMonth} ${localDateTime.month} ${localDateTime.year}"
}

fun String.toFormatedDate():String{
    val instant = Instant.parse(this)
    val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.dayOfMonth} ${localDateTime.month} ${localDateTime.year}"
}

fun JavaLocalDate.toStringLocalTime():String{
    val localtime =   this.toKotlinLocalDate()
    return localtime.toJavaLocalDate().toKotlinLocalDate().atStartOfDayIn(TimeZone.currentSystemDefault()).toString()
}

fun String.fromStringToLocalTime():JavaLocalDateTime{
    val instant = Instant.parse(this)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
}

fun Long.toLocaltime():String{
    val instant = Instant.fromEpochMilliseconds(this)
    return instant.toString()
}

fun Long.intoMidnight(): Long {
    val instant = Instant.fromEpochMilliseconds(this)
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = instant.toLocalDateTime(timeZone)
    val midnightLocalDateTime = LocalDateTime(
        year = localDateTime.year,
        monthNumber = localDateTime.monthNumber,
        dayOfMonth = localDateTime.dayOfMonth,
        hour = 0,
        minute = 0,
        second = 0,
        nanosecond = 0
    )

    val midnightTime = midnightLocalDateTime.toInstant(timeZone)

    return midnightTime.toEpochMilliseconds()
}

fun getEndOfMonthUnixTime(): Long {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val nextMonth = currentDate.plus(1, DateTimeUnit.MONTH)
    val firstDayOfNextMonth = LocalDate(nextMonth.year, nextMonth.month, 1)
    val lastDayOfCurrentMonth = firstDayOfNextMonth.minus(1, DateTimeUnit.DAY)
    val endOfMonthDateTime = lastDayOfCurrentMonth.atTime(23, 59, 59)
    val unixTime =
        endOfMonthDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    return unixTime
}

fun startDateAndEndDateOfMonth(
    month: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).monthNumber,
    currentYear: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
): Pair<Long, Long> {

    val startDate = LocalDate(currentYear, month, 1)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    val daysInMonth = LocalDate(currentYear, month, 1)
        .plus(1, DateTimeUnit.MONTH)
        .minus(1, DateTimeUnit.DAY)
        .atTime(23, 59, 59, 999_999_999)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    return Pair(startDate, daysInMonth)
}