package com.averito.firito.di

import com.averito.firito.core.interactors.FoodInteractor
import com.averito.firito.core.repositories.FoodRepository
import com.averito.firito.core.services.FoodService
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.daos.FoodDao
import com.averito.firito.data.interactors.food.FoodInteractorImpl
import com.averito.firito.data.repositories.food.FoodRepositoryImpl
import com.averito.firito.data.services.food.FoodServiceImpl
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodModule {
    @Provides
    @Singleton
    fun provideFoodRepositoryImpl(dao: FoodDao): FoodRepository {
        return FoodRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideFoodServiceImpl(
        repository: FoodRepository,
        @DefaultAppLogger defaultAppLogger: AppLogger
    ): FoodService {
        return FoodServiceImpl(repository, defaultAppLogger)
    }

    @Provides
    @Singleton
    fun provideFoodInteractorImpl(
        service: FoodService,
    ): FoodInteractor {
        return FoodInteractorImpl(service)
    }
}