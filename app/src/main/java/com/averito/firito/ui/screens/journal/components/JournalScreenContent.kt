package com.averito.firito.ui.screens.journal.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.ui.screens.journal.DayLogListFilter
import com.averito.firito.ui.screens.journal.JournalTab
import com.averito.firito.ui.screens.journal.LogRange
import java.time.LocalDate

@Composable
fun JournalScreenContent(
    allDayLogsWithFoods: List<DayLogWithFoodsModel>,
    dayLogWithFoods: DayLogWithFoodsModel?,
    activeDates: Set<LocalDate>,
    selectedTabIndex: Int,
    updateSelectedTab: (Int) -> Unit,
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    onSelectMonthOrYear: (LocalDate) -> Unit,
    dayLogListFilter: DayLogListFilter,
    updateDayLogListFilter: (DayLogListFilter) -> Unit,
    toDayLogDetails: (LocalDate) -> Unit,
    loadDayLogsWithFoods: (LogRange) -> Unit
) {
    val tabs = JournalTab.all
    val selectedTab = tabs[selectedTabIndex]

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { updateSelectedTab(index) },
                    text = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (selectedTab) {
            JournalTab.Calendar -> JournalScreenCalendar(
                dayLogWithFoods = dayLogWithFoods,
                selectedDate = selectedDate,
                daysWithData = activeDates,
                onDateSelected = { onSelectDate(it) },
                onDetailsClick = { toDayLogDetails(it) },
                onSelectMonthOrYear = onSelectMonthOrYear,
                loadDayLogsWithFoods = loadDayLogsWithFoods
            )
            JournalTab.List -> JournalScreenList(
                logs = allDayLogsWithFoods,
                selectedFilter = dayLogListFilter,
                onFilterSelected = { updateDayLogListFilter(it) },
                onDayClick = { toDayLogDetails(it) },
                loadDayLogsWithFoods = loadDayLogsWithFoods
            )
        }
    }
}
