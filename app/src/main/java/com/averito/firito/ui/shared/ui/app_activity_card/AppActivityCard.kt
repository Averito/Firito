package com.averito.firito.ui.shared.ui.app_activity_card

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
fun AppActivityCard(steps: Int, distance: Double, stepsGoal: Int, distanceGoal: Double) {
    val stepsCompleted = steps >= stepsGoal
    val distanceCompleted = distance >= distanceGoal

    val successColor = MaterialTheme.colorScheme.tertiary
    val defaultColor = MaterialTheme.colorScheme.primary

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Физическая активность", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if (stepsGoal > 0) {
                Text(
                    "Шаги: $steps / $stepsGoal",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                AppLinearProgressIndicator(
                    currentValue = steps.toFloat(),
                    maxValue = stepsGoal.toFloat(),
                    modifier = Modifier.fillMaxWidth(),
                    color = if (stepsCompleted) successColor else defaultColor
                )
                Spacer(Modifier.height(12.dp))
            } else {
                Text("Шаги: $steps")
            }

            if (distanceGoal > 0) {
                Text(
                    "Дистанция: %.2f / %.2f км".format(distance, distanceGoal),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                AppLinearProgressIndicator(
                    currentValue = distance.toFloat(),
                    maxValue = distanceGoal.toFloat(),
                    modifier = Modifier.fillMaxWidth(),
                    color = if (distanceCompleted) successColor else defaultColor
                )
            } else {
                Text("Дистанция: %.2f км".format(distance))
            }
        }
    }
}
