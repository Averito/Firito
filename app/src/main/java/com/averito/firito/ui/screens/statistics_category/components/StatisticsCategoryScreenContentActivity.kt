package com.averito.firito.ui.screens.statistics_category.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
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
import com.averito.firito.core.models.statistics.ActivityDiff
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.StatValue
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import java.time.format.DateTimeFormatter

@Composable
fun StatisticsCategoryScreenContentActivity(activityStats: ActivityStats, activityDiff: ActivityDiff) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surface
    val tertiary = MaterialTheme.colorScheme.tertiary

    fun <T : Number> createLine(label: String, color: Color, items: List<StatValue<T>>): Line {
        return Line(
            values = items.map { it.value.toDouble() },
            color = SolidColor(color),
            label = label,
            curvedEdges = items.size < 15,
            dotProperties = DotProperties(
                enabled = true,
                radius = 2.dp,
                strokeWidth = 2.dp,
                color = SolidColor(surface),
                strokeColor = SolidColor(color)
            )
        )
    }

    var stepsData by remember {
        mutableStateOf(
            listOf(createLine("Шаги", primary, activityStats.steps))
        )
    }

    var distanceData by remember {
        mutableStateOf(
            listOf(createLine("Расстояние (км)", tertiary, activityStats.distance))
        )
    }

    LaunchedEffect(activityStats) {
        stepsData = listOf(createLine("Шаги", primary, activityStats.steps))
        distanceData = listOf(createLine("Расстояние (км)", tertiary, activityStats.distance))
    }

    StatChartBlock(
        title = "Шаги",
        data = stepsData,
        labels = activityStats.steps.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(activityStats.stepsStats),
        diffs = listOf(activityDiff.stepsDiff)
    )

    HorizontalDivider(modifier = Modifier.height(12.dp), color = Color.Transparent)

    StatChartBlock(
        title = "Расстояние (км)",
        data = distanceData,
        labels = activityStats.distance.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(activityStats.distanceStats),
        diffs = listOf(activityDiff.distanceDiff)
    )

}