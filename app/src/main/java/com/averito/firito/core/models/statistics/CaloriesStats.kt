package com.averito.firito.core.models.statistics

interface CaloriesStats {
    val calories: List<Int>
    val proteins: List<Float>
    val fats: List<Float>
    val carbs: List<Float>

    val caloriesStats: StatSeries<Int>
    val proteinsStats: StatSeries<Float>
    val fatsStats: StatSeries<Float>
    val carbsStats: StatSeries<Float>
}