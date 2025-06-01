package com.averito.firito.ui.screens.statistics.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.statistics.MacroStats
import com.averito.firito.ui.shared.ui.app_month_selector.AppMonthSelector
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie
import java.time.YearMonth

@Composable
fun StatisticsScreenContent(
    selectedDate: YearMonth,
    macroStats: MacroStats,
    updateDate: (YearMonth) -> Unit,
    toStatisticsDetail: (AppNavGraphRoutes.StatisticsCategory.Category) -> Unit
) {
    val isEmptyStats = macroStats.proteins <= 0f && macroStats.fats <= 0f && macroStats.carbs <= 0f

    fun createPieDataFrom(stats: MacroStats): List<Pie> {
        return listOf(
            Pie(
                label = "Белки",
                data = stats.proteins.toDouble(),
                color = Color(0xFF80CBC4),
                selectedColor = Color(0xFF4DB6AC)
            ),
            Pie(
                label = "Жиры",
                data = stats.fats.toDouble(),
                color = Color(0xFFFFCC80),
                selectedColor = Color(0xFFFFB74D)
            ),
            Pie(
                label = "Углеводы",
                data = stats.carbs.toDouble(),
                color = Color(0xFFA5D6A7),
                selectedColor = Color(0xFF81C784)
            )
        )
    }

    var data by remember {
        mutableStateOf(
            createPieDataFrom(macroStats)
        )
    }

    LaunchedEffect(macroStats) {
        data = createPieDataFrom(macroStats)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AppMonthSelector(
                    selectedDate = selectedDate,
                    updateDate = updateDate
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    isEmptyStats -> {
                        Text(
                            text = "Недостаточно данных",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    else -> {
                        Box(
                            modifier = Modifier
                                .size(350.dp)
                                .padding(bottom = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            PieChart(
                                modifier = Modifier.size(300.dp),
                                data = data,
                                onPieClick = {
                                    val pieIndex = data.indexOf(it)
                                    data = data.mapIndexed { index, pie ->
                                        pie.copy(selected = pieIndex == index && !pie.selected)
                                    }
                                },
                                selectedScale = 1.2f,
                                scaleAnimEnterSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                colorAnimEnterSpec = tween(300),
                                colorAnimExitSpec = tween(300),
                                scaleAnimExitSpec = tween(300),
                                spaceDegreeAnimExitSpec = tween(300),
                                style = Pie.Style.Fill
                            )

                            val total = data.sumOf { it.data }
                            val selected = data.firstOrNull { it.selected }
                            val percent = selected?.let { (it.data / total * 100).toInt() } ?: 0

                            Surface(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                shape = MaterialTheme.shapes.small,
                                tonalElevation = 4.dp
                            ) {
                                Text(
                                    text = selected?.let { "${it.label}: ${it.data.toInt()} г ($percent%)" } ?: "Нажмите на сектор",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            data.forEach { pie ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .padding(end = 4.dp)
                                            .background(pie.color, shape = MaterialTheme.shapes.small)
                                    )
                                    Text(
                                        text = pie.label ?: "",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }



        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                HorizontalDivider(modifier = Modifier.height(1.dp))
                StatisticsCategoryListItem(
                    title = "Активность",
                    subtitle = "Шаги и расстояние",
                    onClick = { toStatisticsDetail(AppNavGraphRoutes.StatisticsCategory.Category.ACTIVITY) }
                )
                HorizontalDivider(modifier = Modifier.height(1.dp))
                StatisticsCategoryListItem(
                    title = "Калории и БЖУ",
                    subtitle = "Потреблённые калории, белки, жиры и углеводы",
                    onClick = { toStatisticsDetail(AppNavGraphRoutes.StatisticsCategory.Category.CALORIES) }
                )
                HorizontalDivider(modifier = Modifier.height(1.dp))
            }
        }
    }
}
