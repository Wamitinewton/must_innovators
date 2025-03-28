package com.newton.account.di

import com.newton.core.data.remote.TestimonialsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestimonialsApiModule {
    @Provides
    @Singleton
    fun provideTestimonialApi(retrofit: Retrofit): TestimonialsService {
        return retrofit.create(TestimonialsService::class.java)
    }
}