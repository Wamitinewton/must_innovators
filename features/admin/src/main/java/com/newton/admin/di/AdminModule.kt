package com.newton.admin.di

import com.newton.admin.data.repository.*
import com.newton.admin.navigation.*
import com.newton.core.data.remote.*
import com.newton.core.domain.repositories.*
import com.newton.database.dao.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import retrofit2.*
import javax.inject.*

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
        eventDao: EventDao,
        userFeedbackDao: UserFeedbackDao,
        eventsFeedbackDao: EventsFeedbackDao,
        partnersDao: PartnersDao
    ): AdminRepository =
        AdminRepositoryImpl(eventApi, eventDao, userFeedbackDao, eventsFeedbackDao, partnersDao)
}
