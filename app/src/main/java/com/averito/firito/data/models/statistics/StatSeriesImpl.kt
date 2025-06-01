package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.StatSeries

data class StatSeriesImpl<T : Number>(
    override val min: T,
    override val avg: T,
    override val max: T
) : StatSeries<T>