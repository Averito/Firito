package com.averito.firito.core.services

import com.averito.firito.core.models.goals.GoalsModel

interface GoalsService {
    suspend fun save(goals: GoalsModel): Unit
    suspend fun getAll(): GoalsModel
}
