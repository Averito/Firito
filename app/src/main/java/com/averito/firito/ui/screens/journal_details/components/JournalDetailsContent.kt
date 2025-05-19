package com.averito.firito.ui.screens.journal_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.ui.shared.app_linear_progress_inidicator.AppLinearProgressIndicator
import com.averito.firito.ui.shared.app_food_card.AppFoodCard
import java.time.format.DateTimeFormatter

@Composable
fun JournalDetailsContent(dayLogWithFoods: DayLogWithFoodsModel) {
    val log = dayLogWithFoods.dayLog
    val foods = dayLogWithFoods.foods

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Дневной лог на ${log.date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Spacer(Modifier.height(12.dp))
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Калории", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Активные: ${log.activeCalories} ккал")
                    Text("Было съедено: ${log.totalCalories} ккал")
                    Spacer(Modifier.height(6.dp))
                    AppLinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        currentValue = log.totalCalories.toFloat(),
                        maxValue = log.activeCalories.toFloat()
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Макронутриенты",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
        }

        item {
            MacroNutrientCard(
                name = "Белки",
                value = log.totalProteins,
                unit = "г",
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            MacroNutrientCard(
                name = "Жиры",
                value = log.totalFats,
                unit = "г",
                color = MaterialTheme.colorScheme.secondary
            )
        }

        item {
            MacroNutrientCard(
                name = "Углеводы",
                value = log.totalCarbs,
                unit = "г",
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        item {
            Spacer(Modifier.height(16.dp))
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Физическая активность", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Шаги: ${log.totalSteps}")
                    Text("Дистанция: ${"%.2f".format(log.totalDistanceKm)} км")
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Продукты за день",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
        }

        when {
            foods.isEmpty() -> {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Список еды пуст."
                    )
                }
            }
            else -> {
                items(foods) { food ->
                    AppFoodCard(
                        foodModel = food,
                        details = true
                    )
                }
            }
        }
    }
}
