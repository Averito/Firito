package com.averito.firito.ui.screens.journal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl
import com.averito.firito.ui.shared.app_food_card.AppFoodCard
import java.time.LocalDate

@Composable
fun DayLogSummary(log: DayLogWithFoodsModel, onDetailsClick: (LocalDate) -> Unit) {
    val day = log.dayLog
    val foods = log.foods

    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Сводка дня",
                style = MaterialTheme.typography.titleMedium
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Калории: ${day.totalCalories} ккал",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Б: ${day.totalProteins} г   Ж: ${day.totalFats} г   У: ${day.totalCarbs} г",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Шаги: ${day.totalSteps}; Расстояние: ${"%.1f".format(day.totalDistanceKm)} км",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (foods.isNotEmpty()) {
                Text(
                    text = "Принятая еда",
                    style = MaterialTheme.typography.labelLarge
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    foods.forEach { food ->
                        AppFoodCard(foodModel = food)
                    }
                }
            }

            Button(
                onClick = { onDetailsClick(day.date) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Подробнее")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayLogSummaryPreview() {
    DayLogSummary(
        log = DayLogWithFoodsModelImpl(),
        onDetailsClick = {}
    )
}

