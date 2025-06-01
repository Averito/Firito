package com.averito.firito.core.models.statistics

interface ActivityStats {
    val steps: List<Int>
    val distance: List<Double>

    val stepsStats: StatSeries<Int>
    val distanceStats: StatSeries<Double>
}
