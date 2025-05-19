package com.averito.firito.data.mappers

import com.averito.firito.core.models.day_log.DayLogModel
import com.averito.firito.data.models.day_log.DayLogEntity

fun DayLogModel.toEntity() = DayLogEntity(date, totalCalories, totalProteins, totalFats, totalCarbs, totalSteps, totalDistanceKm, activeCalories, finalAt)