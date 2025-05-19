package com.averito.firito.core.models.food

import java.time.LocalDate
import java.time.LocalDateTime

interface FoodModel {
    val id: Long
    val date: LocalDate
    val name: String
    val calories: Int
    val proteins: Float
    val fats: Float
    val carbs: Float
    val weightInGrams: Int
    val description: String?
    val createdAt: LocalDateTime
}