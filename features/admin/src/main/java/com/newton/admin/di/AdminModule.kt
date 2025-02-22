package com.newton.admin.di

import com.newton.admin.data.remote.EventApi
import com.newton.admin.data.repository.AdminRepositoryImpl
import com.newton.admin.domain.repository.AdminRepository
import com.newton.admin.navigation.AdminNavigationApi
import com.newton.admin.navigation.AdminNavigationApiImpl
import com.newton.database.dao.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdminModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): AdminNavigationApi {
        return AdminNavigationApiImpl()
    }

    @Provides
    @Singleton
    fun providesEventApi(retrofit: Retrofit): EventApi {
        return retrofit.create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        eventApi: EventApi,
        eventDao:EventDao
    ): AdminRepository = AdminRepositoryImpl(eventApi,eventDao)

}