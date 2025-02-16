package com.newton.events.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil3.network.HttpException
import com.newton.database.dao.EventDao
import com.newton.database.dao.EventRemoteKeysDao
import com.newton.database.db.AppDatabase
import com.newton.database.entities.EventEntity
import com.newton.database.entities.EventRemoteKeys
import com.newton.events.data.mappers.EventMappers.toDomainEvent
import com.newton.events.data.mappers.EventMappers.toEventEntity
import com.newton.events.data.remote.EventApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator @Inject constructor(
    private val eventDb: AppDatabase,
    private val eventApi: EventApi,
    private val eventDao: EventDao,
    private val remoteKeysDao: EventRemoteKeysDao
) : RemoteMediator<Int, EventEntity>() {

    private val STARTING_PAGE_INDEX = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        val page = when(val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        return try {
            val response = eventApi.getAllEvents(page = page, pageSize = state.config.pageSize)
            val endOfList = response.data.results.isEmpty()
            if (response.status == "success") {
                eventDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        remoteKeysDao.clearRemoteKeys()
                        eventDao.clearAll()
                    }

                    val nextPage = if (endOfList) null else page + 1
                    val prevPage = if (page == 1) null else page - 1
                    remoteKeysDao.insertAll(
                        response.data.results.map {
                            EventRemoteKeys(
                                eventId = it.id,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }
                    )

                    eventDao.insertAll(
                        response.data.results.map {
                            it.toDomainEvent().toEventEntity(page)
                        }
                    )

                }
                MediatorResult.Success(endOfPaginationReached = response.data.next == null)
            } else {
                MediatorResult.Error(Exception(response.message))
            }
        }catch (e: IOException){
            return   MediatorResult.Error(e)
        }catch (e: HttpException){
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getKeyPageData(loadType: LoadType,state: PagingState<Int, EventEntity>) : Any{
        return when(loadType){
            LoadType.REFRESH->{
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextPage?.minus(1)?:STARTING_PAGE_INDEX
            }
            LoadType.PREPEND->{
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevPage ?:MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND->{
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextPage ?:MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, EventEntity>) : EventRemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { eventId -> remoteKeysDao.remoteKeysEventId(eventId.id)}
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, EventEntity>) : EventRemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .lastOrNull{it.data.isNotEmpty()}
                ?.data?.lastOrNull()
                ?.let { eventId -> remoteKeysDao.remoteKeysEventId(eventId.id) }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, EventEntity>) : EventRemoteKeys? {
        return withContext(Dispatchers.IO){
            state.anchorPosition?.let { position->
                state.closestItemToPosition(position)?.id?.let {repId->
                    remoteKeysDao.remoteKeysEventId(repId)
                }
            }
        }
    }

}