package com.averito.firito.di

import android.content.Context
import androidx.room.Room
import com.averito.firito.data.daos.DayLogDao
import com.averito.firito.data.daos.FoodDao
import com.averito.firito.data.database.AppDatabase
import com.averito.firito.data.database.AppDatabaseHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        val instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "firito_db"
            )
            .fallbackToDestructiveMigration(false)
            .build()

        AppDatabaseHolder.db = instance
        return instance
    }

    @Provides
    fun provideFoodDao(
        db: AppDatabase
    ): FoodDao = db.foodDao()

    @Provides
    fun provideDayLogDao(
        db: AppDatabase
    ): DayLogDao = db.dayLogDao()
}
