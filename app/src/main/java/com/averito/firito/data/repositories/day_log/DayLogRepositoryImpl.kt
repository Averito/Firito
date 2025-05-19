package com.averito.firito.data.repositories.day_log

import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.repositories.DayLogRepository
import com.averito.firito.data.daos.DayLogDao
import com.averito.firito.data.mappers.toEntity
import java.time.LocalDate
import javax.inject.Inject

class DayLogRepositoryImpl @Inject constructor(private val dayLogDao: DayLogDao) : DayLogRepository {
    override suspend fun getByMonthYear(
        month: Int,
        year: Int
    ): Result<List<DayLogModel>> {
        try {
            val dayLogs = dayLogDao.getByMonthYear("%02d".format(month), year.toString())
            return Result.success(dayLogs)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getByDate(date: LocalDate): Result<DayLogModel?> {
        try {
            val dayLog = dayLogDao.getByDate(date)
            return Result.success(dayLog)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun create(dayLog: DayLogModel): Result<LocalDate> {
        try {
            dayLogDao.insert(dayLog.toEntity())
            return Result.success(dayLog.date)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun update(dayLog: DayLogModel): Result<Unit> {
        try {
            dayLogDao.update(dayLog.toEntity())
            return Result.success(Unit)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getWithFoodsByDate(date: LocalDate): Result<DayLogWithFoodsModel?> {
        try {
            val dayLogWithFoods = dayLogDao.getWithFoodsByDate(date)
            return Result.success(dayLogWithFoods)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getWithFoodsByRangeDate(
        from: LocalDate,
        to: LocalDate
    ): Result<List<DayLogWithFoodsModel>> {
        try {
            val dayLogsWithFoods = dayLogDao.getDayLogsWithFoodsBetween(from, to)
            return Result.success(dayLogsWithFoods)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}