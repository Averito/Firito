package com.averito.firito.ui.screens.journal_details

import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl
import com.averito.firito.data.models.goals.GoalsModelImpl

data class JournalDetailsUiState(
    val dayLogWithFoods: DayLogWithFoodsModel? = DayLogWithFoodsModelImpl(),
    val goals: GoalsModel = GoalsModelImpl()
)
