package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.newton.database.entities.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): EventEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: EventEntity)

    @Update
    fun updateEvent(event: EventEntity)

    @Query("DELETE FROM events WHERE id = :id")
    fun deleteEvent(id: Int)
}
