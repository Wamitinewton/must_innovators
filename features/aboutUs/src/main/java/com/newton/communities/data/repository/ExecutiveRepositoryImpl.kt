package com.newton.communities.data.repository

import com.newton.core.data.remote.*
import com.newton.core.domain.models.aboutUs.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class ExecutiveRepositoryImpl
@Inject
constructor(
    private val executiveApi: AboutClubService
) : ExecutiveRepository {
    override suspend fun getExecutives(): Flow<Resource<List<Executive>>> = fetchRemoteExecutives()

    private fun fetchRemoteExecutives(): Flow<Resource<List<Executive>>> =
        safeApiCall {
            val response = executiveApi.getExecutives().data
            response
        }
}
