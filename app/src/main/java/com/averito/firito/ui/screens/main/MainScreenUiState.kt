package com.averito.firito.ui.screens.main

import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl

data class MainScreenUiState(
    val dayLogWithFoods: DayLogWithFoodsModel = DayLogWithFoodsModelImpl(),
    val isLoading: Boolean = false,
    val permissionsGranted: Boolean = false
)
