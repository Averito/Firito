package com.averito.firito.ui.screens.main

import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl
import com.averito.firito.data.models.goals.GoalsModelImpl

data class MainScreenUiState(
    val dayLogWithFoods: DayLogWithFoodsModel = DayLogWithFoodsModelImpl(),
    val goals: GoalsModel = GoalsModelImpl(),
    val isLoading: Boolean = false,
    val permissionsGranted: Boolean = false
)
