package com.averito.firito.data.interactors.day_log

import com.averito.firito.core.interactors.DayLogInteractor
import com.averito.firito.core.interactors.FoodInteractor
import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.core.models.day_log.DayLogWithFoodsModel
import com.averito.firito.core.services.DayLogService
import com.averito.firito.core.services.HealthConnectService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.models.day_log.DayLogModelImpl
import com.averito.firito.data.models.day_log.DayLogWithFoodsModelImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class DayLogInteractorImpl @Inject constructor(
    private val dayLogService: DayLogService,
    private val foodInteractor: FoodInteractor,
    private val healthConnectService: HealthConnectService,
    @DefaultAppLogger private val defaultAppLogger: AppLogger
) : DayLogInteractor {
    private val name = "DayLogInteractorImpl"

    override suspend fun updateCurrently(): Unit {
        updateByDate(LocalDateTime.now())
    }

    override suspend fun finalizeDayLog(): DayLogModel {
        val currently = ensureDayLogExists(LocalDate.now())
        val previouslyLogWithFood = dayLogService.getWithFoodsByDate(LocalDate.now().minusDays(1))

        if (previouslyLogWithFood == null || previouslyLogWithFood.dayLog.finalAt != null) {
            dayLogService.update(DayLogModelImpl(
                date = currently.date,
                totalSteps = currently.totalSteps,
                totalDistanceKm = currently.totalDistanceKm,
                totalCarbs = currently.totalCarbs,
                totalProteins = currently.totalProteins,
                totalCalories = currently.totalCalories,
                totalFats = currently.totalFats,
                activeCalories = currently.activeCalories,
                finalAt = LocalDateTime.now()
            ))
            return ensureDayLogExists(LocalDate.now().plusDays(1))
        }

        dayLogService.update(DayLogModelImpl(
            date = previouslyLogWithFood.dayLog.date,
            totalSteps = previouslyLogWithFood.dayLog.totalSteps,
            totalDistanceKm = previouslyLogWithFood.dayLog.totalDistanceKm,
            totalCarbs = previouslyLogWithFood.dayLog.totalCarbs,
            totalProteins = previouslyLogWithFood.dayLog.totalProteins,
            totalCalories = previouslyLogWithFood.dayLog.totalCalories,
            totalFats = previouslyLogWithFood.dayLog.totalFats,
            activeCalories = previouslyLogWithFood.dayLog.activeCalories,
            finalAt = LocalDateTime.now()
        ))

        return ensureDayLogExists(LocalDate.now())
    }

    override suspend fun getCurrentlyWithFoods(): DayLogWithFoodsModel {
        val dayLog = ensureDayLogExists(LocalDate.now())
        val dayLogWithFoods = dayLogService.getWithFoodsByDate(LocalDate.now())

        return dayLogWithFoods ?: DayLogWithFoodsModelImpl(dayLog = dayLog, foods = emptyList())
    }

    override suspend fun getWithFoodsByDate(date: LocalDate): DayLogWithFoodsModel? {
        val dayLogWithFoods = dayLogService.getWithFoodsByDate(date)
        return dayLogWithFoods
    }

    override suspend fun getWithFoodsByRangeDate(
        from: LocalDate,
        to: LocalDate
    ): List<DayLogWithFoodsModel> {
        return dayLogService.getWithFoodsByRangeDate(from, to)
    }

    private suspend fun ensureDayLogExists(date: LocalDate): DayLogModel {
        val currently = dayLogService.getWithFoodsByDate(date)
        if (currently != null) return currently.dayLog
        defaultAppLogger.info("$name: ensureDayLogExists: Дневной лог не существует - создаю новый.")

        val newDayLog = DayLogModelImpl()

        dayLogService.create(newDayLog)
        return newDayLog
    }

    private suspend fun updateByDate(date: LocalDateTime) {
        val log = ensureDayLogExists(date.toLocalDate())
        val foodByToday = foodInteractor.getByDate(date.toLocalDate())
        defaultAppLogger.debug("$name: updateByDate: Обновление данных лога $date. Еда: ${foodByToday}")

        dayLogService.update(DayLogModelImpl(
            date = log.date,
            totalCalories = foodByToday.sumOf { it.weightInGrams.toDouble() / 100.0 * it.calories }.toInt(),
            totalFats = foodByToday.sumOf { it.weightInGrams.toDouble() / 100.0 * it.fats.toDouble() }.toFloat(),
            totalCarbs = foodByToday.sumOf { it.weightInGrams.toDouble() / 100.0 * it.carbs.toDouble() }.toFloat(),
            totalProteins = foodByToday.sumOf { it.weightInGrams.toDouble() / 100.0 * it.proteins.toDouble() }.toFloat(),
            totalSteps = healthConnectService.getSteps(
                date.toLocalDate().atStartOfDay(),
                date
            ),
            totalDistanceKm = healthConnectService.getDistanceKm(
                date.toLocalDate().atStartOfDay(),
                date
            ),
            activeCalories = healthConnectService.getActiveCalories(
                date.toLocalDate().atStartOfDay(),
                date
            )
        ))
    }
}