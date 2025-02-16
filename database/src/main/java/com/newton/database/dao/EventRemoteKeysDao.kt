package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.EventRemoteKeys

@Dao
interface EventRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<EventRemoteKeys>)

    @Query("SELECT * FROM event_remote_keys WHERE eventId = :eventId")
    suspend fun remoteKeysEventId(eventId: Int): EventRemoteKeys?

    @Query("DELETE FROM event_remote_keys")
    suspend fun clearRemoteKeys()

}