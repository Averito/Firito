package com.averito.firito.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.data.models.day_log.DayLogModelImpl
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl
import com.averito.firito.data.models.food.FoodModelImpl
import com.averito.firito.ui.layouts.base.BaseAppLayoutUiStateFloatingButtonOptions
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.main.components.MainScreenContent
import com.averito.firito.ui.shared.app_navigation.AppNavGraphRoutes
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel<MainScreenViewModel>(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel,
    toFoodPage: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    fun onAddFood(food: FoodModel) {
        coroutineScope.launch {
            val newId = viewModel.createFood(food)
            toFoodPage(newId)
        }
    }

    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.Main.title)
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(true)
        baseAppLayoutViewModel.setActions(null)
        baseAppLayoutViewModel.setNavigationIcon(null)
        baseAppLayoutViewModel.setFloatingButton(BaseAppLayoutUiStateFloatingButtonOptions(
            onClick = { onAddFood(FoodModelImpl()) },
            imageVector = Icons.Default.Add,
            contentDescription = "Добавить еду"
        ))
        viewModel.updateDayLog()
    }

    LaunchedEffect(uiState.permissionsGranted) {
        if (!uiState.permissionsGranted) return@LaunchedEffect
        viewModel.updateDayLog()
    }

    var isRefreshing by remember { mutableStateOf(false) }

    fun onRefresh() {
        isRefreshing = true
        viewModel.updateDayLog()
        isRefreshing = false
    }

    MainScreenContent(
        dayLogWithFoods = uiState.dayLogWithFoods,
        isLoading = uiState.isLoading,
        onRemoveFood = { viewModel.removeFood(it) },
        onClickFood = toFoodPage,
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh() }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        dayLogWithFoods = DayLogWithFoodsModelImpl(
            dayLog = DayLogModelImpl(
                date = LocalDate.now(),
                totalCalories = 625,
                totalProteins = 45f,
                totalFats = 50f,
                totalCarbs = 100f,
                totalSteps = 3420,
                totalDistanceKm = 2.5,
                activeCalories = 1340,
                finalAt = null
            ),
            foods = listOf(
                FoodModelImpl(
                    id = 1,
                    name = "Чипсы",
                    calories = 500,
                    carbs = 100f,
                    fats = 30f,
                    proteins = 4f,
                    weightInGrams = 125,
                    description = null
                ),
                FoodModelImpl(
                    id = 2,
                    name = "Чипсы",
                    calories = 500,
                    carbs = 100f,
                    fats = 30f,
                    proteins = 4f,
                    weightInGrams = 125,
                    description = null
                ),
                FoodModelImpl(
                    id = 3,
                    name = "Чипсы",
                    calories = 500,
                    carbs = 100f,
                    fats = 30f,
                    proteins = 4f,
                    weightInGrams = 125,
                    description = null
                )
            )
        ),
        isLoading = false,
        isRefreshing = false,
        onRemoveFood = {},
        onClickFood = {},
        onRefresh = {}
    )
}