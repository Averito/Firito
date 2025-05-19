package com.averito.firito.core.repositories

import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import java.time.LocalDate

interface DayLogRepository {
    suspend fun getByMonthYear(month: Int, year: Int): Result<List<DayLogModel>>
    suspend fun getByDate(date: LocalDate): Result<DayLogModel?>
    suspend fun create(dayLog: DayLogModel): Result<LocalDate>
    suspend fun update(dayLog: DayLogModel): Result<Unit>
    suspend fun getWithFoodsByDate(date: LocalDate): Result<DayLogWithFoodsModel?>
    suspend fun getWithFoodsByRangeDate(from: LocalDate, to: LocalDate): Result<List<DayLogWithFoodsModel>>
}