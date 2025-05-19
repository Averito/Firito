package com.averito.firito.ui.screens.food

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.averito.firito.ui.layouts.base.BaseAppLayoutViewModel
import com.averito.firito.ui.screens.food.components.FoodScreenContent
import com.averito.firito.ui.shared.app_navigation.AppNavGraphRoutes
import kotlinx.coroutines.launch

@Composable
fun FoodScreen(
    viewModel: FoodScreenViewModel = hiltViewModel<FoodScreenViewModel>(),
    baseAppLayoutViewModel: BaseAppLayoutViewModel,
    foodId: Long,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    fun saveAndBack() {
        coroutineScope.launch {
            viewModel.updateFood(uiState.food)
            if (uiState.food.name.isEmpty()) viewModel.removeFood(uiState.food)
            onBack()
        }
    }

    BackHandler {
        saveAndBack()
    }

    LaunchedEffect(Unit) {
        baseAppLayoutViewModel.setTitle(AppNavGraphRoutes.MainFood.title)
        baseAppLayoutViewModel.setTopBarVisibility(true)
        baseAppLayoutViewModel.setBottomBarVisibility(false)
        baseAppLayoutViewModel.setFloatingButton(null)
        baseAppLayoutViewModel.setNavigationIcon {
            IconButton(onClick = { saveAndBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }
        baseAppLayoutViewModel.setActions {
            IconButton(onClick = { saveAndBack() }) {
                Icon(Icons.Default.Check, contentDescription = "Сохранить")
            }
        }
    }

    LaunchedEffect(foodId) {
        viewModel.loadFood(foodId)
    }

    FoodScreenContent(
        uiStateFood = viewModel.uiStateFood,
        updateUiStateFood = { field, value -> viewModel.updateUiStateFood(field, value) },
        foodTemplatesLoading = uiState.foodTemplatesLoading,
        foodTemplates = uiState.foodTemplates,
        loadFoodTemplates = suspend { viewModel.loadFoodTemplates() },
        fillFoodByTemplate = { viewModel.fillFoodByTemplate(it) },
    )
}

@Preview(showBackground = true)
@Composable
fun FoodScreenPreview() {
    FoodScreenContent(
        uiStateFood = FoodScreenUiStateFood(
            id = 1,
            name = "Чипсы",
            calories = "500",
            carbs = "100",
            fats = "30.55",
            proteins = "4.2",
            weightInGrams = "21",
            description = ""
        ),
        updateUiStateFood = {} as (String, String) -> Unit,
        foodTemplatesLoading = false,
        foodTemplates = emptyList(),
        loadFoodTemplates = {},
        fillFoodByTemplate = {},
    )
}
