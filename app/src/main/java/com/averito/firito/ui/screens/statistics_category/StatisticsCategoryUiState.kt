package com.averito.firito.ui.screens.statistics_category

import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.core.models.statistics.ActivityDiff
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.CaloriesDiff
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.data.models.goals.GoalsModelImpl
import com.averito.firito.data.models.statistics.ActivityDiffImpl
import com.averito.firito.data.models.statistics.ActivityStatsImpl
import com.averito.firito.data.models.statistics.CaloriesDiffImpl
import com.averito.firito.data.models.statistics.CaloriesStatsImpl
import java.time.YearMonth

data class StatisticsCategoryUiState(
    val activityStats: ActivityStats = ActivityStatsImpl(),
    val caloriesStats: CaloriesStats = CaloriesStatsImpl(),
    val activityDiff: ActivityDiff = ActivityDiffImpl(),
    val caloriesDiff: CaloriesDiff = CaloriesDiffImpl(),
    val goals: GoalsModel = GoalsModelImpl(),
    val selectedDate: YearMonth = YearMonth.now(),
    val hasData: Boolean = true
)
