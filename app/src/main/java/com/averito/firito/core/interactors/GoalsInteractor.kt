package com.averito.firito.core.interactors

import com.averito.firito.core.models.goals.GoalsModel

interface GoalsInteractor {
    suspend fun save(goals: GoalsModel): Unit
    suspend fun getAll(): GoalsModel
}
