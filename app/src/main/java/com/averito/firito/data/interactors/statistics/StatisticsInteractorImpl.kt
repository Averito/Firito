package com.averito.firito.data.interactors.statistics

import com.averito.firito.core.interactors.StatisticsInteractor
import com.averito.firito.core.models.statistics.ActivityDiff
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.CaloriesDiff
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.core.models.statistics.MacroDiff
import com.averito.firito.core.models.statistics.MacroStats
import com.averito.firito.core.services.DayLogService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.extensions.safeAverage
import com.averito.firito.data.extensions.safeMax
import com.averito.firito.data.extensions.safeMin
import com.averito.firito.data.extensions.sumOf
import com.averito.firito.data.models.statistics.ActivityDiffImpl
import com.averito.firito.data.models.statistics.ActivityStatsImpl
import com.averito.firito.data.models.statistics.CaloriesDiffImpl
import com.averito.firito.data.models.statistics.CaloriesStatsImpl
import com.averito.firito.data.models.statistics.MacroDiffImpl
import com.averito.firito.data.models.statistics.MacroStatsImpl
import com.averito.firito.data.models.statistics.StatSeriesImpl
import com.averito.firito.data.models.statistics.StatValueImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes
import java.time.YearMonth
import javax.inject.Inject

class StatisticsInteractorImpl @Inject constructor(
    @DefaultAppLogger private val defaultAppLogger: AppLogger,
    private val dayLogService: DayLogService
) : StatisticsInteractor {
    private val name = "StatisticsInteractorImpl"

    override suspend fun getMacroForMonth(date: YearMonth): MacroStats {
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

    override suspend fun getMacroDiffForMonth(date: YearMonth): MacroDiff {
        defaultAppLogger.debug("$name: Получение разницы с прошлым месяцем в макронутриенах.")

        val previous = getMacroForMonth(date.minusMonths(1))
        val current = getMacroForMonth(date)

        return MacroDiffImpl(
            proteins = safePercentChange(current.proteins, previous.proteins),
            fats = safePercentChange(current.fats, previous.fats),
            carbs = safePercentChange(current.carbs, previous.carbs)
        )
    }

    override suspend fun getActivityDiffForMonth(date: YearMonth): ActivityDiff {
        defaultAppLogger.debug("$name: Получение разницы с прошлым месяцем в активности.")

        val previous = getActivityForMonth(date.minusMonths(1))
        val current = getActivityForMonth(date)

        return ActivityDiffImpl(
            stepsDiff = StatSeriesImpl<Int>(
                min = safePercentChange(current.stepsStats.min, previous.stepsStats.min),
                avg = safePercentChange(current.stepsStats.avg, previous.stepsStats.avg),
                max = safePercentChange(current.stepsStats.max, previous.stepsStats.max)
            ),
            distanceDiff = StatSeriesImpl<Int>(
                min = safePercentChange(current.distanceStats.min, previous.distanceStats.min),
                avg = safePercentChange(current.distanceStats.avg, previous.distanceStats.avg),
                max = safePercentChange(current.distanceStats.max, previous.distanceStats.max)
            ),
        )
    }

    override suspend fun getCaloriesDiffForMonth(date: YearMonth): CaloriesDiff {
        defaultAppLogger.debug("$name: Получение разницы с прошлым месяцем в калориях.")

        val previous = getCaloriesForMonth(date.minusMonths(1))
        val current = getCaloriesForMonth(date)

        return CaloriesDiffImpl(
            caloriesDiff = StatSeriesImpl<Int>(
                min = safePercentChange(current.caloriesStats.min, previous.caloriesStats.min),
                avg = safePercentChange(current.caloriesStats.avg, previous.caloriesStats.avg),
                max = safePercentChange(current.caloriesStats.max, previous.caloriesStats.max)
            ),
            proteinsDiff = StatSeriesImpl<Int>(
                min = safePercentChange(current.proteinsStats.min, previous.proteinsStats.min),
                avg = safePercentChange(current.proteinsStats.avg, previous.proteinsStats.avg),
                max = safePercentChange(current.proteinsStats.max, previous.proteinsStats.max)
            ),
            fatsDiff = StatSeriesImpl<Int>(
                min = safePercentChange(current.fatsStats.min, previous.fatsStats.min),
                avg = safePercentChange(current.fatsStats.avg, previous.fatsStats.avg),
                max = safePercentChange(current.fatsStats.max, previous.fatsStats.max)
            ),
            carbsDiff = StatSeriesImpl<Int>(
                min = safePercentChange(current.carbsStats.min, previous.carbsStats.min),
                avg = safePercentChange(current.carbsStats.avg, previous.carbsStats.avg),
                max = safePercentChange(current.carbsStats.max, previous.carbsStats.max)
            ),
        )
    }

    private fun safePercentChange(current: Int, previous: Int): Int =
        if (previous == 0) 0 else current / previous * 100 - 100
    private fun safePercentChange(current: Float, previous: Float): Int =
        if (previous == 0f) 0 else (current / previous * 100f - 100f).toInt()
    private fun safePercentChange(current: Double, previous: Double): Int =
        if (previous == 0.0) 0 else (current / previous * 100.0 - 100.0).toInt()
}