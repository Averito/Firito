package com.averito.firito.data.models.day_log

import com.averito.firito.core.models.day_log.DayLogModel
import java.time.LocalDate
import java.time.LocalDateTime

data class DayLogModelImpl(
    override val date: LocalDate = LocalDate.now(),
    override val totalCalories: Int = 0,
    override val totalProteins: Float = 0f,
    override val totalFats: Float = 0f,
    override val totalCarbs: Float = 0f,
    override val totalSteps: Int = 0,
    override val totalDistanceKm: Double = 0.0,
    override val activeCalories: Int = 0,
    override val finalAt: LocalDateTime? = null
) : DayLogModel