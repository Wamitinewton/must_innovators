package com.newton.communities.domain.repository

import com.newton.core.domain.models.about_us.Community
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun getCommunities(isRefreshing: Boolean): Flow<Resource<List<Community>>>

    suspend fun getCommunityById(communityId: Int): Resource<Community>

    suspend fun deleteCommunity(communityId: Int): Resource<Unit>

    suspend fun clearAllData(): Resource<Unit>
}