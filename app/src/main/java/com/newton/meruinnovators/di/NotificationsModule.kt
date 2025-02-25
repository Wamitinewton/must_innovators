package com.newton.meruinnovators.di

import com.newton.core.notification_service.NotificationService
import com.newton.meruinnovators.notification.AndroidNotificationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationsModule {

    @Binds
    @Singleton
    abstract fun bindNotificationService(
        impl: AndroidNotificationService
    ): NotificationService
}