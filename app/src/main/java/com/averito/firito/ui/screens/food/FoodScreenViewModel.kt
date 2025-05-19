package com.averito.firito.ui.screens.food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.averito.firito.core.interactors.FoodInteractor
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.data.mappers.toImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FoodScreenViewModel @Inject constructor(
    private val foodInteractor: FoodInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    var uiStateFood by mutableStateOf(FoodScreenUiStateFood())
        private set

    fun updateUiStateFood(field: String, value: String) {
        uiStateFood = when (field) {
            "name" -> uiStateFood.copy(name = value)
            "calories" -> uiStateFood.copy(calories = value)
            "proteins" -> uiStateFood.copy(proteins = value)
            "fats" -> uiStateFood.copy(fats = value)
            "carbs" -> uiStateFood.copy(carbs = value)
            "weightInGrams" -> uiStateFood.copy(weightInGrams = value)
            "description" -> uiStateFood.copy(description = value)
            else -> uiStateFood
        }

        _uiState.value = _uiState.value.copy(food = uiStateFood.toImpl())
    }

    suspend fun loadFoodTemplates() {
        _uiState.value = _uiState.value.copy(foodTemplatesLoading = true)
        val foodTemplates = foodInteractor.getFoodTemplates()
        _uiState.value = _uiState.value.copy(foodTemplatesLoading = false, foodTemplates = foodTemplates)
    }

    fun fillFoodByTemplate(template: FoodModel) {
        _uiState.value = _uiState.value.copy(
            food = _uiState.value.food.copy(
                name = template.name,
                calories = template.calories,
                proteins = template.proteins,
                fats = template.fats,
                carbs = template.carbs,
                weightInGrams = template.weightInGrams
            )
        )
        uiStateFood = _uiState.value.food.toUiModel()
    }

    suspend fun updateFood(foodModel: FoodModel) {
        foodInteractor.update(foodModel)
    }

    suspend fun removeFood(foodModel: FoodModel) {
        foodInteractor.remove(foodModel)
    }

    suspend fun loadFood(id: Long) {
        val food = foodInteractor.getById(id)
        _uiState.value = _uiState.value.copy(food = food.toImpl())
        uiStateFood = food.toImpl().toUiModel()
    }
}