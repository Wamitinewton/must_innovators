package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.newton.database.entities.PartnersDataEntity
import com.newton.database.entities.UserFeedbackEntity


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