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
import com.averito.firito.data.models.statistics.ActivityStatsImpl
import com.averito.firito.data.models.statistics.CaloriesStatsImpl
import com.averito.firito.data.models.statistics.StatSeriesImpl
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.statistics.StatisticsCategoryViewModel
import com.averito.firito.ui.screens.statistics_category.components.StatisticsCategoryScreenContent
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes
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
        viewModel.updateStatistics(category)
    }

    fun updateDate(date: YearMonth) {
        viewModel.updateDate(date)
        viewModel.updateStatistics(category)
    }

    StatisticsCategoryScreenContent(
        selectedDate = uiState.selectedDate,
        updateDate = { updateDate(it) },
        activityStats = uiState.activityStats,
        caloriesStats = uiState.caloriesStats,
        category = category,
        hasData = uiState.hasData
    )
}

@Preview(showBackground = true)
@Composable
fun StatisticsCategoryScreenPreview() {
    val steps = List(31) { (3000..12000).random() }
    val distance = steps.map { it * 0.0008 }

    val proteins = List(31) { (60..160).random().toFloat() }
    val fats = List(31) { (50..120).random().toFloat() }
    val carbs = List(31) { (150..350).random().toFloat() }
    val calories = List(31) { (1600..2800).random() }

    StatisticsCategoryScreenContent(
        selectedDate = YearMonth.now(),
        updateDate = {},
        activityStats = ActivityStatsImpl(
            steps = steps,
            distance = distance,
            stepsStats = StatSeriesImpl(
                min = steps.min(),
                avg = steps.average().toInt(),
                max = steps.max()
            ),
            distanceStats = StatSeriesImpl(
                min = distance.min(),
                avg = distance.average(),
                max = distance.max()
            )
        ),
        caloriesStats = CaloriesStatsImpl(
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs,
            caloriesStats = StatSeriesImpl(
                min = calories.min(),
                avg = calories.average().toInt(),
                max = calories.max()
            ),
            proteinsStats = StatSeriesImpl(
                min = proteins.min(),
                avg = proteins.average().toFloat(),
                max = proteins.max()
            ),
            fatsStats = StatSeriesImpl(
                min = fats.min(),
                avg = fats.average().toFloat(),
                max = fats.max()
            ),
            carbsStats = StatSeriesImpl(
                min = carbs.min(),
                avg = carbs.average().toFloat(),
                max = carbs.max()
            )
        ),
        category = AppNavGraphRoutes.StatisticsCategory.Category.ACTIVITY,
        hasData = true
    )
}
