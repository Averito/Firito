package com.averito.firito.ui.screens.food.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.ui.screens.food.FoodScreenUiStateFood
import com.averito.firito.ui.shared.ui.app_food_card.AppFoodCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreenContent(
    uiStateFood: FoodScreenUiStateFood,
    updateUiStateFood: (String, String) -> Unit,
    foodTemplatesLoading: Boolean,
    foodTemplates: List<FoodModel>,
    filteredFoodTemplates: List<FoodModel>,
    foodTemplatesSearch: String,
    updateFoodTemplatesSearch: (String) -> Unit,
    loadFoodTemplates: suspend () -> Unit,
    fillFoodByTemplate: (FoodModel) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(sheetState.isVisible) {
        updateFoodTemplatesSearch("")
    }

    fun onSheetDismiss() {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    fun openFoodTemplateSelector() {
        coroutineScope.launch {
            sheetState.show()
            loadFoodTemplates()
        }
    }

    fun onClickFoodItem(foodTemplate: FoodModel) {
        fillFoodByTemplate(foodTemplate)
        onSheetDismiss()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiStateFood.name,
            onValueChange = { updateUiStateFood("name", it) },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiStateFood.calories,
            onValueChange = { updateUiStateFood("calories", it) },
            label = { Text("Калории") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = uiStateFood.proteins,
                onValueChange = { updateUiStateFood("proteins", it) },
                label = { Text("Белки") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = uiStateFood.fats,
                onValueChange = { updateUiStateFood("fats", it) },
                label = { Text("Жиры") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = uiStateFood.carbs,
                onValueChange = { updateUiStateFood("carbs", it) },
                label = { Text("Углеводы") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            "Калории и макросы указываются за 100 г продукта.",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = uiStateFood.weightInGrams,
            onValueChange = { updateUiStateFood("weightInGrams", it) },
            label = { Text("Фактический вес (г)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiStateFood.description,
            onValueChange = { updateUiStateFood("description", it) },
            label = { Text("Описание (необязательно)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            onClick = { openFoodTemplateSelector() }
        ) {
            Text("Выбрать из ранее добавленного")
        }

        if (sheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = { onSheetDismiss() },
                sheetState = sheetState
            ) {
                when {
                    foodTemplatesLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    foodTemplates.isEmpty() -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Шаблонов еды не обнаружено.")
                        }
                    }
                    else -> {
                        LazyColumn(modifier = Modifier.padding(8.dp)) {
                            item {
                                OutlinedTextField(
                                    value = foodTemplatesSearch,
                                    onValueChange = { updateFoodTemplatesSearch(it) },
                                    placeholder = { Text("Поиск") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp, bottom = 12.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedBorderColor = Color.Gray,
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        cursorColor = MaterialTheme.colorScheme.primary,
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    singleLine = true,
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Поиск",
                                            tint = Color.Gray
                                        )
                                    }
                                )
                            }

                            items(filteredFoodTemplates) { foodTemplate ->
                                AppFoodCard(foodModel = foodTemplate, onClick = { onClickFoodItem(foodTemplate) })
                            }
                        }
                    }
                }
            }
        }
    }
}
