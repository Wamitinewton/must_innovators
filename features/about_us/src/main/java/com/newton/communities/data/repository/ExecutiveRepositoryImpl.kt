package com.newton.communities.data.repository

import com.newton.core.data.remote.AboutClubService
import com.newton.core.domain.models.about_us.Executive
import com.newton.core.domain.repositories.ExecutiveRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExecutiveRepositoryImpl @Inject constructor(
    private val executiveApi: AboutClubService,
): ExecutiveRepository {

    override suspend fun getExecutives(): Flow<Resource<List<Executive>>> =
        fetchRemoteExecutives()

    private fun fetchRemoteExecutives(): Flow<Resource<List<Executive>>> = safeApiCall {
        val response = executiveApi.getExecutives().data
        response
    }
}