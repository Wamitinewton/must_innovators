package com.newton.home.di

import com.newton.core.data.remote.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import retrofit2.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object ProvideHomeApiService {
    @Provides
    @Singleton
    fun provideHomeApiService(retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }
}
