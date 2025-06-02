package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.CaloriesDiff
import com.averito.firito.core.models.statistics.StatSeries

data class CaloriesDiffImpl(
    override val caloriesDiff: StatSeries<Int> = StatSeriesImpl(0, 0, 0),
    override val proteinsDiff: StatSeries<Int> = StatSeriesImpl(0, 0, 0),
    override val fatsDiff: StatSeries<Int> = StatSeriesImpl(0, 0, 0),
    override val carbsDiff: StatSeries<Int> = StatSeriesImpl(0, 0, 0)
) : CaloriesDiff
