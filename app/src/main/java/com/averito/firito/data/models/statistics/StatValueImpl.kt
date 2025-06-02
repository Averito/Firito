package com.averito.firito.data.models.statistics

import com.averito.firito.core.models.statistics.StatValue
import java.time.LocalDate

data class StatValueImpl<T : Number>(
    override val value: T,
    override val date: LocalDate
) : StatValue<T>
