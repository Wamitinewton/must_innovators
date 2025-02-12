package com.newton.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newton.database.converters.DataConverters
import com.newton.database.dao.EventDao
import com.newton.database.entities.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 0,
    exportSchema = true
)
@TypeConverters(DataConverters::class)
abstract class EventDatabase : RoomDatabase(){
    abstract fun eventDao(): EventDao
}