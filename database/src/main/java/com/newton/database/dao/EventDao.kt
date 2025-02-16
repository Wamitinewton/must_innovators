package com.newton.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.EventEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Query("SELECT * FROM events ORDER BY id ASC")
    fun getEventsByPage(): PagingSource<Int, EventEntity>

    @Query("DELETE FROM events WHERE page = :page")
    suspend fun deleteByPage(page: Int)

    @Query("DELETE FROM events")
    suspend fun clearAll()

}
