package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.MacroDiff

data class MacroDiffImpl(
    override val proteins: Int = 0,
    override val fats: Int = 0,
    override val carbs: Int = 0
) : MacroDiff
