package com.newton.communities.data.repository

import com.newton.communities.data.mappers.toExecutiveDomain
import com.newton.communities.data.mappers.toExecutiveEntity
import com.newton.communities.data.remote.AboutUsApi
import com.newton.communities.domain.repository.ExecutiveRepository
import com.newton.core.domain.models.about_us.Executive
import com.newton.core.utils.Resource
import com.newton.database.dao.ExecutiveDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ExecutiveRepositoryImpl @Inject constructor(
    private val executiveApi: AboutUsApi,
    private val dao: ExecutiveDao
): ExecutiveRepository {

    override suspend fun getExecutives(): Flow<Resource<List<Executive>>> = flow {
        emit(Resource.Loading(true))
        try {
            val localExecutive = dao.getExecutives().map { it.toExecutiveDomain() }
            if (localExecutive.isNotEmpty()) {
                emit(Resource.Success(data = localExecutive))
            }
            val response = executiveApi.getExecutives()
            dao.insertExecutives(response.data.map { it.toExecutiveEntity() })
            val updatedExecutives = dao.getExecutives().map { it.toExecutiveDomain() }
            emit(Resource.Success(data = updatedExecutives))
        } catch (e: HttpException) {
            emit(Resource.Error("An HTTP error occurred: ${e.message ?: "Unknown HTTP error"}"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.message ?: "Unknown error"}"))
        }
    }
}