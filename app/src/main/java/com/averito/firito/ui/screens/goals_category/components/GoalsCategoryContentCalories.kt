package com.averito.firito.ui.screens.goals_category.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun GoalsCategoryContentCalories(
    calories: String,
    updateCalories: (String) -> Unit,
    proteins: String,
    updateProteins: (String) -> Unit,
    fats: String,
    updateFats: (String) -> Unit,
    carbs: String,
    updateCarbs: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = calories,
            onValueChange = updateCalories,
            label = { Text("Цель по калориям") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = proteins,
            onValueChange = updateProteins,
            label = { Text("Цель по белкам (г)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            value = fats,
            onValueChange = updateFats,
            label = { Text("Цель по жирам (г)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            value = carbs,
            onValueChange = updateCarbs,
            label = { Text("Цель по углеводам (г)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )
    }
}
