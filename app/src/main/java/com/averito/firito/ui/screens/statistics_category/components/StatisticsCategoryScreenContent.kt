package com.averito.firito.ui.screens.statistics_category.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.statistics.ActivityStats
import com.averito.firito.core.models.statistics.CaloriesStats
import com.averito.firito.ui.shared.ui.app_month_selector.AppMonthSelector
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraphRoutes
import java.time.YearMonth

@Composable
fun StatisticsCategoryScreenContent(
    selectedDate: YearMonth,
    updateDate: (YearMonth) -> Unit,
    activityStats: ActivityStats,
    caloriesStats: CaloriesStats,
    category: AppNavGraphRoutes.StatisticsCategory.Category,
    hasData: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
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

        if (!hasData) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет данных за выбранный месяц",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            return@LazyColumn
        }

        when (category) {
            AppNavGraphRoutes.StatisticsCategory.Category.ACTIVITY -> {
                item {
                    StatisticsCategoryScreenContentActivity(activityStats)
                }
            }

            AppNavGraphRoutes.StatisticsCategory.Category.CALORIES -> {
                item {
                    StatisticsCategoryScreenContentCalories(caloriesStats)
                }
            }
        }
    }
}
