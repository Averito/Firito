package com.averito.firito.core.repositories

import com.averito.firito.core.models.goals.GoalsModel

interface GoalsRepository {
    suspend fun save(goals: GoalsModel): Result<Unit>
    suspend fun getAll(): Result<GoalsModel>
}