package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newton.database.entities.CommunityEntity
import androidx.room.Transaction
import com.newton.database.entities.CommunityWithRelations
import com.newton.database.entities.MemberEntity
import com.newton.database.entities.SessionEntity
import com.newton.database.entities.SocialMediaEntity
import com.newton.database.entities.TechStackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunity(community: CommunityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunities(communities: List<CommunityEntity>)

    @Query("SELECT * FROM communities")
    fun getAllCommunities(): Flow<List<CommunityEntity>>

    @Query("SELECT * FROM communities WHERE id = :communityId")
    suspend fun getCommunityById(communityId: Int): CommunityEntity?

    @Query("DELETE FROM communities")
    suspend fun deleteAllCommunities()

    @Query("DELETE FROM communities WHERE id = :communityId")
    suspend fun deleteCommunityById(communityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSocialMedia(socialMedia: SocialMediaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSocialMediaList(socialMediaList: List<SocialMediaEntity>)

    @Query("SELECT * FROM social_media WHERE communityId = :communityId")
    suspend fun getSocialMediaForCommunity(communityId: Int): List<SocialMediaEntity>

    @Query("DELETE FROM social_media WHERE communityId = :communityId")
    suspend fun deleteSocialMediaForCommunity(communityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechStack(techStack: TechStackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechStackList(techStackList: List<TechStackEntity>)

    @Query("SELECT * FROM tech_stack WHERE communityId = :communityId")
    suspend fun getTechStackForCommunity(communityId: Int): List<TechStackEntity>

    @Query("DELETE FROM tech_stack WHERE communityId = :communityId")
    suspend fun deleteTechStackForCommunity(communityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: MemberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(members: List<MemberEntity>)

    @Query("SELECT * FROM members WHERE communityId = :communityId")
    suspend fun getMembersForCommunity(communityId: Int): List<MemberEntity>

    @Query("DELETE FROM members WHERE communityId = :communityId")
    suspend fun deleteMembersForCommunity(communityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessions(sessions: List<SessionEntity>)

    @Query("SELECT * FROM sessions WHERE communityId = :communityId")
    suspend fun getSessionsForCommunity(communityId: Int): List<SessionEntity>

    @Query("DELETE FROM sessions WHERE communityId = :communityId")
    suspend fun deleteSessionsForCommunity(communityId: Int)

    @Transaction
    @Query("SELECT * FROM communities")
    fun getCommunitiesWithRelations(): Flow<List<CommunityWithRelations>>

    @Transaction
    @Query("SELECT * FROM communities WHERE id = :communityId")
    suspend fun getCommunityWithRelationsById(communityId: Int): CommunityWithRelations?

    @Transaction
    suspend fun deleteCommunityWithRelations(communityId: Int) {
        deleteSocialMediaForCommunity(communityId)
        deleteTechStackForCommunity(communityId)
        deleteMembersForCommunity(communityId)
        deleteSessionsForCommunity(communityId)
        deleteCommunityById(communityId)
    }

    @Transaction
    suspend fun deleteAllCommunityData() {
        deleteAllCommunities()
        deleteAllSocialMedia()
        deleteAllTechStack()
        deleteAllMembers()
        deleteAllSessions()
    }

    @Query("DELETE FROM social_media")
    suspend fun deleteAllSocialMedia()

    @Query("DELETE FROM tech_stack")
    suspend fun deleteAllTechStack()

    @Query("DELETE FROM members")
    suspend fun deleteAllMembers()

    @Query("DELETE FROM sessions")
    suspend fun deleteAllSessions()
}