package com.newton.communities.data.remote

import com.newton.communities.data.response.CommunitiesResponse
import com.newton.communities.data.response.CommunityApiResponse
import com.newton.core.domain.models.about_us.ExecutiveData
import retrofit2.http.GET

interface AboutUsApi {
    @GET(AboutUsEndpoints.GET_COMMUNITIES)
    suspend fun getCommunities(): CommunityApiResponse<CommunitiesResponse>

    @GET(AboutUsEndpoints.GET_EXECUTIVES)
    suspend fun getExecutives(): ExecutiveData
}