package com.averito.firito.core.interactors

import com.averito.firito.core.models.statistics.ActivityDiff
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.CaloriesDiff
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.core.models.statistics.MacroDiff
import com.averito.firito.core.models.statistics.MacroStats
import java.time.YearMonth

interface StatisticsInteractor {
    suspend fun getMacroForMonth(date: YearMonth): MacroStats
    suspend fun getActivityForMonth(date: YearMonth): ActivityStats
    suspend fun getCaloriesForMonth(date: YearMonth): CaloriesStats
    suspend fun getMacroDiffForMonth(date: YearMonth): MacroDiff
    suspend fun getActivityDiffForMonth(date: YearMonth): ActivityDiff
    suspend fun getCaloriesDiffForMonth(date: YearMonth): CaloriesDiff
}