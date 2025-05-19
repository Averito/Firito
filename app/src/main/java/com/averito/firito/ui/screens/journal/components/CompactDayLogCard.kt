package com.averito.firito.ui.screens.journal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogModel

@Composable
fun CompactDayLogCard(log: DayLogModel, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = log.date.toString(),
                style = MaterialTheme.typography.titleSmall
            )
            Text("Калории: ${log.totalCalories} ккал")
            Text("Б: ${log.totalProteins} г; Ж: ${log.totalFats} г; У: ${log.totalCarbs} г;")
            Text("Шаги: ${log.totalSteps}, Расстояние: ${"%.1f".format(log.totalDistanceKm)} км")
        }
    }
}