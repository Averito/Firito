package com.averito.firito.core.models.statistics

interface CaloriesDiff {
    val caloriesDiff: StatSeries<Int>
    val proteinsDiff: StatSeries<Int>
    val fatsDiff: StatSeries<Int>
    val carbsDiff: StatSeries<Int>
}
