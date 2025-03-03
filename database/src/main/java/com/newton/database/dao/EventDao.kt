package com.newton.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.newton.database.entities.EventEntity

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Upsert
    suspend fun insertEvent(user: EventEntity)

    @Query("SELECT * FROM events ORDER BY pageNumber ASC, id ASC")
    fun getPagedEvents(): PagingSource<Int, EventEntity>

    @Query("SELECT MAX(pageNumber) FROM events")
    suspend fun getLastPage(): Int?

    @Query("DELETE FROM events WHERE pageNumber = :pageNumber")
    suspend fun clearPage(pageNumber: Int)

    @Query("DELETE FROM events")
    suspend fun clearEvents()

    @Query("""
        SELECT * FROM events 
        WHERE LOWER(name) LIKE LOWER(:query) 
        OR LOWER(description) LIKE LOWER(:query) 
        OR LOWER(location) LIKE LOWER(:query)
    """)
    suspend fun searchEvents(query: String): List<EventEntity>

    @Query("SELECT MAX(timestamp) FROM events WHERE pageNumber = :pageNumber")
    suspend fun getPageTimeStamp(pageNumber: Int): Long?

    @Query("SELECT * FROM events ORDER BY date DESC LIMIT :count")
    suspend fun getLatestEvents(count: Int): List<EventEntity>
}