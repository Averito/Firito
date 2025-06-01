package com.averito.firito.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averito.firito.core.interactors.StatisticsInteractor
import com.averito.firito.ui.screens.statistics_category.StatisticsCategoryUiState
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class StatisticsCategoryViewModel @Inject constructor(
    private val statisticsInteractor: StatisticsInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatisticsCategoryUiState())
    val uiState get() = _uiState.asStateFlow()

    fun updateDate(date: YearMonth) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun updateStatistics(category: AppNavGraphRoutes.StatisticsCategory.Category) {
        viewModelScope.launch {
            when (category) {
                AppNavGraphRoutes.StatisticsCategory.Category.CALORIES -> {
                    val caloriesStats = statisticsInteractor.getCaloriesForMonth(_uiState.value.selectedDate)
                    _uiState.value = _uiState.value.copy(
                        caloriesStats = caloriesStats,
                        hasData = caloriesStats.calories.isNotEmpty() ||
                                caloriesStats.proteins.isNotEmpty() ||
                                caloriesStats.fats.isNotEmpty() ||
                                caloriesStats.carbs.isNotEmpty()
                    )
                }
                AppNavGraphRoutes.StatisticsCategory.Category.ACTIVITY -> {
                    val activityStats = statisticsInteractor.getActivityForMonth(_uiState.value.selectedDate)
                    _uiState.value = _uiState.value.copy(
                        activityStats = activityStats,
                        hasData = activityStats.steps.isNotEmpty() || activityStats.distance.isNotEmpty()
                    )
                }
            }
        }
    }
}