package com.averito.firito.ui.screens.goals_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averito.firito.core.interactors.GoalsInteractor
import com.averito.firito.data.models.goals.GoalsModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsCategoryViewModel @Inject constructor(
    private val goalsInteractor: GoalsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalsCategoryUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        loadGoals()
    }

    fun onCaloriesChange(value: String) { _uiState.value = _uiState.value.copy(calories = value) }
    fun onProteinsChange(value: String) { _uiState.value = _uiState.value.copy(proteins = value) }
    fun onFatsChange(value: String) { _uiState.value = _uiState.value.copy(fats = value) }
    fun onCarbsChange(value: String) { _uiState.value = _uiState.value.copy(carbs = value) }
    fun onStepsChange(value: String) { _uiState.value = _uiState.value.copy(steps = value) }
    fun onDistanceChange(value: String) { _uiState.value = _uiState.value.copy(distance = value) }

    fun onSaveClick() {
        viewModelScope.launch {
            goalsInteractor.save(
                GoalsModelImpl(
                    calories = _uiState.value.calories.toIntOrNull() ?: 0,
                    proteins = _uiState.value.proteins.toFloatOrNull() ?: 0f,
                    fats = _uiState.value.fats.toFloatOrNull() ?: 0f,
                    carbs = _uiState.value.carbs.toFloatOrNull() ?: 0f,
                    steps = _uiState.value.steps.toIntOrNull() ?: 0,
                    distance = _uiState.value.distance.toDoubleOrNull() ?: 0.0
                )
            )
        }
    }

    fun loadGoals() {
        viewModelScope.launch {
            val goals = goalsInteractor.getAll()
            _uiState.value = GoalsCategoryUiState.fromModel(goals)
        }
    }
}
