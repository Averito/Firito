package com.averito.firito.di

import com.averito.firito.core.interactors.StatisticsInteractor
import com.averito.firito.core.services.DayLogService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.interactors.statistics.StatisticsInteractorImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {
    @Provides
    @Singleton
    fun provideStatisticsInteractor(
        @DefaultAppLogger defaultAppLogger: AppLogger,
        dayLogService: DayLogService
    ): StatisticsInteractor {
        return StatisticsInteractorImpl(defaultAppLogger, dayLogService)
    }
}