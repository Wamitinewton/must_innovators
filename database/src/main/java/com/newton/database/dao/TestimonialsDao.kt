package com.newton.database.dao

import androidx.room.*
import com.newton.database.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface TestimonialsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestimonial(testimonial: TestimonialsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTestimonials(testimonials: List<TestimonialsEntity>)

    @Query("SELECT * FROM testimonials")
    fun getTestimonials(): Flow<List<TestimonialsEntity>>
}
