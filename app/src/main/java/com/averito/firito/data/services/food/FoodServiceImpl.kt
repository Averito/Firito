package com.averito.firito.data.services.food

import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.core.repositories.FoodRepository
import com.averito.firito.core.services.FoodService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.models.food.FoodModelImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import java.time.LocalDate
import javax.inject.Inject

class FoodServiceImpl @Inject constructor(
    private val foodRepository: FoodRepository,
    @DefaultAppLogger private val defaultAppLogger: AppLogger
) : FoodService {
    private val name = "FoodServiceImpl"
    override suspend fun getAll(): List<FoodModel> {
        defaultAppLogger.debug("$name: Получение всех данных о еде.")
        val result = foodRepository.getAll()

        if (result.isFailure) {
            defaultAppLogger.error("$name: getAll: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: emptyList()
    }

    override suspend fun getByDate(date: LocalDate): List<FoodModel> {
        defaultAppLogger.debug("$name: Получение данных о еде по дате $date.")
        val result = foodRepository.getByDate(date)

        if (result.isFailure) {
            defaultAppLogger.error("$name: getByDayLog: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: emptyList()
    }

    override suspend fun getById(id: Long): FoodModel {
        defaultAppLogger.debug("$name: Получение данных о еде по id $id.")
        val result = foodRepository.getById(id)

        if (result.isFailure) {
            defaultAppLogger.error("$name: getById: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: FoodModelImpl()
    }

    override suspend fun create(foodModel: FoodModel): Long {
        defaultAppLogger.debug("$name: Создание новых данных о еде (id = ${foodModel.id}; name = ${foodModel.name}).")

        if (!foodModel.date.isEqual(LocalDate.now())) {
            defaultAppLogger.error("$name: create: Создание возможно только в текущем дне.")
        }

        val result = foodRepository.create(foodModel)

        if (result.isFailure) {
            defaultAppLogger.error("$name: create: ${result.exceptionOrNull()?.message}")
        }

        return result.getOrNull() ?: 0
    }

    override suspend fun update(foodModel: FoodModel): Unit {
        defaultAppLogger.debug("$name: Обновление данных о еде (id = ${foodModel.id}; name = ${foodModel.name}).")

        if (foodModel.date.isBefore(LocalDate.now())) {
            defaultAppLogger.error("$name: update: Редактировать можно только текущий день.")
            return
        }

        val result = foodRepository.update(foodModel)

        if (result.isFailure) {
            defaultAppLogger.error("$name: update: ${result.exceptionOrNull()?.message}")
        }
    }

    override suspend fun remove(foodModel: FoodModel): Unit {
        defaultAppLogger.debug("$name: Удаление данных о еде (id = ${foodModel.id}; name = ${foodModel.name}; date = ${foodModel.date}).")

        if (foodModel.date.isBefore(LocalDate.now())) {
            defaultAppLogger.error("$name: remove: Удаление возможно только в текущий день.")
            return
        }

        val result = foodRepository.remove(foodModel)

        if (result.isFailure) {
            defaultAppLogger.error("$name: remove: ${result.exceptionOrNull()?.message}")
        }
    }
}