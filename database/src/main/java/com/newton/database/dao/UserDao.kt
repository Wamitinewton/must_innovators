package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.newton.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM user")
    suspend fun deleteUser()

    @Query("SELECT * FROM user LIMIT 1")
    fun observeUser(): Flow<UserEntity?>

}