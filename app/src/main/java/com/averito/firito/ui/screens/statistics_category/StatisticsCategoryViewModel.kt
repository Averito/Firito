package com.averito.firito.ui.screens.statistics_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averito.firito.core.interactors.StatisticsInteractor
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.data.models.statistics.ActivityStatsImpl
import com.averito.firito.data.models.statistics.CaloriesStatsImpl
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
                    val caloriesDiff = statisticsInteractor.getCaloriesDiffForMonth(_uiState.value.selectedDate)

                    _uiState.value = _uiState.value.copy(
                        caloriesStats = caloriesStats,
                        caloriesDiff = caloriesDiff,
                        hasData = checkHasData(category, caloriesStats = caloriesStats)
                    )
                }
                AppNavGraphRoutes.StatisticsCategory.Category.ACTIVITY -> {
                    val activityStats = statisticsInteractor.getActivityForMonth(_uiState.value.selectedDate)
                    val activityDiff = statisticsInteractor.getActivityDiffForMonth(_uiState.value.selectedDate)

                    _uiState.value = _uiState.value.copy(
                        activityStats = activityStats,
                        activityDiff = activityDiff,
                        hasData = checkHasData(category, activityStats = activityStats)
                    )
                }
            }
        }
    }

    private fun checkHasData(
        category: AppNavGraphRoutes.StatisticsCategory.Category,
        caloriesStats: CaloriesStats = CaloriesStatsImpl(),
        activityStats: ActivityStats = ActivityStatsImpl()
    ) = when (category) {
        AppNavGraphRoutes.StatisticsCategory.Category.CALORIES -> caloriesStats.calories.isNotEmpty() ||
                caloriesStats.proteins.isNotEmpty() ||
                caloriesStats.fats.isNotEmpty() ||
                caloriesStats.carbs.isNotEmpty()
        AppNavGraphRoutes.StatisticsCategory.Category.ACTIVITY -> activityStats.steps.isNotEmpty() || activityStats.distance.isNotEmpty()
    }
}