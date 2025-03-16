package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.newton.database.entities.PartnersDataEntity


@Dao
interface PartnersDao {

    @Upsert
    suspend fun insertPartners(partnersDataEntity: List<PartnersDataEntity>)

    @Query("SELECT * FROM partners")
    suspend fun getPartners(): List<PartnersDataEntity>

    @Query("DELETE FROM partners")
    suspend fun deletePartners()
}