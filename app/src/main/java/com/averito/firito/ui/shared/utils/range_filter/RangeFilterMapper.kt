package com.averito.firito.ui.shared.utils.range_filter

import java.time.LocalDate

fun RangeFilter.toDateRange(): Pair<LocalDate, LocalDate> {
    val now = LocalDate.now()
    return when (this) {
        is RangeFilter.Month -> {
            val start = date.withDayOfMonth(1)
            val end = date.withDayOfMonth(date.lengthOfMonth())
            Pair(start, end)
        }
        is RangeFilter.LastDays -> {
            val start = now.minusDays(days - 1)
            Pair(start, now)
        }
    }
}

fun DaysListFilter.toRangeFilter(): RangeFilter {
    return RangeFilter.LastDays(days)
}
