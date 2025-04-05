package com.newton.database.dao

import androidx.room.*
import com.newton.database.entities.*

@Dao
interface PartnersDao {
    @Upsert
    suspend fun insertPartners(partnersDataEntity: List<PartnersDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartner(partner: PartnersDataEntity)

    @Query("SELECT * FROM partners")
    suspend fun getPartners(): List<PartnersDataEntity>

    @Query("DELETE FROM partners")
    suspend fun deletePartners()
}
