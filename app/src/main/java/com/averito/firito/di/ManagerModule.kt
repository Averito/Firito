package com.averito.firito.di

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.managers.DatabaseBackupIntentManager
import com.averito.firito.data.managers.DatabaseBackupManager
import com.averito.firito.data.managers.HealthConnectManager
import com.averito.firito.data.managers.PermissionManager
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {
    @Provides
    @Singleton
    fun provideHealthConnectManager(
        @ApplicationContext context: Context,
        client: HealthConnectClient
    ): HealthConnectManager {
        return HealthConnectManager(context, client)
    }

    @Provides
    @Singleton
    fun providePermissionManager(
        @ApplicationContext context: Context,
    ): PermissionManager {
        return PermissionManager(context)
    }

    @Provides
    @Singleton
    fun provideDatabaseBackupManager(
        @ApplicationContext context: Context,
        @DefaultAppLogger defaultAppLogger: AppLogger,
    ): DatabaseBackupManager {
        return DatabaseBackupManager(context, defaultAppLogger)
    }

    @Provides
    @Singleton
    fun provideDatabaseBackupIntentManager(
        @DefaultAppLogger defaultAppLogger: AppLogger
    ): DatabaseBackupIntentManager {
        return DatabaseBackupIntentManager(defaultAppLogger)
    }
}