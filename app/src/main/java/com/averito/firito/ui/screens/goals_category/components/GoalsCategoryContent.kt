package com.averito.firito.ui.screens.goals_category.components

import androidx.compose.runtime.Composable
import com.averito.firito.ui.screens.goals_category.GoalsCategoryUiState
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes

@Composable
fun GoalsCategoryContent(
    uiState: GoalsCategoryUiState,
    onCaloriesChange: (String) -> Unit,
    onProteinsChange: (String) -> Unit,
    onFatsChange: (String) -> Unit,
    onCarbsChange: (String) -> Unit,
    onStepsChange: (String) -> Unit,
    onDistanceChange: (String) -> Unit,
    category: AppNavGraphRoutes.GoalsCategory.Category
) {
    when (category) {
        AppNavGraphRoutes.GoalsCategory.Category.ACTIVITY -> {
            GoalsCategoryContentActivity(
                steps = uiState.steps,
                updateSteps = { onStepsChange(it) },
                distance = uiState.distance,
                updateDistance = { onDistanceChange(it) }
            )
        }
        AppNavGraphRoutes.GoalsCategory.Category.CALORIES -> {
            GoalsCategoryContentCalories(
                calories = uiState.calories,
                updateCalories = { onCaloriesChange(it) },
                proteins = uiState.proteins,
                updateProteins = { onProteinsChange(it) },
                fats = uiState.fats,
                updateFats = { onFatsChange(it) },
                carbs = uiState.carbs,
                updateCarbs = { onCarbsChange(it) }
            )
        }
    }
}