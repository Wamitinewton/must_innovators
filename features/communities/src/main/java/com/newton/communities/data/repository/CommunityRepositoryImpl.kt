/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.communities.data.repository

import com.newton.database.dao.*
import com.newton.database.mappers.*
import com.newton.network.*
import com.newton.network.data.mappers.*
import com.newton.network.data.remote.*
import com.newton.network.domain.models.aboutUs.*
import com.newton.network.domain.repositories.*
import kotlinx.coroutines.flow.*
import timber.log.*
import javax.inject.*

class CommunityRepositoryImpl
@Inject
constructor(
    private val aboutUsApi: AboutClubService,
    private val clubBioDao: ClubBioDao
) : CommunityRepository {
    override fun getCommunities(): Flow<Resource<List<Community>>> =
        fetchRemoteCommunities()

    /**
     * Fetches communities from remote API
     */
    private fun fetchRemoteCommunities(): Flow<Resource<List<Community>>> =
        safeApiCall {
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

    override suspend fun getClubBio(): Flow<Resource<ClubBioData>> =
        flow {
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
