package com.newton.database.dao

import androidx.room.*
import com.newton.database.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface UserFeedbackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedbacks(feedbacks: List<UserFeedbackEntity>)

    @Query("SELECT * FROM user_feedbacks")
    fun getAllUserFeedbacks(): Flow<List<UserFeedbackEntity>>

    @Query("DELETE FROM user_feedbacks")
    fun deleteAllFeedbacks()
}
