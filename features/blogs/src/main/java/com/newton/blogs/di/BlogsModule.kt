package com.newton.home.di

import com.newton.blogs.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object BlogsModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): BlogsNavigationApi {
        return BlogsNavigationApiImpl()
    }
}
