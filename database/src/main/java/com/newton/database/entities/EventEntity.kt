package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val description: String,
    val image: String,
    val isVirtual: Boolean,
    val location: String,
    val name: String,
    val organizer: String,
    val title: String,
    val contactEmail: String
)
