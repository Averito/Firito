package com.averito.firito.ui.screens.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.ui.shared.ui.app_activity_card.AppActivityCard
import com.averito.firito.ui.shared.ui.app_calories_card.AppCaloriesCard
import com.averito.firito.ui.shared.ui.app_food_card.AppFoodCard
import com.averito.firito.ui.shared.ui.app_linear_progress_inidicator.AppLinearProgressIndicator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
    goals: GoalsModel,
    dayLogWithFoods: DayLogWithFoodsModel,
    isLoading: Boolean,
    onClickFood: (Long) -> Unit,
    onRemoveFood: (FoodModel) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    val log = dayLogWithFoods.dayLog
    val foods = dayLogWithFoods.foods

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    item {
                        Text(
                            text = log.date.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        log.finalAt?.let {
                            Text(
                                "Завершено в ${it.toLocalTime()}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
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
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Макронутриенты", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(8.dp))

                                MacroItem("Белки", log.totalProteins, goals.proteins)
                                MacroItem("Жиры", log.totalFats, goals.fats)
                                MacroItem("Углеводы", log.totalCarbs, goals.carbs)
                            }
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

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
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
                                    onRemove = { onRemoveFood(food) },
                                    onClick = { onClickFood(food.id) },
                                    details = true
                                )
                            }
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}
