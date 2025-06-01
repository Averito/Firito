package com.averito.firito.ui.screens.journal

import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.ui.shared.utils.range_filter.DaysListFilter
import java.time.LocalDate

data class JournalScreenUiState(
    val dayLogsWithFoods: List<DayLogWithFoodsModel> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedTabIndex: Int = 0,
    val dayLogListFilter: DaysListFilter = DaysListFilter.Last7Days
)
