package com.newton.home.di

import com.newton.core.data.remote.*
import com.newton.core.domain.repositories.*
import com.newton.home.data.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {
    @Provides
    @Singleton
    fun provideHomeRepository(
        partnersService: HomeApiService
//        partnersDao: PartnersDao
    ): HomeRepository = HomeRepositoryImpl(partnersService)
}
