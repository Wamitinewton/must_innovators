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

import com.newton.network.*
import com.newton.network.data.mappers.*
import com.newton.network.data.remote.*
import com.newton.network.domain.models.aboutUs.*
import com.newton.network.domain.repositories.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class CommunityRepositoryImpl
@Inject
constructor(
    private val communitiesApiService: CommunitiesApiService
) : CommunityRepository {
    override fun getCommunities(): Flow<Resource<List<Community>>> =
        fetchRemoteCommunities()

    /**
     * Fetches communities from remote API
     */
    private fun fetchRemoteCommunities(): Flow<Resource<List<Community>>> =
        safeApiCall {
            val response = communitiesApiService.getCommunities()
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
}
