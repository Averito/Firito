package com.averito.firito.ui.screens.goals_category.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GoalsCategoryContentActivity(
    steps: String,
    updateSteps: (String) -> Unit,
    distance: String,
    updateDistance: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = steps,
            onValueChange = updateSteps,
            label = { Text("Цель по шагам") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
        )

        OutlinedTextField(
            value = distance,
            onValueChange = updateDistance,
            label = { Text("Цель по расстоянию (км)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        )
    }
}
