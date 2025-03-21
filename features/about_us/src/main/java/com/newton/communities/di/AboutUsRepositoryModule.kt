package com.newton.communities.di

import com.newton.communities.data.repository.CommunityRepositoryImpl
import com.newton.communities.data.repository.ExecutiveRepositoryImpl
import com.newton.core.data.remote.AboutClubService
import com.newton.core.domain.repositories.CommunityRepository
import com.newton.core.domain.repositories.ExecutiveRepository
import com.newton.database.dao.ClubBioDao
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
        aboutUsApi: AboutClubService,
        clubBioDao: ClubBioDao
    ): CommunityRepository = CommunityRepositoryImpl(aboutUsApi, clubBioDao)

    @Provides
    @Singleton
    fun provideExecutiveRepository(
        executiveApi: AboutClubService,
    ): ExecutiveRepository = ExecutiveRepositoryImpl(executiveApi)

}