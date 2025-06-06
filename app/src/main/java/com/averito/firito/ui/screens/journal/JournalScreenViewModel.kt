package com.averito.firito.ui.screens.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averito.firito.core.interactors.DayLogInteractor
import com.averito.firito.ui.shared.utils.range_filter.DaysListFilter
import com.averito.firito.ui.shared.utils.range_filter.RangeFilter
import com.averito.firito.ui.shared.utils.range_filter.toDateRange
import com.averito.firito.ui.shared.utils.range_filter.toRangeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class JournalScreenViewModel @Inject constructor(
    private val dayLogInteractor: DayLogInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(JournalScreenUiState())
    val uiState get() =_uiState.asStateFlow()

    val activeDates get() = _uiState.value.dayLogsWithFoods.map { it.dayLog.date }.toSet()
    val dayLogWithFoods get() = _uiState.value.dayLogsWithFoods.find { it.dayLog.date == _uiState.value.selectedDate }

    init {
        viewModelScope.launch {
            loadDayLogsWithFoods(RangeFilter.Month(LocalDate.now()))
        }
    }

    suspend fun loadDayLogsWithFoods(logRange: RangeFilter) {
        val pairDates = logRange.toDateRange()
        val dayLogs = dayLogInteractor.getWithFoodsByRangeDate(pairDates.first, pairDates.second)
        _uiState.value = _uiState.value.copy(dayLogsWithFoods = dayLogs)
    }

    fun updateDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun updateTab(newTabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedTabIndex = newTabIndex)
    }

    fun updateDayLogFilter(dayLogFilter: DaysListFilter) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(dayLogListFilter = dayLogFilter)
            loadDayLogsWithFoods(dayLogFilter.toRangeFilter())
        }
    }
}
