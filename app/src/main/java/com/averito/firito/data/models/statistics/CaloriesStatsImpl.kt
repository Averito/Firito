package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.core.models.statistics.StatSeries
import com.averito.firito.core.models.statistics.StatValue

data class CaloriesStatsImpl(
    override val calories: List<StatValue<Int>> = emptyList(),
    override val proteins: List<StatValue<Float>> = emptyList(),
    override val fats: List<StatValue<Float>> = emptyList(),
    override val carbs: List<StatValue<Float>> = emptyList(),

    override val caloriesStats: StatSeries<Int> = StatSeriesImpl(0, 0, 0),
    override val proteinsStats: StatSeries<Float> = StatSeriesImpl(0f, 0f, 0f),
    override val fatsStats: StatSeries<Float> = StatSeriesImpl(0f, 0f, 0f),
    override val carbsStats: StatSeries<Float> = StatSeriesImpl(0f, 0f, 0f)
) : CaloriesStats
