package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.TestimonialsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestimonialsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertTestimonial(testimonial: TestimonialsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTestimonials(testimonials: List<TestimonialsEntity>)

    @Query("SELECT * FROM testimonials")
     fun getTestimonials(): Flow<List<TestimonialsEntity>>
}
