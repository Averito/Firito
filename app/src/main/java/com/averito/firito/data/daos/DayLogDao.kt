package com.averito.firito.data.daos

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
    @Query("SELECT * FROM logs WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: LocalDate): DayLogEntity?

    @Query("""
        SELECT * FROM logs 
        WHERE strftime('%m', date) = :month AND strftime('%Y', date) = :year
        ORDER BY date ASC
    """)
    suspend fun getByMonthYear(month: String, year: String): List<DayLogEntity>

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
