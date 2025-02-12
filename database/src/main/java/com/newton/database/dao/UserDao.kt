package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.newton.database.entities.UserEntity

@Dao
interface UserDao {
    @Upsert
    suspend fun insertUser(user: UserEntity)
}