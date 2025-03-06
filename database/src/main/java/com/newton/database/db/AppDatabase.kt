package com.newton.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newton.database.converters.DataConverters
import com.newton.database.dao.CommunityDao
import com.newton.database.dao.EventDao
import com.newton.database.dao.ExecutiveDao
import com.newton.database.dao.TicketDao
import com.newton.database.dao.UserDao
import com.newton.database.entities.CommunityEntity
import com.newton.database.entities.EventEntity
import com.newton.database.entities.ExecutiveEntity
import com.newton.database.entities.MemberEntity
import com.newton.database.entities.SessionEntity
import com.newton.database.entities.SocialMediaEntity
import com.newton.database.entities.TechStackEntity
import com.newton.database.entities.TicketsEntity
import com.newton.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        EventEntity::class,
        TicketsEntity::class,
        CommunityEntity::class,
        SocialMediaEntity::class,
        TechStackEntity::class,
        MemberEntity::class,
        SessionEntity::class,
    ExecutiveEntity::class],

    version = 17,
    exportSchema = false
)

@TypeConverters(DataConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val eventDao: EventDao
    abstract val ticketDao: TicketDao
    abstract val communityDao: CommunityDao
    abstract val executiveDao: ExecutiveDao
}