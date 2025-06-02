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
import com.averito.firito.core.models.statistics.CaloriesDiff
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.core.models.statistics.StatValue
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import java.time.format.DateTimeFormatter

@Composable
fun StatisticsCategoryScreenContentCalories(caloriesStats: CaloriesStats, caloriesDiff: CaloriesDiff) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surface

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

    var caloriesData by remember {
        mutableStateOf(
            listOf(createLine("Калории", primary, caloriesStats.calories))
        )
    }

    var bjuData by remember {
        mutableStateOf(
            listOf(
                createLine("Белки", Color(0xFF4DB6AC), caloriesStats.proteins),
                createLine("Жиры", Color(0xFFFFB74D), caloriesStats.fats),
                createLine("Углеводы", Color(0xFF81C784), caloriesStats.carbs),
            )
        )
    }

    LaunchedEffect(caloriesStats) {
        caloriesData = listOf(createLine("Калории", primary, caloriesStats.calories))
        bjuData = listOf(
            createLine("Белки", Color(0xFF4DB6AC), caloriesStats.proteins),
            createLine("Жиры", Color(0xFFFFB74D), caloriesStats.fats),
            createLine("Углеводы", Color(0xFF81C784), caloriesStats.carbs),
        )
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