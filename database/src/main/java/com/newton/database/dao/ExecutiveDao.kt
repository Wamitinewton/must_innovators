package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.ExecutiveEntity

@Dao
interface ExecutiveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExecutives(executives: List<ExecutiveEntity>)

    @Query("SELECT * FROM executives")
    suspend fun getExecutives(): List<ExecutiveEntity>

    @Query("DELETE FROM executives")
    suspend fun deleteAllExecutives()
}