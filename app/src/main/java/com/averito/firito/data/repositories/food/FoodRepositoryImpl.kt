package com.averito.firito.data.repositories.food

import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.core.repositories.FoodRepository
import com.averito.firito.data.database.daos.FoodDao
import com.averito.firito.data.mappers.toEntity
import java.time.LocalDate
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(private val foodDao: FoodDao) : FoodRepository {
    override suspend fun getAll(): Result<List<FoodModel>> {
        try {
            val foods = foodDao.getAllFoods()
            return Result.success(foods)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getByDate(date: LocalDate): Result<List<FoodModel>> {
        try {
            val foodModels = foodDao.getByDayDate(date)
            return Result.success(foodModels)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun getById(id: Long): Result<FoodModel?> {
        try {
            val foodModels = foodDao.getById(id)
            return Result.success(foodModels)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun create(foodModel: FoodModel): Result<Long> {
        try {
            val foodId = foodDao.insert(foodModel.toEntity())
            return Result.success(foodId)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun update(foodModel: FoodModel): Result<Unit> {
        try {
            foodDao.update(foodModel.toEntity())
            return Result.success(Unit)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }

    override suspend fun remove(foodModel: FoodModel): Result<Unit> {
        try {
            foodDao.delete(foodModel.toEntity())
            return Result.success(Unit)
        } catch (error: Exception) {
            return Result.failure(error)
        }
    }
}