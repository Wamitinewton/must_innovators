package com.newton.communities.di

import com.newton.communities.data.remote.AboutUsApi
import com.newton.communities.data.repository.CommunityRepositoryImpl
import com.newton.communities.domain.repository.CommunityRepository
import com.newton.database.dao.CommunityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

}