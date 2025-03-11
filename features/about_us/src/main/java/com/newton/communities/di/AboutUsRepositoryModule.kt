package com.newton.communities.di

import com.newton.core.data.remote.AboutUsApi
import com.newton.communities.data.repository.CommunityRepositoryImpl
import com.newton.communities.data.repository.ExecutiveRepositoryImpl
import com.newton.core.domain.repositories.CommunityRepository
import com.newton.core.domain.repositories.ExecutiveRepository
import com.newton.database.dao.CommunityDao
import com.newton.database.dao.ExecutiveDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AboutUsRepositoryModule {
    @Provides
    @Singleton
    fun provideAboutUsRepository(
        aboutUsApi: AboutUsApi,
        dao: CommunityDao,
    ): CommunityRepository = CommunityRepositoryImpl(aboutUsApi, dao)

    @Provides
    @Singleton
    fun provideExecutiveRepository(
        executiveApi: AboutUsApi,
        executiveDao: ExecutiveDao
    ): ExecutiveRepository = ExecutiveRepositoryImpl(executiveApi, executiveDao)

}