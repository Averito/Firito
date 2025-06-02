package com.averito.firito.ui.screens.statistics_category.components

import android.R
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
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.core.models.statistics.ActivityDiff
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.StatValue
import com.averito.firito.data.models.statistics.StatValueImpl
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import java.time.format.DateTimeFormatter

@Composable
fun StatisticsCategoryScreenContentActivity(
    activityStats: ActivityStats,
    activityDiff: ActivityDiff,
    goals: GoalsModel
) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surface
    val tertiary = MaterialTheme.colorScheme.tertiary
    val goalLineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)

    fun <T : Number> createLine(label: String, color: Color, items: List<StatValue<T>>, isGoal: Boolean = false): Line {
        return Line(
            values = items.map { it.value.toDouble() },
            color = SolidColor(color),
            label = label,
            curvedEdges = if (isGoal) false else items.size < 15,
            dotProperties = if (isGoal) null else DotProperties(
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
            buildList {
                add(createLine("Шаги", primary, activityStats.steps))
                if (goals.steps > 0) {
                    val goalLine = createLine(
                        label = "Цель",
                        color = goalLineColor,
                        items = activityStats.steps.map { StatValueImpl(date = it.date, value = goals.steps) },
                        isGoal = true
                    )
                    add(goalLine)
                }
            }
        )
    }

    var distanceData by remember {
        mutableStateOf(
            buildList {
                add(createLine("Расстояние (км)", tertiary, activityStats.distance))
                if (goals.distance > 0) {
                    val goalLine = createLine(
                        label = "Цель",
                        color = goalLineColor,
                        items = activityStats.distance.map { StatValueImpl(date = it.date, value = goals.distance) },
                        isGoal = true
                    )
                    add(goalLine)
                }
            }
        )
    }

    LaunchedEffect(activityStats) {
        stepsData = buildList {
            add(createLine("Шаги", primary, activityStats.steps))
            if (goals.steps > 0) {
                val goalLine = createLine(
                    label = "Цель",
                    color = goalLineColor,
                    items = activityStats.steps.map { StatValueImpl(date = it.date, value = goals.steps) },
                    isGoal = true
                )
                add(goalLine)
            }
        }
        distanceData = buildList {
            add(createLine("Расстояние (км)", tertiary, activityStats.distance))
            if (goals.distance > 0) {
                val goalLine = createLine(
                    label = "Цель",
                    color = goalLineColor,
                    items = activityStats.distance.map { StatValueImpl(date = it.date, value = goals.distance) },
                    isGoal = true
                )
                add(goalLine)
            }
        }
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