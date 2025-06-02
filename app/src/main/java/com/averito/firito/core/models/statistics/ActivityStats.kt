package com.averito.firito.core.models.statistics

interface ActivityStats {
    val steps: List<StatValue<Int>>
    val distance: List<StatValue<Double>>

    val stepsStats: StatSeries<Int>
    val distanceStats: StatSeries<Double>
}
