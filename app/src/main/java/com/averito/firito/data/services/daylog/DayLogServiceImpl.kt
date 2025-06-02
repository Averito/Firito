package com.averito.firito.data.services.daylog

import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.repositories.DayLogRepository
import com.averito.firito.core.services.DayLogService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.di.qualifiers.DefaultAppLogger
import java.time.LocalDate
import javax.inject.Inject

class DayLogServiceImpl @Inject constructor(
    private val dayLogRepository: DayLogRepository,
    @DefaultAppLogger private val defaultAppLogger: AppLogger
) : DayLogService {
    private val name = "DayLogServiceImpl"

    override suspend fun getWithFoodsByDate(date: LocalDate): DayLogWithFoodsModel? {
        defaultAppLogger.debug("$name: Получение текущего агрегированного лога.")

        val result = dayLogRepository.getWithFoodsByDate(date)

        if (result.isFailure) {
            defaultAppLogger.error("$name: getWithFoodsByDate: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull()
    }

    override suspend fun getWithFoodsByRangeDate(
        from: LocalDate,
        to: LocalDate
    ): List<DayLogWithFoodsModel> {
        defaultAppLogger.debug("$name: Получение логов с едой за период с $from, по $to")
        val result = dayLogRepository.getWithFoodsByRangeDate(from, to)

        if (result.isFailure) {
            defaultAppLogger.error("$name: getWithFoodsByRangeDate: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: emptyList()
    }

    override suspend fun create(dayLog: DayLogModel): LocalDate {
        defaultAppLogger.debug("$name: Создание дневного лога (date = ${dayLog.date}).")

        val result = dayLogRepository.create(dayLog)

        if (result.isFailure) {
            defaultAppLogger.error("$name: create: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: LocalDate.MAX
    }

    override suspend fun update(dayLog: DayLogModel): Unit {
        defaultAppLogger.debug("$name: Обновление дневного лога (date = ${dayLog.date}).")

        val currentDayLog = getWithFoodsByDate(dayLog.date)

        if (currentDayLog != null && currentDayLog.dayLog.finalAt != null) {
            defaultAppLogger.error("$name: update: Невозможно обновить завершённый дневной лог")
            return
        }

        val result = dayLogRepository.update(dayLog)

        if (result.isFailure) {
            defaultAppLogger.error("$name: update: ${result.exceptionOrNull()?.message}")
        }
    }
}