package com.newton.database.entities

import androidx.room.*

@Entity(tableName = "testimonials")
data class TestimonialsEntity(
    @PrimaryKey
    val id: Int,
    val content: String,
    val createdAt: String,
    val rating: Int,
    val status: String,
    val user: Int,
    val userName: String
)
