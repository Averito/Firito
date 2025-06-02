package com.averito.firito.core.interactors

import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import java.time.LocalDate

interface DayLogInteractor {
    suspend fun updateCurrently(): Unit
    suspend fun finalizeDayLog(): DayLogModel
    suspend fun getCurrentlyWithFoods(): DayLogWithFoodsModel
    suspend fun getWithFoodsByDate(date: LocalDate): DayLogWithFoodsModel?
    suspend fun getWithFoodsByRangeDate(from: LocalDate, to: LocalDate): List<DayLogWithFoodsModel>
}