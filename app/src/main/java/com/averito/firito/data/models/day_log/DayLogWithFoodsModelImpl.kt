package com.averito.firito.data.models.day_log

import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.models.food.FoodModel

data class DayLogWithFoodsModelImpl(
    override val dayLog: DayLogModel = DayLogModelImpl(),
    override val foods: List<FoodModel> = emptyList()
) : DayLogWithFoodsModel
