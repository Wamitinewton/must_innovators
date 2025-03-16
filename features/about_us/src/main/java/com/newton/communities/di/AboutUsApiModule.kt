package com.newton.communities.di

import com.newton.core.data.remote.AboutClubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AboutUsApiModule {
    @Provides
    @Singleton
    fun provideAboutUsApi(retrofit: Retrofit): AboutClubService {
        return retrofit.create(AboutClubService::class.java)
    }
}