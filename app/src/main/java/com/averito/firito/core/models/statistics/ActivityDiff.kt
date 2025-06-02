package com.averito.firito.core.models.statistics

interface ActivityDiff {
    val stepsDiff: StatSeries<Int>
    val distanceDiff: StatSeries<Int>
}