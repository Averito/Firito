package com.averito.firito.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averito.firito.core.interactors.StatisticsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenViewModel @Inject constructor(
    private val statisticsInteractor: StatisticsInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatisticsScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    fun updateDate(date: YearMonth) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun updateStatistics() {
        viewModelScope.launch {
            val macroStats = statisticsInteractor.getMacrosForMonth(_uiState.value.selectedDate)
            _uiState.value = _uiState.value.copy(macroStats = macroStats)
        }
    }
}