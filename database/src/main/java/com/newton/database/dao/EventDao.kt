/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.database.dao

import androidx.paging.*
import androidx.room.*
import com.newton.database.entities.*

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("SELECT * FROM events")
    suspend fun getListOfEvents(): List<EventEntity>

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

    @Query(
        """
        SELECT * FROM events 
        WHERE LOWER(name) LIKE LOWER(:query) 
        OR LOWER(description) LIKE LOWER(:query) 
        OR LOWER(location) LIKE LOWER(:query)
    """
    )
    suspend fun searchEvents(query: String): List<EventEntity>

    @Query("SELECT MAX(timestamp) FROM events WHERE pageNumber = :pageNumber")
    suspend fun getPageTimeStamp(pageNumber: Int): Long?

    @Query("UPDATE events SET timestamp = :timestamp WHERE pageNumber = :pageNumber")
    suspend fun updatePageTimestamp(
        pageNumber: Int,
        timestamp: Long
    )

    @Query("SELECT COUNT(*) FROM events")
    suspend fun getEventCount(): Int

    @Query("SELECT * FROM events ORDER BY date DESC LIMIT :count")
    suspend fun getLatestEvents(count: Int): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginationMetadata(metadata: EventPaginationMetadata)

    @Query("SELECT nextPageUrl FROM events_pagination_metadata WHERE pageNumber = :pageNumber")
    suspend fun getNextPageUrl(pageNumber: Int): String?

    @Query("DELETE FROM events_pagination_metadata")
    suspend fun clearPaginationMetadata()

    @Query("SELECT * FROM events_pagination_metadata WHERE pageNumber = :pageNumber")
    suspend fun getPaginationMetadata(pageNumber: Int): EventPaginationMetadata?
}
