package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.MacroStats

data class MacroStatsImpl(
    override val proteins: Float = 0f,
    override val fats: Float = 0f,
    override val carbs: Float = 0f
) : MacroStats
