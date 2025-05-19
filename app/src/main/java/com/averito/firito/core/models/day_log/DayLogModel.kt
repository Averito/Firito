package com.averito.firito.core.models.day_log

import java.time.LocalDate
import java.time.LocalDateTime

interface DayLogModel {
    val date: LocalDate
    val totalCalories: Int
    val totalProteins: Float
    val totalFats: Float
    val totalCarbs: Float
    val totalSteps: Int
    val totalDistanceKm: Double
    val activeCalories: Int
    val finalAt: LocalDateTime?
}