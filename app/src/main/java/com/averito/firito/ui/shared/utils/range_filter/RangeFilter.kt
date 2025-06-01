package com.averito.firito.ui.shared.utils.range_filter

import java.time.LocalDate

sealed class RangeFilter {
    data class Month(val date: LocalDate) : RangeFilter()
    data class LastDays(val days: Long) : RangeFilter()
}
