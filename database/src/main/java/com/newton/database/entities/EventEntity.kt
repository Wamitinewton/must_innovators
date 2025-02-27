package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val name: String,
    val category: String,
    val description: String,
    val date: String,
    val location: String,
    val organizer: String,
    val contactEmail: String,
    val isVirtual: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val pageNumber: Int? = null,
)
