package com.averito.firito.data.interactors.statistics

import com.averito.firito.core.interactors.StatisticsInteractor
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.core.models.statistics.MacroStats
import com.averito.firito.core.services.DayLogService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.extensions.safeAverage
import com.averito.firito.data.extensions.safeMax
import com.averito.firito.data.extensions.safeMin
import com.averito.firito.data.extensions.sumOf
import com.averito.firito.data.models.statistics.ActivityStatsImpl
import com.averito.firito.data.models.statistics.CaloriesStatsImpl
import com.averito.firito.data.models.statistics.MacroStatsImpl
import com.averito.firito.data.models.statistics.StatSeriesImpl
import com.averito.firito.data.models.statistics.StatValueImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import java.time.YearMonth
import javax.inject.Inject

class StatisticsInteractorImpl @Inject constructor(
    @DefaultAppLogger private val defaultAppLogger: AppLogger,
    private val dayLogService: DayLogService
) : StatisticsInteractor {
    private val name = "StatisticsInteractorImpl"

    override suspend fun getMacrosForMonth(date: YearMonth): MacroStats {
        defaultAppLogger.debug("$name: Получение статистики макронутриенов.")
        val dayLogsWithFood = dayLogService.getWithFoodsByRangeDate(date.atDay(1), date.atEndOfMonth()).asReversed()
        val dayLogs = dayLogsWithFood.map { it.dayLog }

        return MacroStatsImpl(
            proteins = dayLogs.sumOf { it.totalProteins },
            fats = dayLogs.sumOf { it.totalFats },
            carbs = dayLogs.sumOf { it.totalCarbs }
        )
    }

    override suspend fun getActivityForMonth(date: YearMonth): ActivityStats {
        defaultAppLogger.debug("$name: Получение статистики активности.")
        val dayLogsWithFood = dayLogService.getWithFoodsByRangeDate(date.atDay(1), date.atEndOfMonth()).asReversed()

        val steps = dayLogsWithFood.map { StatValueImpl<Int>(value = it.dayLog.totalSteps, date = it.dayLog.date) }
        val stepsOnlyValues = steps.map { it.value }
        val distance = dayLogsWithFood.map { StatValueImpl<Double>(value = it.dayLog.totalDistanceKm, date = it.dayLog.date) }
        val distanceOnlyValues = distance.map { it.value }

        return ActivityStatsImpl(
            steps = steps,
            distance = distance,

            stepsStats = StatSeriesImpl(
                min = stepsOnlyValues.safeMin(),
                avg = stepsOnlyValues.safeAverage(),
                max = stepsOnlyValues.safeMax()
            ),

            distanceStats = StatSeriesImpl(
                min = distanceOnlyValues.safeMin(),
                avg = distanceOnlyValues.safeAverage(),
                max = distanceOnlyValues.safeMax()
            ),
        )
    }

    override suspend fun getCaloriesForMonth(date: YearMonth): CaloriesStats {
        defaultAppLogger.debug("$name: Получение статистики калорий и БЖУ.")
        val dayLogsWithFood = dayLogService.getWithFoodsByRangeDate(date.atDay(1), date.atEndOfMonth()).asReversed()
        val dayLogs = dayLogsWithFood.map { it.dayLog }

        val calories = dayLogs.map { StatValueImpl<Int>(value = it.totalCalories, date = it.date) }
        val caloriesOnlyValues = calories.map { it.value }
        val proteins = dayLogs.map { StatValueImpl<Float>(value = it.totalProteins, date = it.date) }
        val proteinsOnlyValues = proteins.map { it.value }
        val fats = dayLogs.map { StatValueImpl<Float>(value = it.totalFats, date = it.date) }
        val fatsOnlyValues = fats.map { it.value }
        val carbs = dayLogs.map { StatValueImpl<Float>(value = it.totalCarbs, date = it.date) }
        val carbsOnlyValues = carbs.map { it.value }

        return CaloriesStatsImpl(
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs,

            caloriesStats = StatSeriesImpl(
                min = caloriesOnlyValues.safeMin(),
                avg = caloriesOnlyValues.safeAverage(),
                max = caloriesOnlyValues.safeMax()
            ),

            proteinsStats = StatSeriesImpl(
                min = proteinsOnlyValues.safeMin(),
                avg = proteinsOnlyValues.safeAverage(),
                max = proteinsOnlyValues.safeMax()
            ),

            fatsStats = StatSeriesImpl(
                min = fatsOnlyValues.safeMin(),
                avg = fatsOnlyValues.safeAverage(),
                max = fatsOnlyValues.safeMax()
            ),

            carbsStats = StatSeriesImpl(
                min = carbsOnlyValues.safeMin(),
                avg = carbsOnlyValues.safeAverage(),
                max = carbsOnlyValues.safeMax()
            ),
        )
    }
}