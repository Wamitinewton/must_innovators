package com.newton.events.di

import com.newton.events.data.repository.EventNotificationServiceImpl
import com.newton.events.domain.repository.EventNotificationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationServiceModule {
    @Binds
    abstract fun bindEventNotificationService(
        impl: EventNotificationServiceImpl
    ): EventNotificationService
}