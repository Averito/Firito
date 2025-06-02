package com.averito.firito.ui.screens.journal.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.ui.shared.utils.range_filter.DaysListFilter
import com.averito.firito.ui.shared.utils.range_filter.RangeFilter
import com.averito.firito.ui.shared.utils.range_filter.toRangeFilter
import java.time.LocalDate

@Composable
fun JournalScreenList(
    logs: List<DayLogWithFoodsModel>,
    selectedFilter: DaysListFilter,
    onFilterSelected: (DaysListFilter) -> Unit,
    onDayClick: (LocalDate) -> Unit,
    loadDayLogsWithFoods: (RangeFilter) -> Unit
) {
    LaunchedEffect(Unit) {
        loadDayLogsWithFoods(selectedFilter.toRangeFilter())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        FilterChipsRow(
            selected = selectedFilter,
            onSelected = onFilterSelected
        )

        Spacer(Modifier.height(4.dp))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(logs) { log ->
                CompactDayLogCard(log = log.dayLog, onClick = { onDayClick(log.dayLog.date) })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun JournalScreenListPreview() {
    JournalScreenList(
        logs = emptyList(),
        selectedFilter = DaysListFilter.Last7Days,
        onFilterSelected = {},
        onDayClick = {},
        loadDayLogsWithFoods = {}
    )
}
