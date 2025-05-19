package com.averito.firito.core.repositories

import com.averito.firito.core.models.food.FoodModel
import java.time.LocalDate

interface FoodRepository {
    suspend fun getAll(): Result<List<FoodModel>>
    suspend fun getByDate(date: LocalDate): Result<List<FoodModel>>
    suspend fun getById(id: Long): Result<FoodModel?>
    suspend fun create(foodModel: FoodModel): Result<Long>
    suspend fun update(foodModel: FoodModel): Result<Unit>
    suspend fun remove(foodModel: FoodModel): Result<Unit>
}