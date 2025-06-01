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
        val dayLogsWithFood = dayLogService.getWithFoodsByRangeDate(date.atDay(1), date.atEndOfMonth())
        val foodList = dayLogsWithFood.flatMap { it.foods }

        return MacroStatsImpl(
            proteins = foodList.sumOf { it.proteins },
            fats = foodList.sumOf { it.fats },
            carbs = foodList.sumOf { it.carbs }
        )
    }

    override suspend fun getActivityForMonth(date: YearMonth): ActivityStats {
        defaultAppLogger.debug("$name: Получение статистики активности.")
        val dayLogsWithFood = dayLogService.getWithFoodsByRangeDate(date.atDay(1), date.atEndOfMonth())

        val steps = dayLogsWithFood.map { it.dayLog.totalSteps }
        val distance = dayLogsWithFood.map { it.dayLog.totalDistanceKm }

        return ActivityStatsImpl(
            steps = steps,
            distance = distance,

            stepsStats = StatSeriesImpl(
                min = steps.safeMin(),
                avg = steps.safeAverage(),
                max = steps.safeMax()
            ),

            distanceStats = StatSeriesImpl(
                min = distance.safeMin(),
                avg = distance.safeAverage(),
                max = distance.safeMax()
            ),
        )
    }

    override suspend fun getCaloriesForMonth(date: YearMonth): CaloriesStats {
        defaultAppLogger.debug("$name: Получение статистики калорий и БЖУ.")
        val dayLogsWithFood = dayLogService.getWithFoodsByRangeDate(date.atDay(1), date.atEndOfMonth())
        val foodList = dayLogsWithFood.flatMap { it.foods }

        val calories = foodList.map { it.calories }
        val proteins = foodList.map { it.proteins }
        val fats = foodList.map { it.fats }
        val carbs = foodList.map { it.carbs }

        return CaloriesStatsImpl(
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs,

            caloriesStats = StatSeriesImpl(
                min = calories.safeMin(),
                avg = calories.safeAverage(),
                max = calories.safeMax()
            ),

            proteinsStats = StatSeriesImpl(
                min = proteins.safeMin(),
                avg = proteins.safeAverage(),
                max = proteins.safeMax()
            ),

            fatsStats = StatSeriesImpl(
                min = fats.safeMin(),
                avg = fats.safeAverage(),
                max = fats.safeMax()
            ),

            carbsStats = StatSeriesImpl(
                min = carbs.safeMin(),
                avg = carbs.safeAverage(),
                max = carbs.safeMax()
            ),
        )
    }
}