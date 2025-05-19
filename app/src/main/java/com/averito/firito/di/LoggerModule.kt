package com.averito.firito.di

import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.loggers.DefaultAppLoggerImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
    @Provides
    @Singleton
    @DefaultAppLogger
    fun provideDefaultAppLoggerImpl(): AppLogger {
        return DefaultAppLoggerImpl()
    }
}