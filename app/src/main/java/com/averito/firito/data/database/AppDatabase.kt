package com.averito.firito.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.averito.firito.data.daos.DayLogDao
import com.averito.firito.data.daos.FoodDao
import com.averito.firito.data.models.day_log.DayLogEntity
import com.averito.firito.data.models.food.FoodEntity

@Database(
    entities = [FoodEntity::class, DayLogEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(AppDatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun dayLogDao(): DayLogDao
}