package com.averito.firito.data.interactors.food

import androidx.compose.ui.text.toLowerCase
import com.averito.firito.core.interactors.FoodInteractor
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.core.services.FoodService
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

class FoodInteractorImpl @Inject constructor(
    private val foodService: FoodService,
) : FoodInteractor {
    override suspend fun getFoodTemplates(): List<FoodModel> {
        val allFoods = foodService.getAll()
        return allFoods.filter { !it.name.isEmpty() }.distinctBy { it.name.lowercase(Locale.ROOT) }
    }

    override suspend fun getByDate(date: LocalDate): List<FoodModel> {
        return foodService.getByDate(date)
    }

    override suspend fun getById(id: Long): FoodModel {
        return foodService.getById(id)
    }

    override suspend fun create(foodModel: FoodModel): Long {
        return foodService.create(foodModel)
    }

    override suspend fun update(foodModel: FoodModel): Unit {
        foodService.update(foodModel)
    }

    override suspend fun remove(foodModel: FoodModel): Unit {
        foodService.remove(foodModel)
    }
}