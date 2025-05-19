package com.averito.firito.ui.shared.app_food_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.data.models.food.FoodModelImpl
import java.time.format.DateTimeFormatter

@Composable
fun AppFoodCard(
    foodModel: FoodModel,
    onClick: (() -> Unit)? = null,
    onRemove: (() -> Unit)? = null,
    details: Boolean = false
) {
    val portion = foodModel.weightInGrams
    val totalCalories = foodModel.calories * portion / 100
    val totalProteins = foodModel.proteins * portion / 100f
    val totalFats = foodModel.fats * portion / 100f
    val totalCarbs = foodModel.carbs * portion / 100f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick?.invoke() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = foodModel.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "$totalCalories ккал • $portion г — съедено",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Б: ${"%.1f".format(totalProteins)} • Ж: ${"%.1f".format(totalFats)} • У: ${"%.1f".format(totalCarbs)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (details) {
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "${foodModel.calories} ккал • 100 г — на 100 г",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "Б: ${"%.1f".format(foodModel.proteins)} • Ж: ${"%.1f".format(foodModel.fats)} • У: ${"%.1f".format(foodModel.carbs)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                if (onRemove != null) {
                    IconButton(
                        onClick = { onRemove.invoke() },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Создано: ${foodModel.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm"))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppFoodCardPreview() {
    AppFoodCard(
        foodModel = FoodModelImpl(
            id = 1,
            name = "Чипсы",
            calories = 500,
            carbs = 100f,
            fats = 30f,
            proteins = 4f,
            weightInGrams = 125,
            description = null
        )
    )
}