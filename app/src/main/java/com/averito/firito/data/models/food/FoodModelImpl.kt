package com.averito.firito.data.models.food

import com.averito.firito.core.models.food.FoodModel
import java.time.LocalDate
import java.time.LocalDateTime

data class FoodModelImpl(
    override val id: Long = 0,
    override val date: LocalDate = LocalDate.now(),
    override val name: String = "",
    override val calories: Int = 0,
    override val proteins: Float = 0f,
    override val fats: Float = 0f,
    override val carbs: Float = 0f,
    override val weightInGrams: Int = 0,
    override val description: String? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now()
) : FoodModel
