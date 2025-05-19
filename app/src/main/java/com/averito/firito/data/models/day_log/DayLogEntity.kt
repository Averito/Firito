package com.averito.firito.data.models.day_log

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.averito.firito.core.models.day_log.DayLogModel
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "logs")
data class DayLogEntity(
    @PrimaryKey override val date: LocalDate,
    override val totalCalories: Int,
    override val totalProteins: Float,
    override val totalFats: Float,
    override val totalCarbs: Float,
    override val totalSteps: Int,
    override val totalDistanceKm: Double,
    override val activeCalories: Int,
    override val finalAt: LocalDateTime?
) : DayLogModel
