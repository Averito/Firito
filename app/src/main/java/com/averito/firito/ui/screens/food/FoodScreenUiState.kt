package com.averito.firito.ui.screens.food

import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.data.models.food.FoodModelImpl
import java.time.LocalDate
import java.time.LocalDateTime

data class FoodScreenUiState(
    val food: FoodModelImpl = FoodModelImpl(),
    val foodTemplatesSearch: String = "",
    val foodTemplates: List<FoodModel> = emptyList(),
    val foodTemplatesLoading: Boolean = false
)

data class FoodScreenUiStateFood(
    val id: Long = 0,
    val name: String = "",
    val calories: String = "",
    val proteins: String = "",
    val fats: String = "",
    val carbs: String = "",
    val weightInGrams: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)
