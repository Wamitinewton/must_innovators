package com.newton.events.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import com.newton.events.data.paging.EventPagingSource
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
) : EventRepository {
    override fun getPagedEvents(): Flow<PagingData<EventsData>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EventPagingSource(api) }
        ).flow
    }



    override suspend fun getEventById(id: Int): Flow<Resource<Event>> = flow {

    }



    override suspend fun updateEvent(id: Int, request: EventRequest): Flow<Resource<Event>> = flow {

    }

    override suspend fun deleteEvent(id: Int): Flow<Resource<String>> = flow {
        emit(Resource.Loading(true))
        try {


        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun createEvent(request: EventRequest): Flow<Resource<Event>> = flow {

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

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}

