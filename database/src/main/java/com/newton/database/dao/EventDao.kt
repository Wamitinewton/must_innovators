package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.newton.database.entities.EventEntity


@Dao
interface EventDao {
    @Upsert
    suspend fun insertEvent(user: EventEntity)
}