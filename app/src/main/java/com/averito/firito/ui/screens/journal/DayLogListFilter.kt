package com.averito.firito.ui.screens.journal

sealed class DayLogListFilter(val days: Long, val label: String) {
    object Last7Days : DayLogListFilter(7, "7 дней")
    object Last14Days : DayLogListFilter(14, "14 дней")
    object Last30Days : DayLogListFilter(30, "30 дней")

    companion object {
        val all get() = listOf(Last7Days, Last14Days, Last30Days)
    }
}
