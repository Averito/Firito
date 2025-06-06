package com.averito.firito.ui.screens.statistics_category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.data.models.goals.GoalsModelImpl
import com.averito.firito.data.models.statistics.ActivityDiffImpl
import com.averito.firito.data.models.statistics.ActivityStatsImpl
import com.averito.firito.data.models.statistics.CaloriesDiffImpl
import com.averito.firito.data.models.statistics.CaloriesStatsImpl
import com.averito.firito.data.models.statistics.StatSeriesImpl
import com.averito.firito.data.models.statistics.StatValueImpl
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.statistics_category.components.StatisticsCategoryScreenContent
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun StatisticsCategoryScreen(
    viewModel: StatisticsCategoryViewModel = hiltViewModel(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel,
    category: AppNavGraphRoutes.StatisticsCategory.Category,
    back: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.StatisticsCategory.getTitle(category))
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(false)
        baseAppLayoutViewModel.setFloatingButton(null)
        baseAppLayoutViewModel.setNavigationIcon {
            IconButton(onClick = { back() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }
        baseAppLayoutViewModel.setActions(null)
    }

    LaunchedEffect(Unit) {
        viewModel.updateStatistics(category)
        viewModel.loadGoals()
    }

    fun updateDate(date: YearMonth) {
        viewModel.updateDate(date)
        viewModel.updateStatistics(category)
    }

    StatisticsCategoryScreenContent(
        selectedDate = uiState.selectedDate,
        updateDate = { updateDate(it) },
        goals = uiState.goals,
        activityStats = uiState.activityStats,
        activityDiff = uiState.activityDiff,
        caloriesStats = uiState.caloriesStats,
        caloriesDiff = uiState.caloriesDiff,
        category = category,
        hasData = uiState.hasData
    )
}

@Preview(showBackground = true)
@Composable
fun StatisticsCategoryScreenPreview() {
    val today = LocalDate.now()
    val dates = (0..30).map { today.withDayOfMonth((it + 1).coerceAtMost(today.lengthOfMonth())) }

    val steps = dates.map { date -> StatValueImpl((3000..12000).random(), date) }
    val distance = steps.map { StatValueImpl(it.value * 0.0008, it.date) }

    val proteins = dates.map { date -> StatValueImpl((60..160).random().toFloat(), date) }
    val fats = dates.map { date -> StatValueImpl((50..120).random().toFloat(), date) }
    val carbs = dates.map { date -> StatValueImpl((150..350).random().toFloat(), date) }
    val calories = dates.map { date -> StatValueImpl((1600..2800).random(), date) }

    val caloriesStats = CaloriesStatsImpl(
        calories = calories,
        proteins = proteins,
        fats = fats,
        carbs = carbs,
        caloriesStats = StatSeriesImpl(
            min = calories.minOf { it.value },
            avg = calories.map { it.value }.average().toInt(),
            max = calories.maxOf { it.value }
        ),
        proteinsStats = StatSeriesImpl(
            min = proteins.minOf { it.value },
            avg = proteins.map { it.value }.average().toFloat(),
            max = proteins.maxOf { it.value }
        ),
        fatsStats = StatSeriesImpl(
            min = fats.minOf { it.value },
            avg = fats.map { it.value }.average().toFloat(),
            max = fats.maxOf { it.value }
        ),
        carbsStats = StatSeriesImpl(
            min = carbs.minOf { it.value },
            avg = carbs.map { it.value }.average().toFloat(),
            max = carbs.maxOf { it.value }
        )
    )

    val activityStats = ActivityStatsImpl(
        steps = steps,
        distance = distance,
        stepsStats = StatSeriesImpl(
            min = steps.minOf { it.value },
            avg = steps.map { it.value }.average().toInt(),
            max = steps.maxOf { it.value }
        ),
        distanceStats = StatSeriesImpl(
            min = distance.minOf { it.value },
            avg = distance.map { it.value }.average(),
            max = distance.maxOf { it.value }
        )
    )

    StatisticsCategoryScreenContent(
        selectedDate = YearMonth.now(),
        goals = GoalsModelImpl(),
        updateDate = {},
        activityStats = activityStats,
        caloriesStats = caloriesStats,
        category = AppNavGraphRoutes.StatisticsCategory.Category.CALORIES,
        hasData = true,
        caloriesDiff = CaloriesDiffImpl(
            caloriesDiff = StatSeriesImpl(-5, 3, 12),
            proteinsDiff = StatSeriesImpl(-2, 0, 4),
            fatsDiff = StatSeriesImpl(-4, -0, 2),
            carbsDiff = StatSeriesImpl(-6, -1, 5)
        ),
        activityDiff = ActivityDiffImpl(
            stepsDiff = StatSeriesImpl(-3, 2, 10),
            distanceDiff = StatSeriesImpl(-1, 0, 2)
        )
    )
}

