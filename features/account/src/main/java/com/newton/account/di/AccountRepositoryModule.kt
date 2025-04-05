package com.newton.account.di

import com.newton.account.data.repository.*
import com.newton.core.data.remote.*
import com.newton.core.domain.repositories.*
import com.newton.database.dao.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AccountRepositoryModule {
    @Provides
    @Singleton
    fun provideAccountRepository(
        userDao: UserDao,
        authService: AuthService
    ): UpdateUserRepository = UpdateUserRepositoryImpl(userDao, authService)

    @Provides
    @Singleton
    fun provideTestimonialRepository(
        testimonialsService: TestimonialsService,
        testimonialsDao: TestimonialsDao
    ): TestimonialsRepository = TestimonialRepositoryImpl(testimonialsService, testimonialsDao)
}
