package com.averito.firito.di

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.averito.firito.core.repositories.HealthConnectRepository
import com.averito.firito.core.services.HealthConnectService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.repositories.health.HealthConnectRepositoryImpl
import com.averito.firito.data.services.health.HealthConnectServiceImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HealthConnectModule {

    @Provides
    @Singleton
    fun provideHealthConnectClient(@ApplicationContext context: Context): HealthConnectClient {
        return HealthConnectClient.getOrCreate(context)
    }

    @Provides
    @Singleton
    fun provideHealthConnectRepository(
        client: HealthConnectClient,
    ): HealthConnectRepository {
        return HealthConnectRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideHealthConnectService(
        repository: HealthConnectRepository,
        @DefaultAppLogger logger: AppLogger
    ): HealthConnectService {
        return HealthConnectServiceImpl(repository, logger)
    }
}
