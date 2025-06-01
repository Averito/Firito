package com.averito.firito.ui.screens.statistics

import com.averito.firito.core.models.statistics.MacroStats
import com.averito.firito.data.models.statistics.MacroStatsImpl
import java.time.YearMonth

data class StatisticsScreenUiState(
    val macroStats: MacroStats = MacroStatsImpl(),
    val selectedDate: YearMonth = YearMonth.now()
)
