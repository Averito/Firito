package com.averito.firito.core.interactors

import com.averito.firito.core.models.food.FoodModel
import java.time.LocalDate

interface FoodInteractor {
    suspend fun getFoodTemplates(): List<FoodModel>
    suspend fun getByDate(date: LocalDate): List<FoodModel>
    suspend fun getById(id: Long): FoodModel
    suspend fun create(foodModel: FoodModel): Long
    suspend fun update(foodModel: FoodModel): Unit
    suspend fun remove(foodModel: FoodModel): Unit
}