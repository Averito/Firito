package com.averito.firito.ui.screens.journal_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averito.firito.core.interactors.DayLogInteractor
import com.averito.firito.core.interactors.GoalsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class JournalDetailsViewModel @Inject constructor(
    private val dayLogInteractor: DayLogInteractor,
    private val goalsInteractor: GoalsInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(JournalDetailsUiState())
    val uiState get() = _uiState.asStateFlow()

    fun loadDayLogWithFoods(date: LocalDate) {
        viewModelScope.launch {
            val dayLogWithFoods = dayLogInteractor.getWithFoodsByDate(date)
            _uiState.value = _uiState.value.copy(dayLogWithFoods = dayLogWithFoods)
        }
    }

    fun loadGoals() {
        viewModelScope.launch {
            val goals = goalsInteractor.getAll()
            _uiState.value = _uiState.value.copy(goals = goals)
        }
    }
}