package com.averito.firito.core.services

import java.time.LocalDateTime

interface HealthConnectService {
    suspend fun getSteps(from: LocalDateTime, to: LocalDateTime): Int
    suspend fun getDistanceKm(from: LocalDateTime, to: LocalDateTime): Double
    suspend fun getActiveCalories(from: LocalDateTime, to: LocalDateTime): Int
}