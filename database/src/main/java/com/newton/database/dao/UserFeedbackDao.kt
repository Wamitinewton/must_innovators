package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.CommunityEntity
import com.newton.database.entities.UserFeedbackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFeedbackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedbacks(feedbacks: List<UserFeedbackEntity>)

    @Query("SELECT * FROM user_feedbacks")
    fun getAllUserFeedbacks(): Flow<List<UserFeedbackEntity>>

    @Query("DELETE FROM user_feedbacks")
    fun deleteAllFeedbacks()

}