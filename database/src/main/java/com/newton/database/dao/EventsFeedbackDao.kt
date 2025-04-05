package com.newton.database.dao

import androidx.room.*
import com.newton.database.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface EventsFeedbackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventsFeedback(feedbacks: List<EventsFeedbackEntity>)

    @Query("SELECT * FROM events_feedback")
    fun getAllEventsFeedbacks(): Flow<List<EventsFeedbackEntity>>

    @Query("DELETE FROM events_feedback")
    fun deleteAllEventsFeedbacks()
}
