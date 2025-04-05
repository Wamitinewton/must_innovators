package com.newton.core.data.remote

import com.newton.core.data.response.aboutUs.*
import com.newton.core.domain.models.aboutUs.*
import retrofit2.http.*

interface AboutClubService {
    @GET(ApiEndpoints.GET_COMMUNITIES)
    suspend fun getCommunities(): CommunityApiResponse<CommunitiesResponse>

    @GET(ApiEndpoints.GET_EXECUTIVES)
    suspend fun getExecutives(): ExecutiveData

    @GET(ApiEndpoints.GET_CLUB_BIO)
    suspend fun getClubBio(): ClubBio
}
