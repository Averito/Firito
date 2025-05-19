package com.averito.firito.ui.screens.journal.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.ui.screens.journal.LogRange
import com.averito.firito.ui.shared.app_calendar.AppCalendar
import java.time.LocalDate

@Composable
fun JournalScreenCalendar(
    dayLogWithFoods: DayLogWithFoodsModel?,
    selectedDate: LocalDate?,
    daysWithData: Set<LocalDate>,
    onSelectMonthOrYear: (LocalDate) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onDetailsClick: (LocalDate) -> Unit,
    loadDayLogsWithFoods: (LogRange) -> Unit
) {
    LaunchedEffect(Unit) {
        loadDayLogsWithFoods(LogRange.Month(LocalDate.now()))
    }

    LazyColumn {
        item {
            AppCalendar(
                selectedDate = selectedDate,
                daysWithData = daysWithData,
                onDateSelected = onDateSelected,
                onSelectYear = { onSelectMonthOrYear(it) },
                onSelectMonth = { onSelectMonthOrYear(it) }
            )
        }

        item {
            HorizontalDivider(Modifier.height(17.dp).padding(vertical = 8.dp))
        }

        item {
            when {
                dayLogWithFoods == null -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Дневной лог не существует."
                    )
                }
                else -> {
                    DayLogSummary(dayLogWithFoods, onDetailsClick)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JournalScreenCalendarPreview() {
    JournalScreenCalendar(
        selectedDate = LocalDate.now().plusDays(1),
        daysWithData = emptySet(),
        onDateSelected = {},
        onSelectMonthOrYear = {},
        onDetailsClick = {},
        dayLogWithFoods = null,
        loadDayLogsWithFoods = {}
    )
}
