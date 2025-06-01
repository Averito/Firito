package com.averito.firito.core.interactors

import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.core.models.statistics.MacroStats
import java.time.YearMonth

interface StatisticsInteractor {
    suspend fun getMacrosForMonth(date: YearMonth): MacroStats
    suspend fun getActivityForMonth(date: YearMonth): ActivityStats
    suspend fun getCaloriesForMonth(date: YearMonth): CaloriesStats
}