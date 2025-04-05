package com.newton.account.di

import com.newton.core.data.remote.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import retrofit2.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object TestimonialsApiModule {
    @Provides
    @Singleton
    fun provideTestimonialApi(retrofit: Retrofit): TestimonialsService {
        return retrofit.create(TestimonialsService::class.java)
    }
}
