package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.ActivityDiff
import com.averito.firito.core.models.statistics.StatSeries

data class ActivityDiffImpl(
    override val stepsDiff: StatSeries<Int> = StatSeriesImpl(0, 0, 0),
    override val distanceDiff: StatSeries<Int> = StatSeriesImpl(0, 0, 0)
) : ActivityDiff
