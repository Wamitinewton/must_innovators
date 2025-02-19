    package com.newton.events.data.paging

    import androidx.paging.PagingSource
    import androidx.paging.PagingState
    import com.newton.core.domain.models.event_models.EventsData
    import com.newton.core.utils.CustomErrorManager
    import com.newton.core.utils.toException
    import com.newton.events.data.mappers.EventMappers.toDomainEvent
    import com.newton.events.data.remote.EventApi

    class EventPagingSource(
        private val api: EventApi
    ) : PagingSource<Int, EventsData>() {

        override fun getRefreshKey(state: PagingState<Int, EventsData>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventsData> {
            return try {
                val currentPage = params.key ?: 1

                val response = api.getAllEvents(
                    page = currentPage,
                    pageSize = params.loadSize
                )

                if (response.status == "success") {
                    val events = response.data.results.map { it.toDomainEvent() }

                    val nextKey = if (response.data.next != null) currentPage + 1 else null
                    val prevKey = if (currentPage > 1) currentPage - 1 else null
                    LoadResult.Page(
                        data = events,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                } else {
                    LoadResult.Error(Exception(response.message))
                }
            } catch (e: Exception) {
                LoadResult.Error(CustomErrorManager.from(e).toException())
            }
        }
    }
