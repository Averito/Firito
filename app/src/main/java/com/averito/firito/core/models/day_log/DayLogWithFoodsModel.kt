package com.averito.firito.core.models.day_log

import com.averito.firito.core.models.food.FoodModel

interface DayLogWithFoodsModel {
    val dayLog: DayLogModel
    val foods: List<FoodModel>
}
