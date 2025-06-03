package com.averito.firito.ui.screens.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.data.models.statistics.MacroDiffImpl
import com.averito.firito.data.models.statistics.MacroStatsImpl
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.statistics.components.StatisticsScreenContent
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes
import java.time.YearMonth

@Composable
fun StatisticsScreen(
    viewModel: StatisticsScreenViewModel = hiltViewModel(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel,
    toStatisticsDetail: (AppNavGraphRoutes.StatisticsCategory.Category) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.Statistics.TITLE)
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(true)
        baseAppLayoutViewModel.setActions(null)
        baseAppLayoutViewModel.setNavigationIcon(null)
        baseAppLayoutViewModel.setFloatingButton(null)
    }

    LaunchedEffect(Unit) {
        viewModel.updateStatistics()
    }

    fun updateDate(date: YearMonth) {
        viewModel.updateDate(date)
        viewModel.updateStatistics()
    }

    StatisticsScreenContent(
        selectedDate = uiState.selectedDate,
        macroStats = uiState.macroStats,
        macroDiff = uiState.macroDiff,
        updateDate = { updateDate(it) },
        toStatisticsDetail = { toStatisticsDetail(it) }
    )
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreenContent(
        selectedDate = YearMonth.now(),
        macroStats = MacroStatsImpl(),
        macroDiff = MacroDiffImpl(),
        updateDate = {},
        toStatisticsDetail = {}
    )
}