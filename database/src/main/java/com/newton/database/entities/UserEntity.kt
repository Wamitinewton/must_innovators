package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
