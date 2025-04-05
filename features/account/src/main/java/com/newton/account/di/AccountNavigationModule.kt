package com.newton.account.di

import com.newton.account.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AccountNavigationModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): AccountNavigationApi {
        return AccountNavigationApiImpl()
    }
}
