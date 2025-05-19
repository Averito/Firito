package com.averito.firito.ui.screens.food

import com.averito.firito.data.models.food.FoodModelImpl

fun FoodModelImpl.toUiModel() = FoodScreenUiStateFood(
    id,
    name,
    calories = calories.takeIf { it > 0 }?.toString() ?: "",
    proteins = proteins.takeIf { it > 0f }?.toString() ?: "",
    fats = fats.takeIf { it > 0f }?.toString() ?: "",
    carbs = carbs.takeIf { it > 0f }?.toString() ?: "",
    weightInGrams = weightInGrams.takeIf { it > 0 }?.toString() ?: "",
    description = description ?: "",
    date,
    createdAt
)

fun FoodScreenUiStateFood.toImpl() = FoodModelImpl(
    id,
    date,
    name,
    calories = calories.toIntOrNull() ?: 0,
    proteins = proteins.toFloatOrNull() ?: 0f,
    fats = fats.toFloatOrNull() ?: 0f,
    carbs = carbs.toFloatOrNull() ?: 0f,
    weightInGrams = weightInGrams.toIntOrNull() ?: 0,
    description = description.takeIf { !it.isEmpty() },
    createdAt
)