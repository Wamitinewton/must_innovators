package com.newton.home.di

import com.newton.core.data.remote.HomeApiService
import com.newton.core.domain.repositories.HomeRepository
import com.newton.database.dao.PartnersDao
import com.newton.home.data.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        partnersService: HomeApiService,
        partnersDao: PartnersDao
    ): HomeRepository = HomeRepositoryImpl(partnersService)
}