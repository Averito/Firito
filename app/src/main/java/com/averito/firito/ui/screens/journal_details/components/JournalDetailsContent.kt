package com.averito.firito.ui.screens.journal_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.ui.shared.ui.app_activity_card.AppActivityCard
import com.averito.firito.ui.shared.ui.app_calories_card.AppCaloriesCard
import com.averito.firito.ui.shared.ui.app_food_card.AppFoodCard
import java.time.format.DateTimeFormatter

@Composable
fun JournalDetailsContent(
    dayLogWithFoods: DayLogWithFoodsModel,
    goals: GoalsModel
) {
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
        item { Spacer(Modifier.height(12.dp)) }

        item {
            AppCaloriesCard(
                activeCalories = log.activeCalories,
                totalCalories = log.totalCalories,
                goalCalories =  goals.calories
            )
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            Column {
                Text(
                    text = "Макронутриенты",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))

                MacroNutrientCard(
                    name = "Белки",
                    value = log.totalProteins,
                    maxValue = goals.proteins,
                    unit = "г",
                    color = MaterialTheme.colorScheme.primary
                )

                MacroNutrientCard(
                    name = "Жиры",
                    value = log.totalFats,
                    maxValue = goals.fats,
                    unit = "г",
                    color = MaterialTheme.colorScheme.secondary
                )

                MacroNutrientCard(
                    name = "Углеводы",
                    value = log.totalCarbs,
                    maxValue = goals.carbs,
                    unit = "г",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            AppActivityCard(
                steps = log.totalSteps,
                distance = log.totalDistanceKm,
                stepsGoal = goals.steps,
                distanceGoal = goals.distance
            )
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            Column {
                Text(
                    text = "Продукты за день",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
            }
        }

        when {
            foods.isEmpty() -> item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Список еды пуст."
                )
            }
            else -> items(foods) { food ->
                AppFoodCard(
                    foodModel = food,
                    details = true
                )
            }
        }
    }
}
