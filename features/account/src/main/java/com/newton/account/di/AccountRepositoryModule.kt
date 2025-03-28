package com.newton.account.di

import com.newton.account.data.repository.TestimonialRepositoryImpl
import com.newton.account.data.repository.UpdateUserRepositoryImpl
import com.newton.core.data.remote.AuthService
import com.newton.core.data.remote.TestimonialsService
import com.newton.core.domain.repositories.TestimonialsRepository
import com.newton.core.domain.repositories.UpdateUserRepository
import com.newton.database.dao.TestimonialsDao
import com.newton.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountRepositoryModule {

    @Provides
    @Singleton
    fun provideAccountRepository(
        userDao: UserDao,
        authService: AuthService
    ): UpdateUserRepository = UpdateUserRepositoryImpl(userDao ,authService)

    @Provides
    @Singleton
    fun provideTestimonialRepository(
        testimonialsService: TestimonialsService,
        testimonialsDao: TestimonialsDao
    ): TestimonialsRepository = TestimonialRepositoryImpl(testimonialsService, testimonialsDao)
}