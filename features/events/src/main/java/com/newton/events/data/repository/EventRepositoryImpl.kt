package com.newton.events.data.repository

import com.newton.core.utils.Resource
import com.newton.database.dao.EventDao
import com.newton.events.data.mappers.toDomainEvent
import com.newton.events.data.mappers.toEventEntity
import com.newton.events.data.mappers.toEventList
import com.newton.events.data.mappers.toEventModel
import com.newton.events.data.mappers.toListOfEvent
import com.newton.events.data.remote.EventApi
import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRequest
import com.newton.events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: EventApi,
    private val dao: EventDao,
) : EventRepository {
    override fun refreshEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = api.getAllEvents()
            emit(Resource.Loading(false))
            if (response.status == "success") {
                emit(Resource.Success(response.data.results.toListOfEvent()))
                response.data.results.map { eventData ->
                    dao.insertEvent(eventData.toEventEntity())
                }
            } else {
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    override suspend fun getEventById(id: Int): Flow<Resource<Event>> = flow {
        emit(Resource.Loading(true))
        try {
            val cachedEvent = dao.getEventById(id)
            if (cachedEvent.equals(null)) {
                val response = api.getEventById(id)
                emit(Resource.Loading(false))
                if (response.status == "success") {
                    emit(Resource.Success(response.data.toDomainEvent()))
                    dao.insertEvent(response.data.toEventEntity())
                } else {
                    emit(Resource.Error(response.message))
                }
            } else {
                emit(Resource.Loading(false))
                emit(Resource.Success(cachedEvent.toEventModel()))
            }

        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    override fun getAllEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading(true))
        try {
            val cachedEvent = dao.getAllEvents()
            if (cachedEvent.isEmpty()) {
                val response = api.getAllEvents()
                emit(Resource.Loading(false))
                if (response.status == "success") {
                    emit(Resource.Success(response.data.results.toListOfEvent()))
                    response.data.results.map { eventData ->
                        dao.insertEvent(eventData.toEventEntity())
                    }
                } else {
                    emit(Resource.Error(response.message))
                }
            } else {
                emit(Resource.Loading(false))
                emit(Resource.Success(cachedEvent.toEventList()))
            }

        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    override suspend fun updateEvent(id: Int, request: EventRequest): Flow<Resource<Event>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = api.updateEvent(id, request)
            emit(Resource.Loading(false))
            if (response.status == "success") {
                emit(Resource.Success(response.data.toDomainEvent()))
                dao.updateEvent(response.data.toEventEntity())
            } else {
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    override suspend fun deleteEvent(id: Int): Flow<Resource<String>> = flow {
        emit(Resource.Loading(true))
        try {

            val response = api.deleteEvent(id)
            if (response.status == "success") {
                emit(Resource.Success(response.message))
                dao.deleteEvent(id)
            } else {
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun createEvent(request: EventRequest): Flow<Resource<Event>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = api.createEvent(request)
            emit(Resource.Loading(false))
            if (response.status == "success") {
                emit(Resource.Success(response.data.toDomainEvent()))
                dao.insertEvent(response.data.toEventEntity())
            } else {
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    private fun <T> handleError(exception: Throwable): Resource<T> {
        return when (exception) {

            is IOException -> Resource.Error(
                "IO error: ${exception.message ?: "Unknown IO error"}"
            )

            else -> Resource.Error(
                exception.message ?: "Unknown error occurred"
            )
        }
    }
}
