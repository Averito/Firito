package com.averito.firito.data.repositories.health

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.averito.firito.core.repositories.HealthConnectRepository
import java.time.LocalDateTime

class HealthConnectRepositoryImpl(private val healthConnectClient: HealthConnectClient, ) : HealthConnectRepository {
    override suspend fun getSteps(from: LocalDateTime, to: LocalDateTime): Result<Int> {
        try {
            val request = ReadRecordsRequest(
                recordType = StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(from, to)
            )
            val response = healthConnectClient.readRecords(request)
            return Result.success(response.records.sumOf { it.count.toInt() })
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getDistanceKm(from: LocalDateTime, to: LocalDateTime): Result<Double> {
        try {
            val request = ReadRecordsRequest(
                recordType = DistanceRecord::class,
                timeRangeFilter = TimeRangeFilter.between(from, to)
            )
            val response = healthConnectClient.readRecords(request)
            return Result.success(response.records.sumOf { it.distance.inKilometers })
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getActiveCalories(
        from: LocalDateTime,
        to: LocalDateTime
    ): Result<Int> {
        try {
            val request = ReadRecordsRequest(
                recordType = TotalCaloriesBurnedRecord::class,
                timeRangeFilter = TimeRangeFilter.between(from, to)
            )
            val response = healthConnectClient.readRecords(request)
            return Result.success(response.records.sumOf { it.energy.inKilocalories.toInt() })
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}