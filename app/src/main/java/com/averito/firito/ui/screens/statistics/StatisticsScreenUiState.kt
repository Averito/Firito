package com.averito.firito.ui.screens.statistics

import com.averito.firito.core.models.statistics.MacroDiff
import com.averito.firito.core.models.statistics.MacroStats
import com.averito.firito.data.models.statistics.MacroDiffImpl
import com.averito.firito.data.models.statistics.MacroStatsImpl
import java.time.YearMonth

data class StatisticsScreenUiState(
    val macroStats: MacroStats = MacroStatsImpl(),
    val macroDiff: MacroDiff = MacroDiffImpl(),
    val selectedDate: YearMonth = YearMonth.now()
)
