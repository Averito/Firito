package com.averito.firito.ui.screens.journal

import java.time.LocalDate

sealed class LogRange {
    data class Month(val date: LocalDate) : LogRange()
    data class LastDays(val days: Long) : LogRange()
}
