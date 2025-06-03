package com.averito.firito.data.services.goals

import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.core.repositories.GoalsRepository
import com.averito.firito.core.services.GoalsService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.models.goals.GoalsModelImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import javax.inject.Inject

class GoalsServiceImpl @Inject constructor(
    @DefaultAppLogger private val defaultAppLogger: AppLogger,
    private val goalsRepository: GoalsRepository
) : GoalsService {
    private val name = "GoalsServiceImpl"

    override suspend fun save(goals: GoalsModel) {
        defaultAppLogger.debug("$name: save: $goals")
        val result = goalsRepository.save(goals)

        if (result.isFailure) {
            defaultAppLogger.error("$name: save: ${result.exceptionOrNull()?.message}")
        }
    }

    override suspend fun getAll(): GoalsModel {
        defaultAppLogger.debug("$name: getAll")
        val result = goalsRepository.getAll()

        if (result.isFailure) {
            defaultAppLogger.error("$name: save: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: GoalsModelImpl()
    }
}
