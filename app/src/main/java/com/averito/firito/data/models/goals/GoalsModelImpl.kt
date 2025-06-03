package com.averito.firito.data.models.goals

import com.averito.firito.core.models.goals.GoalsModel

data class GoalsModelImpl(
    override val steps: Int = 0,
    override val distance: Double = 0.0,
    override val calories: Int = 0,
    override val proteins: Float = 100f,
    override val fats: Float = 100f,
    override val carbs: Float = 100f
) : GoalsModel
