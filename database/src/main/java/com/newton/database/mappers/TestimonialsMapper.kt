package com.newton.database.mappers

import com.newton.core.domain.models.testimonials.Testimonials
import com.newton.core.domain.models.testimonials.TestimonialsData
import com.newton.database.entities.TestimonialsEntity

fun TestimonialsData.toDomainTestimonials(): TestimonialsEntity {
    return TestimonialsEntity(
        id = id,
        content = content,
        createdAt = created_at,
        rating = rating,
        status = status,
        user = user,
        userName = user_name
    )
}

fun TestimonialsEntity.toTestimonialsEntity(): TestimonialsData {
    return TestimonialsData(
        id = id,
        content = content,
        created_at = createdAt,
        rating = rating,
        status = status,
        user = user,
        user_name = userName
    )
}