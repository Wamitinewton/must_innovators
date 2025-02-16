package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_remote_keys")
data class EventRemoteKeys(
    @PrimaryKey val eventId: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
