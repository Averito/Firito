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
import com.averito.firito.core.models.statistics.CaloriesStats
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import java.time.format.DateTimeFormatter

@Composable
fun StatisticsCategoryScreenContentCalories(caloriesStats: CaloriesStats) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surface

    var caloriesData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = caloriesStats.calories.map { it.value.toDouble() },
                    color = SolidColor(primary),
                    label = "Калории",
                    curvedEdges = caloriesStats.calories.size < 15,
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

    var bjuData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = caloriesStats.proteins.map { it.value.toDouble() },
                    color = SolidColor(Color(0xFF4DB6AC)),
                    label = "Белки",
                    curvedEdges = caloriesStats.proteins.size < 15,
                    dotProperties = DotProperties(
                        enabled = true,
                        radius = 2.dp,
                        strokeWidth = 2.dp,
                        color = SolidColor(surface),
                        strokeColor = SolidColor(Color(0xFF4DB6AC))
                    )
                ),
                Line(
                    values = caloriesStats.fats.map { it.value.toDouble() },
                    color = SolidColor(Color(0xFFFFB74D)),
                    label = "Жиры",
                    curvedEdges = caloriesStats.fats.size < 15,
                    dotProperties = DotProperties(
                        enabled = true,
                        radius = 2.dp,
                        strokeWidth = 2.dp,
                        color = SolidColor(surface),
                        strokeColor = SolidColor(Color(0xFFFFB74D))
                    )
                ),
                Line(
                    values = caloriesStats.carbs.map { it.value.toDouble() },
                    color = SolidColor(Color(0xFF81C784)),
                    label = "Углеводы",
                    curvedEdges = caloriesStats.carbs.size < 15,
                    dotProperties = DotProperties(
                        enabled = true,
                        radius = 2.dp,
                        strokeWidth = 2.dp,
                        color = SolidColor(surface),
                        strokeColor = SolidColor(Color(0xFF81C784))
                    )
                )
            )
        )
    }

    LaunchedEffect(caloriesStats) {
        caloriesData = listOf(
            Line(
                values = caloriesStats.calories.map { it.value.toDouble() },
                color = SolidColor(primary),
                label = "Калории",
                curvedEdges = caloriesStats.calories.size < 15,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = 2.dp,
                    strokeWidth = 2.dp,
                    color = SolidColor(surface),
                    strokeColor = SolidColor(primary)
                )
            )
        )
        bjuData = listOf(
            Line(
                values = caloriesStats.proteins.map { it.value.toDouble() },
                color = SolidColor(Color(0xFF4DB6AC)),
                label = "Белки",
                curvedEdges = caloriesStats.proteins.size < 15,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = 2.dp,
                    strokeWidth = 2.dp,
                    color = SolidColor(surface),
                    strokeColor = SolidColor(Color(0xFF4DB6AC))
                )
            ),
            Line(
                values = caloriesStats.fats.map { it.value.toDouble() },
                color = SolidColor(Color(0xFFFFB74D)),
                label = "Жиры",
                curvedEdges = caloriesStats.fats.size < 15,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = 2.dp,
                    strokeWidth = 2.dp,
                    color = SolidColor(surface),
                    strokeColor = SolidColor(Color(0xFFFFB74D))
                )
            ),
            Line(
                values = caloriesStats.carbs.map { it.value.toDouble() },
                color = SolidColor(Color(0xFF81C784)),
                label = "Углеводы",
                curvedEdges = caloriesStats.carbs.size < 15,
                dotProperties = DotProperties(
                    enabled = true,
                    radius = 2.dp,
                    strokeWidth = 2.dp,
                    color = SolidColor(surface),
                    strokeColor = SolidColor(Color(0xFF81C784))
                )
            )
        )
    }

    StatChartBlock(
        title = "Калории",
        data = caloriesData,
        labels = caloriesStats.calories.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(caloriesStats.caloriesStats)
    )

    Divider(modifier = Modifier.height(12.dp), color = Color.Transparent)

    StatChartBlock(
        title = "БЖУ (г)",
        data = bjuData,
        labels = caloriesStats.proteins.map { it.date.format(DateTimeFormatter.ofPattern("dd")) },
        stats = listOf(
            caloriesStats.proteinsStats,
            caloriesStats.fatsStats,
            caloriesStats.carbsStats
        )
    )
}