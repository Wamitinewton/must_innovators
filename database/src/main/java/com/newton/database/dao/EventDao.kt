package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.newton.database.entities.EventEntity
import kotlinx.coroutines.flow.Flow

//@Dao
//interface EventDao {
//    @Query("SELECT * FROM events WHERE id = :id")
//    fun getEventById(id: Int): Flow<EventEntity>
//
//    @Query("SELECT * FROM events")
//     fun getAllEvents(): Flow<List<EventEntity>>
//
//    @Update
//    suspend fun updateEvent(event: EventEntity): Int
//
//    @Query("DELETE FROM events WHERE id = :id")
//    suspend fun deleteEvent(id: Int): Int
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertEvent(event: EventEntity): Long
//}