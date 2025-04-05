package com.newton.core.domain.repositories

import com.newton.core.domain.models.homeModels.*
import com.newton.core.domain.models.testimonials.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*

interface HomeRepository {
    suspend fun getPartners(): Flow<Resource<List<PartnersData>>>

    suspend fun getTestimonials(): Flow<Resource<List<TestimonialsData>>>
}
