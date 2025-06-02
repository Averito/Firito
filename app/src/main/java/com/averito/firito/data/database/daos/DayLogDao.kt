package com.averito.firito.data.database.daos

import com.averito.firito.data.models.day_log.DayLogWithFoodsEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.averito.firito.data.models.day_log.DayLogEntity
import java.time.LocalDate

@Dao
interface DayLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dayLog: DayLogEntity): Unit

    @Update
    suspend fun update(dayLog: DayLogEntity): Unit

    @Transaction
    @Query("SELECT * FROM logs WHERE date = :date LIMIT 1")
    suspend fun getWithFoodsByDate(date: LocalDate): DayLogWithFoodsEntity?

    @Transaction
    @Query("""
        SELECT * FROM logs
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)
    suspend fun getDayLogsWithFoodsBetween(startDate: LocalDate, endDate: LocalDate): List<DayLogWithFoodsEntity>
}
