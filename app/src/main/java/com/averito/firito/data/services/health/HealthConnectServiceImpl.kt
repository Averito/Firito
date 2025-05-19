package com.averito.firito.data.services.health

import com.averito.firito.core.repositories.HealthConnectRepository
import com.averito.firito.core.services.HealthConnectService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.di.qualifiers.DefaultAppLogger
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class HealthConnectServiceImpl @Inject constructor(
    private val healthConnectRepository: HealthConnectRepository,
    @DefaultAppLogger private val defaultAppLogger: AppLogger
) : HealthConnectService {
    private val name = "HealthConnectServiceImpl"

    override suspend fun getSteps(from: LocalDateTime, to: LocalDateTime): Int {
        defaultAppLogger.debug("$name: Получение шагов за сегодня.")

        val result = healthConnectRepository.getSteps(from, to)

        if (result.isFailure) {
            defaultAppLogger.error("$name: getSteps: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: 0
    }

    override suspend fun getDistanceKm(from: LocalDateTime, to: LocalDateTime): Double {
        defaultAppLogger.debug("$name: Получение пройденной дистанции в километрах.")

        val result = healthConnectRepository.getDistanceKm(from, to)

        if (result.isFailure) {
            defaultAppLogger.error("$name: getDistanceKm: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: 0.0
    }

    override suspend fun getActiveCalories(
        from: LocalDateTime,
        to: LocalDateTime
    ): Int {
        defaultAppLogger.debug("$name: Получение количества активных калорий")

        val result = healthConnectRepository.getActiveCalories(from, to)

        if (result.isFailure) {
            defaultAppLogger.error("$name: getActiveCalories: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: 0
    }
}