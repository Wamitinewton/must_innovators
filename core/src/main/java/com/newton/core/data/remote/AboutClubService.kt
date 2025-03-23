package com.newton.core.data.remote

import com.newton.core.data.response.about_us.CommunitiesResponse
import com.newton.core.data.response.about_us.CommunityApiResponse
import com.newton.core.domain.models.about_us.ClubBio
import com.newton.core.domain.models.about_us.ExecutiveData
import retrofit2.http.GET

interface AboutClubService {
    @GET(ApiEndpoints.GET_COMMUNITIES)
    suspend fun getCommunities(): CommunityApiResponse<CommunitiesResponse>

    @GET(ApiEndpoints.GET_EXECUTIVES)
    suspend fun getExecutives(): ExecutiveData

    @GET(ApiEndpoints.GET_CLUB_BIO)
    suspend fun getClubBio(): ClubBio
}