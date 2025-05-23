/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.events.data.paging

import androidx.paging.*
import androidx.room.*
import com.newton.database.dao.*
import com.newton.database.db.*
import com.newton.database.entities.*
import com.newton.database.mappers.*
import com.newton.network.data.mappers.*
import com.newton.network.data.remote.*
import retrofit2.*
import java.io.*
import javax.inject.*

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator
@Inject
constructor(
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
        val page =
            when (loadType) {
                LoadType.REFRESH -> {
                    PagingConstants.STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastPage =
                        eventDao.getLastPage()
                            ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val metadata = eventDao.getPaginationMetadata(lastPage)

                    if (metadata?.nextPageUrl == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    lastPage + 1
                }
            }

        val pageSize = state.config.pageSize.coerceAtMost(PagingConstants.NETWORK_PAGE_SIZE)

        return try {
            val response = api.getAllEvents(page = page, pageSize = pageSize)

            if (response.status != "success") {
                return MediatorResult.Error(Exception(response.message))
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    eventDao.clearEvents()
                    eventDao.clearPaginationMetadata()
                }

                val events =
                    response.data.results.map { event ->
                        event.toDomainEvent().toEntity(pageNumber = page)
                    }
                eventDao.insertEvents(events)

                val paginationMetadata =
                    EventPaginationMetadata(
                        pageNumber = page,
                        nextPageUrl = response.data.next,
                        timestamp = System.currentTimeMillis()
                    )
                eventDao.insertPaginationMetadata(paginationMetadata)
            }

            MediatorResult.Success(endOfPaginationReached = response.data.next == null)
        } catch (e: IOException) {
            return MediatorResult.Error(Exception("Could not reach server. Try Again later"))
        } catch (e: HttpException) {
            MediatorResult.Error(Exception("Server error occurred. Error code: ${e.code()}"))
        } catch (e: Exception) {
            MediatorResult.Error(Exception("An unexpected error occurred. Please try again."))
        }
    }
}
