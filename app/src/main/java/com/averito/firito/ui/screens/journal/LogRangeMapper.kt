package com.averito.firito.ui.screens.journal

import java.time.LocalDate

fun LogRange.toDateRange(): Pair<LocalDate, LocalDate> {
    val now = LocalDate.now()
    return when (this) {
        is LogRange.Month -> {
            val start = date.withDayOfMonth(1)
            val end = date.withDayOfMonth(date.lengthOfMonth())
            Pair(start, end)
        }
        is LogRange.LastDays -> {
            val start = now.minusDays(days - 1)
            Pair(start, now)
        }
    }
}

fun DayLogListFilter.toLogRange(): LogRange {
    return LogRange.LastDays(days)
}
