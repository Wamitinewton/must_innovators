/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
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

fun LocalDateTime.toFormattedDate(): String {
    val localDateTime: LocalDateTime = this
    return "${localDateTime.dayOfMonth} ${
        localDateTime.month.toString().lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }, ${localDateTime.year}"
}

fun LocalDateTime.toLocalDateTime(): String {
    val instant = this.toInstant(ZoneOffset.UTC)
    return DateTimeFormatter.ISO_INSTANT.format(instant)
}


fun String.toFormatedDate(): String {
    val instant = Instant.parse(this)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return "${localDateTime.dayOfMonth} ${
        localDateTime.month.toString().lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }, ${localDateTime.year}"
}


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
    return this.toLocalDateTime().toLocalDate()
}

fun Long.intoMidnight(): Long {
    val instant = Instant.ofEpochMilli(this)
    val zoneId = ZoneId.systemDefault()
    val localDateTime = LocalDateTime.ofInstant(instant, zoneId)
    val midnightLocalDateTime = LocalDateTime.of(
        localDateTime.year,
        localDateTime.monthValue,
        localDateTime.dayOfMonth,
        0,
        0,
        0,
        0
    )

    val midnightTime = midnightLocalDateTime.atZone(zoneId).toInstant()

    return midnightTime.toEpochMilli()
}

fun getEndOfMonthUnixTime(): Long {
    val currentDate = LocalDate.now()
    val lastDayOfCurrentMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
    val endOfMonthDateTime = LocalDateTime.of(lastDayOfCurrentMonth, LocalTime.of(23, 59, 59))
    val unixTime = endOfMonthDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    return unixTime
}

fun startDateAndEndDateOfMonth(
    month: Int = LocalDate.now().monthValue,
    currentYear: Int = LocalDate.now().year
): Pair<Long, Long> {
    val startDate = LocalDate.of(currentYear, month, 1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val lastDay = LocalDate.of(currentYear, month, 1)
        .plusMonths(1)
        .minusDays(1)

    val daysInMonth = LocalDateTime.of(
        lastDay,
        LocalTime.of(23, 59, 59, 999_999_999)
    )
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    return Pair(startDate, daysInMonth)
}
