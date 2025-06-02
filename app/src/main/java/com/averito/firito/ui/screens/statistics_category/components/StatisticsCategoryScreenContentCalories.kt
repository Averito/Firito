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
import com.averito.firito.core.models.goals.GoalsModel
import com.averito.firito.core.models.statistics.CaloriesDiff
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.core.models.statistics.StatValue
import com.averito.firito.data.models.statistics.StatValueImpl
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import java.time.format.DateTimeFormatter
import kotlin.collections.buildList

@Composable
fun StatisticsCategoryScreenContentCalories(
    caloriesStats: CaloriesStats,
    caloriesDiff: CaloriesDiff,
    goals: GoalsModel
) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surface
    val proteinColor = Color(0xFF4DB6AC)
    val proteinGoalColor = Color(0xFF00796B).copy(alpha = 0.4f)

    val fatColor = Color(0xFFFFB74D)
    val fatGoalColor = Color(0xFFEF6C00).copy(alpha = 0.4f)

    val carbColor = Color(0xFF81C784)
    val carbGoalColor = Color(0xFF388E3C).copy(alpha = 0.4f)

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

    var caloriesData by remember {
        mutableStateOf(
            buildList {
                add(createLine("Калории", primary, caloriesStats.calories))
                if (goals.calories > 0) {
                    val goalLine = createLine(
                        label = "Цель",
                        color = goalLineColor,
                        items = caloriesStats.calories.map { StatValueImpl(date = it.date, value = goals.calories) },
                        isGoal = true
                    )
                    add(goalLine)
                }
            }
        )
    }

    var bjuData by remember {
        mutableStateOf(
            buildList {
                add(createLine("Белки", proteinColor, caloriesStats.proteins))
                add(createLine("Жиры", fatColor, caloriesStats.fats))
                add(createLine("Углеводы", carbColor, caloriesStats.carbs))
                if (goals.proteins > 0) {
                    add(createLine("Цель белков", proteinGoalColor, caloriesStats.proteins.map { StatValueImpl(date = it.date, value = goals.proteins) }, isGoal = true))
                }
                if (goals.fats > 0) {
                    add(createLine("Цель жиров", fatGoalColor, caloriesStats.fats.map { StatValueImpl(date = it.date, value = goals.fats) }, isGoal = true))
                }
                if (goals.carbs > 0) {
                    add(createLine("Цель углеводов", carbGoalColor, caloriesStats.carbs.map { StatValueImpl(date = it.date, value = goals.carbs) }, isGoal = true))
                }
            }
        )
    }

    LaunchedEffect(caloriesStats) {
        caloriesData = buildList {
            add(createLine("Калории", primary, caloriesStats.calories))
            if (goals.calories > 0) {
                val goalLine = createLine(
                    label = "Цель",
                    color = goalLineColor,
                    items = caloriesStats.calories.map { StatValueImpl(date = it.date, value = goals.calories) },
                    isGoal = true
                )
                add(goalLine)
            }
        }
        bjuData = buildList {
            add(createLine("Белки", proteinColor, caloriesStats.proteins))
            add(createLine("Жиры", fatColor, caloriesStats.fats))
            add(createLine("Углеводы", carbColor, caloriesStats.carbs))
            if (goals.proteins > 0) {
                add(createLine("Цель белков", proteinGoalColor, caloriesStats.proteins.map { StatValueImpl(date = it.date, value = goals.proteins) }, isGoal = true))
            }
            if (goals.fats > 0) {
                add(createLine("Цель жиров", fatGoalColor, caloriesStats.fats.map { StatValueImpl(date = it.date, value = goals.fats) }, isGoal = true))
            }
            if (goals.carbs > 0) {
                add(createLine("Цель углеводов", carbGoalColor, caloriesStats.carbs.map { StatValueImpl(date = it.date, value = goals.carbs) }, isGoal = true))
            }
        }
    }

    StatChartBlock(
        title = "Калории",
        data = caloriesData,
        labels = caloriesStats.calories.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(caloriesStats.caloriesStats),
        diffs = listOf(caloriesDiff.caloriesDiff)
    )

    HorizontalDivider(modifier = Modifier.height(12.dp), color = Color.Transparent)

    StatChartBlock(
        title = "БЖУ (г)",
        data = bjuData,
        labels = caloriesStats.proteins.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(
            caloriesStats.proteinsStats,
            caloriesStats.fatsStats,
            caloriesStats.carbsStats
        ),
        diffs = listOf(
            caloriesDiff.proteinsDiff,
            caloriesDiff.fatsDiff,
            caloriesDiff.carbsDiff
        )
    )

}