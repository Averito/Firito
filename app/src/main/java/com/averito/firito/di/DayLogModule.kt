package com.averito.firito.di

import com.averito.firito.core.interactors.DayLogInteractor
import com.averito.firito.core.interactors.FoodInteractor
import com.averito.firito.core.repositories.DayLogRepository
import com.averito.firito.core.services.DayLogService
import com.averito.firito.core.services.HealthConnectService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.database.daos.DayLogDao
import com.averito.firito.data.interactors.day_log.DayLogInteractorImpl
import com.averito.firito.data.repositories.day_log.DayLogRepositoryImpl
import com.averito.firito.data.services.daylog.DayLogServiceImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DayLogModule {
    @Provides
    @Singleton
    fun provideDayLogRepositoryImpl(dao: DayLogDao): DayLogRepository {
        return DayLogRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideDayLogServiceImpl(
        repository: DayLogRepository,
        @DefaultAppLogger defaultAppLogger: AppLogger
    ): DayLogService {
        return DayLogServiceImpl(repository, defaultAppLogger)
    }

    @Provides
    @Singleton
    fun provideDayLogInteractorImpl(
        service: DayLogService,
        foodInteractor: FoodInteractor,
        healthConnectService: HealthConnectService,
        @DefaultAppLogger defaultAppLogger: AppLogger
    ): DayLogInteractor {
        return DayLogInteractorImpl(service, foodInteractor, healthConnectService, defaultAppLogger)
    }
}