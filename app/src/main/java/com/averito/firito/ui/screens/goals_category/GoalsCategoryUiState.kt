package com.averito.firito.ui.screens.goals_category

import com.averito.firito.core.models.goals.GoalsModel

data class GoalsCategoryUiState(
    val calories: String = "",
    val proteins: String = "",
    val fats: String = "",
    val carbs: String = "",
    val steps: String = "",
    val distance: String = ""
) {
    companion object {
        fun fromModel(model: GoalsModel) = GoalsCategoryUiState(
            calories = model.calories.takeIf { it > 0 }?.toString() ?: "",
            proteins = model.proteins.takeIf { it > 0f }?.toString() ?: "",
            fats = model.fats.takeIf { it > 0f }?.toString() ?: "",
            carbs = model.carbs.takeIf { it > 0f }?.toString() ?: "",
            steps = model.steps.takeIf { it > 0 }?.toString() ?: "",
            distance = model.distance.takeIf { it > 0.0 }?.toString() ?: ""
        )
    }
}
