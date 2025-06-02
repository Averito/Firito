package com.averito.firito.core.services

import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import java.time.LocalDate

interface DayLogService {
    suspend fun getWithFoodsByDate(date: LocalDate): DayLogWithFoodsModel?
    suspend fun getWithFoodsByRangeDate(from: LocalDate, to: LocalDate): List<DayLogWithFoodsModel>
    suspend fun create(dayLog: DayLogModel): LocalDate
    suspend fun update(dayLog: DayLogModel): Unit
}