package com.averito.firito.ui.shared.ui.app_calories_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.averito.firito.ui.shared.ui.app_linear_progress_inidicator.AppLinearProgressIndicator

@Composable
fun AppCaloriesCard(
    activeCalories: Int,
    totalCalories: Int,
    goalCalories: Int
) {
    val exceeded = totalCalories > goalCalories

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Калории", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Text("Активные: $activeCalories ккал")

            if (goalCalories > 0) {
                Text(
                    "Съедено: $totalCalories / $goalCalories ккал",
                    color = if (exceeded) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(4.dp))

                AppLinearProgressIndicator(
                    currentValue = totalCalories.toFloat(),
                    maxValue = goalCalories.toFloat(),
                    modifier = Modifier.fillMaxWidth(),
                    color = if (exceeded) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            } else {
                Text("Съедено: $totalCalories ккал")
            }
        }
    }
}
