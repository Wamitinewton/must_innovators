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
