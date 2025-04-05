package com.newton.communities.di

import com.newton.communities.data.repository.*
import com.newton.core.data.remote.*
import com.newton.core.domain.repositories.*
import com.newton.database.dao.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

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
    fun provideExecutiveRepository(executiveApi: AboutClubService): ExecutiveRepository =
        ExecutiveRepositoryImpl(executiveApi)
}
