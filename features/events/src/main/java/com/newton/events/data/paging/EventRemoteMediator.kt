    package com.newton.events.data.paging

    import androidx.paging.ExperimentalPagingApi
    import androidx.paging.LoadType
    import androidx.paging.PagingState
    import androidx.paging.RemoteMediator
    import androidx.room.withTransaction
    import com.newton.core.data.mappers.toDomainEvent
    import com.newton.core.network.NetworkConfiguration
    import com.newton.core.network.NetworkStatus
    import com.newton.core.utils.CustomErrorManager
    import com.newton.core.utils.toException
    import com.newton.database.db.AppDatabase
    import com.newton.database.entities.EventEntity
    import com.newton.core.data.remote.EventApi
    import com.newton.database.mappers.toEntity
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.coroutineScope
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.withContext
    import retrofit2.HttpException
    import java.io.IOException
    import java.util.concurrent.TimeUnit
    import javax.inject.Inject

    @OptIn(ExperimentalPagingApi::class)
    class EventRemoteMediator @Inject constructor(
        private val api: EventApi,
        private val db: AppDatabase,
        private val networkConfiguration: NetworkConfiguration
    ) : RemoteMediator<Int, EventEntity>() {
        private val eventDao = db.eventDao

        /**
         * This is an initiate function override to initiate refreshing of events
         * In case The cache has been timed out
         * This is great to ensure user is up to date with the latest events
         */

        override suspend fun initialize(): InitializeAction {
            val lastUpdateTimeStamp = eventDao.getPageTimeStamp(PagingConstants.STARTING_PAGE_INDEX)
                ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

            val cacheTimeout = TimeUnit.HOURS.toMillis(PagingConstants.CACHE_TIMEOUT_HOURS.toLong())
            return if (System.currentTimeMillis() - lastUpdateTimeStamp >= cacheTimeout) {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            } else {
                InitializeAction.SKIP_INITIAL_REFRESH
            }
        }

        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, EventEntity>
        ): MediatorResult {
            return try {
                val networkStatus = networkConfiguration.observeNetworkStatus().first()
                if (networkStatus != NetworkStatus.Available) {
                    return if (eventDao.getLastPage() != null) {
                        MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        MediatorResult.Error(IOException(networkConfiguration.getNetworkErrorMessage()))
                    }
                }

                val page = when (loadType) {
                    /**
                     * In case of refresh we start loading from initial page of data
                     */
                    LoadType.REFRESH -> PagingConstants.STARTING_PAGE_INDEX
                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                    LoadType.APPEND -> {
                        val lastPage = eventDao.getLastPage() ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                        lastPage + 1
                    }
                }
                val apiPageSize = state.config.pageSize.coerceAtMost(PagingConstants.NETWORK_PAGE_SIZE)

                coroutineScope {
                    val response = withContext(Dispatchers.IO) {
                        api.getAllEvents(
                            page = page,
                            pageSize = apiPageSize
                        )
                    }

                    if (response.status == "success") {
                        db.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                eventDao.clearEvents()
                            }
                            val events = response.data.results.map { event ->
                                event.toDomainEvent().toEntity(pageNumber = page)
                            }

                            eventDao.insertEvents(events)
                        }

                        MediatorResult.Success(
                            endOfPaginationReached = response.data.next == null
                        )
                    } else {
                        throw Exception(response.message)
                    }
                }
            } catch (e: Exception) {
                MediatorResult.Error(
                    when(e) {
                        is IOException -> Exception(networkConfiguration.getNetworkErrorMessage())
                        is HttpException -> Exception("Server error occurred. Try again later")
                        else -> CustomErrorManager.from(e).toException()
                    }
                )
            }
        }

    }
