package com.newton.feedback.di

import com.newton.feedback.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object UserFeedbackNavigationModule {
    @Provides
    @Singleton
    fun provideUserFeedbackNavigationApi(): FeedbackNavigationApi {
        return FeedbackNavigationImpl()
    }
}
