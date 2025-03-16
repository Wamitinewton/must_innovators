package com.newton.communities.data.repository

import com.newton.core.data.mappers.toDomainList
import com.newton.core.data.remote.AboutClubService
import com.newton.core.domain.models.about_us.Community
import com.newton.core.domain.repositories.CommunityRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import com.newton.database.dao.CommunityDao
import com.newton.database.mappers.toDomain
import com.newton.database.mappers.toEntity
import com.newton.database.mappers.toTechStackEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val aboutUsApi: AboutClubService,
    private val dao: CommunityDao
) : CommunityRepository {

    override fun getCommunities(isRefreshing: Boolean): Flow<Resource<List<Community>>> = flow {
        if (isRefreshing) {
            emitAll(fetchRemoteCommunities())
        } else {
            val localCommunities = dao.getCommunitiesWithRelations().first()

            if (localCommunities.isNotEmpty()) {
                emit(Resource.Success(localCommunities.map { it.toDomain() }))
            } else {
                emitAll(fetchRemoteCommunities())
            }
        }
    }

    /**
     * Fetches communities from remote API and saves to database
     */
    private fun fetchRemoteCommunities(): Flow<Resource<List<Community>>> = safeApiCall {
        val response = aboutUsApi.getCommunities()

        if (response.status == "success") {
            val communities = response.data.toDomainList()
            saveCommunities(communities)
            communities
        } else if (response.data.results.isEmpty()) {
            throw Exception("No communities added. Try again later")
        } else {
            throw Exception("Failed to fetch communities: ${response.message}")
        }
    }

    override suspend fun getCommunityById(communityId: Int): Resource<Community> {
        return try {
            val community = dao.getCommunityWithRelationsById(communityId)
            if (community != null) {
                Resource.Success(community.toDomain())
            } else {
                Resource.Error("Community with ID $communityId not found")
            }
        } catch (e: Exception) {
            Resource.Error("Error retrieving community: ${e.message}")
        }
    }

    override suspend fun deleteCommunity(communityId: Int): Resource<Unit> {
        return try {
            dao.deleteCommunityById(communityId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error deleting community: ${e.message}")
        }
    }

    override suspend fun clearAllData(): Resource<Unit> {
        return try {
            dao.deleteAllCommunityData()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error clearing data: ${e.message}")
        }
    }

    /**
     * Helper method to store communities in the database
     */
    private suspend fun saveCommunities(communities: List<Community>) {
        try {
            dao.deleteAllCommunities()

            communities.forEach { community ->
                // Insert community
                dao.insertCommunity(community.toEntity())

                // Insert social media
                dao.insertSocialMediaList(community.socialMedia.map {
                    it.toEntity(community.id)
                })

                // Insert tech stack
                dao.insertTechStackList(community.techStack.map {
                    it.toTechStackEntity(community.id)
                })

                // Insert members
                dao.insertMembers(community.members.map {
                    it.toEntity(community.id)
                })

                // Insert sessions
                dao.insertSessions(community.sessions.map {
                    it.toEntity(community.id)
                })
            }
        } catch (e: Exception) {
            Timber.e("Error saving communities to database: ${e.message}")
            // Consider re-throwing the exception or returning a status
        }
    }
}