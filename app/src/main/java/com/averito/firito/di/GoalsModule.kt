package com.averito.firito.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.averito.firito.core.interactors.GoalsInteractor
import com.averito.firito.core.repositories.GoalsRepository
import com.averito.firito.core.services.GoalsService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.interactors.goals.GoalsInteractorImpl
import com.averito.firito.data.repositories.goals.GoalsRepositoryImpl
import com.averito.firito.data.services.goals.GoalsServiceImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoalsModule {
    @Singleton
    @Provides
    fun provideGoalsRepository(
        dataStore: DataStore<Preferences>
    ): GoalsRepository {
        return GoalsRepositoryImpl(dataStore)
    }

    @Singleton
    @Provides
    fun provideGoalsService(
        @DefaultAppLogger defaultAppLogger: AppLogger,
        goalsRepository: GoalsRepository
    ): GoalsService {
        return GoalsServiceImpl(defaultAppLogger, goalsRepository)
    }

    @Singleton
    @Provides
    fun provideGoalsInteractor(
        goalsService: GoalsService
    ): GoalsInteractor {
        return GoalsInteractorImpl(goalsService)
    }
}
