package com.newton.admin.di

import com.newton.admin.data.remote.AdminApi
import com.newton.admin.data.repository.AdminRepositoryImpl
import com.newton.core.domain.repositories.AdminRepository
import com.newton.admin.navigation.AdminNavigationApi
import com.newton.admin.navigation.AdminNavigationApiImpl
import com.newton.database.dao.EventDao
import com.newton.database.dao.EventsFeedbackDao
import com.newton.database.dao.PartnersDao
import com.newton.database.dao.UserFeedbackDao
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
    fun providesEventApi(retrofit: Retrofit): AdminApi {
        return retrofit.create(AdminApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        eventApi: AdminApi,
        eventDao:EventDao,
        userFeedbackDao:UserFeedbackDao,
        eventsFeedbackDao:EventsFeedbackDao,
        partnersDao: PartnersDao
    ): AdminRepository = AdminRepositoryImpl(eventApi,eventDao,userFeedbackDao, eventsFeedbackDao,partnersDao)

}