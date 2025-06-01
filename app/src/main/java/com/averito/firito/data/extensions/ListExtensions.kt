package com.averito.firito.data.extensions

fun List<Int>.safeAverage(): Int =
    if (isEmpty()) 0 else average().toInt()
fun List<Double>.safeAverage(): Double =
    if (isEmpty()) 0.0 else average()
fun List<Float>.safeAverage(): Float =
    if (isEmpty()) 0f else average().toFloat()

fun List<Int>.safeMin(): Int =
    minOrNull() ?: 0
fun List<Double>.safeMin(): Double =
    minOrNull() ?: 0.0
fun List<Float>.safeMin(): Float =
    minOrNull() ?: 0f

fun List<Int>.safeMax(): Int =
    maxOrNull() ?: 0
fun List<Double>.safeMax(): Double =
    maxOrNull() ?: 0.0
fun List<Float>.safeMax(): Float =
    maxOrNull() ?: 0f