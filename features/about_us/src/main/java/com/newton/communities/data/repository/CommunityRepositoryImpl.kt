package com.newton.communities.data.repository

import com.newton.core.data.mappers.toDomainList
import com.newton.database.mappers.toEntity
import com.newton.database.mappers.toTechStackEntity
import com.newton.core.data.remote.AboutClubService
import com.newton.core.domain.repositories.CommunityRepository
import com.newton.core.domain.models.about_us.Community
import com.newton.core.utils.Resource
import com.newton.database.dao.CommunityDao
import com.newton.database.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val aboutUsApi: AboutClubService,
    private val dao: CommunityDao
) : CommunityRepository {

    override fun getCommunities(isRefreshing: Boolean): Flow<Resource<List<Community>>> = flow {
        emit(Resource.Loading(true))
        try {
            if (isRefreshing) {
                fetchRemoteCommunities()?.let { emit(it) }
            } else {
                val localCommunities = dao.getCommunitiesWithRelations()

                localCommunities.collect { communities ->
                    if (communities.isNotEmpty()) {
                        emit(Resource.Success(communities.map { it.toDomain() }))
                    } else {
                        fetchRemoteCommunities()?.let { emit(it) }
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error retrieving communities: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    /**
     * Fetches communities from remote API and saves to database
     */
    private suspend fun fetchRemoteCommunities(): Resource<List<Community>>? {
        return try {
            val response = aboutUsApi.getCommunities()

            if (response.status == "success") {
                val communities = response.data.toDomainList()
                saveCommunities(communities)
                Resource.Success(data = communities)
            } else if(response.data.results.isEmpty()) {
                Resource.Error("No communities added. Try again later")
            }
            else  {
                Resource.Error("Failed to fetch communities: ${response.message}")
            }
        } catch (e: HttpException) {
            Resource.Error("An HTTP error occurred: ${e.message ?: "Unknown HTTP error"}")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred: ${e.message ?: "Unknown error"}")
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
            Resource.Error("Error clearing data: ${e.message}")
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

                dao.insertCommunity(community.toEntity())

                /**
                 * Insert all related social media
                 */
                val socialMediaEntities = community.socialMedia.map { it.toEntity(community.id) }
                dao.insertSocialMediaList(socialMediaEntities)

                /**
                 * Insert Tech stack
                 */
                val techStackEntities = community.techStack.map { it.toTechStackEntity(community.id) }
                dao.insertTechStackList(techStackEntities)

                /**
                 * Insert Members
                 */
                val memberEntities = community.members.map { it.toEntity(community.id) }
                dao.insertMembers(memberEntities)

                /**
                 * Insert sessions
                 */
                val sessionEntities = community.sessions.map { it.toEntity(community.id) }
                dao.insertSessions(sessionEntities)
            }
        } catch (e: Exception) {

            Timber.e("Error saving communities to database: ${e.message}")
        }
    }


}