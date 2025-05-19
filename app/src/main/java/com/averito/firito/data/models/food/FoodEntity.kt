package com.averito.firito.data.models.food

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.averito.firito.core.models.food.FoodModel
import com.averito.firito.data.models.day_log.DayLogEntity
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "foods",
    foreignKeys = [
        ForeignKey(
            entity = DayLogEntity::class,
            parentColumns = ["date"],
            childColumns = ["date"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("date")]
)
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    override val date: LocalDate,
    override val name: String,
    override val calories: Int,
    override val proteins: Float,
    override val fats: Float,
    override val carbs: Float,
    override val weightInGrams: Int,
    override val description: String?,
    override val createdAt: LocalDateTime
) : FoodModel