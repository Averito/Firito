package com.averito.firito.ui.screens.journal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl
import com.averito.firito.data.models.food.FoodModelImpl
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.journal.components.JournalScreenContent
import com.averito.firito.ui.shared.app_navigation.AppNavGraphRoutes
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun JournalScreen(
    viewModel: JournalScreenViewModel = hiltViewModel(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel,
    toDayLogDetails: (LocalDate) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    fun onSelectMonthOrYear(date: LocalDate) {
        coroutineScope.launch {
            viewModel.loadDayLogsWithFoods(LogRange.Month(date))
        }
    }

    fun loadDayLogsWithFoods(logRange: LogRange) {
        coroutineScope.launch {
            viewModel.loadDayLogsWithFoods(logRange)
        }
    }

    fun onSelectDate(date: LocalDate) {
        viewModel.updateDate(date)
    }

    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.Journal.title)
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(true)
        baseAppLayoutViewModel.setActions(null)
        baseAppLayoutViewModel.setNavigationIcon(null)
        baseAppLayoutViewModel.setFloatingButton(null)
    }

    JournalScreenContent(
        allDayLogsWithFoods = uiState.dayLogsWithFoods,
        selectedTabIndex = uiState.selectedTabIndex,
        selectedDate = uiState.selectedDate,
        dayLogWithFoods = viewModel.dayLogWithFoods,
        activeDates = viewModel.activeDates,
        toDayLogDetails = { toDayLogDetails(it) },
        updateSelectedTab = { viewModel.updateTab(it) },
        onSelectDate = { onSelectDate(it) },
        onSelectMonthOrYear = { onSelectMonthOrYear(it) },
        loadDayLogsWithFoods = { loadDayLogsWithFoods(it) },
        dayLogListFilter = uiState.dayLogListFilter,
        updateDayLogListFilter = { viewModel.updateDayLogFilter(it) }
    )
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
    JournalScreenContent(
        allDayLogsWithFoods = emptyList(),
        selectedTabIndex = 1,
        selectedDate = LocalDate.now().plusDays(1),
        onSelectDate = {},
        dayLogWithFoods = DayLogWithFoodsModelImpl(
            foods = listOf(FoodModelImpl(
                name = "Яблоко"
            ))
        ),
        toDayLogDetails = {},
        onSelectMonthOrYear = {},
        activeDates = emptySet(),
        updateSelectedTab = {},
        loadDayLogsWithFoods = {},
        dayLogListFilter = DayLogListFilter.Last7Days,
        updateDayLogListFilter = {}
    )
}