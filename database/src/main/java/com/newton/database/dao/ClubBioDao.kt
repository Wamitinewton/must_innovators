package com.newton.database.dao

import androidx.room.*
import com.newton.database.entities.*

@Dao
interface ClubBioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubBio(clubBioEntity: ClubBioEntity)

    @Query("SELECT * FROM club_bio")
    suspend fun getClubBio(): ClubBioEntity?
}
