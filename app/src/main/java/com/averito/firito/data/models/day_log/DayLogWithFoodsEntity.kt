package com.averito.firito.data.models.day_log

import androidx.room.Embedded
import androidx.room.Relation
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.data.models.food.FoodEntity

data class DayLogWithFoodsEntity(
    @Embedded override val dayLog: DayLogEntity,

    @Relation(
        parentColumn = "date",
        entityColumn = "date"
    )
    override val foods: List<FoodEntity>
) : DayLogWithFoodsModel
