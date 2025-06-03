package com.averito.firito.data.repositories.goals

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.core.repositories.GoalsRepository
import com.averito.firito.data.models.goals.GoalsModelImpl
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GoalsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : GoalsRepository {
    private object Keys {
        val CALORIES = intPreferencesKey("goal_calories")
        val PROTEINS = floatPreferencesKey("goal_proteins")
        val FATS = floatPreferencesKey("goal_fats")
        val CARBS = floatPreferencesKey("goal_carbs")
        val STEPS = intPreferencesKey("goal_steps")
        val DISTANCE = doublePreferencesKey("goal_distance")
    }

    override suspend fun save(goals: GoalsModel): Result<Unit> {
        try {
            dataStore.edit {
                it[Keys.CALORIES] = goals.calories
                it[Keys.PROTEINS] = goals.proteins
                it[Keys.FATS] = goals.fats
                it[Keys.CARBS] = goals.carbs
                it[Keys.STEPS] = goals.steps
                it[Keys.DISTANCE] = goals.distance
            }
            return Result.success(Unit)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getAll(): Result<GoalsModel> {
        try {
            val prefs = dataStore.data.first()
            return Result.success(
                GoalsModelImpl(
                    calories = prefs[Keys.CALORIES] ?: 0,
                    proteins = prefs[Keys.PROTEINS] ?: 100f,
                    fats = prefs[Keys.FATS] ?: 100f,
                    carbs = prefs[Keys.CARBS] ?: 100f,
                    steps = prefs[Keys.STEPS] ?: 0,
                    distance = prefs[Keys.DISTANCE] ?: 0.0
                )
            )
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}
