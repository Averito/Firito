package com.averito.firito.ui.shared.app_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.boguszpawlowski.composecalendar.*
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun AppCalendar(
    selectedDate: LocalDate?,
    daysWithData: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    onSelectYear: (LocalDate) -> Unit,
    onSelectMonth: (LocalDate) -> Unit
) {
    var calendarState = rememberSelectableCalendarState(
        initialMonth = YearMonth.now(),
        initialSelectionMode = SelectionMode.Single,
    )
    val today = LocalDate.now()

    fun onUpdateMonth(date: LocalDate) {
        onSelectMonth(date)
    }

    fun onUpdateYear(date: LocalDate) {
        onSelectYear(date)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = {
                    calendarState.monthState.currentMonth = calendarState.monthState.currentMonth.minusYears(1)
                    onUpdateYear(LocalDate.of(calendarState.monthState.currentMonth.year, calendarState.monthState.currentMonth.month, selectedDate?.dayOfMonth ?: 1))
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Предыдущий год")
            }

            Text(
                modifier = Modifier.padding(horizontal = 14.dp),
                text = calendarState.monthState.currentMonth.year.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(onClick = {
                    calendarState.monthState.currentMonth = calendarState.monthState.currentMonth.plusYears(1)
                    onUpdateYear(LocalDate.of(calendarState.monthState.currentMonth.year, calendarState.monthState.currentMonth.month, selectedDate?.dayOfMonth ?: 1))
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Следующий год")
            }
        }

        SelectableCalendar(
            calendarState = calendarState,
            monthHeader = { monthState ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                            monthState.currentMonth = calendarState.monthState.currentMonth.minusMonths(1)
                            onUpdateMonth(monthState.currentMonth.atEndOfMonth())
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Предыдущий месяц")
                    }

                    Text(
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        text = monthState.currentMonth.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()).replaceFirstChar { it.uppercaseChar() } + " " + monthState.currentMonth.year
                    )

                    IconButton(onClick = {
                            monthState.currentMonth = calendarState.monthState.currentMonth.plusMonths(1)
                            onUpdateMonth(monthState.currentMonth.atEndOfMonth())
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Следующий месяц")
                    }
                }
            },
            daysOfWeekHeader = { daysOfWeek ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    daysOfWeek.forEach {
                        Text(
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            text = it.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
                        )
                    }
                }
            },
            dayContent = { dayState ->
                val date = dayState.date
                val isToday = date == today
                val isSelected = date == selectedDate
                val hasData = date in daysWithData
                val isNotThisMonth = !dayState.isFromCurrentMonth
                val isAfterToday = dayState.date.isAfter(today)

                val bgColor = when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    isToday -> MaterialTheme.colorScheme.secondaryContainer
                    hasData -> MaterialTheme.colorScheme.secondary
                    else -> Color.Transparent
                }
                
                val padding = when {
                    isSelected -> 2.dp
                    isToday -> 12.dp
                    hasData -> 8.dp
                    else -> 0.dp
                }

                val textColor = when {
                    isSelected || isToday -> Color.White
                    hasData -> Color.White
                    else -> MaterialTheme.colorScheme.onSurface
                }

                val alpha = when {
                    isNotThisMonth -> 0.4f
                    isAfterToday -> 0.7f
                    else -> 1f
                }

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(padding)
                        .clip(CircleShape)
                        .background(bgColor)
                        .alpha(alpha)
                        .clickable { onDateSelected(date) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppCalendarPreview() {
    AppCalendar(
        selectedDate = LocalDate.now().plusDays(2),
        daysWithData = setOf(LocalDate.now().minusDays(1)),
        onDateSelected = {},
        onSelectYear = {},
        onSelectMonth = {}
    )
}
