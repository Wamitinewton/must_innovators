package com.newton.core.domain.repositories

import com.newton.core.domain.models.aboutUs.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*

interface CommunityRepository {
    fun getCommunities(isRefreshing: Boolean): Flow<Resource<List<Community>>>

    suspend fun getCommunityById(communityId: Int): Resource<Community>

    suspend fun getClubBio(): Flow<Resource<ClubBioData>>
}
