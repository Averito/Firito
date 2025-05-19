package com.averito.firito.core.repositories

import java.time.LocalDateTime

interface HealthConnectRepository {
    suspend fun getSteps(from: LocalDateTime, to: LocalDateTime): Result<Int>
    suspend fun getDistanceKm(from: LocalDateTime, to: LocalDateTime): Result<Double>
    suspend fun getActiveCalories(from: LocalDateTime, to: LocalDateTime): Result<Int>
}