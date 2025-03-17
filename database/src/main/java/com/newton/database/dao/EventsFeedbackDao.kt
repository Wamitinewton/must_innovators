package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.EventsFeedbackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsFeedbackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventsFeedback(feedbacks: List<EventsFeedbackEntity>)

    @Query("SELECT * FROM events_feedback")
    fun getAllEventsFeedbacks(): Flow<List<EventsFeedbackEntity>>

    @Query("DELETE FROM events_feedback")
    fun deleteAllEventsFeedbacks()
}