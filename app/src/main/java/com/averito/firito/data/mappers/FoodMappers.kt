package com.averito.firito.data.mappers

import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.data.models.food.FoodEntity
import com.averito.firito.data.models.food.FoodModelImpl

fun FoodModel.toEntity() = FoodEntity(id, date, name, calories, proteins, fats, carbs, weightInGrams, description, createdAt)
fun FoodModel.toImpl() = FoodModelImpl(id, date, name, calories, proteins, fats, carbs, weightInGrams, description, createdAt)
