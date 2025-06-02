package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.StatSeries

data class ActivityStatsImpl(
    override val steps: List<StatValueImpl<Int>> = emptyList(),
    override val distance: List<StatValueImpl<Double>> = emptyList(),

    override val stepsStats: StatSeries<Int> = StatSeriesImpl(0, 0, 0),
    override val distanceStats: StatSeries<Double> = StatSeriesImpl(0.0, 0.0, 0.0)
) : ActivityStats
