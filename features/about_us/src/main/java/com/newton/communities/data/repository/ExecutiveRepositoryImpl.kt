package com.newton.communities.data.repository

import com.newton.database.mappers.toExecutiveDomain
import com.newton.database.mappers.toExecutiveEntity
import com.newton.core.data.remote.AboutClubService
import com.newton.core.domain.repositories.ExecutiveRepository
import com.newton.core.domain.models.about_us.Executive
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import com.newton.database.dao.ExecutiveDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject

class ExecutiveRepositoryImpl @Inject constructor(
    private val executiveApi: AboutClubService,
    private val dao: ExecutiveDao
): ExecutiveRepository {

    override suspend fun getExecutives(): Flow<Resource<List<Executive>>> = flow {
        try {
            val localExecutives = dao.getExecutives().map { it.toExecutiveDomain() }
            if (localExecutives.isNotEmpty()) {
                emit(Resource.Success(data = localExecutives))
            }
        } catch (e: Exception) {
            // If local data fetch fails, just continue to remote fetch
            // We don't emit an error here as we'll try the remote source
        }

        emitAll(fetchRemoteExecutives())
    }

    private fun fetchRemoteExecutives(): Flow<Resource<List<Executive>>> = safeApiCall {
        val response = executiveApi.getExecutives()
        dao.insertExecutives(response.data.map { it.toExecutiveEntity() })

        dao.getExecutives().map { it.toExecutiveDomain() }
    }
}