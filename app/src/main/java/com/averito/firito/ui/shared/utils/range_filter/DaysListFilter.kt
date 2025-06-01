package com.averito.firito.ui.shared.utils.range_filter

sealed class DaysListFilter(val days: Long, val label: String) {
    object Last7Days : DaysListFilter(7, "7 дней")
    object Last14Days : DaysListFilter(14, "14 дней")
    object Last30Days : DaysListFilter(30, "30 дней")

    companion object {
        val all get() = listOf(Last7Days, Last14Days, Last30Days)
    }
}
