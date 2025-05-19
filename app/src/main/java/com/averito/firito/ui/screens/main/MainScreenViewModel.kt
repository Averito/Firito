package com.averito.firito.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.averito.firito.core.interactors.DayLogInteractor
import com.averito.firito.core.interactors.FoodInteractor
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.data.managers.HealthConnectManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val dayLogInteractor: DayLogInteractor,
    private val foodInteractor: FoodInteractor,
    private val healthConnectManager: HealthConnectManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            healthConnectManager.permissionsGrantedFlow.collect {
                if (it) _uiState.value = _uiState.value.copy(permissionsGranted = true)
            }
        }
    }

    suspend fun createFood(food: FoodModel): Long {
        return foodInteractor.create(food)
    }

    fun removeFood(food: FoodModel) {
        viewModelScope.launch {
            foodInteractor.remove(food)
            dayLogInteractor.updateCurrently()
            val dayLogWithFoods = dayLogInteractor.getCurrentlyWithFoods()
            _uiState.value = _uiState.value.copy(dayLogWithFoods = dayLogWithFoods)
        }
    }

    fun updateDayLog() {
        viewModelScope.launch {
            dayLogInteractor.ensureDayLogExists()
            dayLogInteractor.updateCurrently()
            loadDayLogWithFoodsInternal()
        }
    }

    private suspend fun loadDayLogWithFoodsInternal() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val dayLogWithFoods = dayLogInteractor.getCurrentlyWithFoods()
        _uiState.value = _uiState.value.copy(isLoading = false, dayLogWithFoods = dayLogWithFoods)
    }
}