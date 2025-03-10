package com.newton.feedback.di

import com.newton.feedback.navigation.FeedbackNavigationApi
import com.newton.feedback.navigation.FeedbackNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserFeedbackNavigationModule {
    @Provides
    @Singleton
    fun provideUserFeedbackNavigationApi(): FeedbackNavigationApi {
        return FeedbackNavigationImpl()
    }
}