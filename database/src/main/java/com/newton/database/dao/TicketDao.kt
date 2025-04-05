package com.newton.database.dao

import androidx.room.*
import com.newton.database.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickets(tickets: List<TicketsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketsEntity)

    @Query("SELECT * FROM eventTickets ORDER BY registrationTimestamp DESC")
    fun getAllTickets(): Flow<List<TicketsEntity>>

    @Query("SELECT * FROM eventTickets WHERE event = :eventId LIMIT 1")
    fun getTicketByEventId(eventId: Int): Flow<TicketsEntity?>

    @Query("SELECT EXISTS (SELECT 1 FROM eventTickets WHERE event = :eventId LIMIT 1)")
    suspend fun isUserRegisteredForEvent(eventId: Int): Boolean

    @Query("DELETE FROM eventTickets")
    suspend fun clearAllTickets()
}
