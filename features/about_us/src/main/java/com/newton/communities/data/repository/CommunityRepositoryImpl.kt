package com.newton.communities.data.repository

import com.newton.core.data.mappers.toDomainList
import com.newton.core.data.remote.AboutClubService
import com.newton.core.domain.models.about_us.ClubBioData
import com.newton.core.domain.models.about_us.Community
import com.newton.core.domain.repositories.CommunityRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import com.newton.database.dao.ClubBioDao
import com.newton.database.mappers.toDomain
import com.newton.database.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val aboutUsApi: AboutClubService,
    private val clubBioDao: ClubBioDao
) : CommunityRepository {

    override fun getCommunities(isRefreshing: Boolean): Flow<Resource<List<Community>>> =
        fetchRemoteCommunities()

    /**
     * Fetches communities from remote API
     */
    private fun fetchRemoteCommunities(): Flow<Resource<List<Community>>> = safeApiCall {
        val response = aboutUsApi.getCommunities()
            val communities = response.data.toDomainList()
            communities

    }

    override suspend fun getCommunityById(communityId: Int): Resource<Community> {
        return when (val result = fetchRemoteCommunities().first()) {
            is Resource.Success -> {
                val community = result.data?.find { it.id == communityId }
                if (community != null) {
                    Resource.Success(community)
                } else {
                    Resource.Error("Community with ID $communityId not found")
                }
            }
            is Resource.Error -> Resource.Error(result.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun getClubBio(): Flow<Resource<ClubBioData>> = flow {
        val cachedClub = clubBioDao.getClubBio()

        if (cachedClub != null) {
            emit(Resource.Success(cachedClub.toDomain()))
        }

        safeApiCall {
            aboutUsApi.getClubBio().data
        }.collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { clubBioData ->
                        updateLocalClub(clubBioData)
                    }
                    emit(result)
                }

                is Resource.Error -> {
                    if (cachedClub == null) {
                        emit(result)
                    }
                }

                is Resource.Loading -> {
                    if (result.isLoading && cachedClub == null) {
                        emit(result)
                    }
                }
            }

        }
    }

    private suspend fun updateLocalClub(clubBioData: ClubBioData) {
        try {
            clubBioDao.insertClubBio(clubBioData.toEntity())
        } catch (e: Exception) {

            Timber.e(e.message)
        }
    }

}