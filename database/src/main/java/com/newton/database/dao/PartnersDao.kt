package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.PartnerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartnersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartner(partners: PartnerEntity)

    @Query("SELECT * FROM partners")
    fun getAllPartners(): Flow<List<PartnerEntity>>

    @Query("DELETE FROM partners")
    fun deleteAllPartners()
}