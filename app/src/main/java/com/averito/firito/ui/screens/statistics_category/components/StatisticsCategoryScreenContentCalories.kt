package com.averito.firito.ui.screens.statistics_category.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun StatisticsCategoryScreenContentCalories(caloriesStats: CaloriesStats) {
    val primary = MaterialTheme.colorScheme.primary

    var caloriesData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = caloriesStats.calories.map { it.toDouble() },
                    color = SolidColor(primary),
                    label = "Калории"
                )
            )
        )
    }

    var bjuData by remember {
        mutableStateOf(
            listOf(
                Line(
                    values = caloriesStats.proteins.map { it.toDouble() },
                    color = SolidColor(Color(0xFF4DB6AC)),
                    label = "Белки"
                ),
                Line(
                    values = caloriesStats.fats.map { it.toDouble() },
                    color = SolidColor(Color(0xFFFFB74D)),
                    label = "Жиры"
                ),
                Line(
                    values = caloriesStats.carbs.map { it.toDouble() },
                    color = SolidColor(Color(0xFF81C784)),
                    label = "Углеводы"
                )
            )
        )
    }

    LaunchedEffect(caloriesStats) {
        caloriesData = listOf(
            Line(
                values = caloriesStats.calories.map { it.toDouble() },
                color = SolidColor(primary),
                label = "Калории"
            )
        )
        bjuData = listOf(
            Line(
                values = caloriesStats.proteins.map { it.toDouble() },
                color = SolidColor(Color(0xFF4DB6AC)),
                label = "Белки"
            ),
            Line(
                values = caloriesStats.fats.map { it.toDouble() },
                color = SolidColor(Color(0xFFFFB74D)),
                label = "Жиры"
            ),
            Line(
                values = caloriesStats.carbs.map { it.toDouble() },
                color = SolidColor(Color(0xFF81C784)),
                label = "Углеводы"
            )
        )
    }

    StatChartBlock(
        title = "Калории",
        data = caloriesData,
        stats = listOf(caloriesStats.caloriesStats)
    )

    Divider(modifier = Modifier.height(12.dp))

    StatChartBlock(
        title = "БЖУ (г)",
        data = bjuData,
        stats = listOf(
            caloriesStats.proteinsStats,
            caloriesStats.fatsStats,
            caloriesStats.carbsStats
        )
    )
}