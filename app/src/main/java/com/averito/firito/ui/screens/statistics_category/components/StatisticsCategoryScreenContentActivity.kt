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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.statistics.ActivityStats
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import java.time.format.DateTimeFormatter

@Composable
fun StatisticsCategoryScreenContentActivity(activityStats: ActivityStats) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surface
    val tertiary = MaterialTheme.colorScheme.tertiary

    var stepsData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = activityStats.steps.map { it.value.toDouble() },
                    color = SolidColor(primary),
                    label = "Шаги",
                    curvedEdges = activityStats.steps.size < 15,
                    dotProperties = DotProperties(
                        enabled = true,
                        radius = 2.dp,
                        strokeWidth = 2.dp,
                        color = SolidColor(surface),
                        strokeColor = SolidColor(primary)
                    )
                )
            )
        )
    }

    var distanceData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = activityStats.distance.map { it.value },
                    color = SolidColor(tertiary),
                    label = "Расстояние (км)",
                    curvedEdges = activityStats.distance.size < 15,
                    dotProperties = DotProperties(
                        enabled = true,
                        radius = 2.dp,
                        strokeWidth = 2.dp,
                        color = SolidColor(surface),
                        strokeColor = SolidColor(tertiary)
                    )
                )
            )
        )
    }

    LaunchedEffect(activityStats) {
        stepsData = listOf(
            Line(
                values = activityStats.steps.map { it.value.toDouble() },
                color = SolidColor(primary),
                label = "Шаги",
                curvedEdges = activityStats.steps.size < 15,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = 2.dp,
                    strokeWidth = 2.dp,
                    color = SolidColor(surface),
                    strokeColor = SolidColor(primary)
                )
            )
        )

        distanceData = listOf(
            Line(
                values = activityStats.distance.map { it.value },
                color = SolidColor(tertiary),
                label = "Расстояние (км)",
                curvedEdges = activityStats.distance.size < 15,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = 2.dp,
                    strokeWidth = 2.dp,
                    color = SolidColor(surface),
                    strokeColor = SolidColor(tertiary)
                )
            )
        )
    }

    StatChartBlock(
        title = "Шаги",
        data = stepsData,
        labels = activityStats.steps.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(activityStats.stepsStats)
    )

    Divider(modifier = Modifier.height(12.dp), color = Color.Transparent)

    StatChartBlock(
        title = "Расстояние (км)",
        data = distanceData,
        labels = activityStats.distance.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(activityStats.distanceStats)
    )
}