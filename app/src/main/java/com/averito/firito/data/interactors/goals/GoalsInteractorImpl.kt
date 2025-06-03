package com.averito.firito.data.interactors.goals

import com.averito.firito.core.interactors.GoalsInteractor
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.core.services.GoalsService
import javax.inject.Inject

class GoalsInteractorImpl @Inject constructor(
    private val goalsService: GoalsService
) : GoalsInteractor {
    override suspend fun save(goals: GoalsModel) {
        return goalsService.save(goals)
    }

    override suspend fun getAll(): GoalsModel {
        return goalsService.getAll()
    }
}
