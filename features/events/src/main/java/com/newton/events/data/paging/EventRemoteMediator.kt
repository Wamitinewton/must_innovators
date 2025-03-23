package com.newton.events.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.newton.core.data.mappers.toDomainEvent
import com.newton.core.data.remote.EventService
import com.newton.database.dao.EventDao
import com.newton.database.db.AppDatabase
import com.newton.database.entities.EventEntity
import com.newton.database.mappers.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator @Inject constructor(
    private val api: EventService,
    private val db: AppDatabase,
    private val eventDao: EventDao
) : RemoteMediator<Int, EventEntity>() {

    override suspend fun initialize(): InitializeAction {
        if (eventDao.getEventCount() > 0) {
            return InitializeAction.SKIP_INITIAL_REFRESH
        }
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> PagingConstants.STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastPage = eventDao.getLastPage()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastPage + 1
            }
        }

        val pageSize = state.config.pageSize.coerceAtMost(PagingConstants.NETWORK_PAGE_SIZE)

        return try {
            val response = withContext(Dispatchers.IO) {
                api.getAllEvents(page = page, pageSize = pageSize)
            }

            if (response.status != "success") {
                return MediatorResult.Error(Exception(response.message))
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    eventDao.clearEvents()
                }

                val events = response.data.results.map { event ->
                    event.toDomainEvent().toEntity(pageNumber = page)
                }
                eventDao.insertEvents(events)

                eventDao.updatePageTimestamp(page, System.currentTimeMillis())
            }

            MediatorResult.Success(endOfPaginationReached = response.data.next == null)
        } catch (e: IOException) {
            val hasData = if (loadType == LoadType.REFRESH) {
                eventDao.getEventCount() > 0
            } else {
                true
            }

            if (hasData) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            MediatorResult.Error(Exception("Network connection failed. Please check your connection and try again."))
        } catch (e: HttpException) {
            MediatorResult.Error(Exception("Server error occurred. Error code: ${e.code()}"))
        } catch (e: Exception) {
            MediatorResult.Error(Exception("An unexpected error occurred. Please try again."))
        }
    }
}