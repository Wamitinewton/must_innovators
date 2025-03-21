package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.ClubBioEntity

@Dao
interface ClubBioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubBio(clubBioEntity: ClubBioEntity)

    @Query("SELECT * FROM club_bio")
    suspend fun getClubBio(): ClubBioEntity?
}