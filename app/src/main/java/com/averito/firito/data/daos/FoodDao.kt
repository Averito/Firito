package com.averito.firito.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.averito.firito.data.models.food.FoodEntity
import java.time.LocalDate

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods ORDER BY createdAt DESC")
    suspend fun getAllFoods(): List<FoodEntity>

    @Query("SELECT * FROM foods WHERE date = :date")
    suspend fun getByDayDate(date: LocalDate): List<FoodEntity>

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getById(id: Long): FoodEntity?

    @Insert
    suspend fun insert(food: FoodEntity): Long

    @Update
    suspend fun update(food: FoodEntity): Unit

    @Delete
    suspend fun delete(foodModel: FoodEntity): Unit
}
