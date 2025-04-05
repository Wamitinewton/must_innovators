package com.newton.communities.di

import com.newton.core.data.remote.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import retrofit2.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AboutUsApiModule {
    @Provides
    @Singleton
    fun provideAboutUsApi(retrofit: Retrofit): AboutClubService = retrofit.create(AboutClubService::class.java)
}
