package com.averito.firito.ui.screens.statistics_category.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.statistics.ActivityStats
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun StatisticsCategoryScreenContentActivity(activityStats: ActivityStats) {
    val primary = MaterialTheme.colorScheme.primary
    val tertiary = MaterialTheme.colorScheme.tertiary

    var stepsData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = activityStats.steps.map { it.toDouble() },
                    color = SolidColor(primary),
                    label = "Шаги"
                )
            )
        )
    }

    var distanceData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = activityStats.distance,
                    color = SolidColor(tertiary),
                    label = "Расстояние (км)"
                )
            )
        )
    }

    LaunchedEffect(activityStats) {
        stepsData = listOf(
            Line(
                values = activityStats.steps.map { it.toDouble() },
                color = SolidColor(primary),
                label = "Шаги"
            )
        )

        distanceData = listOf(
            Line(
                values = activityStats.distance,
                color = SolidColor(tertiary),
                label = "Расстояние (км)"
            )
        )
    }

    StatChartBlock(
        title = "Шаги",
        data = stepsData,
        stats = listOf(activityStats.stepsStats)
    )

    Divider(modifier = Modifier.height(12.dp))

    StatChartBlock(
        title = "Расстояние (км)",
        data = distanceData,
        stats = listOf(activityStats.distanceStats)
    )
}