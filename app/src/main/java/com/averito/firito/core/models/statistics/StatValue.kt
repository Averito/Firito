package com.averito.firito.core.models.statistics

import java.time.LocalDate

interface StatValue<T : Number> {
    val value: T
    val date: LocalDate
}
