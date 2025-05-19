package com.averito.firito.ui.screens.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.ui.shared.app_food_card.AppFoodCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
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
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    item {
                        Text(
                            text = log.date.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )

                        log.finalAt?.let {
                            Text("Завершено в ${it.toLocalTime()}", style = MaterialTheme.typography.labelMedium)
                        }
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
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(16.dp))
                    }

                    item {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Макронутриенты", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(8.dp))
                                MacroItem(name = "Белки", value = log.totalProteins)
                                MacroItem(name = "Жиры", value = log.totalFats)
                                MacroItem(name = "Углеводы", value = log.totalCarbs)
                            }
                        }
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
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    }
                    when {
                        foods.isEmpty() -> {
                            item {
                                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = "Список еды пуст.")
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