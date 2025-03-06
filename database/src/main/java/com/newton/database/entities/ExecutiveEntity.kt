package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "executives")
data class ExecutiveEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val position: String,
    val bio: String,
    val email: String,
)
