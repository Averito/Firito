package com.averito.firito.core.models.statistics

interface StatSeries<T : Number> {
    val min: T
    val avg: T
    val max: T
}