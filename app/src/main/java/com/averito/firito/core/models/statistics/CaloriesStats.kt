package com.averito.firito.core.models.statistics

interface CaloriesStats {
    val calories: List<StatValue<Int>>
    val proteins: List<StatValue<Float>>
    val fats: List<StatValue<Float>>
    val carbs: List<StatValue<Float>>

    val caloriesStats: StatSeries<Int>
    val proteinsStats: StatSeries<Float>
    val fatsStats: StatSeries<Float>
    val carbsStats: StatSeries<Float>
}